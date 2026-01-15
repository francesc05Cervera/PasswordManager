package com.francesco.passwordmanager2026.DAO;

import com.francesco.passwordmanager2026.entity.AccountUtente;
import com.francesco.passwordmanager2026.DAO.Interface.AccountUtenteInterface;
import com.francesco.passwordmanager2026.Service.DBConnection;
import java.sql.*; 

public class AccountUtenteDAO implements AccountUtenteInterface
{
	@Override
	public boolean Insert(AccountUtente account) throws SQLException 
	{

	    String SQL = "INSERT INTO AccountUtente(email, telefono, nome, cognome, passwordUtente) VALUES (?, ?, ?, ?, ?)";

	    Connection conn = null;
	    PreparedStatement ps = null;

	    try {
	        conn = DBConnection.getConnection();
	        ps = conn.prepareStatement(SQL);

	        ps.setString(1, account.getEmail());
	        ps.setString(2, account.getTelefono());
	        ps.setString(3, account.getNome());
	        ps.setString(4, account.getCognome());
	        ps.setString(5, account.getPassword());

	        int righeAggiunte = ps.executeUpdate();
	        return righeAggiunte > 0;

	    } catch (SQLException ex) {
	        System.err.println("ERRORE IN AccountUtenteDAO:");
	        ex.printStackTrace();
	        return false;

	    } finally {
	        if (ps != null) {
	            try { ps.close(); } catch (SQLException e) { e.printStackTrace(); }
	        }
	        if (conn != null) {
	            try { conn.close(); } catch (SQLException e) { e.printStackTrace(); }
	        }
	    }
	}
	
	@Override
	public boolean ChangePassword(AccountUtente account, String newPassword) throws SQLException
	{
		String SQL = "UPDATE AccountUtente SET passwordUtente = ? WHERE email = ?";
		
		Connection conn = null; 
		PreparedStatement ps = null;
		
		try 
		{
			conn = DBConnection.getConnection(); 
			ps = conn.prepareStatement(SQL);
			
			ps.setString(1, newPassword);
			ps.setString(2, account.getEmail()); 
			
			int righeModificate = ps.executeUpdate(); 
			
			return righeModificate > 0; 
		}catch (SQLException ex) {
	        System.err.println("ERRORE IN AccountUtenteDAO:");
	        ex.printStackTrace();
	        return false;

	    } finally {
	        if (ps != null) {
	            try { ps.close(); } catch (SQLException e) { e.printStackTrace(); }
	        }
	        if (conn != null) {
	            try { conn.close(); } catch (SQLException e) { e.printStackTrace(); }
	        }
	    }
	}
	
	@Override
	public AccountUtente findUser(String email) throws SQLException
	{
		String SQL = "SELECT * FROM AccountUtente WHERE email = ?"; 
		
		Connection conn = null; 
		PreparedStatement ps = null;
		ResultSet rs = null;
		try 
		{
			conn = DBConnection.getConnection(); 
			ps = conn.prepareStatement(SQL);
			
			ps.setString(1, email);
			
			rs =ps.executeQuery(); 
			
			if(rs.next())
			{
				AccountUtente accountTrovato = new AccountUtente();
				accountTrovato.setEmail(rs.getString("email"));
				accountTrovato.setTelefono(rs.getString("telefono"));
				accountTrovato.setNome(rs.getString("nome"));
				accountTrovato.setCognome(rs.getString("cognome")); 
				accountTrovato.setPassword(rs.getString("passwordUtente"));
				
				return accountTrovato; 
			}
		}catch (SQLException ex) {
	        System.err.println("ERRORE IN AccountUtenteDAO:");
	        ex.printStackTrace();
	        return null;

	    } finally {
	        if (ps != null) {
	        	
	            try { ps.close(); } catch (SQLException e) { e.printStackTrace(); }
	        }
	        if (conn != null) {
	            try { conn.close(); } catch (SQLException e) { e.printStackTrace(); }
	        }
	        
	        if(rs != null) {
	        	try { rs.close(); } catch(SQLException e) { e.printStackTrace(); }
	        }
	    }
		
		return null; 
	}
	

}
