package application.model.dijkstra;

import java.util.ArrayList;

import application.Main;
import application.model.arbreLabyrinthe;
import application.model.dijkstra.PieceDijkstra;
import application.view.Case;
import javafx.scene.paint.Color;

public class DijkstraResolve extends arbreLabyrinthe {
	public DijkstraResolve(int nombrePieceLargeur, int nombrePieceHauteur) {
		super(nombrePieceLargeur, nombrePieceHauteur);
	}

	public DijkstraResolve(arbreLabyrinthe grapheLaby) {
		super(grapheLaby.getNombrePieceLargeur(), grapheLaby.getNombrePieceHauteur());
		upgradeToMain(grapheLaby);
		setVisiteFalse();
	}

	@Override
	public void generer(Case[][] labyRpz) {
		Color brown = Color.rgb(200, 100, 0);
		setVisiteFalse();
		// S : Ensble des cases ayant étées pivot et ne pouvant
		// être atteintes plus rapidement donc ajoutées à S
		ArrayList<PieceDijkstra> S = new ArrayList<PieceDijkstra>();
		ArrayList<PieceDijkstra> X = new ArrayList<PieceDijkstra>(); // Ensble des cases ayant été atteintes

		int stepCounter = 1;
		Case[] parallelChange = new Case[0]; // sera complété comme S pour l'affichage

		// Initialisation : Ajouter pivot=départ à S
		PieceDijkstra depart = new PieceDijkstra(
				getGrillePiece()[Main.infoLaby.getDepart()[0]][Main.infoLaby.getDepart()[1]]);
		depart.setVisite(true);
		PieceDijkstra arrivee = new PieceDijkstra(
				getGrillePiece()[Main.infoLaby.getArrive()[0]][Main.infoLaby.getArrive()[1]]);

		PieceDijkstra pivot = depart;
		setGrillePieceIJ(pivot);

		// Mets la PieceDijkstra pivot dans la grille
		S.add(pivot);
		pivot.setWayCost(0);

		// Boucle principale
		while (arrivee.contenuDans(S) == false) {
			parallelChange = new Case[0];

			// Etude des voisins et ajout à X
			etudeVoisins(pivot, S, X, parallelChange, labyRpz);

			// Détermination du nouveau pivot
			coloriagePorte(parallelChange, S, newPivot(X, S), labyRpz, brown);

			pivot = newPivot(X, S);
			// On marque le nouveau pivot comme visité
			getGrillePiece()[pivot.getiIndexPiece()][pivot.getjIndexPiece()].setVisite(true);
			S.add(pivot);

			// MAJ pour l'affichage
			if (!pivot.equals(arrivee)) {
				coloriage(parallelChange, labyRpz[pivot.getIGridIndex()][pivot.getJGridIndex()], brown);
			}
			stepCounter++;
		}

		// Remontée du chemin par hérédité
		remonteeHereditaire(pivot, depart, parallelChange, labyRpz);

		calculTauxVisite(0);
		Main.infoLaby.setI_RESULTATS_TEMPS(stepCounter, 0);
	}

	// SOUS PROGRAMMES

	// Mise A Jour des coûts pour atteindre les différentes pieces voisines
	public void etudeVoisins(PieceDijkstra pivot, ArrayList<PieceDijkstra> S, ArrayList<PieceDijkstra> X,
			Case[] parallelChange, Case[][] labyRpz) {
		for (int direction = 0; direction < 4; direction++) {// On regarde tous les voisins
			if (pivot.porteOpen(direction)) {// Si la piece voisine est accessible cad la porte est ouverte
				PieceDijkstra voisin = pivot.getVoisin(direction);
				if (voisin.contenuDans(S) == false) {// et appartient à X/S, ici n'appartient pas à S suffit
					if (!X.contains(voisin)) {// Si X ne contient pas encore ce voisin non plus
						X.add(voisin);// Alors on l'y ajoute
						voisin.daron = pivot;
						coloriage(parallelChange, pivot.porteDirection(pivot, direction, labyRpz), Color.YELLOW);
					}
					if (pivot.getWayCost() + pivot.getPorteCost(direction) < voisin.getWayCost()) {
						voisin.setWayCost(pivot.getWayCost() + pivot.getPorteCost(direction));
						voisin.daron = pivot;
					} // Si le coût pour atteindre la piece voisine est inférieur au coût du chemin
						// précédant, on effectue la maj avec le coût minimum
				}
			}
		}
	}

	public PieceDijkstra newPivot(ArrayList<PieceDijkstra> X, ArrayList<PieceDijkstra> S) {
		PieceDijkstra newPivot = null;
		ArrayList<PieceDijkstra> newX = new ArrayList<PieceDijkstra>();
		for (int j = 0; j < X.size(); j++) {
			PieceDijkstra pieceAtteinte = X.get(j);// on regarde les pièces atteintes donc appartenant à X
			if (pieceAtteinte.contenuDans(S) == false) {// on enlève celles déjà dans S
				newX.add(pieceAtteinte);
				if (newPivot == null) {
					newPivot = pieceAtteinte;
				} else {
					if (pieceAtteinte.getWayCost() < newPivot.getWayCost()) {
						// On choisit le pivot avec la longueur de chemin la plus petite
						newPivot = pieceAtteinte;
					}
				}
			}
		}
		X = newX;
		setGrillePieceIJ(newPivot);
		return newPivot;
	}

	public void coloriage(Case[] parallelChange, Case caze, Color couleur) {
		parallelChange = addCase(parallelChange, caze);
		getEtapeResolution().add(parallelChange);
		setEtapeResolutionCouleur(addColor(getEtapeResolutionCouleur(), couleur));
	}

	public void coloriagePorte(Case[] parallelChange, ArrayList<PieceDijkstra> S, PieceDijkstra newPivot,
			Case[][] labyRpz, Color brown) {
		for (int direction = 0; direction < 4; direction++) {
			if (newPivot.porteOpen(direction)) {
				if (newPivot.getVoisin(direction).contenuDans(S)) {
					coloriage(parallelChange, newPivot.porteDirection(newPivot, direction, labyRpz), brown);
				}
			}
		}
	}

	public void remonteeHereditaire(PieceDijkstra pivot, PieceDijkstra depart, Case[] parallelChange,
			Case[][] labyRpz) {
		while (!pivot.equals(depart)) {
			parallelChange = new Case[0];
			// On colorie la porte entre le pivot et son daron
			coloriage(parallelChange, pivot.porteDuDaron(labyRpz), Color.ORANGE);

			// le daron devient le nouveau pivot
			pivot = pivot.daron;

			// Si ce n'est pas le départ, on colorie le nouveau pivot
			if ((!pivot.equals(depart))) {
				coloriage(parallelChange, labyRpz[pivot.getIGridIndex()][pivot.getJGridIndex()], Color.ORANGE);
			}
		}
	}
}