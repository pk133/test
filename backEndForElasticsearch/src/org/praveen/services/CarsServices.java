package org.praveen.services;

import java.io.IOException;
import java.util.List;
import org.elasticsearch.client.RestHighLevelClient;

import org.praveen.DAO.DataBase;
import org.praveen.bean.Car;

public class CarsServices {

	RestHighLevelClient restHighLevelClient;
	
	public Car createCar(Car car) {
		DataBase.makeConnection();
		return DataBase.addCar(car);
	}
	
	public void editCar(Car car, String id) {
		DataBase.makeConnection();
		DataBase.editCar(id, car);
		try {
			DataBase.closeConnection();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public List<Car> getAllCar(){
		DataBase.makeConnection();
		try {
			return DataBase.getAllCars();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public Car getCar(String id) {
		
		DataBase.makeConnection();
		return DataBase.getCar(id);
	}
	
	public void deleteCar(String id) {
		
		DataBase.makeConnection();
		DataBase.deleteCar(id);
	}
	
}
