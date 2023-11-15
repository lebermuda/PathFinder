package application.model;

public class informationLaby {
	private int HAUTEUR_LABY;
	private int LONGUEUR_LABY;
	private int TIME_RESOLVE;
	private String TYPE_SELECT_DEPART;
	private String TYPE_RESOLUTION;
	private arbreLabyrinthe grapheLaby;
	private int[] depart;
	private int[] arrive;
	
	//Resultats sous forme de tableau [Dijkstra, Mur Droit, Profondeur, Tremaux]
	private int[] RESULTATS_TEMPS; // temps d'execution en nombre d'execution
	private double[] RESULTATS_TAUX_VISITE; //nombre de pièces visitées sur nombre de pièces totales
	
	public informationLaby() {
		setHAUTEUR_LABY(4);
		setLONGUEUR_LABY(4);
		setTIME_RESOLVE(500);
		
		double[] nul = {0,0,0,0};
		int[] nul2 = {0,0,0,0};
		setRESULTATS_TAUX_VISITE(nul);
		setRESULTATS_TEMPS(nul2);
		
	}
	
	//getter and setter
	public int getHAUTEUR_LABY() {
		return HAUTEUR_LABY;
	}
	public void setHAUTEUR_LABY(int hAUTEUR_LABY) {
		HAUTEUR_LABY = hAUTEUR_LABY;
	}
	public int getLONGUEUR_LABY() {
		return LONGUEUR_LABY;
	}
	public void setLONGUEUR_LABY(int lONGUEUR_LABY) {
		LONGUEUR_LABY = lONGUEUR_LABY;
	}

	public int getTIME_RESOLVE() {
		return TIME_RESOLVE;
	}

	public void setTIME_RESOLVE(int tIME_RESOLVE) {
		TIME_RESOLVE = tIME_RESOLVE;
	}

	public String getTYPE_SELECT_DEPART() {
		return TYPE_SELECT_DEPART;
	}

	public void setTYPE_SELECT_DEPART(String tYPE_SELECT_DEPART) {
		TYPE_SELECT_DEPART = tYPE_SELECT_DEPART;
	}

	public String getTYPE_RESOLUTION() {
		return TYPE_RESOLUTION;
	}

	public void setTYPE_RESOLUTION(String tYPE_RESOLUTION) {
		TYPE_RESOLUTION = tYPE_RESOLUTION;
	}

	public arbreLabyrinthe getGrapheLaby() {
		return grapheLaby;
	}

	public void setGrapheLaby(arbreLabyrinthe grapheLaby) {
		this.grapheLaby = grapheLaby;
	}

	public int[] getDepart() {
		return depart;
	}

	public void setDepart(int[] depart) {
		this.depart = depart;
	}

	public int[] getArrive() {
		return arrive;
	}

	public void setArrive(int[] arrive) {
		this.arrive = arrive;
	}

	public int[] getRESULTATS_TEMPS() {
		return RESULTATS_TEMPS;
	}
	
	public void setRESULTATS_TEMPS(int[] rESULTATS_TEMPS) {
		RESULTATS_TEMPS = rESULTATS_TEMPS;
	}

	public double[] getRESULTATS_TAUX_VISITE() {
		return RESULTATS_TAUX_VISITE;
	}

	public void setRESULTATS_TAUX_VISITE(double[] rESULTATS_TAUX_VISITE) {
		RESULTATS_TAUX_VISITE = rESULTATS_TAUX_VISITE;
	}
	public void setI_RESULTATS_TEMPS(int rESULTAT_TEMPS,int i) {
	//0:Dijkstra, 1:Mur Droit, 2:Profondeur, 3:Tremaux
		RESULTATS_TEMPS[i]=rESULTAT_TEMPS;
	}
	public void setI_RESULTATS_TAUX_VISITE(double rESULTAT_TAUX_VISITE,int i) {
	//0:Dijkstra, 1:Mur Droit, 2:Profondeur, 3:Tremaux
		RESULTATS_TAUX_VISITE[i]=rESULTAT_TAUX_VISITE;
	}
}
