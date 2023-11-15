package application.view;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Case{
	//Représentation graphique d'une pièce, d'une porte ou d'un mur du labyrinthe
	
	final private int iGridIndex;
	final private int jGridIndex;
	final private double LONGUEUR_COTE;
	public Rectangle r;
	private Color color;
	//private int cost;
	
	protected Case(int iGridIndex, int jGridIndex,double cote1){
		this.iGridIndex=iGridIndex;
		this.jGridIndex=jGridIndex;
		LONGUEUR_COTE=cote1;
		//On commence avec jGridIndex sinon on a une invertion [i][j] en [j][i]
		r=new Rectangle(this.jGridIndex*LONGUEUR_COTE,this.iGridIndex*LONGUEUR_COTE,LONGUEUR_COTE,LONGUEUR_COTE);
		if ((this.iGridIndex%2==1)&&(this.jGridIndex%2==1)) {
			setColor(Color.WHITE);//si impair en i et j
		}
		else {
			setColor(Color.BLACK);
		}
	}
	void drawCase(Group groupe) {
		r.setFill(getColor());
		groupe.getChildren().add(r);
	}
	public String toString() {
		//Pour les tests
		return getIGridIndex()+" ; "+getJGridIndex() ;
		
	}
	
	/*
	//en cours Josh
	void drawCost(int cost) {
		
	}
	*/
	
	//getter and setter
	public double getLONGUEUR_COTE() {
		return LONGUEUR_COTE;
	};
	
	public int getIGridIndex() {
		return iGridIndex;
	}
	public int getJGridIndex() {
		return jGridIndex;
	}
	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
}