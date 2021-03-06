package personnel;

import java.io.Serializable;
import java.sql.SQLException;
import java.time.LocalDate;



/**
 * Employé d'une ligue hébergée par la M2L. Certains peuvent 
 * être administrateurs des employés de leur ligue.
 * Un seul employé, rattaché �  aucune ligue, est le root.
 * Il est impossible d'instancier directement un employé, 
 * il faut passer la méthode {@link Ligue#addEmploye addEmploye}.
 */

public class Employe implements Serializable, Comparable<Employe>
{
	private static final long serialVersionUID = 4795721718037994734L;
	private String nom, prenom, password, mail;
	private LocalDate dateArriv�e, dateD�part;
	private Ligue ligue;
	private GestionPersonnel gestionPersonnel;
	private int id;
	
	Employe(GestionPersonnel gestionPersonnel, Ligue ligue, String nom, String prenom, String mail, String password, LocalDate dateArriv�e, LocalDate dateD�part, int id)
	{
		this.gestionPersonnel = gestionPersonnel;
		this.nom = nom;
		this.prenom = prenom;
		this.password = password;
		this.mail = mail;
		this.dateArriv�e = dateArriv�e;
		this.dateD�part = dateD�part;
		this.ligue = ligue;
		this.id = id;
	}
	
	Employe(GestionPersonnel gestionPersonnel, Ligue ligue, String nom, String prenom, String mail, String password, LocalDate dateArriv�e, LocalDate dateD�part) throws SauvegardeImpossible, SQLException
	{
		this.gestionPersonnel = gestionPersonnel;
		this.nom = nom;
		this.prenom = prenom;
		this.password = password;
		this.mail = mail;
		this.dateArriv�e = dateArriv�e;
		this.dateD�part = dateD�part;
		this.ligue = ligue;
		if (dateArriv�e != null && dateD�part!= null)
		{
			this.id = gestionPersonnel.insertEmploye(this);	
		}	
	}
	
	/**
	 * Retourne vrai ssi l'employé est administrateur de la ligue 
	 * passée en paramètre.
	 * @return vrai ssi l'employé est administrateur de la ligue 
	 * passée en paramètre.
	 * @param ligue la ligue pour laquelle on souhaite vérifier si this 
	 * est l'admininstrateur.
	 */
	
	public boolean estAdmin(Ligue ligue)
	{
		return ligue.getAdministrateur() == this;
	}
	
	/**
	 * Retourne vrai ssi l'employé est le root.
	 * @return vrai ssi l'employé est le root.
	 * @throws SauvegardeImpossible 
	 * @throws SQLException 
	 */
	
	public boolean estRoot() throws SauvegardeImpossible, SQLException
	{
		return GestionPersonnel.getGestionPersonnel().getRoot() == this;
	}
	
	/**
	 * Retourne le nom de l'employé.
	 * @return le nom de l'employé. 
	 */
	
	public int getId()
	{
		return id;
	}
	
	
	
	public String getNom()
	{
		return nom;
	}

	/**
	 * Change le nom de l'employé.
	 * @param nom le nouveau nom.
	 * @throws SauvegardeImpossible 
	 * @throws SQLException 
	 */
	
	public void setNom(String nom) throws SauvegardeImpossible, SQLException
	{
		this.nom = nom;
		gestionPersonnel.updateEmploye(this);
	}

	/**
	 * Retourne le prénom de l'employé.
	 * @return le prénom de l'employé.
	 */
	
	public String getPrenom()
	{
		return prenom;
	}
	
	/**
	 * Change le prénom de l'employé.
	 * @param prenom le nouveau prénom de l'employé. 
	 * @throws SauvegardeImpossible 
	 * @throws SQLException 
	 */

	public void setPrenom(String prenom) throws SauvegardeImpossible, SQLException
	{
		this.prenom = prenom;
		gestionPersonnel.updateEmploye(this);
	}

	/**
	 * Retourne le mail de l'employé.
	 * @return le mail de l'employé.
	 */
	
