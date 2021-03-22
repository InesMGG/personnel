package jdbc;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import personnel.*;

public class JDBC implements Passerelle 
{
	Connection connection;

	public JDBC()
	{
		try
		{
			Class.forName(Credentials.getDriverClassName());
			connection = DriverManager.getConnection(Credentials.getUrl(), Credentials.getUser(), Credentials.getPassword());
		}
		catch (ClassNotFoundException e)
		{
			System.out.println("Pilote JDBC non installÃ©.");
		}
		catch (SQLException e)
		{
			System.out.println(e);
		}
	}
	
	@Override
	public GestionPersonnel getGestionPersonnel() 
	{
		GestionPersonnel gestionPersonnel = new GestionPersonnel();
		try 
		{
			String requete = "select * from ligue";
			Statement instruction = connection.createStatement();
			ResultSet ligues = instruction.executeQuery(requete);
			while (ligues.next())
			{
				Ligue ligue = gestionPersonnel.addLigue(ligues.getInt(1), ligues.getString(2));
				String requete2 = "select * from employe where Numligue=" + ligue.getId();
				Statement instruction2 = connection.createStatement();
				ResultSet employes = instruction.executeQuery(requete);
				while (employes.next())
					ligue.addEmploye(employes.getInt(1), employes.getString(2), employes.getString(3), employes.getString(4), employes.getString(5), LocalDate.parse(employes.getString(6)), LocalDate.parse(employes.getString(7)));
			}
		}
		catch (SQLException e)
		{
			System.out.println(e);
		}
		return gestionPersonnel;
	}
	

	@Override
	public void sauvegarderGestionPersonnel(GestionPersonnel gestionPersonnel) throws SauvegardeImpossible 
	{
		close();
	}
	
	public void close() throws SauvegardeImpossible
	{
		try
		{
			if (connection != null)
				connection.close();
		}
		catch (SQLException e)
		{
			throw new SauvegardeImpossible(e);
		}
	}
	
	@Override
	public int insert(Ligue ligue) throws SauvegardeImpossible 
	{
		try 
		{
			PreparedStatement instruction;
			instruction = connection.prepareStatement("insert into ligue (NomL) values(?)", Statement.RETURN_GENERATED_KEYS);
			instruction.setString(1, ligue.getNom());		
			instruction.executeUpdate();
			ResultSet id = instruction.getGeneratedKeys();
			id.next();
			return id.getInt(1);
		} 
		catch (SQLException exception) 
		{
			exception.printStackTrace();
			throw new SauvegardeImpossible(exception);
		}		
	}
	
//	@Override
//	public int insert(Employe employe) throws SauvegardeImpossible 
//	{
//		try 
//		{
//			PreparedStatement instruction;
//			instruction = connection.prepareStatement("insert into employe (NomE, PrenomE, ) values(?)", Statement.RETURN_GENERATED_KEYS);
//			instruction.setString(1, employe.getNom());
//			instruction.setString(2, employe.getPrenom());
//			instruction.setString(3, employe.getMail());
//			instruction.setString(4, null);
//			instruction.setString(5, employe.getDateArrivée());
//			instruction.setString(6, employe.getDateDépart());
//			instruction.setString(7, 0); 
//			instruction.setString(8, 0);
//			instruction.setString(9, employe.getLigue());		
//			instruction.executeUpdate();
//			ResultSet id = instruction.getGeneratedKeys();
//			id.next();
//			return id.getInt(1);
//		} 
//		catch (SQLException exception) 
//		{
//			exception.printStackTrace();
//			throw new SauvegardeImpossible(exception);
//		}		
//	}
	

}
