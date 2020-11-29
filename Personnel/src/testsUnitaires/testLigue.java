package testsUnitaires;

import static org.junit.jupiter.api.Assertions.*;

import java.text.SimpleDateFormat;

import org.junit.jupiter.api.Test;

import personnel.*;

class testLigue 
{
	GestionPersonnel gestionPersonnel = GestionPersonnel.getGestionPersonnel();
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/mm/yyyy");
	static final String PASSWORD = "motdepasse";
	
	@Test
	void testCreateLigue() throws SauvegardeImpossible
	{
		Ligue ligue = gestionPersonnel.addLigue("Flechete");
		assertEquals("Flechete", ligue.getNom());
	}

	@Test
	void testAddEmploye() throws SauvegardeImpossible
	{
		Ligue ligue = gestionPersonnel.addLigue("Flechete");
		Employe employe = ligue.addEmploye("Bouchard", "Gérard", "g.bouchard@gmail.com", "azerty", "11/04/2001", "10/10/2020"); 
		assertEquals(employe, ligue.getEmployes().first());
	}
	
	@Test
	void testSetAdmininstrateur() throws SauvegardeImpossible
	{
		Ligue ligue1 = gestionPersonnel.addLigue("Flechete");
		Ligue ligue2 = gestionPersonnel.addLigue("basket");
		Employe employe = ligue1.addEmploye("Neymar", "Jean", "adresse@mail.fr", PASSWORD, null, null);
		assertThrows(DroitsInsuffisants.class, () -> {ligue2.setAdministrateur(employe);});
	}
	
	@Test
	void testAddLigue() throws SauvegardeImpossible
	{
		Ligue ligue = gestionPersonnel.addLigue("Flechete");
		assertEquals("Flechete", ligue.getNom());
		assertTrue(gestionPersonnel.getLigues().contains(ligue));
	}
	
	@Test
	void testAddLigueByID() throws SauvegardeImpossible
	{
		Ligue ligue = gestionPersonnel.addLigue("Flechete");
		assertEquals("Flechete", ligue.getNom());
		//assertEquals(1, ligue.getId());
		assertTrue(gestionPersonnel.getLigues().contains(ligue));
	}
	
	@Test
	void addEmploye() throws SauvegardeImpossible
	{
		Ligue ligue = gestionPersonnel.addLigue("Flechete");
		Employe employe = ligue.addEmploye("Neymar", "Jean", "adresse@mail.fr", PASSWORD, null, null);
		assertEquals(employe, ligue.getEmployes().first());
	}
	
	@Test
	void testGetLigues() throws SauvegardeImpossible
	{
		gestionPersonnel.addLigue("Flechete");
		gestionPersonnel.addLigue("basket");
		assertEquals(2, gestionPersonnel.getLigues().size());
	}
	
	@Test
	void testGetLigue() throws SauvegardeImpossible
	{
		Ligue ligue = gestionPersonnel.addLigue("Flechete");
		Employe admin = ligue.addEmploye("Neymar", "Jean", "adresse@mail.fr", PASSWORD, null, null);
		ligue.setAdministrateur(admin);
		Employe employe = ligue.addEmploye("Soule", "Sam", "adresse@email.fr", PASSWORD, null, null);
		Ligue ligueByAdmin = gestionPersonnel.getLigue(admin);
		assertNotNull(ligueByAdmin);
		assertEquals(ligue, ligueByAdmin);
		ligueByAdmin = gestionPersonnel.getLigue(employe);
		assertNull(ligueByAdmin);
	}
	
	@Test
	void testRemoveLigues() throws SauvegardeImpossible
	{
		Ligue ligue = gestionPersonnel.addLigue("Flechete");
		assertEquals("Flechete", ligue.getNom());
		ligue.remove();;
		assertTrue(gestionPersonnel.getLigues().isEmpty());
	}
	
}
