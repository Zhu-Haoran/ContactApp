package isen.java2.view;

import isen.java2.service.StageService;
import isen.java2.service.ViewService;
import javafx.scene.layout.AnchorPane;

public class MainLayoutController {

	public void closeApplication() {
		StageService.closeStage();
	}

	public void gotoHome() {
		AnchorPane homeScreenRoot = ViewService.getView("Table");
		StageService.showView(homeScreenRoot);
	}

	public void gotoPersonAdmin() {
		AnchorPane homeScreenRoot = ViewService.getView("Persons");
		StageService.showView(homeScreenRoot);
	}

}
