package praveen;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import praveen.model.*;

public class DAO {
	private ResultSet rs;
	private Statement stmt;
	private Connection con;
	
	
	public void startDB() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/car","root","root");
			stmt = con.createStatement();
			System.out.println("database started");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<Car> getAllCar() {
		startDB();
		List<Car> cars = new ArrayList<>();
		try {
			rs=stmt.executeQuery("SELECT * FROM `car`.cardetails;");
			System.out.println("getting car details");
			while (rs.next()) {
				Car car = new Car();
				car.setId(rs.getInt("id"));
				car.setTitle(rs.getString("title"));
				car.setBrand(rs.getString("brand"));
				car.setSeries(rs.getString("model"));
				car.setYear(rs.getInt("date"));
				cars.add(car);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cars;
	}
	public Car getCar(int Id) {
		startDB();
		Car car = new Car();
		try {
			rs=stmt.executeQuery("SELECT * FROM `car`.cardetails where id ="+Id+";");
			System.out.println("getting car details");
			while (rs.next()) {
				
				car.setId(rs.getInt("id"));
				car.setTitle(rs.getString("title"));
				car.setBrand(rs.getString("brand"));
				car.setSeries(rs.getString("model"));
				car.setYear(rs.getInt("date"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return car;
	}
	public void editCar(Car car , int Id) {
		startDB();
		try {
			String sql = "UPDATE cardetails " + 
					"SET " + 
					"    title = '"+car.getTitle()+"'," + 
					"    model = '"+car.getSeries()+"'," + 
					"    brand = '"+car.getBrand()+"'," + 
					"    date = '"+car.getYear()+"' " + 
					"WHERE" + 
					"    id = "+Id+";";
			stmt.executeUpdate(sql);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public Car addCar(Car car) {	
		startDB();
		try {
			System.out.println("adding car details");
			String sql = "INSERT INTO `car`.`cardetails` (`title`, `model`, `brand`, `date`) VALUES ('"+car.getTitle()+"', '"+car.getSeries()+"', '"+car.getBrand()+"', '"+car.getYear()+"');";
			stmt.executeUpdate(sql);
			sql = "SELECT * FROM car.cardetails WHERE title = '"+car.getTitle()+"' AND model = '"+car.getSeries()+"' AND brand = '"+car.getBrand()+"' AND date = '"+car.getYear()+"';";
			rs = stmt.executeQuery(sql);
			car.setId(rs.getInt("id"));
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return car;
	}
	public void deleteCar(int id) {
		startDB();
		try {
			String sql = "DELETE FROM `car`.`cardetails` WHERE (`id` = '"+id+"');";
			stmt.executeUpdate(sql);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return the rs
	 */
	public ResultSet getRs() {
		return rs;
	}
	/**
	 * @param rs the rs to set
	 */
	public void setRs(ResultSet rs) {
		this.rs = rs;
	}
	/**
	 * @return the stmt
	 */
	public Statement getStmt() {
		return stmt;
	}
	/**
	 * @param stmt the stmt to set
	 */
	public void setStmt(Statement stmt) {
		this.stmt = stmt;
	}
	/**
	 * @return the con
	 */
	public Connection getCon() {
		return con;
	}
	/**
	 * @param con the con to set
	 */
	public void setCon(Connection con) {
		this.con = con;
	}
}
