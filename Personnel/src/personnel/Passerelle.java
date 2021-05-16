package personnel;

import java.sql.SQLException;

public interface Passerelle 
{
	public GestionPersonnel getGestionPersonnel() throws SauvegardeImpossible, SQLException;
	public void sauvegarderGestionPersonnel(GestionPersonnel gestionPersonnel)  throws SauvegardeImpossible;
	public int insertLigue(Ligue ligue) throws SauvegardeImpossible;
	public int updateLigue(Ligue ligue) throws SauvegardeImpossible;
	public int insertEmploye(Employe employe) throws SauvegardeImpossible;
	public int updateEmploye(Employe employe) throws SauvegardeImpossible;
	public int deleteEmploye(Employe employe) throws SauvegardeImpossible;
	public int newAdmin(Employe employe) throws SauvegardeImpossible, SQLException;
	public int deleteLigue(Ligue ligue) throws SauvegardeImpossible;
}