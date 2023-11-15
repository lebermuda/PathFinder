package application.controller;

import java.io.IOException;

import application.Main;
import application.model.informationLaby;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
//import javafx.scene.layout.GridPane;

public class PageResultatCtrl {
	
    //@FXML
    //private GridPane tabResultat;

    @FXML
    private TextArea murDroitTemps;

    @FXML
    private TextArea dijkstraTemps;

    @FXML
    private TextArea AStarTemps;

    @FXML
    private TextArea tremauxTemps;

    @FXML
    private TextArea dijkstraTaux;

    @FXML
    private TextArea murDroitTaux;

    @FXML
    private TextArea AStarTaux;

    @FXML
    private TextArea tremauxTaux;
    
    @FXML
    private Button buttonRetour;
    
    @FXML
    private void initialize() {
    	dijkstraTemps.setText(Main.infoLaby.getRESULTATS_TEMPS()[0]+" ");
    	dijkstraTaux.setText(Main.infoLaby.getRESULTATS_TAUX_VISITE()[0]+"  ");
    	
    	murDroitTemps.setText(Main.infoLaby.getRESULTATS_TEMPS()[1]+" ");
    	murDroitTaux.setText(Main.infoLaby.getRESULTATS_TAUX_VISITE()[1]+"  ");
    	
    	AStarTemps.setText(Main.infoLaby.getRESULTATS_TEMPS()[2]+" ");
    	AStarTaux.setText(Main.infoLaby.getRESULTATS_TAUX_VISITE()[2]+"  ");
    	
    	tremauxTemps.setText(Main.infoLaby.getRESULTATS_TEMPS()[3]+" ");
    	tremauxTaux.setText(Main.infoLaby.getRESULTATS_TAUX_VISITE()[3]+"  ");
    	
		buttonRetour.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>()
		{
			public void handle(ActionEvent e) {
                try {
                	Main.infoLaby=new informationLaby();
					Main.changerPage("TemplatePageAcceuil.fxml");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
	}
}