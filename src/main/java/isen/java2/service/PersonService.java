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
		stmt.executeUpdate("INSERT INTO person(idperson,lastname, firstname, nickname, phone_number, address, email_address,birth_date)  VALUES (1, 'ZHAO', 'Yudong', 'Dong', 659783877, 'FI 311', 'yudong.zhao@isen.yncrea.fr','1995-04-05')");
		stmt.executeUpdate("INSERT INTO person(idperson,lastname, firstname, nickname, phone_number, address, email_address,birth_date)  VALUES (2, 'YANG', 'Ziheng', 'Heng', 666821026, 'FI 312', 'ziheng.yang@isen.yncrea.fr','1995-11-14')");
		stmt.executeUpdate("INSERT INTO person(idperson,lastname, firstname, nickname, phone_number, address, email_address,birth_date)  VALUES (3, 'FENG', 'Peng', 'Peng', 666821026, 'FI 220', 'peng.feng@isen.yncrea.fr','1993-08-31')");
		stmt.executeUpdate("INSERT INTO person(idperson,lastname, firstname, nickname, phone_number, address, email_address,birth_date)  VALUES (4, 'Nom', 'Prenom', 'Nickname', 0, 'Address', 'email@isen.yncrea.fr','2000-01-01')");
		stmt.executeUpdate("INSERT INTO person(idperson,lastname, firstname, nickname, phone_number, address, email_address,birth_date)  VALUES (5, 'Nom', 'Prenom', 'Nickname', 0, 'Address', 'email@isen.yncrea.fr','2000-01-01')");	
		stmt.executeUpdate("INSERT INTO person(idperson,lastname, firstname, nickname, phone_number, address, email_address,birth_date)  VALUES (6, 'Nom', 'Prenom', 'Nickname', 0, 'Address', 'email@isen.yncrea.fr','2000-01-01')");
		stmt.executeUpdate("INSERT INTO person(idperson,lastname, firstname, nickname, phone_number, address, email_address,birth_date)  VALUES (7, 'Nom', 'Prenom', 'Nickname', 0, 'Address', 'email@isen.yncrea.fr','2000-01-01')");	
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
		
//		persons.add(new Person(1, "ZHAO", "Yudong","Dong",659783877,"FI 311",
//				"yudong.zhao@isen.yncrea.fr",LocalDate.of(1995, Month.APRIL, 05)));		
//		persons.add(new Person(2, "YANG", "Ziheng","Heng",666821026,"FI 312",
//				"ziheng.yang@isen.yncrea.fr",LocalDate.of(1995, Month.NOVEMBER, 14)));
//		persons.add(new Person(3, "FENG", "Peng","Peng",666821026,"FI 220",
//				"peng.feng@isen.yncrea.fr",LocalDate.of(1993, Month.AUGUST, 31)));

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
