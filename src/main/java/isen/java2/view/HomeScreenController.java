package isen.java2.view;

import isen.java2.service.StageService;
import isen.java2.service.ViewService;
import javafx.fxml.FXML;

public class HomeScreenController {

	@FXML
	public void handleLaunchButton() throws Exception {
		StageService.showView(ViewService.getView("Table"));
	}

	

}
