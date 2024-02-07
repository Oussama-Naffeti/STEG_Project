/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entities.Mois;
import java.sql.Connection;
import java.util.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Set;
import utils.DB;

/**
 *
 * @author Oussama
 */
public class MoisCRUD {
    int i ;
    int annee;
    
public void insererMois(Connection cnx,int annee) throws SQLException, ParseException
{
    Mois m=new Mois();
    m.setAnnee(annee);
    this.annee=annee;
    for (i=1;i<13;i++)
    {
        if (i==12)
        {
             m.setMois(i);
        m.setMois_traitement(1);
        m.setAnnee(annee+1);
        }
        else {
            m.setMois(i);
        m.setMois_traitement(i+1);
        }
        
        //Verification_Facturation
        m.calculerFacturation();
        System.out.println(m.getFacturation());
        while (this.isHoliday(cnx,m.getFacturation())||this.isWeekend(m.getFacturation())||this.isHolidayAid(cnx, m.getFacturation(),annee))
        {
           // Convertissez la chaîne de caractères facturation en LocalDate
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy"); // Utilisez le format "dd/MM/yyyy"
        LocalDate dateFacturation = LocalDate.parse(m.getFacturation(), formatter);

            // Incrémentation d'un jour
            LocalDate dateIncr = dateFacturation.plusDays(1);

            // Formatez la date en "dd/MM" et mettez-la dans l'attribut facturation
            m.setFacturation(dateIncr.format(formatter)); ;
        }
        
        //Verification_Edit_Facture
        m.calculerEditFacture();
        while (this.isHoliday(cnx,m.getEdit_facture())||this.isSunday(m.getEdit_facture())||this.isHolidayAid(cnx, m.getEdit_facture(),annee))
        {
           // Convertissez la chaîne de caractères facturation en LocalDate
           DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy"); // Utilisez le format "dd/MM/yyyy"
        LocalDate dateFacturation = LocalDate.parse(m.getEdit_facture(), formatter);

            // Incrémentation d'un jour
            LocalDate dateIncr = dateFacturation.plusDays(1);

            // Formatez la date en "dd/MM" et mettez-la dans l'attribut facturation
            m.setEdit_facture(dateIncr.format(formatter)); 
        }
        
        ////Verification_Edit_releve
        m.calculerEditReleve();
        while (this.isHoliday(cnx,m.getEdit_releve())||this.isSunday(m.getEdit_releve())||this.isHolidayAid(cnx, m.getEdit_releve(),annee))
        {
           // Convertissez la chaîne de caractères facturation en LocalDate
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy"); // Utilisez le format "dd/MM/yyyy"
        LocalDate dateFacturation = LocalDate.parse(m.getEdit_releve() , formatter);

            // Incrémentation d'un jour
            LocalDate dateIncr = dateFacturation.plusDays(1);

            // Formatez la date en "dd/MM" et mettez-la dans l'attribut facturation
            m.setEdit_releve(dateIncr.format(formatter)); 
        }
        
        ////Verification_Fin_Saisie_Releve
        m.calculerFinSaisieReleve();
        while (this.isHoliday(cnx,m.getFin_saisie_releve())||this.isSunday(m.getFin_saisie_releve())||this.isHolidayAid(cnx, m.getFin_saisie_releve(),annee))
        {
           // Convertissez la chaîne de caractères facturation en LocalDate
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy"); // Utilisez le format "dd/MM/yyyy"
        LocalDate dateFacturation = LocalDate.parse(m.getFin_saisie_releve() , formatter);

            // Incrémentation d'un jour
            LocalDate dateIncr = dateFacturation.minusDays(1);

            // Formatez la date en "dd/MM" et mettez-la dans l'attribut facturation
            m.setFin_saisie_releve(dateIncr.format(formatter)); 
        }
        
        
        ////Verification_Prelevement
        m.calculerPrelevement();
        while (this.isHoliday(cnx,m.getPrelevement())||this.isWeekend(m.getPrelevement())||this.isHolidayAid(cnx, m.getPrelevement(),annee))
        {
           // Convertissez la chaîne de caractères facturation en LocalDate
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy"); // Utilisez le format "dd/MM/yyyy"
        LocalDate dateFacturation = LocalDate.parse(m.getPrelevement() , formatter);

            // Incrémentation d'un jour
            LocalDate dateIncr = dateFacturation.plusDays(1);

            // Formatez la date en "dd/MM" et mettez-la dans l'attribut facturation
            m.setPrelevement(dateIncr.format(formatter)); 
        }
        
        
        ////Verification_Relance
        m.calculerRelance();
        while (this.isHoliday(cnx,m.getRelance())||this.isWeekend(m.getRelance())||this.isHolidayAid(cnx, m.getRelance(),annee))
        {
           // Convertissez la chaîne de caractères facturation en LocalDate
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy"); // Utilisez le format "dd/MM/yyyy"
        LocalDate dateFacturation = LocalDate.parse(m.getRelance() , formatter);

            // Incrémentation d'un jour
            LocalDate dateIncr = dateFacturation.plusDays(1);

            // Formatez la date en "dd/MM" et mettez-la dans l'attribut facturation
            m.setRelance(dateIncr.format(formatter)); 
        }
        
        String sql = "INSERT INTO mois (mois, mois_traitement,fin_saisie_releve, facturation, edit_facture, edit_releve, prelevement, relance, annee) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = cnx.prepareStatement(sql)) {
            preparedStatement.setInt(1, m.getMois());
            preparedStatement.setInt(2, m.getMois_traitement());
            preparedStatement.setString(3, m.getFin_saisie_releve());
            preparedStatement.setString(4, m.getFacturation());
            preparedStatement.setString(5, m.getEdit_facture());
            preparedStatement.setString(6, m.getEdit_releve());
            preparedStatement.setString(7, m.getPrelevement());
            preparedStatement.setString(8, m.getRelance());
            preparedStatement.setInt(9, m.getAnnee());

            preparedStatement.executeUpdate();
        }
        
        
        
        
        
        
    }
    
    
    
    
}
   
    
    
    
    
    
    
    
    
    
    
   /* public boolean isHoliday(Connection connection, String dateToCheck) throws SQLException {
        // Créez une requête SQL pour rechercher la date dans toutes les colonnes de la table "jour"
        String sql = "SELECT * FROM jour WHERE "
            + "jour_de_l_an = ? OR "
            + "fete_de_travail = ? OR "
            + "fete_de_l_independance = ? OR "
            + "fete_de_la_revolution = ? OR "
            + "jour_des_martyrs = ? OR "
            + "fete_de_la_republique = ? OR "
            + "fete_de_la_femme = ? OR "
            + "fete_de_l_evacuation = ? OR "
            + "le_mouled = ? OR "
            + "aid_al_fitr = ? OR "
            + "aid_al_idha = ? OR "
            + "jour_de_l_an_hegerien = ?";

        // Préparez la requête SQL
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        for (int i = 1; i <= 12; i++) {
            preparedStatement.setString(i, dateToCheck);
        }

        // Exécutez la requête
        ResultSet resultSet = preparedStatement.executeQuery();

        // Si le résultat de la requête contient des lignes, la date est un jour férié
        return resultSet.next();
    }*/

