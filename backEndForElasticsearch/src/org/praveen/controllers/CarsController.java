package org.praveen.controllers;

import java.util.*;
import com.opensymphony.xwork2.ModelDriven;


import org.apache.struts2.rest.DefaultHttpHeaders;
import org.apache.struts2.rest.HttpHeaders;
import org.praveen.bean.Car;
import org.praveen.services.CarsServices;

public class CarsController implements ModelDriven<Object>{

	private List<Car> list;
	private Car model = new Car();
	private CarsServices car = new CarsServices();
	private String id;
	
	public HttpHeaders show() {
		return new DefaultHttpHeaders("show");
	}
	
	public HttpHeaders index() {
		list = car.getAllCar();
		return new DefaultHttpHeaders("index")
				.disableCaching();
	}
	
	public HttpHeaders create(){
		model = car.createCar(model);
		return new DefaultHttpHeaders("success")
				.setLocationId(model.getId());
	}
	
	public String destroy() {
		car.deleteCar(id);
		return "success";
	}
	
	public String update() {
		car.editCar(model, id);
		return "success";
	}

	public Object getModel() {
		return list!=null?list:model;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		if(id != null) {
			this.model = car.getCar(id);
		}
		this.id = id;
	}
}
