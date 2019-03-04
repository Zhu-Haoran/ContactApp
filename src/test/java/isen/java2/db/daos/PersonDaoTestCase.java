package isen.java2.db.daos;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import isen.java2.db.entities.Person;



public class PersonDaoTestCase {


	private PersonDao personDao=new PersonDao();

	@Before
	public void initDatabase() throws Exception {
		Connection connection = DataSourceFactory.getDataSource().getConnection();
		Statement stmt = connection.createStatement();
		stmt.executeUpdate("DELETE FROM person");
		stmt.executeUpdate("INSERT INTO person(idperson,lastname, firstname, nickname, phone_number, address, email_address,birth_date)  VALUES (1, 'ZHAO', 'Yudong', 'Dong', 659783877, 'FI 311', 'yudong.zhao@isen.yncrea.fr','1995-04-05')");
		stmt.executeUpdate("INSERT INTO person(idperson,lastname, firstname, nickname, phone_number, address, email_address,birth_date)  VALUES (2, 'YANG', 'Ziheng', 'Heng', 666821026, 'FI 312', 'ziheng.yang@isen.yncrea.fr','1995-11-14')");
		stmt.executeUpdate("INSERT INTO person(idperson,lastname, firstname, nickname, phone_number, address, email_address,birth_date)  VALUES (3, 'FENG', 'Peng', 'Peng', 666821026, 'FI 220', 'peng.feng@isen.yncrea.fr','1993-08-31')");
		stmt.close();
		connection.close();		
	}

	@Test
	public void shouldListPersons() {
		// WHEN
		List<Person> persons = personDao.listPersons();
		// THEN
		assertThat(persons).hasSize(3);
		assertThat(persons).extracting(
				"id", "lastname", "firstname", "nickname",
				"phone_number", "address", "email_address", "birth_date").containsOnly(
						tuple(1, "ZHAO", "Yudong","Dong",659783877,"FI 311",
								"yudong.zhao@isen.yncrea.fr",LocalDate.of(1995, Month.APRIL, 05)),
						tuple(2, "YANG", "Ziheng","Heng",666821026,"FI 312",
								"ziheng.yang@isen.yncrea.fr",LocalDate.of(1995, Month.NOVEMBER, 14)),
						tuple(3, "FENG", "Peng","Peng",666821026,"FI 220",
								"peng.feng@isen.yncrea.fr",LocalDate.of(1993, Month.AUGUST, 31))			
						);

	}
	
	@Test
	public void shouldAddPerson2() throws Exception {
		// WHEN 
		personDao.addPerson(new Person(null,"AAA","Yudong","Dong",659783877,"FI 311","yudong.zhao@isen.yncrea.fr",LocalDate.of(1995, Month.APRIL, 05)));
		// THEN
		Connection connection = DataSourceFactory.getDataSource().getConnection();
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery("SELECT * FROM person WHERE lastname='AAA'");
		assertThat(resultSet.next()).isTrue();		
		assertThat(resultSet.getInt("idperson")).isNotNull();
		assertThat(resultSet.getString("lastname")).isEqualTo("AAA");
		assertThat(resultSet.getString("firstname")).isEqualTo("Yudong");
		assertThat(resultSet.getString("nickname")).isEqualTo("Dong");
		assertThat(resultSet.getInt("phone_number")).isEqualTo(659783877);
		assertThat(resultSet.getString("address")).isEqualTo("FI 311");
		assertThat(resultSet.getString("email_address")).isEqualTo("yudong.zhao@isen.yncrea.fr");
		assertThat(resultSet.getDate("birth_date").toLocalDate()).isEqualTo(LocalDate.of(1995, Month.APRIL, 05));	
		assertThat(resultSet.next()).isFalse();
		resultSet.close();
		statement.close();
		connection.close();
	}	
	



}
