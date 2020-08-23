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
import dao.JPAdaoGenerique;

/**
 * Classe visant à simuler et stocker le contenu de la BDD distante en locale
 * afin de minimiser le nombre de requête.
 * 
 * @author Exost
 *
 */
public class BDDCache {

	JPAdaoGenerique dao;

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
	 * Instanciée au lancement du programme afin de récupéré l'ensemble de la BDD
	 * existante sous forme d'objets et gérer les ID des objets à insérer.
	 * 
	 * @param
	 */
	public BDDCache() {

		JPAdaoGenerique dao = new JPAdaoGenerique();
		dao.init();

		this.memoireLocaleAdditifsBDD = dao.selectAllFromEntite(Additif.class.getSimpleName());

		this.memoireLocaleIngredientsBDD = dao.selectAllFromEntite(Ingredient.class.getSimpleName());

		this.memoireLocaleMarquesBDD = dao.selectAllFromEntite(Marque.class.getSimpleName());

		this.memoireLocaleAllergenesBDD = dao.selectAllFromEntite(Allergene.class.getSimpleName());

		this.memoireLocaleCategoriesBDD = dao.selectAllFromEntite(Categorie.class.getSimpleName());

		this.memoireLocaleProduitsBDD = dao.selectAllFromEntite(Produit.class.getSimpleName());
		
		dao.close();

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
