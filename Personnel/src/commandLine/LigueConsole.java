package commandLine;

import static commandLineMenus.rendering.examples.util.InOut.getString;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import commandLineMenus.List;
import commandLineMenus.Menu;
import commandLineMenus.Option;
import personnel.Employe;
import personnel.GestionPersonnel;
import personnel.Ligue;
import personnel.SauvegardeImpossible;


public class LigueConsole extends JFrame
{
	private GestionPersonnel gestionPersonnel;
	private EmployeConsole employeConsole;
	private JPanel Ligue = new JPanel();
	private JPanel Tableau= new JPanel();
	private JPanel AjoutEmploye = new JPanel();
	private JPanel colonne= new JPanel();
	private JButton ajoutEmploye = new JButton("Ajouter un employe");
	private JTable tableau;
	private JTextField nomLigue = new JTextField();
	private JButton modifierNomLigue = new JButton("Modifier le nom de la ligue");
	private JButton modifierEmploye = new JButton("Modifier l'employe");
	public LigueConsole(GestionPersonnel gestionPersonnel, EmployeConsole employeConsole, Employe admin) throws SauvegardeImpossible, SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException
	{
		this.gestionPersonnel = gestionPersonnel;
		this.employeConsole = employeConsole;
		setLookComponent(nomLigue);
		setLookComponent(modifierEmploye);
		setLookComponent(modifierNomLigue);
		setLookComponent(ajoutEmploye);
		Ligue.setLayout(new BoxLayout(Ligue, BoxLayout.LINE_AXIS));
		Tableau.setLayout(new BoxLayout(Tableau, BoxLayout.LINE_AXIS));
		AjoutEmploye.setLayout(new BoxLayout(AjoutEmploye, BoxLayout.LINE_AXIS));
		colonne.setLayout(new BoxLayout(colonne, BoxLayout.PAGE_AXIS));
		if(admin != null)
		{
			if(admin.estRoot())
			{
				JPanel ligneTableauEnTete = new JPanel();
				ligneTableauEnTete.setLayout(new BoxLayout(ligneTableauEnTete, BoxLayout.LINE_AXIS));
				JLabel lesLigues = new JLabel("Le tableau des ligues");
				Ligue.add(lesLigues);
				String colonnes[] = {"Nom ligue", "Voir ligue", "Supprimer ligue"};
				Object[][] donnees = {};
				DefaultTableModel ligneTable = new DefaultTableModel(donnees, colonnes);
				for(Ligue ligues : gestionPersonnel.getLigues())
				{
					ligneTable.addRow(new Object[] {ligues.getNom(), new JButton("Voir "+ ligues.getNom()) ,new JButton("supprimer " + ligues.getNom()) });
				}
				tableau= new JTable(ligneTable);
				tableau.getColumn("Voir ligue").setCellRenderer(new TableComponent());
				tableau.getColumn("Supprimer ligue").setCellRenderer(new TableComponent());
				tableau.getColumn("Supprimer ligue").setCellEditor(new ButtonEditor(new JCheckBox()));
				ligneTableauEnTete.add(tableau.getTableHeader());
				tableau.setVisible(true);
				Tableau.add(tableau);
				colonne.add(Ligue);
				colonne.add(ligneTableauEnTete);
				colonne.add(Tableau);
			}
			else
			{
				nomLigue.setText(gestionPersonnel.getLigue(admin).getNom());
				modifierNomLigue.setVisible(true);
				modifierNomLigue.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent arg0) {
						int erreurNomLigueExistant = 0;
						JOptionPane fenetreSaisieNomLigue = new JOptionPane();
						String nouveauNomLigue = fenetreSaisieNomLigue.showInputDialog(null,"Veuillez saisir un nouveau nom de ligue :","Nouveau nom ligue pour " + nomLigue.getText(),JOptionPane.QUESTION_MESSAGE);
						for(Ligue nomLigueExistant : gestionPersonnel.getLigues())
						{
							if(nomLigueExistant.equals(nouveauNomLigue) || nouveauNomLigue.length() < 1)
								erreurNomLigueExistant ++;
						}
						if(erreurNomLigueExistant > 0) {
							JOptionPane fenetreErreurSaisieNomLigue = new JOptionPane();
							fenetreErreurSaisieNomLigue.showMessageDialog(null, "Erreur saisie Ligue","Le nom de ligue saisie est incorrecte ou d�j� existant.", JOptionPane.ERROR_MESSAGE);
						}
						else
						{
							try {
								admin.getLigue().setNom(nouveauNomLigue);
							} catch (SauvegardeImpossible e) {
								e.printStackTrace();
							}
							JOptionPane fenetreSaisieNomLigueCorrecte = new JOptionPane();
							fenetreSaisieNomLigueCorrecte.showMessageDialog(null,"Nom ligue chang�", "Le nouveau nom de la ligue est : " + nouveauNomLigue, JOptionPane.INFORMATION_MESSAGE);
						}
					}
				});
				Ligue.add(nomLigue);
				Ligue.add(modifierNomLigue);
				colonne.add(Ligue);
				
			}
			this.getContentPane().add(colonne);
			this.setVisible(false);
		}
	}

	public void setLookComponent(Component component) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException
	{
		UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		SwingUtilities.updateComponentTreeUI(component);
	}

	public Component getColonne() {
		return colonne;
	}
	
	Menu menuLigues()
	{
		Menu menu = new Menu("G�rer les ligues", "l");
		menu.add(afficherLigues());
		menu.add(ajouterLigue());
		menu.add(selectionnerLigue());
		menu.addBack("q");
		return menu;
	}

	private Option afficherLigues()
	{
		return new Option("Afficher les ligues", "l", () -> {System.out.println(gestionPersonnel.getLigues());});
	}

	private Option afficher(final Ligue ligue)
	{
		return new Option("Afficher la ligue", "l", 
				() -> 
				{
					System.out.println(ligue);
					System.out.println("administr�e par " + ligue.getAdministrateur());
				}
		);
	}
	private Option afficherEmployes(final Ligue ligue)
	{
		return new Option("Afficher les employes", "l", () -> {System.out.println(ligue.getEmployes());});
	}

	private Option ajouterLigue()
	{
		return new Option("Ajouter une ligue", "a", () -> 
		{
			try
			{
				gestionPersonnel.addLigue(getString("nom : "));
			}
			catch(SauvegardeImpossible exception)
			{
				System.err.println("Impossible de sauvegarder cette ligue");
			}
		});
	}
	
	private Menu editerLigue(Ligue ligue)
	{
		Menu menu = new Menu("Editer " + ligue.getNom());
		menu.add(afficher(ligue));
		menu.add(changerNom(ligue));
		menu.add(supprimer(ligue));
		menu.add(gererEmployes(ligue));
		menu.addBack("q");
		return menu;
	}

	private Option changerNom(final Ligue ligue)
	{
		return new Option("Renommer", "r", 
				() -> {try {
					ligue.setNom(getString("Nouveau nom : "));
				} catch (SauvegardeImpossible e) {
					e.printStackTrace();
				}});
	}

	private List<Ligue> selectionnerLigue()
	{
		return new List<Ligue>("S�lectionner une ligue", "e", 
				() -> new ArrayList<>(gestionPersonnel.getLigues()),
				(element) -> editerLigue(element)
				);
	}
	
	private Option ajouterEmploye(final Ligue ligue)
	{
		return new Option("ajouter un employ�", "a",
				() -> 
				{
					try {
						ligue.addEmploye(getString("nom : "), 
								getString("prenom : "), getString("mail : "), 
								getString("password : "), 
								LocalDate.parse(getString("date de d�part (AAAA-MM-JJ) : ")), LocalDate.parse(getString("date d'arriv�  (AAAA-MM-JJ) : ")));
					} 
					catch (Exception e) {
						System.out.println("erreur de format dans l'insertion de la date");
						e.printStackTrace();
					}
				}
		);
	}
	
	private Menu gererEmployes(Ligue ligue)
	{
		Menu menu = new Menu("G�rer les employ�s de " + ligue.getNom(), "e");
		menu.add(afficherEmployes(ligue));
		menu.add(ajouterEmploye(ligue));
		menu.add(selectionnerEmploye(ligue));
		menu.add(changerAdministrateur(ligue));
		menu.addBack("q");
		return menu;
	}
	
	private List<Employe> selectionnerEmploye(final Ligue ligue)
	{
		return new List<>("Selectionne un employe", "e", 
				() -> new ArrayList<>(ligue.getEmployes()),
				employeConsole.editerEmploye()
				);
	}

	private List<Employe> changerAdministrateur(final Ligue ligue)
	{
		return new List<>("Convetir employ� en admin", "f", 
				() -> new ArrayList<>(ligue.getEmployes()),
				(index, element) -> {try {
					ligue.setAdministrateur(element);
				} catch (SauvegardeImpossible | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				};
				}
				);
	}		
	
	private Option supprimer(Ligue ligue)
	{
		return new Option("Supprimer", "d", () -> {try {
			ligue.remove();
		} catch (SauvegardeImpossible e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}});
	}
	
	class TableComponent extends DefaultTableCellRenderer{

		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int rown, int column)
		{
			if(value instanceof JButton)
				return (JButton) value;
			else
				return this;
		}
	}
	
	class ButtonEditor extends DefaultCellEditor{
		protected JButton bouton;
		private boolean isPushed;
		private ButtonListener bListener = new ButtonListener();
		public ButtonEditor(JCheckBox checkBox) {
			super(checkBox);
			bouton = new JButton();
			bouton.setOpaque(true);
			bouton.addActionListener(bListener);
		}
		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
			bListener.setRow(row);
			bListener.setColumn(column);
			bListener.setTable(table);
			bouton.setText((value == null) ? "" : value.toString());
			return bouton;
		}
		class ButtonListener extends Thread implements ActionListener{
			private int column, row;
			private JTable table;
			private int nbre = 0;
			Thread thread;
			public void setColumn(int col) {this.column = col;}
			public void setRow(int row) {this.row = row;}
			public void setTable(JTable table) {this.table = table;}
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					for(Ligue ligue : gestionPersonnel.getGestionPersonnel().getLigues())
					{
						if(table.getValueAt(this.row, (this.column - 2)).equals(ligue.getNom()) && ligue != null ) {
							try {
								ligue.remove();
								DefaultTableModel ligneARetirer = (DefaultTableModel) table.getModel();
								ligneARetirer.removeRow(this.row);
							} catch (SauvegardeImpossible e) {
								e.printStackTrace();
							}
						}
					}
				} catch (SauvegardeImpossible e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}