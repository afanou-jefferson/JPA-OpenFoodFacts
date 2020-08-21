
package executables;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Logger;

import org.apache.ibatis.jdbc.ScriptRunner;

import core.App;

import utils.Chrono;
import utils.ConnectionBDD;

/**
 * Classe executable permettant d'extraire et d'insérer un échantillon du
 * fichier OpenFoodFacts ( 1000 premières lignes ) en BDD. Utilisée pour
 * effectuer des tests de performance.
 * 
 * @author Exost
 *
 */
public class TestJeuReduit {

	private static final Logger LOGGER = Logger.getLogger(TestJeuReduit.class.getName());

	public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException, SQLException {

		File fichier = new File(System.getProperty("user.dir") + "/src/main/resources/jeuTest.csv");

		Chrono chrono = new Chrono();
		chrono.start(); // démarrage du chrono
		System.out.println("Lancement Programme");

		App myApp = new App(fichier);

		chrono.stop(); // arrêt chrono
		System.out.println("Temps TOTAL : " + chrono.getDureeTxt()); // affichage au format "1 h 26 min 32 s"

		System.out.println("Produits insérés = " + myApp.compteurInsertProduits);
		System.out.println("Catégories insérés = " + myApp.compteurInsertCategorie);
		System.out.println("Ingrédients insérés = " + myApp.compteurInsertIngredients);
		System.out.println("Allergenes insérés = " + myApp.compteurInsertAllergenes);
		System.out.println("Additifs insérés = " + myApp.compteurInsertAdditifs);

	}
}
