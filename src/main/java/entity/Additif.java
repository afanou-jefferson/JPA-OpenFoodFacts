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
 * Représente la table Additif de la BDD qui receuilleles Additifs présent dans les produits
 * @author Exost
 *
 */

@Entity
@Table(name="additif")
public class Additif extends Entite{
	
	@Id
	@GeneratedValue (strategy=GenerationType.IDENTITY)
	public int id_Additif;
	
	@Column(name="nom_additif", length=200, nullable=false, unique= false)
	public String nom_Additif;

	@ManyToMany
	@JoinTable(name="jointure_produit_additif",
		joinColumns= @JoinColumn(name="id_additif_jointure", referencedColumnName="id_Additif"),
		inverseJoinColumns= @JoinColumn(name="id_produit_jointure", referencedColumnName="id_produit")
	)
	private Set<Produit> produits_additif;

	
	public Additif(String nom_Additif) {
		super();
		this.nom_Additif = nom_Additif;
	}
	
	public Additif() {}
	
	@Override
	public String getNomUnique() {
		// TODO Auto-generated method stub
		return this.nom_Additif;
	}
	
	@Override
	public ArrayList<String> getValeurAttributsTable() {
		ArrayList<String> listeValeurs = new ArrayList<String>();
		
		listeValeurs.add(Integer.toString(this.id_Additif));
		listeValeurs.add(this.nom_Additif);
		
		return listeValeurs;
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return this.id_Additif;
	}

	public int getId_Additif() {
		return id_Additif;
	}

	public void setId_Additif(int id_Additif) {
		this.id_Additif = id_Additif;
	}

	public String getNom_Additif() {
		return nom_Additif;
	}

	public void setNom_Additif(String nom_Additif) {
		this.nom_Additif = nom_Additif;
	}

	public Set<Produit> getProduits_additif() {
		return produits_additif;
	}

	public void setProduits_additif(Set<Produit> produits_additif) {
		this.produits_additif = produits_additif;
	}

	

	


}
