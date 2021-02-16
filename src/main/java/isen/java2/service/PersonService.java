package isen.java2.service;

import java.sql.Connection;
import java.sql.Statement;
import java.util.List;

import isen.java2.db.daos.DataSourceFactory;
import isen.java2.db.daos.PersonDao;
import isen.java2.db.entities.Person;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class PersonService {

	private ObservableList<Person> persons;
	
	public static void initDatabase() throws Exception {
		Connection connection = DataSourceFactory.getDataSource().getConnection();
		Statement stmt = connection.createStatement();
		stmt.executeUpdate("DELETE FROM person");
		stmt.executeUpdate("INSERT INTO person(idperson,lastname, firstname, nickname, phone_number, address, email_address,birth_date,category)  VALUES (1, 'GENG', 'Mingxue', 'Mingxuexue', '761559161', '5 rue de FF', 'mingxue@gmail.com','1997-06-16','Famliy')");
		stmt.executeUpdate("INSERT INTO person(idperson,lastname, firstname, nickname, phone_number, address, email_address,birth_date,category)  VALUES (2, 'XU', 'Lu', 'Lulu','536784291', '10 rue de BB', 'lulu@gmail.com','1995-05-09','Friend')");
		stmt.executeUpdate("INSERT INTO person(idperson,lastname, firstname, nickname, phone_number, address, email_address,birth_date,category)  VALUES (3, 'ZHU', 'Haoran', 'Haoran','647386554', '10 rue de AA', 'haoran.zhu@isen.yncrea.fr.com','1995-01-01','Other')");
		System.out.println("Updating database");
		stmt.close();
		connection.close();		
	}

	private PersonService() {
		try {
			initDatabase();
		} catch (Exception e) {
			
			e.printStackTrace();
		}		
		List<Person> p =new PersonDao().listPersons();
		persons = FXCollections.observableArrayList();
		persons.addAll(p);

	}

	public static ObservableList<Person> getPersons() {
		return PersonServiceHolder.INSTANCE.persons;
	}

	public static void addQuestion(Person person) {
		PersonServiceHolder.INSTANCE.persons.add(person);
		new PersonDao().addPerson(person);
	}

	private static class PersonServiceHolder {
		private static final PersonService INSTANCE = new PersonService();
	}
	


}
