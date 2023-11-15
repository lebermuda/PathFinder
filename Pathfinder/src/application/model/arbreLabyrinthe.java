package application.model;

import java.util.LinkedList;

import application.Main;
import application.view.Case;
import javafx.scene.paint.Color;

public abstract class arbreLabyrinthe {
	private Piece[][] grillePiece;
	final private int largeurLaby;
	final private int hauteurLaby;
	final private int nombrePieceLargeur;
	final private int nombrePieceHauteur;
	private LinkedList<Case[]> etapeGeneration;//étape de génération pour les séquences d'animation
	private Color[] etapeGenerationCouleur ;
	private LinkedList<Case[]> etapeResolution;//étape de résolution pour les séquences d'animation
	private Color[] etapeResolutionCouleur ;
	
	abstract public void generer(Case[][] labyRpz);
	
	public arbreLabyrinthe(int nombrePieceLargeur, int nombrePieceHauteur) {
		this.largeurLaby =nombrePieceLargeur*2+1; // *2+1 --> mur entre chaque piece
		this.hauteurLaby = nombrePieceHauteur*2+1;
		this.nombrePieceLargeur=nombrePieceLargeur;
		this.nombrePieceHauteur=nombrePieceHauteur;
		setGrillePiece(new Piece[getNombrePieceHauteur()][getNombrePieceLargeur()]);

		for (int i=0;i<getNombrePieceHauteur();i++) {
			for (int j=0;j<getNombrePieceLargeur();j++) {
				getGrillePiece()[i][j]= new Piece(i,j,20);
			}
		}
		creationArbreLaby(); //créé une LinkedList, on peut passer d'un voisin à l'autre
		setEtapeGeneration(new LinkedList<Case[]>());
		setEtapeGenerationCouleur(new Color[0]);
		setEtapeResolution(new LinkedList<Case[]>());
		setEtapeResolutionCouleur(new Color[0]);
		
	}
	
	public void creationArbreLaby() {
		for (int i=0;i<getNombrePieceHauteur();i++) {
			for (int j=0;j<getNombrePieceLargeur();j++) {
				setVoisinsConditionne(i,j);
			}
		}
	}
	
	public void setVisiteFalse() {
		for (int i=0;i<getNombrePieceHauteur();i++) {
			for (int j=0;j<getNombrePieceLargeur();j++) {
				getGrillePiece()[i][j].setVisite(false);
			}
		}
	}
	
	public void calculTauxVisite(int IDalgo) {
		int piecesVisite=0;
		for (int i=0;i<getNombrePieceHauteur();i++) {
			for (int j=0;j<getNombrePieceLargeur();j++) {
				if (getGrillePiece()[i][j].getVisite()) {
					piecesVisite++;
				}
			}
		}
		Main.infoLaby.setI_RESULTATS_TAUX_VISITE((double) piecesVisite/(getNombrePieceHauteur()*getNombrePieceLargeur()),IDalgo);
	}
	
	private void setVoisinsConditionne(int i, int j) {
		if (i==0) { //1ère ligne donc tout en haut
			if (j==0) {//coin haut gauche ouvert seulement à l'est et au sud
				getGrillePiece()[i][j].setVoisins(null,getGrillePiece()[i][j+1],getGrillePiece()[i+1][j],null);
			}
			else if (j==getNombrePieceLargeur()-1) {//coin haut droit ouvert seulement au sud et à l'ouest
				getGrillePiece()[i][j].setVoisins(null, null, getGrillePiece()[i+1][j], getGrillePiece()[i][j-1]);
			}
			else {//le reste blocqué au nord
				getGrillePiece()[i][j].setVoisins(null,getGrillePiece()[i][j+1],getGrillePiece()[i+1][j], getGrillePiece()[i][j-1]);
			}
		}
		else if (i==getNombrePieceHauteur()-1) {//dernière ligne donc tout en bas
			if (j==0) {//coin gauche
				getGrillePiece()[i][j].setVoisins(getGrillePiece()[i-1][j],getGrillePiece()[i][j+1],null,null);
			}
			else if (j==getNombrePieceLargeur()-1) {//coin droit
				getGrillePiece()[i][j].setVoisins(getGrillePiece()[i-1][j],null,null, getGrillePiece()[i][j-1]);
			}
			else {//reste
				getGrillePiece()[i][j].setVoisins(getGrillePiece()[i-1][j],getGrillePiece()[i][j+1],null, getGrillePiece()[i][j-1]);
			}
		}
		else if (j==0) {//1ère colonne donc tout à gauche
			getGrillePiece()[i][j].setVoisins(getGrillePiece()[i-1][j],getGrillePiece()[i][j+1],getGrillePiece()[i+1][j], null);
		}
		else if (j==getNombrePieceLargeur()-1) {//dernière colonne donc tout à droite
			getGrillePiece()[i][j].setVoisins(getGrillePiece()[i-1][j],null,getGrillePiece()[i+1][j], getGrillePiece()[i][j-1]);
		}
		else {//intérieur
			getGrillePiece()[i][j].setVoisins(getGrillePiece()[i-1][j],getGrillePiece()[i][j+1],getGrillePiece()[i+1][j], getGrillePiece()[i][j-1]);
		}
	}
	
