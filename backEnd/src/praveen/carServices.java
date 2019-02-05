package praveen;
import java.util.Collection;

import org.apache.struts2.rest.DefaultHttpHeaders;
import org.apache.struts2.rest.HttpHeaders;

import com.opensymphony.xwork2.ModelDriven;
import praveen.model.Car;

public class carServices implements ModelDriven<Object> {

	private Car model = new Car();
    private String car;
    private Collection<Car> list;
    private DAO db = new DAO();
    private String id;

    
    public HttpHeaders show() {
        return new DefaultHttpHeaders("show");
    }

    
    public HttpHeaders index() {
    	
    	list = db.getAllCar();
        return new DefaultHttpHeaders("index")
            .disableCaching();
    }
    
    public HttpHeaders create() {
    	model = db.addCar(model);
        return new DefaultHttpHeaders("success")
            .setLocationId(model.getId());
    }
    
    public String destroy() {
    	  
		db.deleteCar(Integer.parseInt(id));
    	  return "success";
    }
    public String update() {
        db.editCar(model, Integer.parseInt(id));
        
        return "success";
    }
    
    
    public void setId(String id) {
        if (id != null) {
            this.model = db.getCar(Integer.parseInt(id));
        }
        this.id = id;
    }
    public String getId() {
    	return this.id;
    }
    public Object getModel() {
    	
        return (list != null ? list : model);
    }

	public String getCar() {
		return car;
	}

	public void setCar(String car) {
		this.car = car;
	}
}
