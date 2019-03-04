package isen.java2.db.daos;

import java.sql.Connection;
import isen.java2.db.entities.Person;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;



public class PersonDao {

	public List<Person> listPersons() {
		List<Person> persons = new ArrayList<>();
		String sqlQuery = "SELECT * FROM person";
		try(Connection connection = DataSourceFactory.getDataSource().getConnection()) {
			try(Statement statement = connection.createStatement()) {
				try (ResultSet resultSet = statement.executeQuery(sqlQuery)) {
					while (resultSet.next()) {

						Person person=extractSinglePerson(resultSet);
						persons.add(person);				
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return persons;
	}

	private Person extractSinglePerson(ResultSet resultSet) throws SQLException {		
		return new Person(
				resultSet.getInt("idperson"),
				resultSet.getString("lastname"),
				resultSet.getString("firstname"),
				resultSet.getString("nickname"),
				resultSet.getInt("phone_number"),
				resultSet.getString("address"),
				resultSet.getString("email_address"),                
				resultSet.getDate("birth_date").toLocalDate()
				);
	}
	
	public void delete(Person person) {
		String sqlQuery="delete from person where idperson=?";
		try (Connection connection = DataSourceFactory.getDataSource().getConnection()) {
			try (PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
				System.out.println("now deleting id："+person.getId());
				statement.setInt(1, person.getId());
				statement.executeUpdate();
				System.out.println("deleting person on database");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void save(Person person) {
		String sqlQuery="UPDATE person set lastname=?, firstname=?, nickname=?,"
				+ "phone_number=?, address=?, email_address=?, birth_date=?  WHERE idperson=?";
		try (Connection connection = DataSourceFactory.getDataSource().getConnection()) {
			try(PreparedStatement statement = connection.prepareStatement(sqlQuery)){
				statement.setString(1, person.getLastname());
				statement.setString(2, person.getFirstname());
				statement.setString(3, person.getNickname());
				statement.setInt(4, person.getPhone_number());
				statement.setString(5, person.getAddress());
				statement.setString(6, person.getEmail_address());
				statement.setDate(7, Date.valueOf(person.getBirth_date()));
				statement.setInt(8,person.getId());
				statement.executeUpdate();
				System.out.println("saving person on database");
			}			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void saveDate(Person person) {
		String sqlQuery="UPDATE person set birth_date=? WHERE idperson=?";
		try (Connection connection = DataSourceFactory.getDataSource().getConnection()) {
			try(PreparedStatement statement = connection.prepareStatement(sqlQuery)){				
				statement.setDate(1, Date.valueOf(person.getBirth_date()));
				statement.setInt(2,person.getId());
				statement.executeUpdate();
			}			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
	}
	
	public void addPerson(Person person) {
		List<Person> persons = new PersonDao().listPersons();
		int size=persons.size();
		int num=persons.get(size-1).getId()+1;
		String sqlQuery = "INSERT INTO person(idperson,lastname,firstname,nickname,phone_number,address,email_address,birth_date) VALUES(?,?,?,?,?,?,?,?)";
		try(Connection connection = DataSourceFactory.getDataSource().getConnection()) {
			try (PreparedStatement statement = connection.prepareStatement(sqlQuery,Statement.RETURN_GENERATED_KEYS)) {
				statement.setInt(1, num);
				statement.setString(2, person.getLastname());
				statement.setString(3, person.getFirstname());
				statement.setString(4, person.getNickname());
				statement.setInt(5, person.getPhone_number());
				statement.setString(6, person.getAddress());
				statement.setString(7, person.getEmail_address());
				statement.setDate(8, Date.valueOf(person.getBirth_date()));
				statement.executeUpdate();
				System.out.println("adding person on database");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}


	
}
