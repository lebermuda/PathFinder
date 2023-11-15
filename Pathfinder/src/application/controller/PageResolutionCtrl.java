package application.controller;

import java.io.IOException;
import application.Main;
import application.model.arbreLabyrinthe;
import application.model.astar.AstarResolve;
import application.model.tremaux.tremauxResolve;
import application.model.dijkstra.DijkstraResolve;
import application.model.mur_droit.Mur_droitResolve;
import application.view.LabyrintheAffichage;
import javafx.animation.ParallelTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class PageResolutionCtrl{

    @FXML
    private Group groupeLabyResolution;
    
    @FXML
    private Group groupeLabyResolution1;

    @FXML
    private Group groupeLabyResolution2;

    @FXML
    private Group groupeLabyResolution3;

    @FXML
    private Button buttonResultat;
    
    @FXML
    private Text dijkstraText;

    @FXML
    private Text murDroitText;

    @FXML
    private Text aStarText;

    @FXML
    private Text tremauxText;
    
	@FXML
	void initialize() {
		//Choix du départ et de l'arrivée pour que ce soit commun à tous.
		Main.infoLaby.getGrapheLaby().departAleatoire();
		//Création d'un graphe par algo
		//Dijkstra
		arbreLabyrinthe grapheLabyDijkstra = new DijkstraResolve(Main.infoLaby.getGrapheLaby());
		
		//MurDroit
		arbreLabyrinthe grapheLabyMurDroit = new Mur_droitResolve(Main.infoLaby.getGrapheLaby());
		
		//A*
		arbreLabyrinthe grapheLabyAStar = new AstarResolve(Main.infoLaby.getGrapheLaby());

		//Tremaux
		arbreLabyrinthe grapheLabyTremaux = new tremauxResolve(Main.infoLaby.getGrapheLaby());
		
		if (Main.infoLaby.getTYPE_RESOLUTION().equals("Comparatif")) {
			//Création de 4 labyrinthes dans les 4 coins de la page
			LabyrintheAffichage labyrinthe0 = new LabyrintheAffichage(groupeLabyResolution,370,255,Main.infoLaby.getLONGUEUR_LABY(),Main.infoLaby.getHAUTEUR_LABY());
			labyrinthe0.dessinerLaby(Main.infoLaby.getGrapheLaby());
			LabyrintheAffichage labyrinthe1 = new LabyrintheAffichage(groupeLabyResolution1,370,255,Main.infoLaby.getLONGUEUR_LABY(),Main.infoLaby.getHAUTEUR_LABY());
			labyrinthe1.dessinerLaby(Main.infoLaby.getGrapheLaby());
			LabyrintheAffichage labyrinthe2 = new LabyrintheAffichage(groupeLabyResolution2,370,255,Main.infoLaby.getLONGUEUR_LABY(),Main.infoLaby.getHAUTEUR_LABY());
			labyrinthe2.dessinerLaby(Main.infoLaby.getGrapheLaby());
			LabyrintheAffichage labyrinthe3 = new LabyrintheAffichage(groupeLabyResolution3,370,255,Main.infoLaby.getLONGUEUR_LABY(),Main.infoLaby.getHAUTEUR_LABY());
			labyrinthe3.dessinerLaby(Main.infoLaby.getGrapheLaby());
			
			//afficahge des noms des algos
			dijkstraText.setLayoutX(50);
			dijkstraText.setLayoutY(12);
			dijkstraText.setVisible(true);
			murDroitText.setLayoutX(460);
			murDroitText.setLayoutY(12);
			murDroitText.setVisible(true);
			aStarText.setLayoutX(50);
			aStarText.setLayoutY(570);
			aStarText.setVisible(true);
			tremauxText.setLayoutX(460);
			tremauxText.setLayoutY(570);
			tremauxText.setVisible(true);
						
			//Résolvation des algo avec les bonne labyrithe de représentation
			grapheLabyDijkstra.generer(labyrinthe0.grille);
			grapheLabyMurDroit.generer(labyrinthe1.grille);
			grapheLabyAStar.generer(labyrinthe2.grille);
			grapheLabyTremaux.generer(labyrinthe3.grille);
			
			//Mise en parrallèle de toute les animations
			ParallelTransition ptComparatif=new ParallelTransition();
			
			//Dijsktra en haut à gauche
			labyrinthe0.etapeGenerationLaby = grapheLabyDijkstra.getEtapeResolution();
			labyrinthe0.etapeCouleurGenerationLaby=grapheLabyDijkstra.getEtapeResolutionCouleur();
			ptComparatif.getChildren().add(labyrinthe0.sequenceDAnimation(Main.infoLaby.getTIME_RESOLVE()));
			
			//Mur Droit en haut à droite
			labyrinthe1.etapeGenerationLaby = grapheLabyMurDroit.getEtapeResolution();
			labyrinthe1.etapeCouleurGenerationLaby=grapheLabyMurDroit.getEtapeResolutionCouleur();
			ptComparatif.getChildren().add(labyrinthe1.sequenceDAnimation(Main.infoLaby.getTIME_RESOLVE()));
			
			
			//A* en bas à gauche
			labyrinthe2.etapeGenerationLaby = grapheLabyAStar.getEtapeResolution();
			labyrinthe2.etapeCouleurGenerationLaby=grapheLabyAStar.getEtapeResolutionCouleur();
			ptComparatif.getChildren().add(labyrinthe2.sequenceDAnimation(Main.infoLaby.getTIME_RESOLVE()));
			
			
			//Trémaux en bas à gauche
			labyrinthe3.etapeGenerationLaby = grapheLabyTremaux.getEtapeResolution();
			labyrinthe3.etapeCouleurGenerationLaby=grapheLabyTremaux.getEtapeResolutionCouleur();
			ptComparatif.getChildren().add(labyrinthe3.sequenceDAnimation(Main.infoLaby.getTIME_RESOLVE()));
			
			//Lancer l'animation de tous les algos
			ptComparatif.play();
		}
		else {
			//On cache les noms des algos
			dijkstraText.setVisible(false);
			murDroitText.setVisible(false);
			aStarText.setVisible(false);
			tremauxText.setVisible(false);
			
			//Création de la représentation du labyrinthe
			LabyrintheAffichage labyrinthe = new LabyrintheAffichage(groupeLabyResolution,700,500,Main.infoLaby.getLONGUEUR_LABY(),Main.infoLaby.getHAUTEUR_LABY());
			labyrinthe.dessinerLaby(Main.infoLaby.getGrapheLaby());
			//System.out.println(Main.infoLaby.getGrapheLaby());
			
			//Génération de tous les algorithmes pour les résultats
			grapheLabyDijkstra.generer(labyrinthe.grille);
			grapheLabyMurDroit.generer(labyrinthe.grille);
			grapheLabyAStar.generer(labyrinthe.grille);
			grapheLabyTremaux.generer(labyrinthe.grille);
			
			//Choix de l'animation de quelle algo choisir
			switch(Main.infoLaby.getTYPE_RESOLUTION()) {
				case "Dijkstra":
					dijkstraText.setLayoutX(150);
					dijkstraText.setLayoutY(12);
					dijkstraText.setVisible(true);
					
					labyrinthe.etapeGenerationLaby = grapheLabyDijkstra.getEtapeResolution();
					labyrinthe.etapeCouleurGenerationLaby=grapheLabyDijkstra.getEtapeResolutionCouleur();
				
					labyrinthe.afficherGenerationLaby(Main.infoLaby.getTIME_RESOLVE());
					break;
				case "Mur Droit":
					murDroitText.setLayoutX(150);
					murDroitText.setLayoutY(12);
					murDroitText.setVisible(true);
					
					labyrinthe.etapeGenerationLaby = grapheLabyMurDroit.getEtapeResolution();
					labyrinthe.etapeCouleurGenerationLaby=grapheLabyMurDroit.getEtapeResolutionCouleur();
					
					labyrinthe.afficherGenerationLaby(Main.infoLaby.getTIME_RESOLVE());
					break;
				case "A*":
					aStarText.setLayoutX(150);
					aStarText.setLayoutY(12);
					aStarText.setVisible(true);
					
					labyrinthe.etapeGenerationLaby = grapheLabyAStar.getEtapeResolution();
					labyrinthe.etapeCouleurGenerationLaby=grapheLabyAStar.getEtapeResolutionCouleur();
				
					labyrinthe.afficherGenerationLaby(Main.infoLaby.getTIME_RESOLVE());
					
					break;
				case "Trémaux":
					tremauxText.setLayoutX(150);
					tremauxText.setLayoutY(12);
					tremauxText.setVisible(true);
					labyrinthe.etapeGenerationLaby = grapheLabyTremaux.getEtapeResolution();
					labyrinthe.etapeCouleurGenerationLaby=grapheLabyTremaux.getEtapeResolutionCouleur();
				
					labyrinthe.afficherGenerationLaby(Main.infoLaby.getTIME_RESOLVE());
					break;
				default:
					System.out.println("erreur Dans Choix De L'algo De Resolution");
			}
		}

		//Changer de page
    	buttonResultat.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>()
		{
			public void handle(ActionEvent e) {
                try {
					Main.changerPage("TemplatePageResultat.fxml");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

	}
}