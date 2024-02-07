package services;

import entities.Jour;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import utils.DB;
import services.MoisCRUD;

public class JourCRUD {
    Connection cnx;

    public void ajouterJour(Jour jour) throws SQLException, ParseException {
        MoisCRUD M=new MoisCRUD();
    try {
        String query = "INSERT INTO `jour` (`jour_de_l_an`, `fete_de_travail`, `fete_de_l_independance`, `fete_de_la_revolution`, `jour_des_martyrs`, `fete_de_la_republique`, `fete_de_la_femme`, `fete_de_l_evacuation`, `le_mouled`, `aid_al_fitr`, `aid_al_idha`, `jour_de_l_an_hegerien`, `annee`) VALUES ('"
                       + Jour.jour_de_l_an + "','" + Jour.fete_de_travail + "','" + Jour.fete_de_l_independance + "','"
                       + Jour.fete_de_la_revolution + "','" + Jour.jour_des_martyrs + "','" + Jour.fete_de_la_republique + "','"
                       + Jour.fete_de_la_femme + "','" + Jour.fete_de_l_evacuation + "','" + jour.getLe_mouled() + "','"
                       + jour.getAid_al_fitr() + "','" + jour.getAid_al_idha() + "','" + jour.getJour_de_l_an_hegerien() + "','"
                       + jour.getAnnee() + "')";
        cnx = DB.getInstance().getConnection();
        Statement stm = cnx.createStatement();
        stm.executeUpdate(query);
        
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
    M.insererMois(cnx, Integer.parseInt(jour.getAnnee()));
}


    public List<Jour> afficherJours() {
        List<Jour> jours = new ArrayList<>();
        try {
            String query = "SELECT * FROM `jour`";
            cnx = DB.getInstance().getConnection();
            Statement stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(query);
            while (rs.next()) {
                Jour jour = new Jour();
                jour.setLe_mouled(rs.getString(1));
                jour.setAid_al_fitr(rs.getString(2));
                jour.setAid_al_idha(rs.getString(3));
                jour.setJour_de_l_an_hegerien(rs.getString(4));
                jour.setAnnee(rs.getString(5));
                jours.add(jour);
            }
            return jours;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return jours;
    }
    
    public boolean anneeExists(String annee) {
        try {
            String query = "SELECT COUNT(*) FROM jour WHERE annee = '" + annee + "'";
            cnx = DB.getInstance().getConnection();
            Statement stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(query);
            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0; // Si count est supérieur à 0, l'année existe
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return false; // Si une exception se produit, considérez que l'année n'existe pas
    }

    public void supprimerJour(String annee) {
        try {
            String query = "DELETE FROM jour WHERE annee = '" + annee + "'";
            cnx = DB.getInstance().getConnection();
            Statement stm = cnx.createStatement();
            stm.executeUpdate(query);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void modifierJour(Jour jour) {
        try {
            String query = "UPDATE jour SET `le_mouled`='" + jour.getLe_mouled() + "', `aid_al_fitr`='"
                           + jour.getAid_al_fitr() + "', `aid_al_idha`='" + jour.getAid_al_idha()
                           + "', `jour_de_l_an_hegerien`='" + jour.getJour_de_l_an_hegerien() + "', `annee`='"
                           + jour.getAnnee() + "' WHERE annee='" + jour.getAnnee() + "'";
            cnx = DB.getInstance().getConnection();
            Statement stm = cnx.createStatement();
            stm.executeUpdate(query);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    
}
