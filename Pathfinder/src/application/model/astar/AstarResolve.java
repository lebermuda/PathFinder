package application.model.astar;


import java.util.ArrayList;
import application.Main;
import application.model.Piece;
import application.model.arbreLabyrinthe;
import application.view.Case;
import javafx.scene.paint.Color;
import application.model.astar.pieceAstar;


public class AstarResolve extends arbreLabyrinthe{
	
	
	public AstarResolve(int nombrePieceLargeur, int nombrePieceHauteur) {
		super(nombrePieceLargeur, nombrePieceHauteur);
	}
	public AstarResolve(arbreLabyrinthe grapheLaby) {
		super(grapheLaby.getNombrePieceLargeur(),grapheLaby.getNombrePieceHauteur());
		upgradeToMain(grapheLaby);
	}
	
	
	public void generer(Case[][] labyRpz) {
		setVisiteFalse();
		Case[] parallelChange=new Case[0];// ça sert à quoi?
		
		ArrayList<pieceAstar> closeList =new ArrayList<pieceAstar>(); //pièce déjà visitée, le chemin est tracé à partir des parents de ces pièces
		ArrayList<pieceAstar> openList =new ArrayList<pieceAstar>();// pièces envisagées pour le prochain deplacement
		
		
		int[] finish = Main.infoLaby.getArrive();
		
		Piece current = getGrillePiece()[Main.infoLaby.getDepart()[0]][Main.infoLaby.getDepart()[1]];
		pieceAstar currently = new pieceAstar(current);
		setGrillePieceIJ(currently);
		currently.g = 0;
		currently.setVisite(true);
		int tour = 0;
		parallelChange = addCase(parallelChange, labyRpz[currently.getIGridIndex()][currently.getJGridIndex()]);
		
		getEtapeResolution().add(parallelChange);
		setEtapeResolutionCouleur(addColor(getEtapeResolutionCouleur(), Color.GREEN));
		
		
		while (((currently.getiIndexPiece()!= finish[0]) || (currently.getjIndexPiece()!= finish[1])) && tour<900) { //boucle while condition: tant que le noeud courrant n'est pas l'arrivée  
			//closeList.add(currently);
			parallelChange = new Case[0];
	
			if (currently.getPorteEst()){//on regarde quelles portes sont ouvertes autour de la position actuelle
				if (! (currently.getVoisinEst() instanceof pieceAstar)) {
					pieceAstar voisinEst = new pieceAstar(currently.getVoisinEst());//on convertie les voisin en piece astar on remplace la piece par la pieceAstar avec setGrille et on l'ajoute à l'openList
					setGrillePieceIJ(voisinEst);
					if(!(voisinEst.contenuDans(openList)) && !(voisinEst.contenuDans(closeList))) {//vérifier place "!"
						openList.add(voisinEst);
						//on ajoute currently comme parent des cases alentour, penser à comparer plus tard si il y a déjà un parent pour cette case et garder le g le plus petit
						bestparent(currently, voisinEst);//on met à jour g si on a trouvé le chemin le plus court vers cette case
						parallelChange=addCase(parallelChange, porteDuParent(labyRpz,voisinEst));
					}
				}
			}	
			
			if (currently.getPorteNord()) {
				if (! (currently.getVoisinNord() instanceof pieceAstar)) {
					pieceAstar voisinNord = new pieceAstar(currently.getVoisinNord());
					setGrillePieceIJ(voisinNord);
					if(!(voisinNord.contenuDans(openList)) && !(voisinNord.contenuDans(closeList))) {
						openList.add(voisinNord);
						bestparent(currently, voisinNord);
						parallelChange=addCase(parallelChange, porteDuParent(labyRpz,voisinNord));
					}	
				}
			}
			
			if (currently.getPorteOuest()) {
				if (! (currently.getVoisinOuest() instanceof pieceAstar)) {
					pieceAstar voisinOuest = new pieceAstar(currently.getVoisinOuest());
					setGrillePieceIJ(voisinOuest);
					if(!(voisinOuest.contenuDans(openList)) && !(voisinOuest.contenuDans(closeList))) {
						openList.add(voisinOuest);
						bestparent(currently, voisinOuest);
						parallelChange=addCase(parallelChange, porteDuParent(labyRpz,voisinOuest));
					}
				}
			}
			
			if (currently.getPorteSud()) {
				if (! (currently.getVoisinSud() instanceof pieceAstar)) {
			
					pieceAstar voisinSud = new pieceAstar(currently.getVoisinSud());
					setGrillePieceIJ(voisinSud);
					if(!(voisinSud.contenuDans(openList)) && !(voisinSud.contenuDans(closeList))) {
						openList.add(voisinSud);
						//voisinSud.parent = currently;
						bestparent(currently, voisinSud);
						parallelChange=addCase(parallelChange, porteDuParent(labyRpz,voisinSud));
					}
				}
			}
			//Animation des portes vers l'extérieur de closeList
			getEtapeResolution().add(parallelChange);
			setEtapeResolutionCouleur(addColor(getEtapeResolutionCouleur(), Color.GREY));
			parallelChange=new Case[0];
			
			currently = getTheClosest(openList); //on se met sur la case avec la h le plus petit et on recommence
			closeList.add(currently);
			currently.setVisite(true);
			retirer(openList, currently); //on retire currently de l'openList
			
			tour +=1;
				
			parallelChange = addCase(parallelChange, labyRpz[currently.getIGridIndex()][currently.getJGridIndex()]);
			parallelChange=addCase(parallelChange, porteDuParent(labyRpz,currently));
			getEtapeResolution().add(parallelChange);
			setEtapeResolutionCouleur(addColor(getEtapeResolutionCouleur(), Color.rgb(200,100,0)));
		}
		parallelChange=new Case[0];
		parallelChange = addCase(parallelChange, labyRpz[currently.getIGridIndex()][currently.getJGridIndex()]);
		getEtapeResolution().add(parallelChange);
		setEtapeResolutionCouleur(addColor(getEtapeResolutionCouleur(), Color.RED));
		
		plusCourtChemin(currently,labyRpz);
		calculTauxVisite(2);
		Main.infoLaby.setI_RESULTATS_TEMPS(tour,2);
		
		System.out.println("vous êtes à l'arrivée, nombre de tours"+tour);
		System.out.println("départ"+Main.infoLaby.getDepart()[0]+";"+Main.infoLaby.getDepart()[1]);
	}
	
