package isen.java2.view;

import java.util.ArrayList;

import isen.java2.db.entities.Person;
import isen.java2.service.IndexService;
import isen.java2.service.PersonService;
import isen.java2.service.StageService;
import isen.java2.service.ViewService;
import isen.java2.util.PersonListener;
import isen.java2.util.PersonValueFactory;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;

public class TableController {
	
	@FXML private TableView<Person> personsTable;
	@FXML private TableColumn<Person, String> tc_lastname;
	@FXML private TableColumn<Person, String> tc_firstname;
	@FXML private TableColumn<Person, String> tc_nickname;
	@FXML private TableColumn<Person, String> tc_phone;
	@FXML private TableColumn<Person, String> tc_adress;
	@FXML private TableColumn<Person, String> tc_eadress;
	@FXML private TableColumn<Person, String> tc_birth;
	@FXML private TableColumn<Person, String> tc_category;
	
	@FXML private TextField TF_search;	
	@FXML private AnchorPane formPane;	
	@FXML private ImageView imageview;
	@FXML RadioButton radio1;
	@FXML RadioButton radio2;
	@FXML RadioButton radio3;
	
	private Person currentPerson;	
	private String name;	
	private ObservableList<Person> personsSearch = FXCollections.observableArrayList();
	private ArrayList<Person> searchResult=new ArrayList<Person>();	

	
	@FXML
    private void initialize() {		
		this.tc_lastname.setCellValueFactory(new PersonValueFactory());
		this.tc_firstname.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Person, String>, ObservableValue<String>>() {  
			@Override  
			public ObservableValue<String> call(CellDataFeatures<Person, String> arg0) {       
				return new SimpleStringProperty(arg0.getValue().getFirstname()); }  
		}); 
		this.tc_nickname.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Person, String>, ObservableValue<String>>() {  
			@Override  
			public ObservableValue<String> call(CellDataFeatures<Person, String> arg0) {       
				return new SimpleStringProperty(arg0.getValue().getNickname()); }  
		}); 
		this.tc_phone.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Person, String>, ObservableValue<String>>() {  
			@Override  
			public ObservableValue<String> call(CellDataFeatures<Person, String> arg0) {       
				return new SimpleStringProperty(arg0.getValue().getPhone_number()); }  
		}); 
		this.tc_adress.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Person, String>, ObservableValue<String>>() {  
			@Override  
			public ObservableValue<String> call(CellDataFeatures<Person, String> arg0) {       
				return new SimpleStringProperty(arg0.getValue().getAddress()); }  
		});
		this.tc_eadress.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Person, String>, ObservableValue<String>>() {  
			@Override  
			public ObservableValue<String> call(CellDataFeatures<Person, String> arg0) {       
				return new SimpleStringProperty(arg0.getValue().getEmail_address()); }  
		});
		this.tc_birth.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Person, String>, ObservableValue<String>>() {  
			@Override  
			public ObservableValue<String> call(CellDataFeatures<Person, String> arg0) {       
				return new SimpleStringProperty(arg0.getValue().getBirth_date().toString()); }  
		});
		this.tc_category.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Person, String>,ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<Person, String> arg0) {
				return new SimpleStringProperty(arg0.getValue().getCategory());	}
		});

		this.tc_lastname.setSortable(true); 
		this.tc_firstname.setSortable(true); 
		this.tc_nickname.setSortable(true); 
		this.tc_phone.setSortable(true); 
		this.tc_adress.setSortable(true); 
		this.tc_eadress.setSortable(true); 
		this.tc_birth.setSortable(true);
		this.tc_category.setSortable(true);
		
        this.populateList();
        this.personsTable.getSelectionModel().selectedItemProperty().addListener(new PersonListener() {
            @Override
            public void handleNewValue(Person newValue) {
                showPersonDetails(newValue);
            }
        });
        this.resetView();
        loadImage();
        
    }
	
	private void resetView() {
        this.showPersonDetails(null);
        this.refreshList();
    }
	
	
    private void refreshList() {    	
        this.personsTable.refresh();
        this.personsTable.getSelectionModel().clearSelection();
    }

    private void populateList() {
        this.personsTable.setItems(PersonService.getPersons());
        this.refreshList();
    }
	
        
    private void showPersonDetails(Person question) {
        if (question != null) { 
            this.currentPerson = question;    
        }
    }
    
    @FXML
    private void handleEditButton() {    	
    	if(this.currentPerson!=null) {
    		System.out.println("CurrentPerson id:"+this.currentPerson.getId());
    		IndexService.setIndex(this.currentPerson.getId());
    	}
    	else
    		IndexService.setIndex(0);
    	System.out.println("Index:"+IndexService.getIndex());
    	StageService.showView(ViewService.getView("Persons"));
    }
    
    @FXML
    private void handleSearchButton() {     	
    	if(this.TF_search.getText()!=null&&this.TF_search.getLength()!=0) {
    		this.name=this.TF_search.getText();
        	this.searchNom(this.name);
    	}else
    		this.refreshList(); 
    }
    
    public void searchNom(String nom) {    	
    	System.out.println("Searching nom:"+nom);
    	System.out.println("Searching from "+personsTable.getItems().size()+" items");
		for(int i=0;i<personsTable.getItems().size();i++) {
			if(personsTable.getItems().get(i).getLastname().toLowerCase().contains(nom.toLowerCase())) {
				this.searchResult.add(personsTable.getItems().get(i));
			}			
		}
		System.out.println("Result number:"+searchResult.size());
		personsSearch.clear();    	
		personsSearch.addAll(searchResult);	
		searchResult.clear();
		this.personsTable.setItems(personsSearch);
		this.personsTable.refresh();
    }
    
    @FXML
    private void handleRefreshButton() { 
    	this.populateList();
    	this.TF_search.setText(null);
    	this.TF_search.setPromptText("Search the lastname");
    }
    
    void loadImage() {    	
    	Image image=new Image("/isen/java2/view/search.jpg");
    	imageview.setImage(image);    
    }

    
    

}