	public String getMail()
	{
		return mail;
	}
	
	/**
	 * Change le mail de l'employé.
	 * @param mail le nouveau mail de l'employé.
	 * @throws SauvegardeImpossible 
	 * @throws SQLException 
	 */

	public void setMail(String mail) throws SauvegardeImpossible, SQLException
	{
		this.mail = mail;
		gestionPersonnel.updateEmploye(this);
	}

	/**
	 * Retourne vrai ssi le password passé en paramètre est bien celui
	 * de l'employé.
	 * @return vrai ssi le password passé en paramètre est bien celui
	 * de l'employé.
	 * @param password le password auquel comparer celui de l'employé.
	 */
	
	public boolean checkPassword(String password)
	{
		return this.password.equals(password);
	}

	/**
	 * Change le password de l'employé.
	 * @param password le nouveau password de l'employé. 
	 * @throws SauvegardeImpossible 
	 * @throws SQLException 
	 */
	
	public void setPassword(String password) throws SauvegardeImpossible, SQLException
	{
		this.password= password;
		gestionPersonnel.updateEmploye(this);
	}

	public String getPassword()
	{
		return password;
	}
	/**
	 * Retourne la ligue �  laquelle l'employé est affecté.
	 * @return la ligue �  laquelle l'employé est affecté.
	 */
	
	public LocalDate getDateD�part() {
		return dateD�part;
	}

	public void setDateD�part(String dateD�part) throws ImpossibleChangerDate, SauvegardeImpossible, SQLException 
	{
		LocalDate dateDepart = LocalDate.parse(dateD�part);
		if (dateArriv�e == null)
		{
			this.dateD�part = dateDepart;
		}
		else
		{
			boolean isBefore = dateDepart.isBefore(dateArriv�e);
			if (isBefore)
			{
				throw new ImpossibleChangerDate();
			}
			else
			{
				this.dateD�part = dateDepart;
				gestionPersonnel.updateEmploye(this);
			}
		}
	}

	public LocalDate getDateArriv�e() {
		return dateArriv�e;
	}

	public void setDateArriv�e(String dateArriv�e) throws ImpossibleChangerDate, SauvegardeImpossible, SQLException {
		LocalDate dateArrivee = LocalDate.parse(dateArriv�e);
		if(dateD�part == null)
		{
			this.dateArriv�e = dateArrivee;
		}
		else
		{
			boolean isBefore = dateArrivee.isBefore(dateD�part);
			if (isBefore)
			{
				this.dateArriv�e = dateArrivee;
				gestionPersonnel.updateEmploye(this);
			}
			else
				
			{
				throw new ImpossibleChangerDate();
			}
		}
		
	}
	
	public Ligue getLigue()
	{
		return ligue;
	}

	/**
	 * Supprime l'employé. Si celui-ci est un administrateur, le root
	 * récupère les droits d'administration sur sa ligue.
	 * @throws SauvegardeImpossible 
	 * @throws SQLException 
	 */
	
	public void remove() throws SauvegardeImpossible, SQLException
	{
		Employe root = GestionPersonnel.getGestionPersonnel().getRoot();
		if (this != root)
		{
			if (estAdmin(getLigue()))
				getLigue().setAdministrateur(root);
			ligue.remove(this);
			gestionPersonnel.deleteEmploye(this);
		}
		else
			throw new ImpossibleDeSupprimerRoot();
	}

	@Override
	public int compareTo(Employe autre)
	{
		int cmp = getNom().compareTo(autre.getNom());
		if (cmp != 0)
			return cmp;
		return getPrenom().compareTo(autre.getPrenom());
	}
	
	@Override
	public String toString()
	{
		String res = nom +" " +prenom + " "+ mail+" " + dateArriv�e+ " "+ dateD�part + " ";
		try {
			if (estRoot())
				res += " (super-utilisateur)";
			else
				res += ligue.toString();
		} catch (SauvegardeImpossible e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res ;
	}
	
}