	public pieceAstar getTheClosest(ArrayList<pieceAstar> openList) {//prend la piece astar dans l'openlist qui a le plus petit f
		double fmin = Double.POSITIVE_INFINITY;
		int indice = 0;
		for(int i =0; i < openList.size(); i++) {
			openList.get(i).f = openList.get(i).g + openList.get(i).h*2;
			int openf = openList.get(i).f;
			if (openf < fmin) {
				fmin = openf;
				indice = i;
			}
			
		}
		return openList.get(indice);
	}
	
	public void bestparent(pieceAstar currently, pieceAstar voisin ) { //vérifie que le parent proposé est le meilleur et donc permet d'identifier le plus court chemin
		if(voisin.gotParent) {
			if (voisin.g+1 > currently.g ) {
				voisin.parent = currently;
				voisin.g = currently.g+1; 
			}
		} else {
			voisin.parent = currently;
			voisin.g = currently.g+1;
		}
	}
	
	public void retirer(ArrayList<pieceAstar> list, pieceAstar piece) {
		if (piece.contenuDans(list)) {
			for (int i = 0; i < list.size(); i++) {
				if ((list.get(i).getiIndexPiece() == piece.getiIndexPiece()	&& list.get(i).getjIndexPiece() == piece.getjIndexPiece())) {
						list.remove(i);
				}
			}
		
		}
	}
	
	
	public void plusCourtChemin(pieceAstar actuelle,Case[][] labyRpz) {//remonter les parents depuis l'arrivée pour afficher le plus court chemin
		Case[] parallelChange=new Case[0];
		parallelChange=addCase(parallelChange, porteDuParent(labyRpz,actuelle));
		getEtapeResolution().add(parallelChange);
		setEtapeResolutionCouleur(addColor(getEtapeResolutionCouleur(), Color.ORANGE));
		int secu=0;
		pieceAstar mere;
		//Case[] arrivee=new Case[0];//pour colorier le parent de l'arrivée
		//arrivee= addCase(arrivee, porteDuParent(labyRpz,actuelle.parent));
		//getEtapeResolution().add(arrivee);
		//setEtapeResolutionCouleur(addColor(getEtapeResolutionCouleur(), Color.PURPLE));
		
		while(((actuelle.getiIndexPiece()!=Main.infoLaby.getDepart()[0])||(actuelle.getjIndexPiece()!=Main.infoLaby.getDepart()[1]))&& secu<500) {//tant qu'on on est pas au départ
			
			mere = actuelle.parent;
			parallelChange=new Case[0];
			if((mere.getiIndexPiece()!=Main.infoLaby.getDepart()[0])|| mere.getjIndexPiece()!=Main.infoLaby.getDepart()[1]) {
				parallelChange = addCase(parallelChange, labyRpz[mere.getIGridIndex()][mere.getJGridIndex()]);
				parallelChange=addCase(parallelChange, porteDuParent(labyRpz,mere));
				getEtapeResolution().add(parallelChange);
				setEtapeResolutionCouleur(addColor(getEtapeResolutionCouleur(), Color.ORANGE));
			}
			actuelle = mere;
			secu+=1;
			//System.out.println("remontée tour:"+ secu);
		}
	}
	
	private Case porteDuParent(Case[][] labyRpz,pieceAstar piece) {
        if (piece.parent.equals(piece.getVoisinNord())) {
            return labyRpz[piece.getIGridIndex()-1][piece.getJGridIndex()];
        }
        if (piece.parent.equals(piece.getVoisinEst())) {
            return labyRpz[piece.getIGridIndex()][piece.getJGridIndex()+1];
        }
        if (piece.parent.equals(piece.getVoisinSud())) {
            return labyRpz[piece.getIGridIndex()+1][piece.getJGridIndex()];
        }
        if (piece.parent.equals(piece.getVoisinOuest())) {
            return labyRpz[piece.getIGridIndex()][piece.getJGridIndex()-1];
        }
        else {
            return null;
        }
    }
	
	
	
}









//colorier les portes
//pourquoi il s'éloigne encore trop au lieu d'éssayer de se rapprocher tout le temps? ->problème dans l'interprétation de la distance? pondération?

//pondere h et g (vraiment nécéssaire ?)