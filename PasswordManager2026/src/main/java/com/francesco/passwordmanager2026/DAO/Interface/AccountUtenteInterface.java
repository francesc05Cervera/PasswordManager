package com.francesco.passwordmanager2026.DAO.Interface;

import java.sql.SQLException;

import com.francesco.passwordmanager2026.entity.AccountUtente;

public interface AccountUtenteInterface 
{
	
	public boolean Insert(AccountUtente account) throws SQLException; 
	
	public boolean ChangePassword(AccountUtente account, String newPassword) throws SQLException ; 
	
	public AccountUtente findUser(String email) throws SQLException; 

}
