package commandLine;

import static commandLineMenus.rendering.examples.util.InOut.getString;

import commandLineMenus.ListOption;
import commandLineMenus.Menu;
import commandLineMenus.Option;
import personnel.Employe;
import personnel.ImpossibleDeChangerDate;

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
			employe.remove();
		
		}
	);
	}
	
	private Option changerDateArriv�e(final Employe employe) 
	{
		return new Option("Changer la date d'arriv�e", "a", () -> {try {
			employe.setDateArriv�e(getString("Nouvelle date d'arriv�e : "));
		} catch (ImpossibleDeChangerDate e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}});
	}

	private Option changerDateD�part(final Employe employe) 
	{
		return new Option("Changer la date de d�part", "d", () -> {try {
			employe.setDateD�part(getString("Nouvelle date de d�part : "));
		} catch (ImpossibleDeChangerDate e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}});
	}

	private Option changerNom(final Employe employe)
	{
		return new Option("Changer le nom", "n", () -> {employe.setNom(getString("Nouveau nom : "));});
	}
	
	private Option changerPrenom(final Employe employe)
	{
		return new Option("Changer le pr�nom", "p", () -> {employe.setPrenom(getString("Nouveau pr�nom : "));});
	}
	
	private Option changerMail(final Employe employe)
	{
		return new Option("Changer le mail", "e", () -> {employe.setMail(getString("Nouveau mail : "));});
	}
	
	private Option changerPassword(final Employe employe)
	{
		return new Option("Changer le password", "x", () -> {employe.setPassword(getString("Nouveau password : "));});
	}
	
}
