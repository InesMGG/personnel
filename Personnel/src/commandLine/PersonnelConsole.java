package commandLine;

import personnel.*;
import commandLineMenus.*;
import commandLineMenus.Action;
import commandLineMenus.Menu;
import commandLineMenus.Option;
import personnel.GestionPersonnel;
import personnel.Ligue;
import personnel.SauvegardeImpossible;

import static commandLineMenus.rendering.examples.util.InOut.*;
import static commandLineMenus.rendering.examples.util.InOut.getString;

import java.sql.SQLException;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;



public class PersonnelConsole extends JFrame
{
	private GestionPersonnel gestionPersonnel;
	LigueConsole ligueConsole;
	EmployeConsole employeConsole;
	private JPanel Titre = new JPanel();
	private JPanel Identifiant = new JPanel();
	private JPanel MotDePasse = new JPanel();
	private JPanel Confirmer = new JPanel();
	private JPanel colonne= new JPanel();
	private JButton confirmer = new JButton("Confirmer");
	private JTextField username = new JTextField();
	private JPasswordField motDePasse = new JPasswordField();
	private JLabel erreurConnexion = new JLabel("Erreur lors de l'identification");
	
	public PersonnelConsole(GestionPersonnel gestionPersonnel) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException, SauvegardeImpossible, SQLException
	{
		this.gestionPersonnel = gestionPersonnel;
		this.employeConsole = new EmployeConsole();
		this.setTitle("Maison des ligues : Gestion des Ligues");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBackground(Color.WHITE);
		this.setLocationRelativeTo(null);
		this.setSize(700,400);
		setLookComponent(confirmer);
		setLookComponent(username);
		setLookComponent(motDePasse);
		setLookComponent(erreurConnexion);
		Titre.setLayout(new BoxLayout(Titre, BoxLayout.LINE_AXIS));
		Identifiant.setLayout(new BoxLayout(Identifiant, BoxLayout.LINE_AXIS));
		MotDePasse.setLayout(new BoxLayout(MotDePasse, BoxLayout.LINE_AXIS));
		Confirmer.setLayout(new BoxLayout(Confirmer, BoxLayout.LINE_AXIS));
		colonne.setLayout(new BoxLayout(colonne, BoxLayout.PAGE_AXIS));
		Titre.add(new JLabel("Veuillez vous identifier "));
		Identifiant.add(new JLabel("Identifiant :"));
		username.setMinimumSize(new Dimension(150,25));
		username.setMaximumSize(new Dimension(150,25));
		Identifiant.add(username);
		MotDePasse.add(new JLabel("Mot de passe :"));
		motDePasse.setMinimumSize(new Dimension(150,25));
		motDePasse.setMaximumSize(new Dimension(150,25));
		MotDePasse.add(motDePasse);
		
		confirmer.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				try {
					for(Ligue ligue: GestionPersonnel.getGestionPersonnel().getLigues())	
					{
						if(ligue.getAdministrateur().getMail().equals(username.getText().trim()) && ligue.getAdministrateur().getPassword().equals(motDePasse.getText().trim()))
						{
							LigueConsole ligueConsole;
							try {
								ligueConsole = new LigueConsole(gestionPersonnel, employeConsole,ligue.getAdministrateur());
								colonne.removeAll();
								colonne.add(ligueConsole.getColonne());
								pack();
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
				} catch (SauvegardeImpossible e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					if(GestionPersonnel.getGestionPersonnel().getRoot().getNom().equals(username.getText().trim()) && GestionPersonnel.getGestionPersonnel().getRoot().getPassword().equals(motDePasse.getText().trim()))
					{
						LigueConsole ligueConsole;
						try {
							if(colonne.getComponentCount() > 4)
								colonne.remove(erreurConnexion);
							ligueConsole = new LigueConsole(gestionPersonnel, employeConsole,gestionPersonnel.getRoot());
							colonne.removeAll();
							colonne.add(ligueConsole.getColonne());
							pack();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					else
						colonne.add(erreurConnexion);
				} catch (SauvegardeImpossible e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		Confirmer.add(confirmer);
		colonne.add(Titre);
		colonne.add(Identifiant);
		colonne.add(MotDePasse);
		colonne.add(Confirmer);
		this.getContentPane().add(colonne);
		this.setVisible(true);
	}
	
	public void setLookComponent(Component component) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException
	{
		UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		SwingUtilities.updateComponentTreeUI(component);
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
		menu.add(quitterEtEnregistrer());
		// menu.add(menuQuitter());
		return menu;
	}

//	private Menu menuQuitter()
//	{
//		Menu menu = new Menu("Quitter", "q");
//		menu.add(quitterEtEnregistrer());
//		menu.add(quitterSansEnregistrer());
//		menu.addBack("r");
//		return menu;
//	}
	
	private Option quitterEtEnregistrer()
	{
		return new Option("Quitter", "q",
		// return new Option("Quitter et enregistrer", "q", 
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
		/*JFrame frame = new JFrame();
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
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);*/
		
		
		try {
			PersonnelConsole fenetre = new PersonnelConsole(GestionPersonnel.getGestionPersonnel());
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		PersonnelConsole personnelConsole;
//		personnelConsole = new PersonnelConsole(GestionPersonnel.getGestionPersonnel());
//		if (personnelConsole.verifiePassword())
//			personnelConsole.start();
	}
	
}
