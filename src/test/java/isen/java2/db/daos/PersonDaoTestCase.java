package isen.java2.db.daos;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
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
		stmt.executeUpdate("INSERT INTO person(idperson,lastname, firstname, nickname, phone_number, address, email_address,birth_date,category)  VALUES (1, 'GENG', 'Mingxue', 'Mingxuexue', '761559161', '5 rue de FF', 'mingxue@gmail.com','1997-06-16','Family')");
		stmt.executeUpdate("INSERT INTO person(idperson,lastname, firstname, nickname, phone_number, address, email_address,birth_date,category)  VALUES (2, 'PAN', 'Yuanxiang', 'Panpan','766291882', '5 rue de LL', 'yuanxiang@gamil.com','1997-02-24','Friend')");
		stmt.executeUpdate("INSERT INTO person(idperson,lastname, firstname, nickname, phone_number, address, email_address,birth_date,category)  VALUES (3, 'ZHENG', 'Wanqi', 'Qiqi','655193872', '5 rue de EE', 'wanqi@gmail.com','1996-07-27','Roommate')");
		stmt.executeUpdate("INSERT INTO person(idperson,lastname, firstname, nickname, phone_number, address, email_address,birth_date,category)  VALUES (4, 'CAO', 'Yu', 'Yuyu','567532098', '5 rue de NN', 'yuyu@gmail.com','1996-07-08','Friend')");
		stmt.executeUpdate("INSERT INTO person(idperson,lastname, firstname, nickname, phone_number, address, email_address,birth_date,category)  VALUES (5, 'WANG', 'Xin', 'Xinxin','762543829', '5 rue de DD', 'xinxin@gmail.com','1996-01-30','Friend')");	
		stmt.executeUpdate("INSERT INTO person(idperson,lastname, firstname, nickname, phone_number, address, email_address,birth_date,category)  VALUES (6, 'XU', 'Lu', 'Lulu','536784291', '10 rue de BB', 'lulu@gmail.com','1995-05-09','Friend')");
		stmt.executeUpdate("INSERT INTO person(idperson,lastname, firstname, nickname, phone_number, address, email_address,birth_date,category)  VALUES (7, 'ZHU', 'Haoran', 'Haoran','647386554', '10 rue de AA', 'haoran@gmail.com','1995-01-01','Friend')");	
		
		System.out.println("Updating database");
		stmt.close();
		connection.close();		
	}
	
	@Test
	public void shouldListPersons() throws SQLException {
		// GIVEN
		PersonDao PersonDao = new PersonDao();
		// WHEN
		List<Person> PersonList = PersonDao.listPersons();
		// THEN
		assertThat(PersonList).hasSize(7).doesNotContainNull();
	}

	@Test
	public void shouldAddPerson2() throws Exception {
		// WHEN 
		personDao.addPerson(new Person(null,"ZHUZHU","Haoran","Hao","647386554","AA","hao@gmail.com",LocalDate.of(1995, Month.JANUARY, 01),"Friend"));
		// THEN
		Connection connection = DataSourceFactory.getDataSource().getConnection();
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery("SELECT * FROM person WHERE lastname='ZHUZHU'");
		assertThat(resultSet.next()).isTrue();		
		assertThat(resultSet.getInt("idperson")).isNotNull();
		assertThat(resultSet.getString("lastname")).isEqualTo("ZHUZHU");
		assertThat(resultSet.getString("firstname")).isEqualTo("Haoran");
		assertThat(resultSet.getString("nickname")).isEqualTo("Hao");
		assertThat(resultSet.getString("phone_number")).isEqualTo("647386554");
		assertThat(resultSet.getString("address")).isEqualTo("AA");
		assertThat(resultSet.getString("email_address")).isEqualTo("hao@gmail.com");
		assertThat(resultSet.getDate("birth_date").toLocalDate()).isEqualTo(LocalDate.of(1995, Month.JANUARY, 01));
		assertThat(resultSet.getString("category")).isEqualTo("Friend");
		assertThat(resultSet.next()).isFalse();
		resultSet.close();
		statement.close();
		connection.close();
	}	
	



}
