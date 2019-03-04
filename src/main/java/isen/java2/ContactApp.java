package isen.java2;

import isen.java2.service.StageService;
import isen.java2.service.ViewService;
import javafx.application.Application;
import javafx.stage.Stage;

public class ContactApp extends Application{
	
	public ContactApp() {

	}

	@Override
	public void start(Stage primaryStage) {
		StageService.initPrimaryStage(primaryStage);
		StageService.showView(ViewService.getView("Table"));
	}

	public static void main(String[] args) {
		launch(args);
	}

}
