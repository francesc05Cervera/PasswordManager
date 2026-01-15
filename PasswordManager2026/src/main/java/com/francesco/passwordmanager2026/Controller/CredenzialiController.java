package com.francesco.passwordmanager2026.Controller;

import com.francesco.passwordmanager2026.DAO.CredenzialiDAO;
import com.francesco.passwordmanager2026.Service.DataCheck;
import com.francesco.passwordmanager2026.entity.CredenzialiAccesso;

import java.sql.SQLException;
import java.util.List;

public class CredenzialiController {

    private CredenzialiDAO credDAO;

    public CredenzialiController() {
        credDAO = new CredenzialiDAO();
    }

    // INSERIMENTO NUOVA CREDENZIALE
    public boolean inserisciCredenziale(String nomePiattaforma, String username, String passwordP,
                                        String utenteCheCreaRecord, boolean a2fPresent) {

        if (!DataCheck.VerifyInput_4StringAND1Boolean(nomePiattaforma, username, passwordP, utenteCheCreaRecord, a2fPresent))
            return false;

        CredenzialiAccesso nuova = new CredenzialiAccesso(
                nomePiattaforma,
                username,
                passwordP,
                utenteCheCreaRecord,
                a2fPresent
        );

        try {
            return credDAO.insert(nuova);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // MODIFICA PASSWORD DI UNA CREDENZIALE
    public boolean modificaPassword(int idCredenziale, String nuovaPassword) {

        if (!DataCheck.VerifyInput_1String(nuovaPassword))
            return false;

        try {
            return credDAO.changePassword(idCredenziale, nuovaPassword);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ELIMINAZIONE CREDENZIALE
    public boolean eliminaCredenziale(int idCredenziale) {

        if (!DataCheck.VerifyInput_1Int(idCredenziale))
            return false;

        try {
            return credDAO.delete(idCredenziale);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // RECUPERO DI TUTTE LE CREDENZIALI DI UN UTENTE
    public List<CredenzialiAccesso> recuperaCredenzialiUtente(String emailCreatore) {

        if (!DataCheck.VerifyInput_1String(emailCreatore))
            return null;

        try {
            return credDAO.findByCreator(emailCreatore);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}