package isen.java2.view;

import java.io.BufferedWriter;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;

import isen.java2.db.daos.PersonDao;
import isen.java2.db.entities.Person;
import isen.java2.service.IndexService;
import isen.java2.service.PersonService;
import isen.java2.service.StageService;
import isen.java2.service.ViewService;
import isen.java2.util.PersonListener;
import isen.java2.util.PersonValueFactory;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;


public class PersonController {

    @FXML private TableView<Person> personsTable;
	@FXML private TableColumn<Person, String> tc_lastname;
	@FXML private TableColumn<Person, String> tc_firstname;
	@FXML private AnchorPane formPane;	

	@FXML private TextField TF_id;
	@FXML private TextField TF_lastname;
	@FXML private TextField TF_firstname;
	@FXML private TextField TF_nickname;
	@FXML private TextField TF_phone;
	@FXML private TextField TF_address;
	@FXML private TextField TF_email;
	@FXML private TextField TF_birth;
	
	private Person currentPerson;	
	
	public void show(int id) {
		for(int i=0;i<personsTable.getItems().size();i++) {
			if(personsTable.getItems().get(i).getId()==id) {
				this.showPersonDetails(personsTable.getItems().get(i));
			}
		}		
	}
	
	@FXML
	public void backButton() throws Exception {
		StageService.showView(ViewService.getView("Table"));
	}
   
    @FXML
    private void initialize() {
        this.tc_lastname.setCellValueFactory(new PersonValueFactory());
        this.tc_firstname.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Person, String>, ObservableValue<String>>() {  
			@Override  
			public ObservableValue<String> call(CellDataFeatures<Person, String> arg0) {       
				return new SimpleStringProperty(arg0.getValue().getFirstname()); }  
		}); 
        this.populateList();
        this.personsTable.getSelectionModel().selectedItemProperty().addListener(new PersonListener() {
            @Override
            public void handleNewValue(Person newValue) {
                showPersonDetails(newValue);
            }
        });
        System.out.println("index:"+IndexService.getIndex());
        this.resetView();
    }

    @FXML
    private void handleSaveButton() {
    	System.out.println("save button: currentPerson id "+this.currentPerson.getId());
        this.currentPerson.setLastname(this.TF_lastname.getText());
        this.currentPerson.setFirstname(this.TF_firstname.getText());
        this.currentPerson.setNickname(this.TF_nickname.getText());
        this.currentPerson.setPhone_number(new Integer(this.TF_phone.getText()));
        this.currentPerson.setAddress(this.TF_address.getText());
        this.currentPerson.setEmail_address(this.TF_email.getText());
        this.currentPerson.setBirth_date(this.TF_birth.getText());   
        this.personsTable.refresh();
        this.showPersonDetails(this.currentPerson);
        
        Person p=new Person(this.currentPerson.getId(),this.TF_lastname.getText(),this.TF_firstname.getText(),
        			this.TF_nickname.getText(),new Integer(this.TF_phone.getText()),this.TF_address.getText(),
        			this.TF_email.getText(),LocalDate.of(2000, Month.JANUARY, 01));
        System.out.println("now saving");
        new PersonDao().save(p);        
        
    }


    @FXML
    private void handleNewButton() {
        Person question = new Person(null,"Nom","Prenom","Nickname",0,"Adress","E-mail",LocalDate.of(2000, Month.JANUARY, 01));
        PersonService.addQuestion(question);

        int size=personsTable.getItems().size();
        System.out.println("personsTable.getItems().size():"+size);
        int num=personsTable.getItems().get(size-2).getId()+1;
        System.out.println("add id:"+num);
        question.setId(num);
        
        personsTable.getSelectionModel().select(question);
    }


    @FXML
    private void handleDeleteButton() {
        int selectedIndex = personsTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
        	System.out.println("deleting item:"+selectedIndex);
        	new PersonDao().delete(personsTable.getItems().get(selectedIndex).getPerson());
        	personsTable.getItems().remove(selectedIndex);
            resetView();            
        }

    }

    private void refreshList() {
        this.personsTable.refresh();
        this.personsTable.getSelectionModel().clearSelection();
    }

    private void populateList() {
        this.personsTable.setItems(PersonService.getPersons());
        this.refreshList();
    }
    
    private void resetView() {
    	if(IndexService.getIndex()!=0) {
    		this.show(IndexService.getIndex());    		
    		IndexService.setIndex(0);
    	}else {
        this.showPersonDetails(null);
        this.refreshList();}
    }

    private void showPersonDetails(Person question) {
        if (question == null) {
            this.formPane.setVisible(false);
        } else {
            this.formPane.setVisible(true);
            this.currentPerson = question;
            this.TF_lastname.setText(this.currentPerson.getLastname());
            this.TF_firstname.setText(this.currentPerson.getFirstname());
            this.TF_nickname.setText(this.currentPerson.getNickname());
            this.TF_phone.setText(this.currentPerson.getPhone_number().toString());
            this.TF_address.setText(this.currentPerson.getAddress());
            this.TF_email.setText(this.currentPerson.getEmail_address());
            this.TF_birth.setText(this.currentPerson.getBirth_date().toString());
            
        }
    }


    
    @FXML
	public void handleExportButton() throws Exception {
		System.out.println("Exporting");
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
        		new FileChooser.ExtensionFilter("Business Card (vCard) File(*.vcf)", "*.vcf"),
        		new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt")        		
        		);

        Stage s = new Stage();
        File file = fileChooser.showSaveDialog(s);
        
        	Path outputFile=Paths.get(file.getAbsolutePath());

        	try (BufferedWriter writer = Files.newBufferedWriter(outputFile, StandardCharsets.UTF_8)) {
        		ArrayList<String> line=new ArrayList<String>();
        		line.add("BEGIN:VCARD");
        		line.add("VERSION:2.1");
        		line.add("N:"+this.currentPerson.getLastname()+";"+this.currentPerson.getFirstname());
        		line.add("FN:"+this.currentPerson.getLastname()+" "+this.currentPerson.getFirstname());
        		line.add("NICKNAME:"+this.currentPerson.getNickname());
        		line.add("TEL;CELL;VOICE:"+this.currentPerson.getPhone_number());
        		line.add("ADR;HOME;POSTAL;PARCEL:;;"+this.currentPerson.getAddress()+";Lille;Lille;59000;France");
        		line.add("LABEL;HOME;ENCODING=QUOTED-PRINTABLE:=B5=D8=D6=B7");
        		line.add("=C0=EF=B6=FB");
        		line.add("=C0=EF=B6=FB");
        		line.add("59000");
        		line.add("=B7=A8=B9=FA");
        		line.add("BDAY:"+this.currentPerson.getBirth_date().toString().replace("-", ""));
        		line.add("KEY;X509;ENCODING=BASE64:");
        		line.add(" ");
        		line.add(" ");
        		line.add("EMAIL;PREF;INTERNET:"+this.currentPerson.getEmail_address());
        		line.add("REV:20180411T181004Z");
        		line.add("END:VCARD");

        		for(int i=0;i<line.size();i++) {
        			writer.write(line.get(i));
        			writer.newLine();
        		}
        		writer.flush();
        	} 
        

    }
    


}
