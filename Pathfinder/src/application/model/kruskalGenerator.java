package application.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import application.view.Case;
import javafx.scene.paint.Color;

public class kruskalGenerator extends arbreLabyrinthe{
	ArrayList<LinkedList<Case>> sameKruskalIndex = new ArrayList<LinkedList<Case>>();//creer un sac de n sacs des kruskalIndex
			
	public kruskalGenerator(int nombrePieceLargeur, int nombrePieceHauteur) {
		super(nombrePieceLargeur, nombrePieceHauteur);
		//initialisation
		for (int i=0;i<getNombrePieceHauteur();i++) {
			for (int j=0;j<getNombrePieceLargeur();j++) {
				//Attribution d'un nombre différent à chaque case 
				getGrillePiece()[i][j].setKruskalIndex(i*getNombrePieceLargeur()+j);
				sameKruskalIndex.add(new LinkedList<Case>());
				sameKruskalIndex.get(i*getNombrePieceLargeur()+j).add(getGrillePiece()[i][j]);
			}
		}
	}

	@Override
	public void generer(Case[][] labyRpz) {
		int n=getNombrePieceHauteur()*getNombrePieceLargeur();
		int k=0;
		int[] randomMur=new int[3];
		int red;
		int green;
		int blue;
				
		while(k<n-1) { 
			//on choisit n-1 murs compatibles au hasard défini par une pièce et une de ses portes
			randomMur[0]=(int) (Math.random()*(getNombrePieceHauteur()));//i
			randomMur[1]=(int) (Math.random()*(getNombrePieceLargeur()));//j
			randomMur[2]=(int) (Math.random()*4);// 0:Nord,1:Est,2:Sud,3:Ouest
			
			//Attribution d'une couleur différente pour chaque étape
			// (rd*200) + 55 pour éviter les couleurs noir
			red=(int) (Math.random()*200)+55;
			green=(int) (Math.random()*200)+55;
			blue=(int) (Math.random()*200)+55;
			
			Case[] parallelChange=new Case[0];
			
			if (conditionRaccordementKruskal(randomMur)) {
				//Afficher le mur sélectionné en jaune
				switch(randomMur[2]) {
					case 0://Nord
						parallelChange= addCase(parallelChange,labyRpz[randomMur[0]*2][randomMur[1]*2+1]);
						break;
					case 1://Est
						parallelChange= addCase(parallelChange,labyRpz[randomMur[0]*2+1][randomMur[1]*2+2]);
						break;
					case 2://Sud
						parallelChange= addCase(parallelChange,labyRpz[randomMur[0]*2+2][randomMur[1]*2+1]);
						break;
					case 3://Ouest
						parallelChange= addCase(parallelChange,labyRpz[randomMur[0]*2+1][randomMur[1]*2]);
						break;
				}
				getEtapeGeneration().add(parallelChange);
				setEtapeGenerationCouleur(addColor(getEtapeGenerationCouleur(),Color.YELLOW));
				
				//Algorithme de Kruskal
				ouvrirPorte(randomMur);
				k++;
				
				//Animation de Kruskal
				setEtapeGenerationCouleur(addColor(getEtapeGenerationCouleur(),Color.rgb(red,green,blue)));
				parallelChange=changementCouleurKruskalIndex(randomMur,parallelChange,labyRpz);
				getEtapeGeneration().add(parallelChange);
			}
		}	
	}
	
	private Case[] changementCouleurKruskalIndex(int[] rd,Case[] parallelChange, Case[][] labyRpz) {
	//construit les séquences d'animations en parallèle => colorie tous les changements de l'étapes
		Iterator<Case> it;
		it=sameKruskalIndex.get(getGrillePiece()[rd[0]][rd[1]].getKruskalIndex()).iterator();
		while (it.hasNext()) {
			Piece cur = (Piece) it.next();
			parallelChange=addCase(parallelChange,labyRpz[cur.getIGridIndex()][cur.getJGridIndex()]);
			if (cur.getPorteEst()) {
				parallelChange=addCase(parallelChange,labyRpz[cur.getIGridIndex()][cur.getJGridIndex()+1]);
			}
			if (cur.getPorteSud()) {
				parallelChange=addCase(parallelChange,labyRpz[cur.getIGridIndex()+1][cur.getJGridIndex()]);
			}
		}
		return parallelChange;
	}
	