	public void departAleatoire() {
		if(Main.infoLaby.getTYPE_SELECT_DEPART().equals("Aléatoire")) {
			//choix, stockage et coloration Depart et Arrivé (Il faudra diférencier depart et arriver)
			int[] depart = {(int)(Math.random()*getNombrePieceHauteur()),(int) (Math.random()*getNombrePieceLargeur())};
			int[] arrive = {(int)(Math.random()*getNombrePieceHauteur()),(int) (Math.random()*getNombrePieceLargeur())};
			while ((depart[0]==arrive[0])&&(depart[1]==arrive[1])) {
				arrive[0]=(int)(Math.random()*getNombrePieceHauteur());
				arrive[1]=(int) (Math.random()*getNombrePieceLargeur());
			}
			Main.infoLaby.setDepart(depart);
			Main.infoLaby.setArrive(arrive);
		}
	}
	
	public String toString() {
		String rep="#";
		for (int j=0;j<getNombrePieceLargeur();j++) {
			rep+="##";
		}
		for (int i=0;i<getNombrePieceHauteur();i++) {
			rep+="\n#";
			//Regarde que Est
			for (int j=0;j<getNombrePieceLargeur();j++) {
				if (getGrillePiece()[i][j].getPorteEst()) {
					rep+="..";
				}
				else {
					rep+=".#";
				}
			}
			rep+="\n#";
			//Regarde que Sud
			for (int j=0;j<getNombrePieceLargeur();j++) {
				if (getGrillePiece()[i][j].getPorteSud()) {
					rep+=".#";
				}
				else {
					rep+="##";
				}
			}
		}
		return rep;
	}
	
	public Case[] addCase(Case[] tab,Case plusCase) {
		Case[] newTab=new Case[tab.length+1];
		for (int i=0;i<tab.length;i++) {
			newTab[i]=tab[i];
		}
		newTab[tab.length]=plusCase;
		return newTab;
	}
	
	public Color[] addColor(Color[] tab,Color plusColor) {
		Color[] newTab=new Color[tab.length+1];
		for (int i=0;i<tab.length;i++) {
			newTab[i]=tab[i];
		}
		newTab[tab.length]=plusColor;
		return newTab;
	}
	//getter and setter
	public int getLargeurLaby() {
		return largeurLaby;
	}
	public int getHauteurLaby() {
		return hauteurLaby;
	}

	public int getNombrePieceLargeur() {
		return nombrePieceLargeur;
	}

	public int getNombrePieceHauteur() {
		return nombrePieceHauteur;
	}

	public Piece[][] getGrillePiece() {
		return grillePiece;
	}

	public void setGrillePiece(Piece[][] grillePiece) {
		this.grillePiece = grillePiece;
	}
	
	public void setGrillePieceIJ(Piece piece) {
	//Intégrer une pièce dans le graphe
		this.grillePiece[piece.getiIndexPiece()][piece.getjIndexPiece()] = piece;
		if (this.grillePiece[piece.getiIndexPiece()][piece.getjIndexPiece()].getVoisinNord()!=null) {
			this.grillePiece[piece.getiIndexPiece()][piece.getjIndexPiece()].getVoisinNord().setVoisinSud(piece);
		}
		if (this.grillePiece[piece.getiIndexPiece()][piece.getjIndexPiece()].getVoisinEst()!=null) {
			this.grillePiece[piece.getiIndexPiece()][piece.getjIndexPiece()].getVoisinEst().setVoisinOuest(piece);
		}
		if (this.grillePiece[piece.getiIndexPiece()][piece.getjIndexPiece()].getVoisinSud()!=null) {
			this.grillePiece[piece.getiIndexPiece()][piece.getjIndexPiece()].getVoisinSud().setVoisinNord(piece);
		}
		if (this.grillePiece[piece.getiIndexPiece()][piece.getjIndexPiece()].getVoisinOuest()!=null) {
			this.grillePiece[piece.getiIndexPiece()][piece.getjIndexPiece()].getVoisinOuest().setVoisinEst(piece);
		}
	}

	public LinkedList<Case[]> getEtapeGeneration() {
		return etapeGeneration;
	}

	public void setEtapeGeneration(LinkedList<Case[]> etapeGeneration) {
		this.etapeGeneration = etapeGeneration;
	}

	public Color[] getEtapeGenerationCouleur() {
		return etapeGenerationCouleur;
	}

	public void setEtapeGenerationCouleur(Color[] etapeGenerationCouleur) {
		this.etapeGenerationCouleur = etapeGenerationCouleur;
	}

	public LinkedList<Case[]> getEtapeResolution() {
		return etapeResolution;
	}

	public void setEtapeResolution(LinkedList<Case[]> etapeResolution) {
		this.etapeResolution = etapeResolution;
	}

	public Color[] getEtapeResolutionCouleur() {
		return etapeResolutionCouleur;
	}

	public void setEtapeResolutionCouleur(Color[] etapeResolutionCouleur) {
		this.etapeResolutionCouleur = etapeResolutionCouleur;
	}
	public void upgradeToMain(arbreLabyrinthe graphe) {
		setGrillePiece(graphe.getGrillePiece());
		setEtapeGeneration(graphe.getEtapeGeneration());
		setEtapeGenerationCouleur(graphe.getEtapeGenerationCouleur());
	}

}
