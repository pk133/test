package org.praveen.DAO;

import java.io.IOException;

import java.util.*;

import org.apache.http.HttpHost;

import org.elasticsearch.ElasticsearchException;

import org.elasticsearch.action.delete.*;
import org.elasticsearch.action.get.*;
import org.elasticsearch.action.index.*;
import org.elasticsearch.action.search.*;
import org.elasticsearch.action.update.*;

import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

import org.elasticsearch.common.xcontent.XContentType;

import org.elasticsearch.search.SearchHit;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.praveen.bean.Car;

public class DataBase {
	private static final String HOST = "localhost";
    private static final int PORT_ONE = 9200;
    private static final int PORT_TWO = 9201;
    private static final String SCHEME = "http";

    private static RestHighLevelClient restHighLevelClient;
    private static ObjectMapper objectMapper = new ObjectMapper();

    private static final String INDEX = "car-crud";
    private static final String TYPE = "cars";
    
    public static synchronized RestHighLevelClient makeConnection() {
    	System.out.println("connecting ...");
        if(restHighLevelClient == null) {
            restHighLevelClient = new RestHighLevelClient(
                    RestClient.builder(
                            new HttpHost(HOST, PORT_ONE, SCHEME),
                            new HttpHost(HOST, PORT_TWO, SCHEME)));
        }

        return restHighLevelClient;
    }
    public static synchronized void closeConnection() throws IOException {
        restHighLevelClient.close();
        restHighLevelClient = null;
    }
    public static Car addCar(Car car){
    	System.out.println("creating......");
    	Map<String , Object> record = new HashMap<String , Object>();
    	car.setId(new Random().nextInt(100000-10)+10);
    	record.put("id" , car.getId());
    	record.put("brand" , car.getBrand());
    	record.put("series" , car.getSeries());
    	record.put("year" , car.getYear());
    	
    	IndexRequest indexRequest = new IndexRequest(INDEX, TYPE, String.valueOf(car.getId()))
                .source(record);
        try {
            restHighLevelClient.index(indexRequest);
        } catch(ElasticsearchException e) {
            e.getDetailedMessage();
        } catch (java.io.IOException ex){
            ex.getLocalizedMessage();
        }
    	
    	return car;
    }
    public static Car editCar(String id, Car car) {
    	System.out.println("updating......");
    	UpdateRequest updateRequest = new UpdateRequest(INDEX, TYPE, id)
                .fetchSource(true);    // Fetch Object after its update
        try {
            String postJson = objectMapper.writeValueAsString(car);
            updateRequest.doc(postJson, XContentType.JSON);
            UpdateResponse updateResponse = restHighLevelClient.update(updateRequest);
            return objectMapper.convertValue(updateResponse.getGetResult().sourceAsMap(), Car.class);
        }catch (JsonProcessingException e){
            e.getMessage();
        } catch (java.io.IOException e){
            e.getLocalizedMessage();
        }
        System.out.println("Unable to update person");
        return null;
    }
    
    public static Car getCar(String id) {
    	System.out.println("getting......");
    	GetRequest getPersonRequest = new GetRequest(INDEX, TYPE, id);
        GetResponse getResponse = null;
        try {
            getResponse = restHighLevelClient.get(getPersonRequest);
        } catch (java.io.IOException e){
            e.getLocalizedMessage();
        }
        return getResponse != null ?
                objectMapper.convertValue(getResponse.getSourceAsMap(), Car.class) : null;
    	
    }
    
    public static List<Car> getAllCars() throws IOException{
    	System.out.println("getting......");
    	SearchRequest searchRequest = new SearchRequest(INDEX);
    	SearchResponse searchResponse = restHighLevelClient.search(searchRequest);
    	SearchHit[] hits = searchResponse.getHits().getHits();
    	List<Car> cars=new ArrayList<>();
    	for(SearchHit h:hits) {
    		if(h!=null && TYPE.equals(h.getType())) {
    			cars.add(getCar(String.valueOf(h.getId())));
    		}
    	}
    	return cars;
    }
	
    public static void deleteCar(String id) {
    	System.out.println("deleting......");
    	DeleteRequest deleteRequest = new DeleteRequest(INDEX, TYPE, id);
        try {
            restHighLevelClient.delete(deleteRequest);
            
        } catch (java.io.IOException e){
            e.getLocalizedMessage();
        }
    }
    
}
