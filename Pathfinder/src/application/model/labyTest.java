package application.model;



import application.view.Case;

public class labyTest extends arbreLabyrinthe {
	public labyTest() {
		super(5,4);
		/*
		 
		 ###########
		 #.....#...#
		 ###.#.#.###
		 #...#...#.#
		 #.###.#.#.#
		 #.#...#...#
		 ###.###.###
		 #...#.....#
		 ###########
		 
		 */
		
		getGrillePiece()[0][0].setPorteEst(true);
		getGrillePiece()[0][1].setPorteOuest(true);
		
		getGrillePiece()[0][1].setPorteSud(true);
		getGrillePiece()[1][1].setPorteNord(true);
		
		getGrillePiece()[0][1].setPorteEst(true);
		getGrillePiece()[0][2].setPorteOuest(true);
		
		getGrillePiece()[1][1].setPorteOuest(true);
		getGrillePiece()[1][0].setPorteEst(true);
		
		getGrillePiece()[1][0].setPorteSud(true);
		getGrillePiece()[2][0].setPorteNord(true);
		
		getGrillePiece()[0][2].setPorteSud(true);
		getGrillePiece()[1][2].setPorteNord(true);
		
		getGrillePiece()[1][2].setPorteSud(true);
		getGrillePiece()[2][2].setPorteNord(true);
		
		getGrillePiece()[0][3].setPorteSud(true);
		getGrillePiece()[1][3].setPorteNord(true);
		
		getGrillePiece()[1][3].setPorteSud(true);
		getGrillePiece()[2][3].setPorteNord(true);
		
		getGrillePiece()[2][1].setPorteSud(true);
		getGrillePiece()[3][1].setPorteNord(true);
		
		getGrillePiece()[2][3].setPorteSud(true);
		getGrillePiece()[3][3].setPorteNord(true);
		
		getGrillePiece()[1][4].setPorteSud(true);
		getGrillePiece()[2][4].setPorteNord(true);
		
		getGrillePiece()[2][1].setPorteEst(true);
		getGrillePiece()[2][2].setPorteOuest(true);
		
		getGrillePiece()[1][2].setPorteEst(true);
		getGrillePiece()[1][3].setPorteOuest(true);
		
		getGrillePiece()[3][0].setPorteEst(true);
		getGrillePiece()[3][1].setPorteOuest(true);
		
		getGrillePiece()[3][2].setPorteEst(true);
		getGrillePiece()[3][3].setPorteOuest(true);
		
		getGrillePiece()[3][3].setPorteEst(true);
		getGrillePiece()[3][4].setPorteOuest(true);
		
		getGrillePiece()[2][3].setPorteEst(true);
		getGrillePiece()[2][4].setPorteOuest(true);
		
		getGrillePiece()[0][3].setPorteEst(true);
		getGrillePiece()[0][4].setPorteOuest(true);
	}

	@Override
	public void generer(Case[][] labyRpz) {
		System.out.println("Test only");
	}
	
}
