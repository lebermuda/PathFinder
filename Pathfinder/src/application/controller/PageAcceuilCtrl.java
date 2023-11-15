package application.controller;

import java.io.IOException;

//import javax.swing.event.ChangeListener;

import application.Main;
import application.model.labyTest;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class PageAcceuilCtrl {
	
	@FXML
    private RadioButton dijkstraButton;

    @FXML
    private ToggleGroup uniqueResolution;

    @FXML
    private RadioButton aEtoileButton;

    @FXML
    private RadioButton murDroitButton;

    @FXML
    private RadioButton tremauxButton;

    @FXML
    private RadioButton comparatifButton;

    @FXML
    private Button genererButton;

    @FXML
    private Slider hauteurSelector;

    @FXML
    private Text hauteurText;

    @FXML
    private Text longueurText;

    @FXML
    private Slider longueurSelector;

    @FXML
    private Text tempsText;

    @FXML
    private Slider tempsSelector;

    // ToggleGroup = ensemble de boutons, permet d'en sélectionner qu'un à la fois et de prendre la valeur du bouton coché
    @FXML
    private RadioButton departAleatoire;

    @FXML
    private ToggleGroup uniqueGeneration; //choix séléction départ arrivée

    @FXML
    private RadioButton departManuelle;
    
    @FXML
    private TextField iDepart;

    @FXML
    private TextField jDepart;

    @FXML
    private TextField iArrivee;

    @FXML
    private TextField jArrivee;
    
    @FXML
    private Pane panelChoixManuel;
    
    @FXML
    private Button testButton;

    
    @FXML
    void initialize() {
    	//Afficher la valeur du selecteur en temps réel
    	afficherNombreSelector(hauteurSelector,hauteurText,"Hauteur");
    	afficherNombreSelector(longueurSelector,longueurText,"Longueur");
    	afficherNombreSelector(tempsSelector, tempsText, "Temps(ms)");
    	
    	departManuelle.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>()
    		{
    				public void handle(ActionEvent e) {
    					panelChoixManuel.setVisible(true);
    				}
    		});
    	departAleatoire.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>()
			{
				public void handle(ActionEvent e) {
					panelChoixManuel.setVisible(false);
				}
			});
    	
    	genererButton.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>()
    			{
    				public void handle(ActionEvent e) {
    	                try {
    	                	//Stocker les informations entrées par l'user 
    	                	Main.infoLaby.setHAUTEUR_LABY(getIntValueSelector(hauteurSelector));
    	                	Main.infoLaby.setLONGUEUR_LABY(getIntValueSelector(longueurSelector));
    	                	Main.infoLaby.setTIME_RESOLVE(getIntValueSelector(tempsSelector));
    	                	Main.infoLaby.setTYPE_SELECT_DEPART( ((RadioButton) uniqueGeneration.getSelectedToggle()).getText());
    	                	Main.infoLaby.setTYPE_RESOLUTION( ((RadioButton) uniqueResolution.getSelectedToggle()).getText());
    	                	eviterErreurDepartArrivee();
    	                	
							//Passer à la  page suivante
    	                	Main.changerPage("TemplatePageGeneration.fxml");
						} catch (IOException e1) {
							e1.printStackTrace();
						}
    				}
    			});
    	testButton.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>()
		{
			public void handle(ActionEvent e) {
                try {
                	//Stocker le labyrinthe pour des tests sur une structure fixe 
                	labyTest grapheLabyTest=new labyTest();
        			Main.infoLaby.setHAUTEUR_LABY(grapheLabyTest.getNombrePieceHauteur());
        			Main.infoLaby.setLONGUEUR_LABY(grapheLabyTest.getNombrePieceLargeur());
        			Main.infoLaby.setGrapheLaby(grapheLabyTest);
                	Main.infoLaby.setTIME_RESOLVE(getIntValueSelector(tempsSelector));
                	Main.infoLaby.setTYPE_SELECT_DEPART( ((RadioButton) uniqueGeneration.getSelectedToggle()).getText());
                	Main.infoLaby.setTYPE_RESOLUTION( ((RadioButton) uniqueResolution.getSelectedToggle()).getText());
					eviterErreurDepartArrivee();
                	//Passer à la  page suivante
                	Main.changerPage("TemplatePageResolution.fxml");
				} catch (IOException e2) {
					e2.printStackTrace();
				}
			}
		});
    	
    }
    
    private void eviterErreurDepartArrivee() {
    // Gère les erreurs dans les entrées des indices de depart et d'arrivée
    	if (iDepart.getText().matches("-?\\d+") && jDepart.getText().matches("-?\\d+")
        		&& iArrivee.getText().matches("-?\\d+") && jArrivee.getText().matches("-?\\d+")){
        	//Vérifier que ce sont des entiers
        		int[] depart = { Integer.parseInt(iDepart.getText()) ,Integer.parseInt(jDepart.getText())};
        		int[] arrivee = { Integer.parseInt(iArrivee.getText()) ,Integer.parseInt(jArrivee.getText())};
        		
           		if ((depart[0]<0)||(Main.infoLaby.getHAUTEUR_LABY()<depart[0])||(depart[1]<0)||(Main.infoLaby.getLONGUEUR_LABY() <depart[1])
           			||(arrivee[0]<0)||(Main.infoLaby.getHAUTEUR_LABY()<arrivee[0])||(arrivee[1]<0)||(Main.infoLaby.getLONGUEUR_LABY() <arrivee[1])) {
           		// Si en dehors du laby on passe en Aléatoire
           			Main.infoLaby.setTYPE_SELECT_DEPART("Aléatoire");
           		}
           		else {
           			Main.infoLaby.setArrive(arrivee);
           			Main.infoLaby.setDepart(depart);
           		}
			}
        	else {
        		Main.infoLaby.setTYPE_SELECT_DEPART("Aléatoire");
        	}
    }
    
	private void afficherNombreSelector(Slider selector, Text text,String id) {
		selector.setOnMouseReleased((EventHandler<MouseEvent>) new EventHandler<MouseEvent>()
		{
			@Override
			public void handle(MouseEvent arg0) {
             	text.setText(id +" : "+ getIntValueSelector(selector));					
			}
		});
		
	}
	
	//getter and setter
    public int getIntValueSelector(@SuppressWarnings("exports") Slider selector1) {
    	return (int) selector1.getValue();
    }
}

