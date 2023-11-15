package application.model.dijkstra;

import java.util.ArrayList;

import application.model.Piece;
import application.view.Case;
//import javafx.scene.paint.Color;
//import application.model.arbreLabyrinthe;

public class PieceDijkstra extends Piece {
	// porteCost stocke le coût de passage des portes Nord=0, Est=1, Sud=2, Ouest=3
	// dans un tableau d'entier
	private int[] porteCost;
	private int wayCost;
	public PieceDijkstra daron;

	PieceDijkstra(int iIndexPiece, int jIndexPiece, double cote) {
		super(iIndexPiece, jIndexPiece, cote);
		initWayCost();
	}

	PieceDijkstra(Piece piece) {
		super(piece.getiIndexPiece(), piece.getjIndexPiece(), piece.getLONGUEUR_COTE());
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

		this.porteCost = new int[4];
		daron = null;
		initWayCost();
		setPorteCost();

	}

	// Rappel : Directions : Nord=0, Est=1, Sud=2, Ouest=3

	public String toString() {
		return "(" + getiIndexPiece() + "," + getjIndexPiece() + ") ";
		// + ", coût " + porteCost[0] + "/ " + porteCost[1]
		// + "/ " + porteCost[2] + "/ " + porteCost[3];
	}

	public int getWayCost() {
		return wayCost;
	}

	public void setWayCost(int Cost) {
		this.wayCost = Cost;
	}

	public void initWayCost() {
		wayCost = 1000;
	}

	// Inutile ici car toutes les portes ont le même coût et il n'y a qu'un chemin
	public void setPorteCost() {
		for (int direction = 0; direction < 4; direction++) {
			if (porteOpen(direction)) {
				porteCost[direction] = 1; // 1 si la porte est ouverte
			} else {
				porteCost[direction] = 1000; // 1000 si la porte est fermée
			}
		}
	}

	public int getPorteCost(int direction) {
		return porteCost[direction];
	}

	public boolean porteOpen(int direction) {
		if ((direction == 0 && porteNord) || (direction == 1 && porteEst) || (direction == 2 && porteSud)
				|| (direction == 3 && porteOuest)) {
			return true;
		} else {
			return false;
		}
	}

	public PieceDijkstra getVoisin(int direction) {
		switch (direction) {
		case 0:
			return new PieceDijkstra(getVoisinNord());
		case 1:
			return new PieceDijkstra(getVoisinEst());
		case 2:
			return new PieceDijkstra(getVoisinSud());
		case 3:
			return new PieceDijkstra(getVoisinOuest());
		default:
			System.out.println("Erreur dans getVoisin");
			return null;
		}
	}

	public Case porteDirection(PieceDijkstra piece, int direction, Case[][] labyRpz) {
		// Défini la porte en fonction de la direction
		if (direction == 0) {
			return labyRpz[piece.getIGridIndex() - 1][piece.getJGridIndex()];
		} else if (direction == 1) {
			return labyRpz[piece.getIGridIndex()][piece.getJGridIndex() + 1];
		} else if (direction == 2) {
			return labyRpz[piece.getIGridIndex() + 1][piece.getJGridIndex()];
		} else if (direction == 3) {
			return labyRpz[piece.getIGridIndex()][piece.getJGridIndex() - 1];
		} else {
			System.out.println("Erreur dans la selection de la porte à colorier => PieceDijkstra: " + direction);
			return null;
		}
	}

	public boolean equals(PieceDijkstra piece) {
		if (piece.getiIndexPiece() == getiIndexPiece() && piece.getjIndexPiece() == getjIndexPiece()) {
			return true;
		} else {
			return false;
		}
	}

	public boolean contenuDans(ArrayList<PieceDijkstra> S) {
		boolean PieceEstContenu = false;
		for (int i = 0; i < S.size(); i++) {
			if (!(S.get(i).equals(this))) {
			} else {
				// System.out.println("S contient déjà " + this);
				PieceEstContenu = true;
				break;
			}
		}
		return PieceEstContenu;
	}
	
	public void setDaron(PieceDijkstra piece) {
		daron=piece;
	}
	public Case porteDuDaron(Case[][] labyRpz) {
		if (daron.equals(getVoisinNord())) {
			return labyRpz[getIGridIndex() - 1][getJGridIndex()];
		}
		if (daron.equals(getVoisinEst())) {
			return labyRpz[getIGridIndex()][getJGridIndex() + 1];
		}
		if (daron.equals(getVoisinSud())) {
			return labyRpz[getIGridIndex() + 1][getJGridIndex()];
		}
		if (daron.equals(getVoisinOuest())) {
			return labyRpz[getIGridIndex()][getJGridIndex() - 1];
		} else {
			return null;
		}
	}

}