public boolean isHoliday(Connection connection, String dateToCheck) throws SQLException {
    // Convertissez la date en format "dd/MM" en extrayant les six premiers caractères
    String formattedDate = dateToCheck.substring(0, 5);
    

    // Créez une requête SQL pour rechercher la date dans toutes les colonnes de la table "jour"
    String sql = "SELECT * FROM jour WHERE "
        + "jour_de_l_an = ? OR "
        + "fete_de_travail = ? OR "
        + "fete_de_l_independance = ? OR "
        + "fete_de_la_revolution = ? OR "
        + "jour_des_martyrs = ? OR "
        + "fete_de_la_republique = ? OR "
        + "fete_de_la_femme = ? OR "
        + "fete_de_l_evacuation = ? OR "
        + "le_mouled = ? OR "
        + "aid_al_fitr = ? OR "
        + "aid_al_idha = ? OR "
        + "jour_de_l_an_hegerien = ?";

    // Préparez la requête SQL
    PreparedStatement preparedStatement = connection.prepareStatement(sql);
    for (int i = 1; i <= 12; i++) {
        preparedStatement.setString(i, formattedDate);
    }

    // Exécutez la requête
    ResultSet resultSet = preparedStatement.executeQuery();

    // Si le résultat de la requête contient des lignes, la date est un jour férié
    return resultSet.next();
}

    
    
   


