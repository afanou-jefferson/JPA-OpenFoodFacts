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

@Entity
@Table (name="allergene")
public class Allergene extends Entite{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int id_Allergene;
	
	@Column (name="nom_Allergene", length=200, nullable=false, unique= false)
	public String nom_Allergene;
	
	@ManyToMany
	@JoinTable(name="jointure_produit_allergene",
		joinColumns= @JoinColumn(name="id_allergene_jointure", referencedColumnName="id_allergene"),
		inverseJoinColumns= @JoinColumn(name="id_produit_jointure", referencedColumnName="id_produit")
	)
	private Set<Produit> produits_allergene;


	public Allergene(int id_Allergene, String nom_Allergene) {
		super();
		this.id_Allergene = id_Allergene;
		this.nom_Allergene = nom_Allergene;
	}
	
	@Override
	public String getNomUnique() {
		// TODO Auto-generated method stub
		return this.nom_Allergene;
	}
	
	@Override
	public ArrayList<String> getValeurAttributsTable() {
		ArrayList<String> listeValeurs = new ArrayList<String>();
		
		listeValeurs.add(Integer.toString(this.id_Allergene));
		listeValeurs.add(this.nom_Allergene);
		
		return listeValeurs;
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return this.id_Allergene;
	}
	
	
}
