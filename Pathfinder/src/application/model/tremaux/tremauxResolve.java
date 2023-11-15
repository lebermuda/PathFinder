package application.model.tremaux;

import java.util.ArrayList;

import application.Main;
import application.model.Piece;
import application.model.arbreLabyrinthe;
import application.view.Case;
import javafx.scene.paint.Color;

public class tremauxResolve extends arbreLabyrinthe{
	
	public tremauxResolve(int nombrePieceLargeur, int nombrePieceHauteur) {
		super(nombrePieceLargeur, nombrePieceHauteur);
	}
	public tremauxResolve(arbreLabyrinthe grapheLaby) {
		super(grapheLaby.getNombrePieceLargeur(),grapheLaby.getNombrePieceHauteur());
		upgradeToMain(grapheLaby);
	}
	
	@Override
	public void generer(Case[][] labyRpz) {
		Case[] parallelChange=new Case[0];
		Color couleur= Color.ORANGE;
		int securite=1; //sert aussi à compter le nombre d'étape
		setVisiteFalse();
		
		//Initialisation : partir du départ et marquer la sortie
		Piece actualPiece = getGrillePiece()[Main.infoLaby.getDepart()[0]][Main.infoLaby.getDepart()[1]];
		actualPiece = new pieceTremaux(actualPiece);
		setGrillePieceIJ(actualPiece);
		String direction=premiereDirection(actualPiece);
		marquerCheminSorti((pieceTremaux) actualPiece,direction);

		//animation
		parallelChange=addCase(parallelChange, porteDirection(actualPiece,direction,labyRpz));
		getEtapeResolution().add(parallelChange);
		setEtapeResolutionCouleur(addColor(getEtapeResolutionCouleur(),couleur));
		actualPiece.setVisite(true);
		
		actualPiece=changerDeDirection(actualPiece,direction);
		actualPiece.setVisite(true);
		
		while ((actualPiece!=getGrillePiece()[Main.infoLaby.getArrive()[0]][Main.infoLaby.getArrive()[1]])&&(securite<500)) {
			parallelChange=new Case[0];
			
			if (pieceEstJonction(actualPiece)) {
				//Si c'est la première fois que l'on passe par cette jonction on la transforme en pieceTremaux
				if (! (actualPiece instanceof pieceTremaux)) {
					actualPiece = new pieceTremaux(actualPiece);
					setGrillePieceIJ(actualPiece);
				}

				marquerCheminArrive((pieceTremaux) actualPiece,direction);

				direction=choisirDirection((pieceTremaux) actualPiece,direction);

				marquerCheminSorti((pieceTremaux) actualPiece,direction);

				couleur=choixCouleur((pieceTremaux) actualPiece, direction);
				}
			//Sinon rien à faire on traverse la pièce de Nord <-> Sud et Ouest <-> Est donc direction ne change passe
			
			//Animation (piece +porte)
			parallelChange=addCase(parallelChange, labyRpz[actualPiece.getIGridIndex()][actualPiece.getJGridIndex()]);
			parallelChange=addCase(parallelChange, porteDirection(actualPiece,direction,labyRpz));
			getEtapeResolution().add(parallelChange);
			setEtapeResolutionCouleur(addColor(getEtapeResolutionCouleur(),couleur));
			
			if (actualPiece==getGrillePiece()[Main.infoLaby.getDepart()[0]][Main.infoLaby.getDepart()[1]]) {
				parallelChange=new Case[0];
				parallelChange=addCase(parallelChange, labyRpz[actualPiece.getIGridIndex()][actualPiece.getJGridIndex()]);
				getEtapeResolution().add(parallelChange);
				setEtapeResolutionCouleur(addColor(getEtapeResolutionCouleur(),Color.GREEN));
			}
			//Passer à la pièce suivante
			actualPiece=changerDeDirection(actualPiece,direction);
			securite++;
			actualPiece.setVisite(true);
		}
		Main.infoLaby.setI_RESULTATS_TEMPS(securite, 3);
		calculTauxVisite(3);
	}
	
	private Color choixCouleur(pieceTremaux actualPiece, String direction) {
	//Colorier en orange si s'est la première fois qu'on passe en Gris sinon
		if (actualPiece==getGrillePiece()[Main.infoLaby.getDepart()[0]][Main.infoLaby.getDepart()[1]]) {
			return Color.ORANGE;
		}
		else if (actualPiece.getCompteurs(direction)>=2 ) {
			return Color.rgb(200,100,0);
		}
		else {
			return Color.ORANGE;
		}
	}
	private Case porteDirection(Piece piece, String direction,Case[][] labyRpz) {
	//Definir la case en fonction de la direction	
		if(direction.equals("Nord")) {
			return labyRpz[piece.getIGridIndex()-1][piece.getJGridIndex()];
		}
		else if(direction.equals("Est")) {
			return labyRpz[piece.getIGridIndex()][piece.getJGridIndex()+1];
		}
		else if(direction.equals("Sud")) {
			return labyRpz[piece.getIGridIndex()+1][piece.getJGridIndex()];
		}
		else if(direction.equals("Ouest")) {
			return labyRpz[piece.getIGridIndex()][piece.getJGridIndex()-1];
		}
		else {
			System.out.println("Erreur dans la selection de la porte à coloriser => TremauxResolve : "+direction);
			return null;
		}
	}

