package application.view;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class InterfaceGraphique {
	//Super Class des différentes pages
	
	private Parent parent;
	private String fichierFXML;
	
	public InterfaceGraphique(String fichierFXML) throws IOException {
		this.setFichierFXML(fichierFXML);
		parent = FXMLLoader.load(getClass().getResource(getFichierFXML()));
	}
	
	//getter and setter
	public Parent getParent() {
		return parent;
	}
	public String getFichierFXML() {
		return fichierFXML;
	}
	public void setFichierFXML(String fichierFXML) {
		this.fichierFXML = fichierFXML;
	}
}
