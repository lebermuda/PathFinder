package application.model.mur_droit;



import application.Main;
import application.model.arbreLabyrinthe;
import application.model.Piece;
import application.view.Case;
import javafx.scene.paint.Color;



public class Mur_droitResolve extends arbreLabyrinthe{ 
		public Mur_droitResolve (int nombrePieceLargeur, int nombrePieceHauteur) {
			super(nombrePieceLargeur, nombrePieceHauteur);
		}

		public Mur_droitResolve(arbreLabyrinthe grapheLaby) {
			super(grapheLaby.getNombrePieceLargeur(), grapheLaby.getNombrePieceHauteur());
			upgradeToMain(grapheLaby);
		}

		public void generer(Case[][] labyRpz) {
			setVisiteFalse();
			Case[] parallelChange=new Case[0];
			Case[] parallelChange2=new Case[0];
			int compteur=1; 
			
		
		
			// Initialisation de la position de départ
		
			Piece position = getGrillePiece()[Main.infoLaby.getDepart()[0]][Main.infoLaby.getDepart()[1]];
			String direction = "Nord";
			position.setVisite(true);
			//Aventurier av = new Aventurier();
			//av.setInit(); 	// Par défaut l'aventurier commence face au nord

			// Initialisation de la sortie
			//Piece arrivee = getGrillePiece()[Main.infoLaby.getArrive()[0]][Main.infoLaby.getArrive()[1]];

			// Déplacement en suivant le mur droit
			while ( ((position.getiIndexPiece()!= Main.infoLaby.getArrive()[0]) || (position.getjIndexPiece()!=Main.infoLaby.getArrive()[1])) && (compteur<900) ) {
				parallelChange2=parallelChange.clone();
				parallelChange=new Case[0];
				
				
				//int[] card = av.Decision(position);   // L'algorithme choisit tout d'abord par où il doit passer
				//av.rotation(card);      // Rotation du repère de l'aventurier
				//position=Deplacement(position, labyRpz, card, parallelChange);   // Déplacement dans la pièce suivante
				
				
				direction=longeMurDroit(position, direction);
				parallelChange = addCase(parallelChange, colorierPorte(position, direction, labyRpz));
				position=getVoisins(position,direction);
				position.setVisite(true);
				
				//Animation :mets en jaune l'étape n-1, mets en bleu l'étape n (paralleChange) 
				getEtapeResolution().add(parallelChange2);
				setEtapeResolutionCouleur(addColor(getEtapeResolutionCouleur(),Color.YELLOW));
				
				parallelChange = addCase(parallelChange,labyRpz[position.getIGridIndex()][position.getJGridIndex()]);		// Animation graphique
				getEtapeResolution().add(parallelChange);
				setEtapeResolutionCouleur(addColor(getEtapeResolutionCouleur(),Color.BLUE));
				
				compteur++;
			}
			getEtapeResolution().add(parallelChange);
			setEtapeResolutionCouleur(addColor(getEtapeResolutionCouleur(),Color.YELLOW));
			
			//Remets l'arrivé en rouge
			parallelChange= new Case[1];
			parallelChange[0]=labyRpz[Main.infoLaby.getArrive()[0]*2+1][Main.infoLaby.getArrive()[1]*2+1];
			getEtapeResolution().add(parallelChange);
			setEtapeResolutionCouleur(addColor(getEtapeResolutionCouleur(),Color.RED));
			
			//Résultats
			Main.infoLaby.setI_RESULTATS_TEMPS(compteur, 1);
			calculTauxVisite(1);
		}
		
		private Piece getVoisins(Piece piece,String direction) {
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
				System.out.println("Erreur GetVoisins");
				return null;
			}
		}
		
		/*
		public Piece Deplacement(Piece p,Case[][] Laby, int[] card, Case[] parallelChange) {
				if (card[0] == 0) {
					return getGrillePiece()[p.getiIndexPiece() - 1 ][p.getjIndexPiece()];							// Déplacement sur le labyrinthe (pas uniquement graphique)
				}
				else if (card[0] == 1) {
					return getGrillePiece()[p.getiIndexPiece()][p.getjIndexPiece() + 1];
				}
				else if (card[0] == 2) {
					return getGrillePiece()[p.getiIndexPiece() + 1 ][p.getjIndexPiece()];
				}
				else if (card[0] == 3) {
					return getGrillePiece()[p.getiIndexPiece()][p.getjIndexPiece() - 1];
				}
				else {
					System.out.println("Déplacement impossible");
					return null;
				}
			}
		*/
		
		private String longeMurDroit(Piece piece,String directionEnArrivant) {
			directionEnArrivant=porteSuivante(piece, directionEnArrivant);
			while (!porteOuverte(piece, directionEnArrivant)) {
				directionEnArrivant=directionOppose(directionEnArrivant);
				directionEnArrivant=porteSuivante(piece, directionEnArrivant);
			}
			
			return directionEnArrivant;
		}
		
		private String porteSuivante(Piece piece, String directionEnArrivant) {
			//Renvoyer la direction 
				if(directionEnArrivant.equals("Nord")) {
					return "Est";
				}
				else if(directionEnArrivant.equals("Est")) {
					return "Sud";
				}
				else if(directionEnArrivant.equals("Sud")) {
					return "Ouest";
				}
				else if(directionEnArrivant.equals("Ouest")) {
					return "Nord";
				}
				else {
					System.out.println();
					return null;
				}
			}
		private boolean porteOuverte(Piece piece,String direction) {
			if(direction.equals("Nord")) {
				return piece.getPorteNord();
			}
			else if(direction.equals("Est")) {
				return piece.getPorteEst();
			}
			else if(direction.equals("Sud")) {
				return piece.getPorteSud();
			}
			else if(direction.equals("Ouest")) {
				return piece.getPorteOuest();
			}
			else {
				return false;
			}
			
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
				System.out.println("Erreur dans direction opposé");
				return null;
			}
		}
		private Case colorierPorte(Piece piece, String direction,Case[][] labyRpz) {
		//Definir la case Porte d'une pièce à partir d'une direction
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
}