	private void marquerCheminArrive(pieceTremaux pieceT,String direction) {
	//incrémentation du marquage du chemin en arrivant à la jonction
		if(direction.equals("Nord")) {
			pieceT.setCompteurSud(pieceT.getCompteurSud()+1);
		}
		else if(direction.equals("Est")) {
			pieceT.setCompteurOuest(pieceT.getCompteurOuest()+1);
		}
		else if(direction.equals("Sud")) {
			pieceT.setCompteurNord(pieceT.getCompteurNord()+1);
		}
		else if(direction.equals("Ouest")) {
			pieceT.setCompteurEst(pieceT.getCompteurEst()+1);
		}
		else {
			System.out.println("Erreur de l'incrémentation du marquage du chemin en arrivé de jonction=> TremauxResolve : "+direction);
		}
	}
	
	private void marquerCheminSorti(pieceTremaux pieceT,String direction) {
	//incrémentation du marquage du chemin en sorti de jonction
		if(direction.equals("Nord")) {
			pieceT.setCompteurNord(pieceT.getCompteurNord()+1);
		}
		else if(direction.equals("Est")) {
			pieceT.setCompteurEst(pieceT.getCompteurEst()+1);
		}
		else if(direction.equals("Sud")) {
			pieceT.setCompteurSud(pieceT.getCompteurSud()+1);
		}
		else if(direction.equals("Ouest")) {
			pieceT.setCompteurOuest(pieceT.getCompteurOuest()+1);
		}
		else {
			System.out.println("Erreur de l'incrémentation du marquage du chemin en sorti de jonction=> TremauxResolve : "+direction);
		}
	}
	
	private String premiereDirection(Piece actualPiece) {
	//détermine  la première direction à prendre
		ArrayList<String> firstChoice =directionPossible(actualPiece);
		double number =Math.random()*firstChoice.size();
		return firstChoice.get((int)number);
	}

	private String directionOppose(String direction) {
		if(direction.equals("Nord")) {
			return "Sud";
		}
		else if(direction.equals("Est")) {
			return "Ouest";
		}
		else if(direction.equals("Sud")) {
			return "Nord";
		}
		else if(direction.equals("Ouest")) {
			return "Est";
		}
		else {
			System.out.println("Erreur dans traverser piece => TremauxResolve : "+direction);
			return null;
		}
	}

	private boolean pieceEstJonction(Piece piece) {
	//vérifier si une pièce est une jonction
		
		//il existe au moins un coude
		if (piece.getPorteNord() || piece.getPorteSud()) {
			if (piece.getPorteOuest() || piece.getPorteEst()) {
				return true;
			}
		}
		//gestion des culs de sac
		if ( !piece.getPorteNord() && !piece.getPorteEst() && !piece.getPorteSud()) {
			return true;
		}
		if ( !piece.getPorteNord() && !piece.getPorteEst() && !piece.getPorteOuest()) {
			return true;
		}
		if ( !piece.getPorteNord() && !piece.getPorteOuest() && !piece.getPorteSud()) {
			return true;
		}
		if ( !piece.getPorteOuest() && !piece.getPorteEst() && !piece.getPorteSud()) {
			return true;
		}
		return false;
	}
	
	private String choisirDirection(pieceTremaux pieceT,String direction) {
	//Condition sur le choix de direction aux différentes jonctions 
		ArrayList<String> directionsPossible=new ArrayList<String>();
		ArrayList<String> directions=new ArrayList<String>();
		directions.add("Nord");
		directions.add("Est");
		directions.add("Sud");
		directions.add("Ouest");
		//Enlever les directions qui ne sont pas possible : porte pas ouverte ou si on est déjà passé deux fois
		for(String cur : directions) {
			if(pieceT.getCompteurs(cur)<2) {
				directionsPossible.add(cur);
			}
		}
		
		//Clone du tableau sans aucun lien
		ArrayList<String> directionsVierges= new ArrayList<String>(directionsPossible);
		//Selectionner les directions jamais prises 
		for (String cur : directionsPossible) {
			if(pieceT.getCompteurs(cur)>=1) {
				directionsVierges.remove(cur);
			}
		}
		//Choix aléatoire parmi directionsVierges
		if (directionsVierges.size()!=0) {
			int number =(int) (Math.random()*directionsVierges.size());
			return directionsVierges.get(number);
		}
		
		else {
			if (pieceT.getCompteurs(directionOppose(direction))==1) {
				return directionOppose(direction);
			}
			else {
				int number =(int) (Math.random()*directionsPossible.size());
				return directionsPossible.get(number);
			}
		}
	}

	private Piece changerDeDirection(Piece piece,String direction) {
		if(direction.equals("Nord")) {
			return piece.getVoisinNord();
		}
		else if(direction.equals("Est")) {
			return piece.getVoisinEst();
		}
		else if(direction.equals("Sud")) {
			return piece.getVoisinSud();
		}
		else if(direction.equals("Ouest")) {
			return piece.getVoisinOuest();
		}
		else {
			System.out.println("Erreur dans le changement de direction => TremauxResolve");
			return null;
		}
	}
	private ArrayList<String> directionPossible(Piece piece) {
		ArrayList<String> directionPossible= new ArrayList<String>();
		if (piece.getPorteNord()) {
			directionPossible.add("Nord");
		}
		if (piece.getPorteEst()) {
			directionPossible.add("Est");
		}
		if (piece.getPorteSud()) {
			directionPossible.add("Sud");
		}
		if (piece.getPorteOuest()) {
			directionPossible.add("Ouest");
		}
		return directionPossible;
	}
	
	public void testJonction() {
		System.out.println(premiereDirection(getGrillePiece()[Main.infoLaby.getDepart()[0]][Main.infoLaby.getDepart()[1]]));
	}
}