public boolean isHolidayAid(Connection connection, String dateToCheck, int year) throws SQLException, ParseException {
    // Construisez la requête SQL pour récupérer les dates aid_al_fitr et aid_al_idha pour l'année spécifiée
    String sql = "SELECT aid_al_fitr, aid_al_idha FROM jour WHERE annee = ?";

    // Préparez la requête SQL
    PreparedStatement preparedStatement = connection.prepareStatement(sql);
    preparedStatement.setInt(1, year);

    // Exécutez la requête
    ResultSet resultSet = preparedStatement.executeQuery();

    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    // Parcourez les résultats pour rechercher les dates correspondantes
    while (resultSet.next()) {
        // Obtenez les dates de la base de données
        String dbDateAidAlFitr = resultSet.getString("aid_al_fitr");
        String dbDateAidAlIdha = resultSet.getString("aid_al_idha");

        // Ajoutez l'année de paramètre aux dates de la base de données
        Date dbDateAidAlFitrWithYear = (Date) sdf.parse(dbDateAidAlFitr + "/" + year);
        Date dbDateAidAlIdhaWithYear = (Date) sdf.parse(dbDateAidAlIdha + "/" + year);
        Date dateToCheckWithYear = (Date) sdf.parse(dateToCheck + "/" + year);

        // Comparez DateToCheck avec les dates de la base de données, le jour précédent et le jour suivant
        Calendar calendarAidAlFitr = Calendar.getInstance();
        calendarAidAlFitr.setTime(dbDateAidAlFitrWithYear);

        Calendar calendarAidAlIdha = Calendar.getInstance();
        calendarAidAlIdha.setTime(dbDateAidAlIdhaWithYear);

        // Ajoutez un jour aux dates de la base de données
        calendarAidAlFitr.add(Calendar.DAY_OF_YEAR, 1);
        Date nextDayAidAlFitr = (Date) calendarAidAlFitr.getTime();

        calendarAidAlIdha.add(Calendar.DAY_OF_YEAR, 1);
        Date nextDayAidAlIdha = (Date) calendarAidAlIdha.getTime();

        // Soustrayez un jour aux dates de la base de données
        calendarAidAlFitr.setTime(dbDateAidAlFitrWithYear);
        calendarAidAlFitr.add(Calendar.DAY_OF_YEAR, -1);
        Date previousDayAidAlFitr = (Date) calendarAidAlFitr.getTime();

        calendarAidAlIdha.setTime(dbDateAidAlIdhaWithYear);
        calendarAidAlIdha.add(Calendar.DAY_OF_YEAR, -1);
        Date previousDayAidAlIdha = (Date) calendarAidAlIdha.getTime();

        // Comparez DateToCheck avec les dates de la base de données
        if ((dateToCheckWithYear.equals(dbDateAidAlFitrWithYear) || dateToCheckWithYear.equals(nextDayAidAlFitr) || dateToCheckWithYear.equals(previousDayAidAlFitr)) ||
            (dateToCheckWithYear.equals(dbDateAidAlIdhaWithYear) || dateToCheckWithYear.equals(nextDayAidAlIdha) || dateToCheckWithYear.equals(previousDayAidAlIdha))) {
            return true;
        }
    }

    return false;
}







    
   public boolean isWeekend(String dateToCheck) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    LocalDate date = LocalDate.parse(dateToCheck, formatter);
    DayOfWeek dayOfWeek = date.getDayOfWeek();

    // Vérifiez si la date correspond à un samedi ou un dimanche
    return dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY;
}
   public boolean isSunday(String dateToCheck) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    LocalDate date = LocalDate.parse(dateToCheck, formatter);
    DayOfWeek dayOfWeek = date.getDayOfWeek();

    // Vérifiez si la date correspond à un dimanche
    return dayOfWeek  == DayOfWeek.SUNDAY;
}
   
    public List<Mois> getMois(Connection cnx, int annee) throws SQLException {
    List<Mois> moisList = new ArrayList<>();

    // Requête SQL pour récupérer les mois de la même année
    String sql1 = "SELECT mois, mois_traitement, fin_saisie_releve, facturation, edit_facture, edit_releve, prelevement, relance FROM mois WHERE annee = ? AND mois_traitement <> 1";

    try (PreparedStatement preparedStatement1 = cnx.prepareStatement(sql1)) {
        preparedStatement1.setInt(1, annee);
        ResultSet resultSet1 = preparedStatement1.executeQuery();

        while (resultSet1.next()) {
            Mois mois = new Mois();
            mois.setMois(resultSet1.getInt("mois"));
            mois.setMois_traitement(resultSet1.getInt("mois_traitement"));
            mois.setFin_saisie_releve(resultSet1.getString("fin_saisie_releve"));
            mois.setFacturation(resultSet1.getString("facturation"));
            mois.setEdit_facture(resultSet1.getString("edit_facture"));
            mois.setEdit_releve(resultSet1.getString("edit_releve"));
            mois.setPrelevement(resultSet1.getString("prelevement"));
            mois.setRelance(resultSet1.getString("relance"));
            
            moisList.add(mois);
        }
    }

    // Requête SQL pour récupérer la ligne supplémentaire
    String sql2 = "SELECT mois, mois_traitement, fin_saisie_releve, facturation, edit_facture, edit_releve, prelevement, relance FROM mois WHERE annee = ? AND mois_traitement = 1";

    try (PreparedStatement preparedStatement2 = cnx.prepareStatement(sql2)) {
        preparedStatement2.setInt(1, annee+1);
        ResultSet resultSet2 = preparedStatement2.executeQuery();

        if (resultSet2.next()) {
            Mois mois = new Mois();
            mois.setMois(resultSet2.getInt("mois"));
            mois.setMois_traitement(resultSet2.getInt("mois_traitement"));
            mois.setFin_saisie_releve(resultSet2.getString("fin_saisie_releve"));
            mois.setFacturation(resultSet2.getString("facturation"));
            mois.setEdit_facture(resultSet2.getString("edit_facture"));
            mois.setEdit_releve(resultSet2.getString("edit_releve"));
            mois.setPrelevement(resultSet2.getString("prelevement"));
            mois.setRelance(resultSet2.getString("relance"));
            
            moisList.add(mois);
        }
    }

    return moisList;
}
    
    public void supprimerJoursFeries(Connection connection, int annee) throws SQLException {
    String sql = "DELETE FROM jour WHERE annee = ?";
    
    try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
        preparedStatement.setInt(1, annee);
        preparedStatement.executeUpdate();
    }
}

