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
	public GestionPersonnel getGestionPersonnel() throws SauvegardeImpossible 
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
				ResultSet employes = instruction2.executeQuery(requete2);
				while (employes.next())
					ligue.addEmploye(employes.getInt(1), employes.getString(2), employes.getString(3), employes.getString(4), employes.getString(5), LocalDate.parse(employes.getDate(6).toString()), LocalDate.parse(employes.getDate(7).toString()));
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
	public int insertLigue(Ligue ligue) throws SauvegardeImpossible 
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
	
	@Override
	public int updateLigue(Ligue ligue) throws SauvegardeImpossible 
	{
		try 
		{
			PreparedStatement instruction;
			instruction = connection.prepareStatement("update ligue set NomL = ? where NumL = ?", Statement.RETURN_GENERATED_KEYS);
			instruction.setString(1, ligue.getNom());
			instruction.setInt(2, ligue.getId());
			instruction.executeUpdate();
			return 0;
		} 
		catch (SQLException exception) 
		{
			exception.printStackTrace();
			throw new SauvegardeImpossible(exception);
		}		
	}
	
	
	@Override
	public int insertEmploye(Employe employe) throws SauvegardeImpossible 
	{
		try 
		{
			PreparedStatement instruction;
			instruction = connection.prepareStatement("insert into employe (NomE, PrenomE,CourrielE,PasswordE,DAE,DDE,NumLigue ) values(?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
			instruction.setString(1, employe.getNom());
			instruction.setString(2, employe.getPrenom());
			instruction.setString(3, employe.getMail());
			instruction.setString(4, employe.getPassword());
			instruction.setString(5, employe.getDateArrivée().toString());
			instruction.setString(6, employe.getDateDépart().toString());
			instruction.setInt(7, employe.getLigue().getId());		
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
	
	@Override
	public void updateEmploye(Employe employe) throws SauvegardeImpossible 
	{
		try 
		{
			PreparedStatement instruction;
			instruction = connection.prepareStatement("update employe set NomE = ?, PrenomE = ?, CourrielE = ?, PasswordE = ?, DAE = ?, DDE = ? where NumLigue = ?  ", Statement.RETURN_GENERATED_KEYS);
			instruction.setString(1, employe.getNom());
			instruction.setString(2, employe.getPrenom());
			instruction.setString(3, employe.getMail());
			instruction.setString(4, employe.getPassword());
			instruction.setString(5, employe.getDateArrivée().toString());
			instruction.setString(6, employe.getDateDépart().toString());
			instruction.setInt(7, employe.getLigue().getId());		
			instruction.executeUpdate();
		} 
		catch (SQLException exception) 
		{
			exception.printStackTrace();
			throw new SauvegardeImpossible(exception);
		}		
	}
	
	@Override
	public int deleteEmploye(Employe employe) throws SauvegardeImpossible
	{
		try
		{
			PreparedStatement instruction;
			instruction = connection.prepareStatement("delete from employe where NumE = ?", Statement.RETURN_GENERATED_KEYS);
			instruction.setInt(1, employe.getId());
			instruction.executeUpdate();
			return 0;
		}
		catch (SQLException exception)
		{
			exception.printStackTrace();
			throw new SauvegardeImpossible(exception);
		}
	}
	
	@Override
	public int newAdmin(Employe employe) throws SauvegardeImpossible, SQLException
	{
		String requete = "select NumE from employe where EstAdmin = 1 and NumLigue = " + employe.getLigue().getId();
		Statement instruction = connection.createStatement();
		ResultSet admin = instruction.executeQuery(requete);
		if(admin.next())
		{
			PreparedStatement statement;
			statement = connection.prepareStatement("update employe set EstAdmin = 0 where NumE = ? ", Statement.RETURN_GENERATED_KEYS);
			statement.setInt(1, admin.getInt(1));
			statement.executeUpdate();
		}
		PreparedStatement statement;
		statement = connection.prepareStatement("update employe set EstAdmin = 1 where NumE = ? ", Statement.RETURN_GENERATED_KEYS);
		statement.setInt(1, employe.getId());
		statement.executeUpdate();
		return 0;
	
	}
	
	@Override
	public int deleteLigue(Ligue ligue) throws SauvegardeImpossible
	{
		try
		{
			PreparedStatement instruction2;
			instruction2 = connection.prepareStatement("delete from employe where NumLigue = ?", Statement.RETURN_GENERATED_KEYS);
			instruction2.setInt(1, ligue.getId());
			instruction2.executeUpdate();
			PreparedStatement instruction;
			instruction = connection.prepareStatement("delete from ligue where NumL = ?", Statement.RETURN_GENERATED_KEYS);
			instruction.setInt(1, ligue.getId());
			instruction.executeUpdate();
			return 0;
		}
		catch (SQLException exception)
		{
			exception.printStackTrace();
			throw new SauvegardeImpossible(exception);
		}
		
	}
	
}
