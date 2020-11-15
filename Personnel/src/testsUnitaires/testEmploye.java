package testsUnitaires;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

import personnel.Employe;
import personnel.GestionPersonnel;
import personnel.ImpossibleDeSupprimerRoot;
import personnel.Ligue;

public class testEmploye 
{
	
	GestionPersonnel gestionPersonnel = GestionPersonnel.getGestionPersonnel();
	static final String PASSWORD ="motdepasse";
	
	@Test
	void testRemoveEmployeRoot()
	{
		Employe root = gestionPersonnel.getRoot();
		assertThrows(ImpossibleDeSupprimerRoot.class, () -> {root.remove();});
	}
	
	@Test
	void testRemoveEmploye() 
	{
		Ligue ligue = this.gestionPersonnel.addLigue(1, "Flechete");
		Employe employe = ligue.addEmploye("Neymar", "Jean", "adresse@mail.fr", PASSWORD, null, null);
		assertTrue(ligue.getEmployes().contains(employe));
		ligue.setAdministrateur(employe);
		employe.remove();
		assertFalse(ligue.getEmployes().contains(employe));
		assertEquals(this.gestionPersonnel.getRoot(), ligue.getAdministrateur());
	}
	
	@Test
	void testRemoveEmploye1()
	{
		Ligue ligue = this.gestionPersonnel.addLigue(1, "Flechete");
		Employe employe1 = ligue.addEmploye("Neymar", "Jean", "adresse@mail.fr", PASSWORD, null, null);
		Employe employe2 = ligue.addEmploye("Soule", "Sam", "adresse@email.fr", PASSWORD, null, null);
		assertTrue(ligue.getEmployes().contains(employe1));
		assertTrue(ligue.getEmployes().contains(employe2));
		ligue.setAdministrateur(employe2);
		employe1.remove();
		assertFalse(ligue.getEmployes().contains(employe1));
		assertNotEquals(this.gestionPersonnel.getRoot(), ligue.getAdministrateur());
		assertEquals(employe2, ligue.getAdministrateur());
	}
	
	@Test
	void testCheckPassword()
	{
		Ligue ligue =this.gestionPersonnel.addLigue(1, "Flechete");
		Employe employe = ligue.addEmploye("Neymar", "Jean", "adresse@mail.fr", PASSWORD, null, null);
		assertTrue(employe.checkPassword(PASSWORD));
		assertFalse(employe.checkPassword("notPASSWORD"));
	}
	
	@Test
	void testEstAdmin()
	{
		Ligue ligue1 = this.gestionPersonnel.addLigue(1, "Flechete");
		Employe employe = ligue1.addEmploye("Neymar", "Jean", "adresse@mail.fr", PASSWORD, null, null);
		assertFalse(employe.estAdmin(ligue1));
		ligue1.setAdministrateur(employe);
		assertTrue(employe.estAdmin(ligue1));
		Ligue ligue2 = this.gestionPersonnel.addLigue(1, "Basket");
		assertFalse(employe.estAdmin(ligue2));
	}
	
}
