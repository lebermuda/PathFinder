package application.controller;

import java.io.IOException;

import application.Main;
import application.model.arbreLabyrinthe;
import application.model.kruskalGenerator;
import application.view.LabyrintheAffichage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Button;

public class PageGenerationCtrl {
    @FXML
    private Group groupeLabyrinthe;
    
    @FXML
    private Button buttonResolution;
    
	@FXML
	void initialize() {
		//Affichage de la grille vierge
		LabyrintheAffichage labyrinthe = new LabyrintheAffichage(groupeLabyrinthe,760,560,Main.infoLaby.getLONGUEUR_LABY(),Main.infoLaby.getHAUTEUR_LABY());
		labyrinthe.dessinerLabyVide();

		arbreLabyrinthe grapheLaby= new kruskalGenerator(Main.infoLaby.getLONGUEUR_LABY(),Main.infoLaby.getHAUTEUR_LABY());
		grapheLaby.generer(labyrinthe.grille);
		
		//Associer les informations d'animation à affichageLaby + stocker le graphe
		labyrinthe.etapeGenerationLaby = grapheLaby.getEtapeGeneration();
		labyrinthe.etapeCouleurGenerationLaby=grapheLaby.getEtapeGenerationCouleur();
		Main.infoLaby.setGrapheLaby(grapheLaby);
		
		//Lancer l'animation
		labyrinthe.afficherGenerationLaby(Main.infoLaby.getTIME_RESOLVE());
		
		//changer de page
    	buttonResolution.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>()
		{
			public void handle(ActionEvent e) {
                try {
					Main.changerPage("TemplatePageResolution.fxml");
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
	}

}
