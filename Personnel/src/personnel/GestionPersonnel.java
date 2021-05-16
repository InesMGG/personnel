package personnel;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Gestion du personnel. Un seul objet de cette classe existe.
 * Il n'est pas possible d'instancier directement cette classe, 
 * la m矇thode {@link #getGestionPersonnel getGestionPersonnel} 
 * le fait automatiquement et retourne toujours le m礙me objet.
 * Dans le cas o羅 {@link #sauvegarder()} a 矇t矇 appel矇 lors 
 * d'une ex矇cution pr矇c矇dente, c'est l'objet sauvegard矇 qui est
 * retourn矇.
 */

public class GestionPersonnel implements Serializable
{
	private static final long serialVersionUID = -105283113987886425L;
	private static GestionPersonnel gestionPersonnel = null;
	private SortedSet<Ligue> ligues;

	private Employe root;

	public final static int SERIALIZATION = 1, JDBC = 2, 
			TYPE_PASSERELLE = JDBC;  
	private static Passerelle passerelle = TYPE_PASSERELLE == JDBC ? new jdbc.JDBC() : new serialisation.Serialization();	
	
	/**
	 * Retourne l'unique instance de cette classe.
	 * Cr矇e cet objet s'il n'existe d矇j�.
	 * @return l'unique objet de type {@link GestionPersonnel}.
	 * @throws SauvegardeImpossible 
	 * @throws SQLException 
	 */
	
	public static GestionPersonnel getGestionPersonnel() throws SauvegardeImpossible, SQLException
	{
		if (gestionPersonnel == null)
		{
			gestionPersonnel = passerelle.getGestionPersonnel();
			if (gestionPersonnel == null)
				gestionPersonnel = new GestionPersonnel();
		}
		return gestionPersonnel;
	}

	public GestionPersonnel() throws SauvegardeImpossible, SQLException
	{
		if (gestionPersonnel != null)
			throw new RuntimeException("Vous ne pouvez cr嶪r qu'une seuls instance de cet objet.");
		ligues = new TreeSet<>();
		gestionPersonnel = this;
		root = new Employe(this, null, "root", " ", " ", "toor", null, null);
	}
	
	public void sauvegarder() throws SauvegardeImpossible
	{
		passerelle.sauvegarderGestionPersonnel(this);
	}
	
	/**
	 * Retourne la ligue dont administrateur est l'administrateur,
	 * null s'il n'est pas un administrateur.
	 * @param administrateur l'administrateur de la ligue recherch矇e.
	 * @return la ligue dont administrateur est l'administrateur.
	 */
	
	public Ligue getLigue(Employe administrateur)
	{
		if (administrateur.estAdmin(administrateur.getLigue()))
			return administrateur.getLigue();
		else
			return null;
	}

	/**
	 * Retourne toutes les ligues enregistr矇es.
	 * @return toutes les ligues enregistr矇es.
	 */
	
	public SortedSet<Ligue> getLigues()
	{
		return Collections.unmodifiableSortedSet(ligues);
	}

	public Ligue addLigue(String nom) throws SauvegardeImpossible, SQLException
	{
		Ligue ligue = new Ligue(this, nom); 
		ligues.add(ligue);
		return ligue;
	}
	
	public Ligue addLigue(int id, String nom)
	{
		Ligue ligue = new Ligue(this, id, nom);
		ligues.add(ligue);
		return ligue;
	}

	void remove(Ligue ligue)
	{
		ligues.remove(ligue);
	}
	
	int insertLigue(Ligue ligue) throws SauvegardeImpossible
	{
		return passerelle.insertLigue(ligue);
	}
	
	int updateLigue(Ligue ligue) throws SauvegardeImpossible
	{
		return passerelle.updateLigue(ligue);
	}
	
	int insertEmploye(Employe employe) throws SauvegardeImpossible
	{
		return passerelle.insertEmploye(employe);
	}
	
	void updateEmploye(Employe employe) throws SauvegardeImpossible
	{
		passerelle.updateEmploye(employe);
	}
	
	int deleteEmploye(Employe employe) throws SauvegardeImpossible
	{
		return passerelle.deleteEmploye(employe);
	}
	
	int newAdmin(Employe employe) throws SauvegardeImpossible, SQLException
	{
		return passerelle.newAdmin(employe);
	}
	
	int deleteLigue(Ligue ligue) throws SauvegardeImpossible
	{
		return passerelle.deleteLigue(ligue);
	}

	/**
	 * Retourne le root (super-utilisateur).
	 * @return le root.
	 */
	
	public Employe getRoot()
	{
		return root;
	}
}