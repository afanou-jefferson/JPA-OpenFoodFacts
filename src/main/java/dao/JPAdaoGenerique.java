package dao;

import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import entity.Additif;
import entity.Allergene;
import entity.Categorie;
import entity.Entite;
import entity.Ingredient;
import entity.Marque;
import entity.Produit;

public class JPAdaoGenerique {

	private EntityManagerFactory factory = null;

	public void init() {
		factory = Persistence.createEntityManagerFactory("jpa_OpenFoodFacts");
	}

	public void close() {
		if (factory != null) {
			factory.close();
		}
	}

	// Creer un EM et ouvrir une transaction
	private EntityManager newEntityManager() {
		init();
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		return (em);
	}

	// Fermer un EM et defaire la transaction si necessaire
	private void closeEntityManager(EntityManager em) {
		if (em != null) {
			if (em.isOpen()) {
				EntityTransaction t = em.getTransaction();
				if (t.isActive()) {
					try {
						t.rollback();
					} catch (PersistenceException e) {
						e.printStackTrace();

					}
				}
				em.close();
			}
		}
	}

	public HashMap<String, Entite> selectAllFromEntite(String nomEntite) {
		EntityManager em = null;
		HashMap<String, Entite> tableExistanteEnBDD = new HashMap<String, Entite>();

		String query = "SELECT ent FROM " + nomEntite + " ent";

		try {
			em = newEntityManager();

			switch (nomEntite) {

			case "Additif":
				TypedQuery<Additif> qAdditif = em.createQuery(query, Additif.class);
				List<Additif> resultatAdditifs = qAdditif.getResultList();
				for (Additif additif : resultatAdditifs) {
					tableExistanteEnBDD.put(additif.getNomUnique(), additif);
				}
				break;

			case "Allergene":
				TypedQuery<Allergene> qAllergene = em.createQuery(query, Allergene.class);
				List<Allergene> resultatAllergenes = qAllergene.getResultList();
				for (Allergene allergene : resultatAllergenes) {
					tableExistanteEnBDD.put(allergene.getNomUnique(), allergene);
				}
				break;

			case "Categorie":
				TypedQuery<Categorie> qCategorie = em.createQuery(query, Categorie.class);
				List<Categorie> resultatCategories = qCategorie.getResultList();
				for (Categorie categorie : resultatCategories) {
					tableExistanteEnBDD.put(categorie.getNomUnique(), categorie);
				}
				break;

			case "Ingredient":
				TypedQuery<Ingredient> qIngredient = em.createQuery(query, Ingredient.class);
				List<Ingredient> resultatIngredients = qIngredient.getResultList();
				for (Ingredient ingredient : resultatIngredients) {
					tableExistanteEnBDD.put(ingredient.getNomUnique(), ingredient);
				}
				break;

			case "Marque":
				TypedQuery<Marque> qMarque = em.createQuery(query, Marque.class);
				List<Marque> resultatMarques = qMarque.getResultList();
				for (Marque marque : resultatMarques) {
					tableExistanteEnBDD.put(marque.getNomUnique(), marque);
				}
				break;

			case "Produit":
				TypedQuery<Produit> qProduit = em.createQuery(query, Produit.class);
				List<Produit> resultatProduits = qProduit.getResultList();
				for (Produit produit : resultatProduits) {
					tableExistanteEnBDD.put(produit.getNomUnique(), produit);
				}
				break;
			}

			return tableExistanteEnBDD;

		} finally {
			closeEntityManager(em);
		}

	}

	public void insertTable(Entite entiteToInsert) {

		EntityManager em = null;

		em = newEntityManager();

		switch (entiteToInsert.getNomTable()) {

		case "Additif":
			Additif additifToInsert = (Additif) entiteToInsert;
			em.persist(additifToInsert);
			break;

		case "Allergene":
			Allergene allergeneToInsert = (Allergene) entiteToInsert;
			em.persist(allergeneToInsert);
			break;

		case "Categorie":
			Categorie categorieToInsert = (Categorie) entiteToInsert;
			em.persist(categorieToInsert);
			break;

		case "Ingredient":
			Ingredient ingredientToInsert = (Ingredient) entiteToInsert;
			em.persist(ingredientToInsert);
			break;

		case "Marque":
			Marque marqueToInsert = (Marque) entiteToInsert;
			em.persist(marqueToInsert);
			break;

		case "Produit":
			Produit produitToInsert = (Produit) entiteToInsert;
			em.persist(produitToInsert);
			break;
		}
		em.getTransaction().commit();
	}
}
