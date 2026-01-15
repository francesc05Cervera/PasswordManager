package com.francesco.passwordmanager2026.DAO;

import com.francesco.passwordmanager2026.DAO.Interface.CredenzialiDAOInterface;
import com.francesco.passwordmanager2026.Service.DBConnection;
import com.francesco.passwordmanager2026.entity.CredenzialiAccesso;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CredenzialiDAO implements CredenzialiDAOInterface {

    @Override
    public boolean insert(CredenzialiAccesso credenziale) throws SQLException {

        String SQL = "INSERT INTO CredenzialiAccesso(nomePiattaforma, username, passwordP, utenteCheCreaRecord, a2f_Present) "
                   + "VALUES (?, ?, ?, ?, ?)";

        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = DBConnection.getConnection();
            ps = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, credenziale.getNomePiattaforma());
            ps.setString(2, credenziale.getUsername());
            ps.setString(3, credenziale.getPasswordP());
            ps.setString(4, credenziale.getUtenteCheCreaRecord());
            ps.setBoolean(5, credenziale.isA2fPresent());

            int rows = ps.executeUpdate();

            if (rows > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    credenziale.setId(rs.getInt(1)); // assegno l’ID generato dal DB
                }
                return true;
            }

            return false;

        } catch (SQLException ex) {
            System.err.println("ERRORE in CredenzialiAccessoDAO.insert:");
            ex.printStackTrace();
            return false;

        } finally {
            if (ps != null) try { ps.close(); } catch (SQLException ignored) {}
            if (conn != null) try { conn.close(); } catch (SQLException ignored) {}
        }
    }

    @Override
    public boolean changePassword(int idCredenziale, String nuovaPassword) throws SQLException {

        String SQL = "UPDATE CredenzialiAccesso SET passwordP = ? WHERE idcredenziali = ?";

        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = DBConnection.getConnection();
            ps = conn.prepareStatement(SQL);

            ps.setString(1, nuovaPassword);
            ps.setInt(2, idCredenziale);

            int rows = ps.executeUpdate();
            return rows > 0;

        } catch (SQLException ex) {
            System.err.println("ERRORE in CredenzialiAccessoDAO.changePassword:");
            ex.printStackTrace();
            return false;

        } finally {
            if (ps != null) try { ps.close(); } catch (SQLException ignored) {}
            if (conn != null) try { conn.close(); } catch (SQLException ignored) {}
        }
    }

    @Override
    public CredenzialiAccesso findById(int id) throws SQLException {

        String SQL = "SELECT * FROM CredenzialiAccesso WHERE idcredenziali = ?";

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.getConnection();
            ps = conn.prepareStatement(SQL);
            ps.setInt(1, id);

            rs = ps.executeQuery();

            if (rs.next()) {
                CredenzialiAccesso c = new CredenzialiAccesso();
                c.setId(rs.getInt("id"));
                c.setNomePiattaforma(rs.getString("nomePiattaforma"));
                c.setUsername(rs.getString("username"));
                c.setPasswordP(rs.getString("passwordP"));
                c.setUtenteCheCreaRecord(rs.getString("utenteCheCreaRecord"));
                c.setA2fPresent(rs.getBoolean("a2fPresent"));
                return c;
            }

            return null;

        } catch (SQLException ex) {
            System.err.println("ERRORE in CredenzialiAccessoDAO.findById:");
            ex.printStackTrace();
            return null;

        } finally {
            if (rs != null) try { rs.close(); } catch (SQLException ignored) {}
            if (ps != null) try { ps.close(); } catch (SQLException ignored) {}
            if (conn != null) try { conn.close(); } catch (SQLException ignored) {}
        }
    }

    @Override
    public List<CredenzialiAccesso> findByCreator(String emailCreatore) throws SQLException {

        String SQL = "SELECT * FROM CredenzialiAccesso WHERE utenteCheCreaRecord = ?";

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        List<CredenzialiAccesso> lista = new ArrayList<>();

        try {
            conn = DBConnection.getConnection();
            ps = conn.prepareStatement(SQL);
            ps.setString(1, emailCreatore);

            rs = ps.executeQuery();

            while (rs.next()) {
                CredenzialiAccesso c = new CredenzialiAccesso();
                c.setId(rs.getInt("idcredenziali"));
                c.setNomePiattaforma(rs.getString("nomePiattaforma"));
                c.setUsername(rs.getString("username"));
                c.setPasswordP(rs.getString("passwordP"));
                c.setUtenteCheCreaRecord(rs.getString("utenteCheCreaRecord"));
                c.setA2fPresent(rs.getBoolean("a2f_Present"));

                lista.add(c);
            }

            return lista;

        } catch (SQLException ex) {
            System.err.println("ERRORE in CredenzialiAccessoDAO.findByCreator:");
            ex.printStackTrace();
            return null;

        } finally {
            if (rs != null) try { rs.close(); } catch (SQLException ignored) {}
            if (ps != null) try { ps.close(); } catch (SQLException ignored) {}
            if (conn != null) try { conn.close(); } catch (SQLException ignored) {}
        }
    }

    @Override
    public boolean delete(int id) throws SQLException {

        String SQL = "DELETE FROM CredenzialiAccesso WHERE idcredenziali = ?";

        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = DBConnection.getConnection();
            ps = conn.prepareStatement(SQL);
            ps.setInt(1, id);

            int rows = ps.executeUpdate();
            return rows > 0;

        } catch (SQLException ex) {
            System.err.println("ERRORE in CredenzialiAccessoDAO.delete:");
            ex.printStackTrace();
            return false;

        } finally {
            if (ps != null) try { ps.close(); } catch (SQLException ignored) {}
            if (conn != null) try { conn.close(); } catch (SQLException ignored) {}
        }
    }
}