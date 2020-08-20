package core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import exceptions.TraitementFichierException;
import entity.Additif;
import entity.Allergene;
import entity.Categorie;
import entity.Entite;
import entity.Ingredient;
import entity.Marque;
import entity.Produit;
import utils.NettoyageString;

/**
 * Classe visant à simuler et stocker le contenu de la BDD distante en locale afin de minimiser le nombre de requête.
 * @author Exost
 *
 */
public class BDDCache {
	
	Connection connection;
	
	private HashMap<String, Entite> memoireLocaleAdditifsBDD;
	private HashMap<String, Entite> memoireLocaleIngredientsBDD;
	private HashMap<String, Entite> memoireLocaleMarquesBDD;
	private HashMap<String, Entite> memoireLocaleAllergenesBDD;
	private HashMap<String, Entite> memoireLocaleCategoriesBDD;
	private HashMap<String, Entite> memoireLocaleProduitsBDD;

	private int compteurIDCategorie;
	private int compteurIDAdditif;
	private int compteurIDIngredient;
	private int compteurIDMarque;
	private int compteurIDAllergene;
	private int compteurIDProduit;
	
	/**
	 * Instanciée au lancement du programme afin de récupéré l'ensemble de la BDD existante sous forme d'objets et gérer les ID des objets à insérer.
	 * @param connectionParam = Un objet java.sql.Connection relié à la BDD
	 */
	public BDDCache(Connection connectionParam) {
		
		this.connection = connectionParam;
		
		this.memoireLocaleAdditifsBDD = selectAllFromEntite(Additif.class.getSimpleName());
		this.compteurIDAdditif = selectMaxIDfromEntite(Additif.class.getSimpleName());
		
		this.memoireLocaleIngredientsBDD = selectAllFromEntite(Ingredient.class.getSimpleName());
		this.compteurIDIngredient = selectMaxIDfromEntite(Ingredient.class.getSimpleName());
		
		this.memoireLocaleMarquesBDD = selectAllFromEntite(Marque.class.getSimpleName());
		this.compteurIDMarque = selectMaxIDfromEntite(Marque.class.getSimpleName());
		
		this.memoireLocaleAllergenesBDD = selectAllFromEntite(Allergene.class.getSimpleName());
		this.compteurIDAllergene = selectMaxIDfromEntite(Allergene.class.getSimpleName());
		
		this.memoireLocaleCategoriesBDD = selectAllFromEntite(Categorie.class.getSimpleName());
		this.compteurIDCategorie = selectMaxIDfromEntite(Categorie.class.getSimpleName());
		
		this.memoireLocaleProduitsBDD = selectAllFromEntite(Produit.class.getSimpleName());
		this.compteurIDProduit = selectMaxIDfromEntite(Produit.class.getSimpleName());	
	}
	
	/**
	 * Selectionne tous les enregistrements de la table placée en paramètre et les convertis en objets stockée dans une Hashmap
	 * @param nomEntite
	 * @return Hashmap dont la clé est nom_$nomEntite et la valeur l'objet correspondant
	 */
	public HashMap<String, Entite> selectAllFromEntite(String nomEntite) {

		HashMap<String, Entite> tableExistanteEnBDD = new HashMap<String, Entite>();

		String query = "SELECT * FROM " + nomEntite;

		try {
			PreparedStatement selectEntite = this.connection.prepareStatement(query);

			ResultSet result = selectEntite.executeQuery();

			while (result.next()) {

				switch (nomEntite) {
				case "Additif":
					Additif additifEnBDD = new Additif((result.getInt(1)),result.getString(2));
					tableExistanteEnBDD.put(additifEnBDD.nom_Additif, additifEnBDD);
					break;
					
				case "Allergene":
					Allergene allergeneEnBDD = new Allergene(result.getInt(1),result.getString(2));
					tableExistanteEnBDD.put(allergeneEnBDD.nom_Allergene, allergeneEnBDD);
					break;
					
				case "Categorie" :
					Categorie categorieEnBDD = new Categorie(result.getInt(1),result.getString(2));
					tableExistanteEnBDD.put(categorieEnBDD.nom_Categorie, categorieEnBDD);
					break;
					
				case "Ingredient":
					Ingredient ingredientEnBDD = new Ingredient(result.getInt(1),result.getString(2));
					tableExistanteEnBDD.put(ingredientEnBDD.nom_Ingredient, ingredientEnBDD);
					break;
				
				case "Marque":
					Marque marqueEnBDD = new Marque(result.getInt(1),result.getString(2));
					tableExistanteEnBDD.put(marqueEnBDD.nom_Marque, marqueEnBDD);
					break;
				case "Produit" :
					Produit produitEnBDD = new Produit(result.getInt(1), result.getString(2),result.getString(3), result.getInt(4));
					tableExistanteEnBDD.put(produitEnBDD.nom_Produit, produitEnBDD);
				}
			}
			return tableExistanteEnBDD;

		} catch (SQLException e) {
			throw new TraitementFichierException("Erreur SQL : Echec lors de l'importation de la table " + nomEntite + " en mémoire cache", e);
		}
	}

