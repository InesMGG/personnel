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
		return new Option("Afficher l'employé", "l", () -> {System.out.println(employe);});
	}

	ListOption<Employe> editerEmploye()
	{
		return (employe) -> editerEmploye(employe);		
	}

	Option editerEmploye(Employe employe)
	{
			Menu menu = new Menu("Gérer le compte " + employe.getNom(), "c");
			menu.add(afficher(employe));
			menu.add(changerNom(employe));
			menu.add(changerPrenom(employe));
			menu.add(changerMail(employe));
			menu.add(changerPassword(employe));
			menu.add(changerDateArrivée(employe));
			menu.add(changerDateDépart(employe));
			menu.add(supprimerEmploye(employe));
			menu.addBack("q");
			return menu;
	}

	private Option supprimerEmploye(final Employe employe)
	{
		return new Option("Supprimer un employé", "z", ()->{
			try {
				employe.remove();
			} catch (SauvegardeImpossible e) {
				System.out.println("Impossible de supprimer cette employé");
			} catch (SQLException e) {
				System.out.println("Impossible de supprimer cette employé");
			}
		
		}
	);
	}
	
	private Option changerDateArrivée(final Employe employe) 
	{
		return new Option("Changer la date d'arrivée YYYY-MM-DD", "a", () -> {try {
			employe.setDateArrivée(getString("Nouvelle date d'arrivée : "));
		} catch (ImpossibleChangerDate e) {
			System.out.println("Impossible de changer la date d'arrivée de cette employé");
		} catch (SauvegardeImpossible e) {
			System.out.println("Impossible de changer la date d'arrivée de cette employé");
		}});
	}

	private Option changerDateDépart(final Employe employe) 
	{
		return new Option("Changer la date de départ YYYY-MM-DD", "d", () -> {try {
			employe.setDateDépart(getString("Nouvelle date de départ : "));
		} catch (ImpossibleChangerDate e) {
			System.out.println("Impossible de changer la date de départ de cette employé");
		} catch (SauvegardeImpossible e) {
			System.out.println("Impossible de changer la date de départ de cette employé");
		}});
	}

	private Option changerNom(final Employe employe)
	{
		return new Option("Changer le nom", "n", () -> {try {
			employe.setNom(getString("Nouveau nom : "));
		} catch (SauvegardeImpossible e) {
			System.out.println("Impossible de changer le nom de cette employé");
		}});
	}
	
	private Option changerPrenom(final Employe employe)
	{
		return new Option("Changer le prénom", "p", () -> {try {
			employe.setPrenom(getString("Nouveau prénom : "));
		} catch (SauvegardeImpossible e) {
			System.out.println("Impossible de changer le prénom de cette employé");
		}});
	}
	
	private Option changerMail(final Employe employe)
	{
		return new Option("Changer le mail", "e", () -> {try {
			employe.setMail(getString("Nouveau mail : "));
		} catch (SauvegardeImpossible e) {
			System.out.println("Impossible de changer le courriel de cette employé");
		}});
	}
	
	private Option changerPassword(final Employe employe)
	{
		return new Option("Changer le password", "x", () -> {try {
			employe.setPassword(getString("Nouveau password : "));
		} catch (SauvegardeImpossible e) {
			System.out.println("Impossible de changer le mot de passe de cette employé");
		}});
	}
	
}
