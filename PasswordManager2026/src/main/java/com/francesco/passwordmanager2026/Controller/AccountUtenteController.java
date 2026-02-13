package com.francesco.passwordmanager2026.Controller;

import com.francesco.passwordmanager2026.Service.DataCheck;
import com.francesco.passwordmanager2026.Service.CryptoUtil;  // AGGIUNTO
import java.sql.SQLException;
import com.francesco.passwordmanager2026.DAO.AccountUtenteDAO;
import com.francesco.passwordmanager2026.entity.AccountUtente;

public class AccountUtenteController {

    private AccountUtenteDAO utDAO;

    public AccountUtenteController() {
        utDAO = new AccountUtenteDAO();
    }

    // REGISTRAZIONE NUOVO UTENTE - MODIFICATO
    public boolean RegistraNuovoUtente(String email, String telefono, String Nome, String Cognome, String Password) {

        if (!DataCheck.VerifyInput_5String(email, telefono, Nome, Cognome, Password))
            return false;

        try {
            AccountUtente trovato = utDAO.findUser(email);
            if (trovato != null) {
                return false; // utente già esistente
            }

            // CRITTOGRAFIA: Hash della password con BCrypt
            String hashedPassword = CryptoUtil.hashPassword(Password);

            AccountUtente nuovo = new AccountUtente(email, telefono, Nome, Cognome, hashedPassword);
            return utDAO.Insert(nuovo);

        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    // CAMBIO PASSWORD - MODIFICATO
    public boolean ChangePassword(AccountUtente account, String newPassword) {

        if (!DataCheck.VerifyInput_1StringAND1User(account, newPassword))
            return false;

        try {
            // CRITTOGRAFIA: Hash della nuova password
            String hashedPassword = CryptoUtil.hashPassword(newPassword);
            
            return utDAO.ChangePassword(account, hashedPassword);
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
    
    // LOGIN - MODIFICATO
    public boolean Login(String email, String Password) {
        if(!DataCheck.VerifyInput_2String(email, Password)) {
            return false; 
        }
        
        try {
            AccountUtente trovato = utDAO.findUser(email);
            
            if (trovato == null) {
                return false; // utente non trovato
            }
            
            // CRITTOGRAFIA: Verifica password con BCrypt
            return CryptoUtil.verifyPassword(Password, trovato.getPassword());
            
        } catch (SQLException e) {
            e.printStackTrace();
        } 
        return false; 
    }
}