	private void ouvrirPorte(int[] rd) { 
	// Ouvre les portes et Mise A Jour des kruskalIndex ainsi que du tableau de kruskalIndex
		Iterator<Case> it;
		switch(rd[2]) {
			case 0://Nord
				getGrillePiece()[rd[0]][rd[1]].setPorteNord(true);//ouvre la porte Nord de la case
				getGrillePiece()[rd[0]][rd[1]].getVoisinNord().setPorteSud(true);// ouvre la porte Sud du voisin Nord
				
				//on mets toutes les cases à l'index de la case depuis laquelle on a ouvert le mur :
				//on addAll les cases possédant le même index que la case voisine nouvellement reliée dans sameKruskalIndex
				//Sur chaque case possédant l'ancien index, on attribut l'index de la case actuelle
				sameKruskalIndex.get(getGrillePiece()[rd[0]][rd[1]].getKruskalIndex()).addAll(sameKruskalIndex.get(getGrillePiece()[rd[0]][rd[1]].getVoisinNord().getKruskalIndex()));
				it = sameKruskalIndex.get(getGrillePiece()[rd[0]][rd[1]].getVoisinNord().getKruskalIndex()).iterator();
				while (it.hasNext()) {
					Piece cur =(Piece) it.next();
					cur.setKruskalIndex(getGrillePiece()[rd[0]][rd[1]].getKruskalIndex());
				}
				break;
			case 1://Est
				getGrillePiece()[rd[0]][rd[1]].setPorteEst(true);
				getGrillePiece()[rd[0]][rd[1]].getVoisinEst().setPorteOuest(true);

				sameKruskalIndex.get(getGrillePiece()[rd[0]][rd[1]].getKruskalIndex()).addAll(sameKruskalIndex.get(getGrillePiece()[rd[0]][rd[1]].getVoisinEst().getKruskalIndex()));
				it = sameKruskalIndex.get(getGrillePiece()[rd[0]][rd[1]].getVoisinEst().getKruskalIndex()).iterator();
				while (it.hasNext()) {
					Piece cur = (Piece) it.next();
					cur.setKruskalIndex(getGrillePiece()[rd[0]][rd[1]].getKruskalIndex());
				}
				break;
			case 2://Sud
				getGrillePiece()[rd[0]][rd[1]].setPorteSud(true);
				getGrillePiece()[rd[0]][rd[1]].getVoisinSud().setPorteNord(true);
				
				sameKruskalIndex.get(getGrillePiece()[rd[0]][rd[1]].getKruskalIndex()).addAll(sameKruskalIndex.get(getGrillePiece()[rd[0]][rd[1]].getVoisinSud().getKruskalIndex()));
				it = sameKruskalIndex.get(getGrillePiece()[rd[0]][rd[1]].getVoisinSud().getKruskalIndex()).iterator();
				while (it.hasNext()) {
					Piece cur = (Piece) it.next();
					cur.setKruskalIndex(getGrillePiece()[rd[0]][rd[1]].getKruskalIndex());
				}
				break;
			case 3://Ouest
				getGrillePiece()[rd[0]][rd[1]].setPorteOuest(true);
				getGrillePiece()[rd[0]][rd[1]].getVoisinOuest().setPorteEst(true);
				
				sameKruskalIndex.get(getGrillePiece()[rd[0]][rd[1]].getKruskalIndex()).addAll(sameKruskalIndex.get(getGrillePiece()[rd[0]][rd[1]].getVoisinOuest().getKruskalIndex()));
				it = sameKruskalIndex.get(getGrillePiece()[rd[0]][rd[1]].getVoisinOuest().getKruskalIndex()).iterator();
				while (it.hasNext()) {
					Piece cur = (Piece) it.next();
					cur.setKruskalIndex(getGrillePiece()[rd[0]][rd[1]].getKruskalIndex());
				}
				break;
		}
	}
	
	private boolean conditionRaccordementKruskal(int[] rd) { 
	//gère les erreurs aux bords et les murs qui séparent deux cases au même kruskalIndex
		switch(rd[2]) {
			case 0://Nord
				if (getGrillePiece()[rd[0]][rd[1]].getVoisinNord()==null) return false;
				else if (getGrillePiece()[rd[0]][rd[1]].getKruskalIndex()==getGrillePiece()[rd[0]][rd[1]].getVoisinNord().getKruskalIndex()) return false;
				break;
			case 1://Est
				if (getGrillePiece()[rd[0]][rd[1]].getVoisinEst()==null) return false;
				else if (getGrillePiece()[rd[0]][rd[1]].getKruskalIndex()==getGrillePiece()[rd[0]][rd[1]].getVoisinEst().getKruskalIndex()) return false;
				break;
			case 2://Sud
				if (getGrillePiece()[rd[0]][rd[1]].getVoisinSud()==null) return false;
				else if (getGrillePiece()[rd[0]][rd[1]].getKruskalIndex()==getGrillePiece()[rd[0]][rd[1]].getVoisinSud().getKruskalIndex()) return false;
				break;
			case 3://Ouest
				if (getGrillePiece()[rd[0]][rd[1]].getVoisinOuest()==null) return false;
				else if (getGrillePiece()[rd[0]][rd[1]].getKruskalIndex()==getGrillePiece()[rd[0]][rd[1]].getVoisinOuest().getKruskalIndex()) return false;
				break;
		}
		return true;
	}
	
	//TEST_CONSOLE
	public void show() {
		for (int i=0;i<getNombrePieceHauteur();i++) {
			for (int j=0;j<getNombrePieceLargeur();j++) {
				System.out.print(getGrillePiece()[i][j]);
			}
			System.out.println();
		}
	}
	
}

