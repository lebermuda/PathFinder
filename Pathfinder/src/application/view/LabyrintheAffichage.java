package application.view;

import javafx.scene.Group;
import javafx.scene.paint.Color;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

import application.Main;
import application.model.arbreLabyrinthe;
import javafx.animation.FillTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.SequentialTransition;
import javafx.util.Duration;

public class LabyrintheAffichage {
	private Group groupe;
	private double HAUTEUR_GROUPE;
	private double LONGUEUR_GROUPE;
	
	private int l; //largeur
	private int h; //hauteur 
	public Case[][] grille; //tableau de Case 
	
	public Collection<Case[]> etapeGenerationLaby;
	public Color[] etapeCouleurGenerationLaby;
	
	
	public LabyrintheAffichage(Group groupe,double longueurGroupe,double hauteurGroupe,int l,int h){
		this.groupe=groupe;
		HAUTEUR_GROUPE=hauteurGroupe;
		LONGUEUR_GROUPE=longueurGroupe;
		this.l=l;
		this.h=h;
		grille = new Case[2*h+1][2*l+1];
		for (int i=0;i<h*2+1;i++) {
			for (int j=0;j<l*2+1;j++) {
				grille[i][j]=new Case(i,j,Math.min((HAUTEUR_GROUPE)/(2*h+1), (LONGUEUR_GROUPE)/(2*l+1)));
			}
		}		
		etapeGenerationLaby=new LinkedList<Case[]>();		
	}
	
	public void dessinerLabyVide() {
	//Afficher une grille avec les pièces en blanc le reste en noir
		for (int i=0;i<h*2+1;i++) {
			for (int j=0;j<l*2+1;j++) {
				grille[i][j].drawCase(groupe);
			}
		}
	}
	
	public void dessinerLaby(arbreLabyrinthe grapheLaby) {
	//Dessiner un labyrinthe à partir d'un graphe et y mettre un départ et une arrivé
		grille[Main.infoLaby.getDepart()[0]*2+1][Main.infoLaby.getDepart()[1]*2+1].setColor(Color.GREEN);
		grille[Main.infoLaby.getArrive()[0]*2+1][Main.infoLaby.getArrive()[1]*2+1].setColor(Color.RED);
		
		//Colorer en noir la premiere ligne (leur couleur est déjà noir)
		for (int i=0;i<2*h+1;i++) {
			grille[i][0].drawCase(groupe);
		}
		//Colorer en noir la premiere colonne (leur couleur est déjà noir)
		for (int j=1;j<2*l+1;j++) {
			grille[0][j].drawCase(groupe);
		}
		//Colorer les pieces avec les murs les entourant donc 4 cases par 4 cases :
		//la pièce ([2*i+1,2*j+1]), sa porte Est ([2*i+1,2*j+2]) et Sud ([2*i+2,2*j+1]) et le mur de l'angle en bas à droite ([2*i+2,2*j+2])
		for (int i=0;i<h;i++) {
			for (int j=0;j<l;j++) {
				
				if (((i!=Main.infoLaby.getDepart()[0]) || (j!=Main.infoLaby.getDepart()[1])) && ((i!=Main.infoLaby.getArrive()[0]) || (j!=Main.infoLaby.getArrive()[1]))) {
					grille[2*i+1][2*j+1].setColor(Color.WHITE);
				}
				grille[2*i+1][2*j+1].drawCase(groupe);
				
				if (grapheLaby.getGrillePiece()[i][j].getPorteSud()) {
					grille[2*i+2][2*j+1].setColor(Color.WHITE);
				}
				else {
					grille[2*i+2][2*j+1].setColor(Color.BLACK);
				}
				grille[2*i+2][2*j+1].drawCase(groupe);
				if (grapheLaby.getGrillePiece()[i][j].getPorteEst()) {
					grille[2*i+1][2*j+2].setColor(Color.WHITE);
				}
				else {
					grille[2*i+1][2*j+2].setColor(Color.BLACK);
				}
				grille[2*i+1][2*j+2].drawCase(groupe);
				//mur est tjrs noir
				grille[2*i+2][2*j+2].drawCase(groupe);
			}

		}
	}
	
	public void afficherGenerationLaby(int intervalTemps) {
	//afficher un labyrinthe en suivant les étapes de construction (de classe LinkedList<Case[]>()) 
	//les Case des Case[] sont les changements en parallèle et chaques éléments de la linkedList s'affichent à la suite tout les interval temps 
		
		Iterator<Case[]> iteratorEtapeGenerationLaby= etapeGenerationLaby.iterator();
		SequentialTransition st = new SequentialTransition();
		int nombreEtape=0;
		
		while (iteratorEtapeGenerationLaby.hasNext()) {
			Case[] actualCases=iteratorEtapeGenerationLaby.next();
			ParallelTransition pt = new ParallelTransition();
			for (int i=0;i<actualCases.length;i++) {
				pt.getChildren().add(animerChangerCouleurCase(actualCases[i],intervalTemps,nombreEtape));
			}
			st.getChildren().add(pt);
			nombreEtape++;
		}
		st.play();
	}
	
	public SequentialTransition sequenceDAnimation(int intervalTemps) {
	//Envoie la suite d'animation de la génération
		
		Iterator<Case[]> iteratorEtapeGenerationLaby= etapeGenerationLaby.iterator();
		SequentialTransition st = new SequentialTransition();
		int nombreEtape=0;
		
		while (iteratorEtapeGenerationLaby.hasNext()) {
			Case[] actualCases=iteratorEtapeGenerationLaby.next();
			ParallelTransition pt = new ParallelTransition();
			for (int i=0;i<actualCases.length;i++) {
				pt.getChildren().add(animerChangerCouleurCase(actualCases[i],intervalTemps,nombreEtape));
			}
			st.getChildren().add(pt);
			nombreEtape++;
		}
		return st;
	}
	
	private FillTransition animerChangerCouleurCase(Case caseChanger,int intervalTemps,int nEtape){
		FillTransition ft=new FillTransition(Duration.millis(intervalTemps),caseChanger.r);
		ft.setToValue(etapeCouleurGenerationLaby[nEtape]);
		caseChanger.setColor(etapeCouleurGenerationLaby[nEtape]);
		return ft;
	}

	public double getLongueurGroupe() {
		return LONGUEUR_GROUPE;
	}

	public double getHauteurGroupe() {
		return HAUTEUR_GROUPE;
	}
	//TEST_CONSOLE
	public String toStringEtapeGenerationLaby() {
		return "oui";
	}
}