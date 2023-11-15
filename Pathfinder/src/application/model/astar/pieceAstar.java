package application.model.astar;

import application.Main;
import application.model.Piece;
import java.lang.Math;
import java.util.ArrayList;


public class pieceAstar extends Piece{
	boolean gotParent;
	public pieceAstar parent;
	boolean iscurrent;
	int g;
	int h;
	int f;
	
	
	pieceAstar(int iIndexPiece, int jIndexPiece, double cote1) {
		super(iIndexPiece, jIndexPiece, cote1);
	}
				
	pieceAstar(Piece piece){
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
		this.g = 0;
		this.h = Math.abs(Main.infoLaby.getArrive()[0] - this.getiIndexPiece()) + Math.abs(Main.infoLaby.getArrive()[1] - this.getjIndexPiece());
		//this.f = g+h;
		this.gotParent = false;
	}
	
	
	public int getF() {
		return this.f;
	}
	
	public int getG() {
		return this.g;
	}
	
	
	public int getH() {
		return this.h;
	}
	
	
	public boolean contenuDans(ArrayList<pieceAstar> closeList) {
		boolean PieceEstContenu = false;
		for (int i = 0; i < closeList.size(); i++) {
			if (!(closeList.get(i).getiIndexPiece() == getiIndexPiece()
					&& closeList.get(i).getjIndexPiece() == getjIndexPiece())) {
//				System.out.println(S.get(i).getiIndexPiece() + "!=" + getiIndexPiece() + " ou "
//						+ S.get(i).getjIndexPiece() + "!=" + getjIndexPiece());
			} else {
				//System.out.println("S contient déjà " + this);
				PieceEstContenu = true;
				break;
			}
		}
		return PieceEstContenu;
	}
}