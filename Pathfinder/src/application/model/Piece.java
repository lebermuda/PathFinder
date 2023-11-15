package application.model;
import application.view.Case;

public class Piece extends Case {
	final private int iIndexPiece;
	final private int jIndexPiece;
	protected boolean porteNord;
	protected boolean porteEst;
	protected boolean porteSud;
	protected boolean porteOuest;
	
	//porteCost stocke le coût de passage des portes Nord=0, Est=1, Sud=2, Ouest=3 dans un tableau d'entier
	//private int[] porteCost;
	
	private boolean visite;
	
	private Piece voisinNord;
	private Piece voisinEst;
	private Piece voisinSud;
	private Piece voisinOuest;
	
	private int kruskalIndex;
	
	protected Piece(int iIndexPiece, int jIndexPiece, double cote1) {
		//Brique de base du labyrinthe, les pieces sont reliées entre elles par des portes qui sont de base fermées --> boolean=false
		super(iIndexPiece*2+1, jIndexPiece*2+1, cote1);//explication du *2+1 : on intercale les portes entre les pieces
		this.iIndexPiece=iIndexPiece;
		this.jIndexPiece=jIndexPiece;
		setPorteNord(false);
		setPorteEst(false);
		setPorteSud(false);
		setPorteOuest(false);
		
		setVisite(false);
	}
	public Piece(Case caze) {
		super(caze.getIGridIndex(),caze.getJGridIndex(),caze.getLONGUEUR_COTE());
		this.iIndexPiece=caze.getIGridIndex()*2+1;
		this.jIndexPiece=caze.getJGridIndex()*2+1;
		setPorteNord(false);
		setPorteEst(false);
		setPorteSud(false);
		setPorteOuest(false);
		
	}
	
	public String toString() {
		return getiIndexPiece()+","+getjIndexPiece();
	}
	
	public void debogErreurVoisin() {
		//test des voisins
		System.out.println(this);
		System.out.println("Nord : "+this.getVoisinNord()+" "+this.haveVoisin("Nord"));
		System.out.println("Est : "+this.getVoisinEst()+" "+this.haveVoisin("Est"));
		System.out.println("Sud : "+this.getVoisinSud()+" "+this.haveVoisin("Sud"));
		System.out.println("Ouest : "+this.getVoisinOuest()+" "+this.haveVoisin("Ouest"));
		System.out.println();
	}
	
	public boolean haveVoisin(String direction) {
		switch(direction) {
			case "Nord":
				return getPorteNord();
			case "Est":
				return getPorteEst();
			case "Sud":
				return getPorteSud();
			case "Ouest":
				return getPorteOuest();
			default:
				System.out.println("Erreur dans haveVoisin => Piece");
				return false;
		}
	}
	//Getter and setter 
	public boolean getPorteEst() {
		return porteEst;
	}
	public void setPorteEst(boolean porteEst) {
		this.porteEst = porteEst;
	}
	public boolean getPorteNord() {
		return porteNord;
	}
	public void setPorteNord(boolean porteNord) {
		this.porteNord = porteNord;
	}
	public boolean getPorteSud() {
		return porteSud;
	}
	public void setPorteSud(boolean porteSud) {
		this.porteSud = porteSud;
	}
	public boolean getPorteOuest() {
		return porteOuest;
	}
	public void setPorteOuest(boolean porteOuest) {
		this.porteOuest = porteOuest;
	}
	public boolean getVisite() {
		return visite;
	}
	public void setVisite(boolean visite) {
		this.visite = visite;
	}
	public Piece getVoisinNord() {
		return voisinNord;
	}
	public void setVoisinNord(Piece voisinNord) {
		this.voisinNord = voisinNord;
	}
	public Piece getVoisinEst() {
		return voisinEst;
	}
	public void setVoisinEst(Piece voisinEst) {
		this.voisinEst = voisinEst;
	}
	public Piece getVoisinSud() {
		return voisinSud;
	}
	public void setVoisinSud(Piece voisinSud) {
		this.voisinSud = voisinSud;
	}
	public Piece getVoisinOuest() {
		return voisinOuest;
	}
	public void setVoisinOuest(Piece voisinOuest) {
		this.voisinOuest = voisinOuest;
	}
	public void setVoisins(Piece voisinNord,Piece voisinEst,Piece voisinSud,Piece voisinOuest) {
		this.voisinNord=voisinNord;
		this.voisinEst=voisinEst;
		this.voisinSud=voisinSud;
		this.voisinOuest=voisinOuest;
	}
	public int getKruskalIndex() {
		return kruskalIndex;
	}
	public void setKruskalIndex(int kruskalIndex) {
		this.kruskalIndex = kruskalIndex;
	}
	public int getiIndexPiece() {
		return iIndexPiece;
	}
	public int getjIndexPiece() {
		return jIndexPiece;
	}
}
