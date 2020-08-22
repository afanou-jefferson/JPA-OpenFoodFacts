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
 * Représente la table Categorie de la BDD qui receuille les catégories qui classifient les produits
 * @author Exost
 *
 */
@Entity
@Table (name="marque")
public class Marque extends Entite{
	
	@Id
	@GeneratedValue (strategy=GenerationType.IDENTITY)
	public int id_Marque;
	
	@Column(name="nom_marque", length=200, nullable=false, unique= false)
	public String nom_Marque;
	
	@ManyToMany
	@JoinTable(name="jointure_produit_marque",
		joinColumns= @JoinColumn(name="id_marque_jointure", referencedColumnName="id_marque"),
		inverseJoinColumns= @JoinColumn(name="id_produit_jointure", referencedColumnName="id_produit")
	)
	private Set<Produit> produits_marque;

	public Marque () {}
	
	public Marque(String nom_Marque) {
		super();
		this.nom_Marque = nom_Marque;
	}

	@Override
	public String toString() {
		return "Marque [id_Marque=" + id_Marque + ", nom_Marque=" + nom_Marque + "]";
	}

	@Override
	public String getNomUnique() {
		// TODO Auto-generated method stub
		return this.nom_Marque;
	}

	@Override
	public ArrayList<String> getValeurAttributsTable() {
		ArrayList<String> listeValeurs = new ArrayList<String>();
		
		listeValeurs.add(Integer.toString(this.id_Marque));
		listeValeurs.add(this.nom_Marque);
		
		return listeValeurs;
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return this.id_Marque;
	}
	
	
	
	
	

}
