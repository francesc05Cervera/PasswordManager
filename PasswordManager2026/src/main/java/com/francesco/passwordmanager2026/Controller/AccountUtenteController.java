package com.francesco.passwordmanager2026.Controller;

import com.francesco.passwordmanager2026.Service.DataCheck;
import java.sql.SQLException;
import com.francesco.passwordmanager2026.DAO.AccountUtenteDAO;
import com.francesco.passwordmanager2026.entity.AccountUtente;

public class AccountUtenteController {

    private AccountUtenteDAO utDAO;

    public AccountUtenteController() {
        utDAO = new AccountUtenteDAO();
    }

    // REGISTRAZIONE NUOVO UTENTE
    public boolean RegistraNuovoUtente(String email, String telefono, String Nome, String Cognome, String Password) {

        if (!DataCheck.VerifyInput_5String(email, telefono, Nome, Cognome, Password))
            return false;

        try {
            AccountUtente trovato = utDAO.findUser(email);
            if (trovato != null) {
                return false; // utente già esistente
            }

            AccountUtente nuovo = new AccountUtente(email, telefono, Nome, Cognome, Password);
            return utDAO.Insert(nuovo);

        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    // CAMBIO PASSWORD
    public boolean ChangePassword(AccountUtente account, String newPassword) {

        if (!DataCheck.VerifyInput_1StringAND1User(account, newPassword))
            return false;

        try {
            return utDAO.ChangePassword(account, newPassword);
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
    
   public boolean Login(String email, String Password)
   {
	   if(!DataCheck.VerifyInput_2String(email, Password))
	   {
		   return false; 
	   }
	   
	   try {
		AccountUtente trovato = utDAO.findUser(email);
		if(trovato.getPassword().equals(Password))
			return true;
		else
			return false;
		
	} catch (SQLException e) {
		e.printStackTrace();
	} 
	   return false; 
	   
   }
}