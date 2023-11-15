package application.model.mur_droit;

/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *  Cette classe ne sert finalement pas                      *
 *  mais on en parle dans le livrable 3 donc on l'a laissé   *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

import application.model.Piece;

public class Aventurier {
	private int[] Orientation; // Avant=0, Droite=1, Arrière=2, Gauche=3 comme pour la direction d'une pièce
	
	Aventurier(){
		this.Orientation = new int[0];
	}
	
	
	
	public int[] getOrientation() {	
		return this.Orientation;
	}
	
	
	
	public void setOrientation(int[] tab) {
		this.Orientation = tab;
	}
	
	
	
	public void setInit() {
		int[] tab = {0,1,2,3};
		this.Orientation = tab;
	}
	
	
	// Recherche du point cardinal correspondant à l'orientation voulue (Note 1 ci-dessous)
	public int recherchedirection(int[] orientation, int direction) {
		int i= 0;
		for (int x=0; x<4; x++) {
			if (orientation[x]==direction) {
				i= x;
			}
		}
		return i;
	}
	
	
	
	public boolean getBonnePorte (int direction, Piece p) {
		switch (direction) {
		case 0:
			return p.getPorteNord();
		case 1:
			return p.getPorteEst();	
		case 2:
			return p.getPorteSud();
		case 3:
			return p.getPorteOuest();
		default:
			System.out.println("Méthode getVoisin de Piece invalide");
			return(false);
		}
	}
	
	
	
	public int[] Decision(Piece p) {	 		// Note 2
		int[] cardface= {recherchedirection(this.Orientation, 0),0};            // cardface = (coord cardinale, nb de rotations à 90° pour se retrouver de face)
		int[] carddroite = {recherchedirection(this.Orientation, 1),1};
		int[] cardarriere = {recherchedirection(this.Orientation, 2),2};
		int[] cardgauche = {recherchedirection(this.Orientation, 3),3};
		
	// Présence d'un passage à droite
		if (getBonnePorte(carddroite[0],p)) {
			return (carddroite);
		}
			
	// Absence d'un passage à droite
		else { 
			if (getBonnePorte(cardface[0],p) && getBonnePorte(cardgauche[0],p)) {
				return(cardface);
			}
			else if (getBonnePorte(cardface[0],p) && !getBonnePorte(cardgauche[0],p)) {
				return(cardface);
			}
			else if (!getBonnePorte(cardface[0],p) && getBonnePorte(cardgauche[0],p)) {
				return(cardgauche);
			}
			else {
				return (cardarriere);
			}
	    }	
	}
	
	
	
	public void rotation (int[] card) {
		for (int x=0; x<4; x++) {
			this.Orientation[x]= (this.Orientation[x] + card[1]) % 4; // Note 3
		}
	}
}



// Note 1 : La pièce et l'aventurier (modèlise le trajet parcouru) ont deux repères disctincts.
// 		    Le repère de l'aventurier bouge dans des pièces au repère fixe.
//		    Aller à droite pour l'aventurier peut signifier aller au nord, est, sud ou ouest suivant la situation.
//          Cet algorithme sert donc à définir l'orientation qu'a l'aventurier par rapport à la direction
// 		    qu'il prend dans son repère.



//Note 2 : Il y a 5 situation possibles:
//		   		1) Il y a un passage à droite (tourne à droite)
//				2) Il y a un passage à gauche et tout droit (continue tout droit)
//				3) Il y un passage seulement à gauche (tourne à gauche)
//				4) Il y a un passage seulement tout droit (continue tout droit)
//				5) C'est un cul-de-sac (fait demi-tour)



//Note 3 : Exemple : L'aventurier est face à l'Est (1) et doit tourner à gauche. Il se tourne donc de 3 crans vers la droite.
//					 Il se retrouvera donc en (1 + 3) % 4 = 0, c'est à dire face au Nord.