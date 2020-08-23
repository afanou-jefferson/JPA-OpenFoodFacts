package entity;

import java.util.ArrayList;
import java.util.Set;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table (name="produit")
public class Produit extends Entite {
	
	@Id
	@GeneratedValue (strategy=GenerationType.IDENTITY)
	public int id_Produit;
	
	@Column(name="nom_produit", length=500, nullable=false, unique= false)
	public String nom_Produit;
	
	@Column(name="grade_nutri_produit", length=50, nullable=false, unique= false)
	public String grade_nutri_produit;
	
	@ManyToOne (cascade=CascadeType.ALL)
	@JoinColumn(name="id_categorie")
	public Categorie categorie_Produit;
	
	@ManyToMany
	@JoinTable(name="jointure_produit_marque",
		joinColumns= @JoinColumn(name="id_produit_jointure", referencedColumnName="id_produit"),
		inverseJoinColumns= @JoinColumn(name="id_marque_jointure", referencedColumnName="id_Marque")
	)
	public Set<Marque> listeMarquesDuProduit;
	
	@ManyToMany
	@JoinTable(name="jointure_produit_ingredient",
		joinColumns= @JoinColumn(name="id_produit_jointure", referencedColumnName="id_produit"),
		inverseJoinColumns= @JoinColumn(name="id_ingredient_jointure", referencedColumnName="id_ingredient")
	)
	public Set<Ingredient> listeIngredientsDuProduit;
	
	@ManyToMany
	@JoinTable(name="jointure_produit_allergene",
		joinColumns= @JoinColumn(name="id_produit_jointure", referencedColumnName="id_produit"),
		inverseJoinColumns= @JoinColumn(name="id_allergene_jointure", referencedColumnName="id_allergene")
	)
	public Set<Allergene> listeAllergenesDuProduit;
	
	@ManyToMany
	@JoinTable(name="jointure_produit_additif",
		joinColumns= @JoinColumn(name="id_produit_jointure", referencedColumnName="id_produit"),
		inverseJoinColumns= @JoinColumn(name="id_additif_jointure", referencedColumnName="id_additif")
	)
	public Set<Additif> listeAdditifsDuProduit;
	
	public Produit() {}
	
	public Produit(String nom_Produit, String grade_nutri_produit, Categorie categorieProduit) {
		super();
		this.nom_Produit = nom_Produit;
		this.grade_nutri_produit = grade_nutri_produit;
		this.categorie_Produit = categorieProduit;
	}

	public Produit(int id_Produit, String nom_Produit, String grade_nutri_produit,  Categorie categorieProduit,
			Set<Marque> listeMarquesDuProduit, Set<Ingredient> listeIngredientsDuProduit,
			Set<Allergene> listeAllergenesDuProduit, Set<Additif> listeAdditifsDuProduit) {
		super();
		this.id_Produit = id_Produit;
		this.nom_Produit = nom_Produit;
		this.grade_nutri_produit = grade_nutri_produit;
		this.categorie_Produit = categorieProduit;
		this.listeMarquesDuProduit = listeMarquesDuProduit;
		this.listeIngredientsDuProduit = listeIngredientsDuProduit;
		this.listeAllergenesDuProduit = listeAllergenesDuProduit;
		this.listeAdditifsDuProduit = listeAdditifsDuProduit;
	}

	@Override
	public ArrayList<String> getValeurAttributsTable() {
		ArrayList<String> listeValeurs = new ArrayList<String>();

		listeValeurs.add(Integer.toString(this.id_Produit));
		listeValeurs.add(this.nom_Produit);
		listeValeurs.add(this.grade_nutri_produit);
		listeValeurs.add(this.categorie_Produit.getNomUnique());

		return listeValeurs;
	}

	@Override
	public String getNomUnique() {
		// TODO Auto-generated method stub
		return this.nom_Produit;
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return this.id_Produit;
	}

	public int getId_Produit() {
		return id_Produit;
	}

	public void setId_Produit(int id_Produit) {
		this.id_Produit = id_Produit;
	}

	public String getNom_Produit() {
		return nom_Produit;
	}

	public void setNom_Produit(String nom_Produit) {
		this.nom_Produit = nom_Produit;
	}

	public String getGrade_nutri_produit() {
		return grade_nutri_produit;
	}

	public void setGrade_nutri_produit(String grade_nutri_produit) {
		this.grade_nutri_produit = grade_nutri_produit;
	}

	public Categorie getCategorie_Produit() {
		return categorie_Produit;
	}

	public void setCategorie_Produit(Categorie categorie_Produit) {
		this.categorie_Produit = categorie_Produit;
	}

	public Set<Marque> getListeMarquesDuProduit() {
		return listeMarquesDuProduit;
	}

	public void setListeMarquesDuProduit(Set<Marque> listeMarquesDuProduit) {
		this.listeMarquesDuProduit = listeMarquesDuProduit;
	}

	public Set<Ingredient> getListeIngredientsDuProduit() {
		return listeIngredientsDuProduit;
	}

	public void setListeIngredientsDuProduit(Set<Ingredient> listeIngredientsDuProduit) {
		this.listeIngredientsDuProduit = listeIngredientsDuProduit;
	}

	public Set<Allergene> getListeAllergenesDuProduit() {
		return listeAllergenesDuProduit;
	}

	public void setListeAllergenesDuProduit(Set<Allergene> listeAllergenesDuProduit) {
		this.listeAllergenesDuProduit = listeAllergenesDuProduit;
	}

	public Set<Additif> getListeAdditifsDuProduit() {
		return listeAdditifsDuProduit;
	}

	public void setListeAdditifsDuProduit(Set<Additif> listeAdditifsDuProduit) {
		this.listeAdditifsDuProduit = listeAdditifsDuProduit;
	}
	
	

}