	/**
	 * Recupère le plus grand id_$nomEntite (primary key) de la table placée en paramètre
	 * @param nomEntite
	 * @return Entier du MAX(id_$nomEntite) from $nomEntite
	 */
	public int selectMaxIDfromEntite(String nomEntite) {
		
		int maxID = 0;
		
		String query = "SELECT max(id_"+ nomEntite +") FROM " + nomEntite + ";";
		
		try {
			PreparedStatement selectMaxIDEntite = this.connection.prepareStatement(query);

			ResultSet result = selectMaxIDEntite.executeQuery();
			
			if ( result.next()) {
				maxID = result.getInt(1);
			}
		} catch (SQLException e) {
			throw new TraitementFichierException("Erreur SQL : Echec lors de la récupération du MAX(ID_table) de la table " + nomEntite, e);
		}
		
		return maxID;		
	}


	
	public int getNewID(String nomEntite) {
		int newID = 0;
		
		return newID;
	}
	
	// GETTERS AND SETTERS 
	public HashMap<String, Entite> getMemoireLocaleAdditifsBDD() {
		return memoireLocaleAdditifsBDD;
	}


	public void setMemoireLocaleAdditifsBDD(HashMap<String, Entite> memoireLocaleAdditifsBDD) {
		this.memoireLocaleAdditifsBDD = memoireLocaleAdditifsBDD;
	}


	public HashMap<String, Entite> getMemoireLocaleIngredientsBDD() {
		return memoireLocaleIngredientsBDD;
	}


	public void setMemoireLocaleIngredientsBDD(HashMap<String, Entite> memoireLocaleIngredientsBDD) {
		this.memoireLocaleIngredientsBDD = memoireLocaleIngredientsBDD;
	}


	public HashMap<String, Entite> getMemoireLocaleMarquesBDD() {
		return memoireLocaleMarquesBDD;
	}


	public void setMemoireLocaleMarquesBDD(HashMap<String, Entite> memoireLocaleMarquesBDD) {
		this.memoireLocaleMarquesBDD = memoireLocaleMarquesBDD;
	}


	public HashMap<String, Entite> getMemoireLocaleAllergenesBDD() {
		return memoireLocaleAllergenesBDD;
	}


	public void setMemoireLocaleAllergenesBDD(HashMap<String, Entite> memoireLocaleAllergenesBDD) {
		this.memoireLocaleAllergenesBDD = memoireLocaleAllergenesBDD;
	}


	public HashMap<String, Entite> getMemoireLocaleCategoriesBDD() {
		return memoireLocaleCategoriesBDD;
	}


	public void setMemoireLocaleCategoriesBDD(HashMap<String, Entite> memoireLocaleCategoriesBDD) {
		this.memoireLocaleCategoriesBDD = memoireLocaleCategoriesBDD;
	}


	public HashMap<String, Entite> getMemoireLocaleProduitsBDD() {
		return memoireLocaleProduitsBDD;
	}


	public void setMemoireLocaleProduitsBDD(HashMap<String, Entite> memoireLocaleProduitsBDD) {
		this.memoireLocaleProduitsBDD = memoireLocaleProduitsBDD;
	}


	public int getCompteurIDCategorie() {
		return compteurIDCategorie;
	}


	public void setCompteurIDCategorie(int compteurIDCategorie) {
		this.compteurIDCategorie = compteurIDCategorie;
	}


	public int getCompteurIDAdditif() {
		return compteurIDAdditif;
	}


	public void setCompteurIDAdditif(int compteurIDAdditif) {
		this.compteurIDAdditif = compteurIDAdditif;
	}


	public int getCompteurIDIngredient() {
		return compteurIDIngredient;
	}


	public void setCompteurIDIngredient(int compteurIDIngredient) {
		this.compteurIDIngredient = compteurIDIngredient;
	}


	public int getCompteurIDMarque() {
		return compteurIDMarque;
	}


	public void setCompteurIDMarque(int compteurIDMarque) {
		this.compteurIDMarque = compteurIDMarque;
	}


	public int getCompteurIDAllergene() {
		return compteurIDAllergene;
	}


	public void setCompteurIDAllergene(int compteurIDAllergene) {
		this.compteurIDAllergene = compteurIDAllergene;
	}


	public int getCompteurIDProduit() {
		return compteurIDProduit;
	}


	public void setCompteurIDProduit(int compteurIDProduit) {
		this.compteurIDProduit = compteurIDProduit;
	}
	
}
