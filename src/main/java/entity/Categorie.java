package entity;

import java.util.ArrayList;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Représente la table Categorie de la BDD qui receuille les catégories qui classifient les produits
 * @author Exost
 *
 */
@Entity
@Table(name="categorie")
public class Categorie extends Entite{ 
	
	@Id
	public int id_Categorie;

	@Column(name="nom_categorie", length=200, nullable=false, unique= false)
	public String nom_Categorie;
	
	@OneToMany(mappedBy="categorie_Produit")
	private Set<Produit> produits_categorie;
	
	public Categorie() {}
	
	
	public int getId_Categorie() {
		return id_Categorie;
	}

	public void setId_Categorie(int id_Categorie) {
		this.id_Categorie = id_Categorie;
	}

	public String getNom_Categorie() {
		return nom_Categorie;
	}

	public void setNom_Categorie(String nom_Categorie) {
		this.nom_Categorie = nom_Categorie;
	}

	public Set<Produit> getProduits_categorie() {
		return produits_categorie;
	}

	public void setProduits_categorie(Set<Produit> produits_categorie) {
		this.produits_categorie = produits_categorie;
	}

	public Categorie(int id_Categorie, String nom_Categorie) {
		super();
		this.id_Categorie = id_Categorie;
		this.nom_Categorie = nom_Categorie;
	}

	@Override
	public String toString() {
		return "Categorie [nomCategorie=" + nom_Categorie + "]";
	}

	@Override
	public String getNomUnique() {
		// TODO Auto-generated method stub
		return this.nom_Categorie;
	}
	@Override
	public ArrayList<String> getValeurAttributsTable() {
		ArrayList<String> listeValeurs = new ArrayList<String>();
		
		listeValeurs.add(Integer.toString(this.id_Categorie));
		listeValeurs.add(this.nom_Categorie);
		
		return listeValeurs;
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return this.id_Categorie;
	}
	
	
	
	
}
