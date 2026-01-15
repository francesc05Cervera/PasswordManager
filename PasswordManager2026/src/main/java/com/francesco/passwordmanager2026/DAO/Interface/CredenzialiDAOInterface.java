package com.francesco.passwordmanager2026.DAO.Interface;

import com.francesco.passwordmanager2026.entity.CredenzialiAccesso;
import java.sql.SQLException;
import java.util.List;

public interface CredenzialiDAOInterface {

    // Inserisce una nuova credenziale (ID generato dal DB)
    boolean insert(CredenzialiAccesso credenziale) throws SQLException;

    // Aggiorna la password della credenziale
    boolean changePassword(int idCredenziale, String nuovaPassword) throws SQLException;

    // Trova una credenziale tramite ID
    CredenzialiAccesso findById(int id) throws SQLException;

    // Trova tutte le credenziali create da un certo utente (FK)
    List<CredenzialiAccesso> findByCreator(String emailCreatore) throws SQLException;

    // Elimina una credenziale tramite ID
    boolean delete(int id) throws SQLException;
}