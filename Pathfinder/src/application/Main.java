package application;

import application.model.informationLaby;
import application.view.InterfaceGraphique;
import application.view.PageAcceuilInt;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class Main extends Application {
	private static Stage primaryStage;
	@SuppressWarnings("exports") //je sais pas ce que c'est
	//Stockage des informations
	public static informationLaby infoLaby=new informationLaby();
	
	public void start(Stage primaryStage) {
		Main.primaryStage=primaryStage;
		//infoLaby=new informationLaby();
		InterfaceGraphique pageActuel;
		Scene scene;
		try {
			pageActuel=new PageAcceuilInt("TemplatePageAcceuil.fxml");
			scene= new Scene(pageActuel.getParent());
		} catch(Exception e) {
			e.printStackTrace();
			return;
		}
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public static void changerPage(String fichierFXML) throws IOException {
		InterfaceGraphique page;
		page= new PageAcceuilInt(fichierFXML);
		Scene scene=new Scene(page.getParent());
		primaryStage.setScene(scene);
	}
}
