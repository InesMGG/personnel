package commandLine;

import static commandLineMenus.rendering.examples.util.InOut.getString;

import java.sql.SQLException;

import commandLineMenus.ListOption;
import commandLineMenus.Menu;
import commandLineMenus.Option;
import personnel.Employe;
import personnel.ImpossibleChangerDate;
import personnel.SauvegardeImpossible;

public class EmployeConsole 
{
	private Option afficher(final Employe employe)
	{
		return new Option("Afficher l'employ�", "l", () -> {System.out.println(employe);});
	}

	ListOption<Employe> editerEmploye()
	{
		return (employe) -> editerEmploye(employe);		
	}

	Option editerEmploye(Employe employe)
	{
			Menu menu = new Menu("G�rer le compte " + employe.getNom(), "c");
			menu.add(afficher(employe));
			menu.add(changerNom(employe));
			menu.add(changerPrenom(employe));
			menu.add(changerMail(employe));
			menu.add(changerPassword(employe));
			menu.add(changerDateArriv�e(employe));
			menu.add(changerDateD�part(employe));
			menu.add(supprimerEmploye(employe));
			menu.addBack("q");
			return menu;
	}

	private Option supprimerEmploye(final Employe employe)
	{
		return new Option("Supprimer un employ�", "z", ()->{
			try {
				employe.remove();
			} catch (SauvegardeImpossible e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		}
	);
	}
	
	private Option changerDateArriv�e(final Employe employe) 
	{
		return new Option("Changer la date d'arriv�e", "a", () -> {try {
			employe.setDateArriv�e(getString("Nouvelle date d'arriv�e : "));
		} catch (ImpossibleChangerDate e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SauvegardeImpossible e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}});
	}

	private Option changerDateD�part(final Employe employe) 
	{
		return new Option("Changer la date de d�part", "d", () -> {try {
			employe.setDateD�part(getString("Nouvelle date de d�part : "));
		} catch (ImpossibleChangerDate e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SauvegardeImpossible e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}});
	}

	private Option changerNom(final Employe employe)
	{
		return new Option("Changer le nom", "n", () -> {try {
			employe.setNom(getString("Nouveau nom : "));
		} catch (SauvegardeImpossible e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}});
	}
	
	private Option changerPrenom(final Employe employe)
	{
		return new Option("Changer le pr�nom", "p", () -> {try {
			employe.setPrenom(getString("Nouveau pr�nom : "));
		} catch (SauvegardeImpossible e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}});
	}
	
	private Option changerMail(final Employe employe)
	{
		return new Option("Changer le mail", "e", () -> {try {
			employe.setMail(getString("Nouveau mail : "));
		} catch (SauvegardeImpossible e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}});
	}
	
	private Option changerPassword(final Employe employe)
	{
		return new Option("Changer le password", "x", () -> {try {
			employe.setPassword(getString("Nouveau password : "));
		} catch (SauvegardeImpossible e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}});
	}
	
}
