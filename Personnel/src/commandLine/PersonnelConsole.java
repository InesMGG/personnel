package commandLine;

import personnel.*;
import commandLineMenus.*;
import commandLineMenus.Action;

import static commandLineMenus.rendering.examples.util.InOut.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.sql.SQLException;

import javax.swing.*;
public class PersonnelConsole
{
	private GestionPersonnel gestionPersonnel;
	LigueConsole ligueConsole;
	EmployeConsole employeConsole;
	
	public PersonnelConsole(GestionPersonnel gestionPersonnel)
	{
		this.gestionPersonnel = gestionPersonnel;
		this.employeConsole = new EmployeConsole();
		this.ligueConsole = new LigueConsole(gestionPersonnel, employeConsole);
		
	}
	
	public void start()
	{
		menuPrincipal().start();
	}
	
	private Menu menuPrincipal()
	{
		Menu menu = new Menu("Gestion du personnel des ligues");
		menu.add(employeConsole.editerEmploye(gestionPersonnel.getRoot()));
		menu.add(ligueConsole.menuLigues());
		menu.add(menuQuitter());
		return menu;
	}

	private Menu menuQuitter()
	{
		Menu menu = new Menu("Quitter", "q");
		menu.add(quitterEtEnregistrer());
		menu.add(quitterSansEnregistrer());
		menu.addBack("r");
		return menu;
	}
	
	private Option quitterEtEnregistrer()
	{
		return new Option("Quitter et enregistrer", "q", 
				() -> 
				{
					try
					{
						gestionPersonnel.sauvegarder();
						Action.QUIT.optionSelected();
					} 
					catch (SauvegardeImpossible e)
					{
						System.out.println("Impossible d'effectuer la sauvegarde");
					}
				}
			);
	}
	
	private Option quitterSansEnregistrer()
	{
		return new Option("Quitter sans enregistrer", "a", Action.QUIT);
	}
	
	private boolean verifiePassword()
	{
		boolean ok = gestionPersonnel.getRoot().checkPassword(getString("password : "));
		if (!ok)
			System.out.println("Password incorrect.");
		return ok;
	}
	
	public static void main(String[] args) throws SauvegardeImpossible, SQLException
	{
		JFrame frame = new JFrame();
	    JPanel panel = new JPanel();
	    panel.setPreferredSize(new Dimension(150, 150));
	    frame.setVisible(true);
	    frame.setTitle("Maison des ligues : Gestion des ligues");
	    frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
	    frame.setContentPane(panel);
	    panel.setBackground(Color.white);
	    frame.setBackground(Color.white);
	    panel.setLayout(new FlowLayout());
	    frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
	    JLabel label = new JLabel("Maison des ligues : Gestion des ligues");
	    panel.add(label);
	    label.setFont(new Font("Times New Roman", Font.BOLD, 40));
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		
		PersonnelConsole personnelConsole = 
				new PersonnelConsole(GestionPersonnel.getGestionPersonnel());
		if (personnelConsole.verifiePassword())
			personnelConsole.start();
	}
}