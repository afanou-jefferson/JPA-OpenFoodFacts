package entity;

import java.util.ArrayList;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;


/**
 * Représente la table Ingredient de la BDD qui receuille les ingrédients composants les produits
 * @author Exost
 *
 */


@Entity
@Table (name="ingredient")
public class Ingredient extends Entite {
	
	@Id
	public int id_Ingredient;
	
	@Column(name="nom_ingredient", length=200, nullable=false, unique= false)
	public String nom_Ingredient;
	
	@ManyToMany
	@JoinTable(name="jointure_produit_ingredient",
		joinColumns= @JoinColumn(name="id_ingredient_jointure", referencedColumnName="id_ingredient"),
		inverseJoinColumns= @JoinColumn(name="id_produit_jointure", referencedColumnName="id_produit")
	)
	private Set<Produit> produits_ingredient;

	public Ingredient(int id_Ingredient, String nom_Ingredient) {
		super();
		this.id_Ingredient = id_Ingredient;
		this.nom_Ingredient = nom_Ingredient;
	}

	@Override
	public String getNomUnique() {
		// TODO Auto-generated method stub
		return this.nom_Ingredient;
	}

	@Override
	public ArrayList<String> getValeurAttributsTable() {
		ArrayList<String> listeValeurs = new ArrayList<String>();

		listeValeurs.add(Integer.toString(this.id_Ingredient));
		listeValeurs.add(this.nom_Ingredient);

		return listeValeurs;
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return this.id_Ingredient;
	}

	public int getId_Ingredient() {
		return id_Ingredient;
	}

	public void setId_Ingredient(int id_Ingredient) {
		this.id_Ingredient = id_Ingredient;
	}

	public String getNom_Ingredient() {
		return nom_Ingredient;
	}

	public void setNom_Ingredient(String nom_Ingredient) {
		this.nom_Ingredient = nom_Ingredient;
	}

	public Set<Produit> getProduits_ingredient() {
		return produits_ingredient;
	}

	public void setProduits_ingredient(Set<Produit> produits_ingredient) {
		this.produits_ingredient = produits_ingredient;
	}
	
	
	
	

}
