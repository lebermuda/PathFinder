package application.model.tremaux;

import application.model.Piece;

public class pieceTremaux extends Piece{
	private int compteurNord;
	private int compteurEst;
	private int compteurSud;
	private int compteurOuest;
	
	pieceTremaux(int iIndexPiece, int jIndexPiece, double cote1) {
		super(iIndexPiece, jIndexPiece, cote1);
	}
	
	pieceTremaux(Piece piece){
	//Constructeur à partir d'une pièce
		super(piece.getiIndexPiece(),piece.getjIndexPiece(),piece.getLONGUEUR_COTE());
		setPorteNord(piece.getPorteNord());
		setPorteEst(piece.getPorteEst());
		setPorteSud(piece.getPorteSud());
		setPorteOuest(piece.getPorteOuest());
		setVisite(piece.getVisite());
		setVoisinNord(piece.getVoisinNord());
		setVoisinEst(piece.getVoisinEst());
		setVoisinSud(piece.getVoisinSud());
		setVoisinOuest(piece.getVoisinOuest());
		setKruskalIndex(piece.getKruskalIndex());
		
		initCompteurs();
	}
	
	private void initCompteurs() {
	//Initialise les compteurs si la porte est fermée elle est blocante donc compteur>=2
		if (getPorteNord()) {
			setCompteurNord(0);
		}
		else {
			setCompteurNord(2);
		}
		if (getPorteEst()) {
			setCompteurEst(0);
		}
		else {
			setCompteurEst(2);
		}
		if (getPorteSud()) {
			setCompteurSud(0);
		}
		else {
			setCompteurSud(2);
		}
		if (getPorteOuest()) {
			setCompteurOuest(0);
		}
		else {
			setCompteurOuest(2);
		}
	}

	public String toString() {
		return getiIndexPiece()+" "+getjIndexPiece()+" : "+getCompteurNord()+"/"+getCompteurEst()+"/"+getCompteurSud()+"/"+getCompteurOuest();
	}
	
	public int getCompteurs(String direction) {
	//Get un compteur à partir d'une direction
		if(direction.equals("Nord")) {
			return getCompteurNord();
		}
		else if(direction.equals("Est")) {
			return getCompteurEst();
		}
		else if(direction.equals("Sud")) {
			return getCompteurSud();
		}
		else if(direction.equals("Ouest")) {
			return getCompteurOuest();
		}
		else {
			System.out.println("Erreur dans la recherche d'un compteur => pieceTremaux : "+direction);
			return (Integer) null;
		}
	}
	
	//setter and getter
	public int getCompteurEst() {
		return compteurEst;
	}

	public void setCompteurEst(int compteurEst) {
		this.compteurEst = compteurEst;
	}

	public int getCompteurNord() {
		return compteurNord;
	}

	public void setCompteurNord(int compteurNord) {
		this.compteurNord = compteurNord;
	}

	public int getCompteurSud() {
		return compteurSud;
	}

	public void setCompteurSud(int compteurSud) {
		this.compteurSud = compteurSud;
	}

	public int getCompteurOuest() {
		return compteurOuest;
	}

	public void setCompteurOuest(int compteurOuest) {
		this.compteurOuest = compteurOuest;
	}
	
}