public void supprimerMoisParAnnee(Connection cnx, int annee) throws SQLException {
    String sqlMois = "DELETE FROM mois WHERE (annee = ?) OR (mois = 12 AND mois_traitement = 1 AND annee = ?)";
    String sqlJour = "DELETE FROM jour WHERE annee = ?";
    
    try (PreparedStatement pstmtMois = cnx.prepareStatement(sqlMois);
         PreparedStatement pstmtJour = cnx.prepareStatement(sqlJour)) {
         
        pstmtMois.setInt(1, annee);
        pstmtMois.setInt(2, annee + 1); // Pour la suppression de l'année suivante
        pstmtMois.executeUpdate();
        
        pstmtJour.setInt(1, annee);
        pstmtJour.executeUpdate();
    }
}




}








    
    /*public boolean isWeekend(String dateToCheck, int year) {
        if (!dateToCheck.matches("\\d{2}/\\d{2}")) {
        // La chaîne n'est pas au format attendu, renvoyez false ou gérez l'erreur selon votre logique
        return false;
    }
        // Conversion de la date au format "dd/MM" en LocalDate
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM");
        LocalDate date = LocalDate.parse(dateToCheck, formatter);

        // Vérification si la date correspond à un samedi ou un dimanche
        DayOfWeek dayOfWeek = date.getDayOfWeek();

        // Ajout de la condition pour vérifier si la date est un week-end en fonction de l'année
        if (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) {
            return true;
        } else {
            // Si la date n'est pas un week-end, vérifiez si elle est le samedi ou dimanche suivant
            LocalDate nextSaturday = LocalDate.of(year, date.getMonthValue(), 1);
            while (nextSaturday.getDayOfWeek() != DayOfWeek.SATURDAY) {
                nextSaturday = nextSaturday.plusDays(1);
            }
            LocalDate nextSunday = nextSaturday.plusDays(1);

            return date.equals(nextSaturday) || date.equals(nextSunday);
        }
    }*/



   
