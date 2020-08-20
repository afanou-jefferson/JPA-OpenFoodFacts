package core;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.util.Set;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.commons.io.FileUtils;

import entity.Additif;
import entity.Allergene;
import entity.Categorie;
import entity.Ingredient;
import entity.Marque;
import entity.Produit;
import utils.Chrono;
import utils.NettoyageString;

//
public class App {

	File fichierEnLecture;
	BDDCache localDB;
	ArrayList<String> stockageRequetesInsert;

	public int compteurInsertCategorie = 0;
	public int compteurInsertAdditifs = 0;
	public int compteurInsertIngredients = 0;
	public int compteurInsertMarques = 0;
	public int compteurInsertAllergenes = 0;
	public int compteurInsertProduits = 0;

	public App(File fichierParam) {

		this.fichierEnLecture = fichierParam;
		this.localDB = new BDDCache();
		this.stockageRequetesInsert = new ArrayList<String>();

		Chrono chronoLecture = new Chrono();
		chronoLecture.start(); // démarrage du chrono

		try {
			List<String> lignes = FileUtils.readLines(fichierEnLecture, "UTF-8");
			EntityManagerFactory entityFacto = Persistence.createEntityManagerFactory("jpa_OpenFoodFacts");
			EntityManager em = entityFacto.createEntityManager();

			// Start 1 pour sauter ligne intitules categories
			for (int i = 1; i < lignes.size(); i++) {

				String[] morceaux = lignes.get(i).split("\\|", -1);

				String nomProduitClean = NettoyageString.nettoyerString(morceaux[2]);
				String gradeNutriProduit = morceaux[3];

				int idCategorieProduit = traitementCategorie(morceaux[0]);

				if (localDB.getMemoireLocaleProduitsBDD().get(nomProduitClean) == null) {
					localDB.setCompteurIDProduit(localDB.getCompteurIDProduit() + 1);
					int newIDProduit = localDB.getCompteurIDProduit();
					Produit newProduit = new Produit(newIDProduit, nomProduitClean, gradeNutriProduit, idCategorieProduit,
							traitementMarques(morceaux[1]), traitementIngredients(morceaux[4]),
							traitementAllergenes(morceaux[28]), traitementAdditifs(morceaux[29]));

					this.localDB.getMemoireLocaleProduitsBDD().put(nomProduitClean, newProduit);
					//this.stockageRequetesInsert.addAll(daoGenerique.insert(newProduit));
					compteurInsertProduits++;
				}
				System.out.println(i);
			}

			chronoLecture.stop(); // arrêt
			System.out.println("Temps pour Lecture : " + chronoLecture.getDureeTxt()); // affichage au format "1 h 26
																						// min 32 s"

			Chrono chronoInsert = new Chrono();
			chronoInsert.start(); // démarrage du chrono

			//daoGenerique.insertAll(this.stockageRequetesInsert);

			chronoInsert.stop(); // arrêt
			System.out.println("Temps pour Insert : " + chronoInsert.getDureeTxt()); // affichage au format "1 h 26 min
																						// 32 s"

		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Effectue les traitements sur une String qui contient les infos sur la
	 * catérogie d'un produit.
	 * 
	 * @param morceauString
	 * @return un integer qui correspond à l'ID de la catégorie dans la BDD
	 */
	public int traitementCategorie(String morceauString) {

		//JDBCdaoGenerique daoGenerique = new JDBCdaoGenerique(this.connectionDB);
		int idCategorieBDD = -1;

		String cleanCategorie = NettoyageString.nettoyerString(morceauString);

		if (localDB.getMemoireLocaleCategoriesBDD().get(cleanCategorie) == null) {
			localDB.setCompteurIDCategorie(localDB.getCompteurIDCategorie() + 1);
			Categorie categorieEnLecture = new Categorie(localDB.getCompteurIDCategorie(), cleanCategorie);
			localDB.getMemoireLocaleCategoriesBDD().put(categorieEnLecture.getNomUnique(), categorieEnLecture);
			//this.stockageRequetesInsert.addAll(daoGenerique.insert(categorieEnLecture));
			idCategorieBDD = categorieEnLecture.getID();
			compteurInsertCategorie++;
		} else {
			idCategorieBDD = localDB.getMemoireLocaleCategoriesBDD().get(cleanCategorie).getID();
		}

		return idCategorieBDD;
	}

	/**
	 * Effectue les traitements sur une String qui contient les infos sur les
	 * Allergenes d'un produit.
	 * 
	 * @param morceauString
	 * @return une Set d'objet de Type Allergene
	 */
	public Set<Allergene> traitementAllergenes(String morceauString) {

		//JDBCdaoGenerique daoGenerique = new JDBCdaoGenerique(this.connectionDB);
		Set<Allergene> listAllergenesProduit = new HashSet<Allergene>();

		String[] elemStringAllergene = morceauString.split(",");

		for (String nomAllergene : elemStringAllergene) {

			String cleanAllergene = NettoyageString.nettoyerString(nomAllergene);

			if (localDB.getMemoireLocaleAllergenesBDD().get(cleanAllergene) == null) {
				localDB.setCompteurIDAllergene(localDB.getCompteurIDAllergene() + 1);
				Allergene allergeneEnLecture = new Allergene(localDB.getCompteurIDAllergene(), cleanAllergene);
				listAllergenesProduit.add(allergeneEnLecture);
				localDB.getMemoireLocaleAllergenesBDD().put(allergeneEnLecture.getNomUnique(), allergeneEnLecture);
				//this.stockageRequetesInsert.addAll(daoGenerique.insert(allergeneEnLecture));
				compteurInsertAllergenes++;
			} else {
				listAllergenesProduit.add((Allergene) localDB.getMemoireLocaleAllergenesBDD().get(cleanAllergene));
			}
		}
		return listAllergenesProduit;
	}

	/**
	 * Effectue les traitements sur une String qui contient les infos sur les
	 * Marques d'un produit.
	 * 
	 * @param morceauString
	 * @return une Set d'objet de Type Marque
	 */
	public Set<Marque> traitementMarques(String morceauString) {

		//JDBCdaoGenerique daoGenerique = new JDBCdaoGenerique(this.connectionDB);
		Set<Marque> listMarquesProduit = new HashSet<Marque>();

		String[] elemStringMarque = morceauString.split(",");

		for (String nomMarque : elemStringMarque) {

			String cleanMarque = NettoyageString.nettoyerString(nomMarque);

			if (localDB.getMemoireLocaleMarquesBDD().get(cleanMarque) == null) {
				localDB.setCompteurIDMarque(localDB.getCompteurIDMarque() + 1);
				Marque marqueEnLecture = new Marque(localDB.getCompteurIDMarque(), cleanMarque);
				listMarquesProduit.add(marqueEnLecture);
				localDB.getMemoireLocaleMarquesBDD().put(marqueEnLecture.getNomUnique(), marqueEnLecture);
				//this.stockageRequetesInsert.addAll(daoGenerique.insert(marqueEnLecture));
				compteurInsertMarques++;
			} else {
				listMarquesProduit.add((Marque) localDB.getMemoireLocaleMarquesBDD().get(cleanMarque));
			}
		}
		return listMarquesProduit;
	}

	/**
	 * Effectue les traitements sur une String qui contient les infos sur les
	 * Ingredients d'un produit.
	 * 
	 * @param morceauString
	 * @return une Set d'objet de Type Inredient
	 */
	public Set<Ingredient> traitementIngredients(String morceauString) {

		//JDBCdaoGenerique daoGenerique = new JDBCdaoGenerique(this.connectionDB);
		Set<Ingredient> listIngredientsProduit = new HashSet<Ingredient>();

		String[] elemStringIngredient = morceauString.split(",");

		for (String nomIngredient : elemStringIngredient) {

			String cleanIngredient = NettoyageString.nettoyerString(nomIngredient);

			if (localDB.getMemoireLocaleIngredientsBDD().get(cleanIngredient) == null) {
				localDB.setCompteurIDIngredient(localDB.getCompteurIDIngredient() + 1);
				Ingredient ingredientEnLecture = new Ingredient(localDB.getCompteurIDIngredient(), cleanIngredient);
				listIngredientsProduit.add(ingredientEnLecture);
				localDB.getMemoireLocaleIngredientsBDD().put(ingredientEnLecture.getNomUnique(), ingredientEnLecture);
				//this.stockageRequetesInsert.addAll(daoGenerique.insert(ingredientEnLecture));
				compteurInsertIngredients++;
			} else {
				listIngredientsProduit.add((Ingredient) localDB.getMemoireLocaleIngredientsBDD().get(cleanIngredient));
			}
		}
		return listIngredientsProduit;
	}

	/**
	 * Effectue les traitements sur une String qui contient les infos sur les
	 * additifs d'un produit.
	 * 
	 * @param morceauString
	 * @return une Arraylist d'objet de type Additif
	 */
	public Set<Additif> traitementAdditifs(String morceauString) {

		//JDBCdaoGenerique daoGenerique = new JDBCdaoGenerique(this.connectionDB);
		Set<Additif> listAdditifsProduit = new HashSet<Additif>();

		String[] elemStringAdditif = morceauString.split(",");

		for (String nomAdditif : elemStringAdditif) {

			// Traitement special
			String cleanAdditif = nomAdditif.replaceAll("[^\\w]\\s", " ").replaceAll("[\\+\\.\\^,%]", " ")
					.replaceAll("[\\_\\-]", " ").replace("fr:", " ").replace("en:", " ").trim();

			if (localDB.getMemoireLocaleAdditifsBDD().get(cleanAdditif) == null) {
				localDB.setCompteurIDAdditif(localDB.getCompteurIDAdditif() + 1);
				Additif additifEnLecture = new Additif(localDB.getCompteurIDAdditif(), cleanAdditif);
				listAdditifsProduit.add(additifEnLecture);
				localDB.getMemoireLocaleAdditifsBDD().put(additifEnLecture.getNomUnique(), additifEnLecture);
				//this.stockageRequetesInsert.addAll(daoGenerique.insert(additifEnLecture));
				compteurInsertAdditifs++;
			} else {
				listAdditifsProduit.add((Additif) localDB.getMemoireLocaleAdditifsBDD().get(cleanAdditif));
			}
		}
		return listAdditifsProduit;
	}

}
