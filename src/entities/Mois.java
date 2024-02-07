/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 *
 * @author Oussama
 */
public class Mois {
    private Integer mois;
    private Integer mois_traitement;
    private String fin_saisie_releve;
    private String facturation;
    private String edit_facture;
    private String edit_releve;
    private String prelevement;
    private String relance;
    private int annee;

    

    public Mois(Integer mois, Integer mois_traitement, String fin_saisie_releve, String facturation, String edit_facture, String edit_releve, String prelevement, String relance, int annee) {
        this.mois = mois;
        this.mois_traitement = mois_traitement;
        this.fin_saisie_releve = fin_saisie_releve;
        this.facturation = facturation;
        this.edit_facture = edit_facture;
        this.edit_releve = edit_releve;
        this.prelevement = prelevement;
        this.relance = relance;
        this.annee = annee;
    }
    

    public Mois(Integer mois, Integer mois_traitement, String fin_saisie_releve, String facturation, String edit_facture, String edit_releve, String prelevement, String relance) {
        this.mois = mois;
        this.mois_traitement = mois_traitement;
        this.fin_saisie_releve = fin_saisie_releve;
        this.facturation = facturation;
        this.edit_facture = edit_facture;
        this.edit_releve = edit_releve;
        this.prelevement = prelevement;
        this.relance = relance;
    }
    

    public Mois() {
    }
    
    public Integer getMois() {
        return mois;
    }

    public void setMois(Integer mois) {
        this.mois = mois;
    }

    public int getAnnee() {
        return annee;
    }

    public void setAnnee(int annee) {
        this.annee = annee;
    }

    public Integer getMois_traitement() {
        return mois_traitement;
    }

    public void setMois_traitement(Integer mois_traitement) {
        this.mois_traitement = mois_traitement;
    }

    public String getFin_saisie_releve() {
        return fin_saisie_releve;
    }

    public void setFin_saisie_releve(String fin_saisie_releve) {
        this.fin_saisie_releve = fin_saisie_releve;
    }

    public String getFacturation() {
        return facturation;
    }

    public void setFacturation(String facturation) {
        this.facturation = facturation;
    }

    public String getEdit_facture() {
        return edit_facture;
    }

    public void setEdit_facture(String edit_facture) {
        this.edit_facture = edit_facture;
    }

    public String getEdit_releve() {
        return edit_releve;
    }

    public void setEdit_releve(String edit_releve) {
        this.edit_releve = edit_releve;
    }

    public String getPrelevement() {
        return prelevement;
    }

    public void setPrelevement(String prelevement) {
        this.prelevement = prelevement;
    }

    public String getRelance() {
        return relance;
    }

    public void setRelance(String relance) {
        this.relance = relance;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.mois);
        hash = 71 * hash + Objects.hashCode(this.mois_traitement);
        hash = 71 * hash + Objects.hashCode(this.fin_saisie_releve);
        hash = 71 * hash + Objects.hashCode(this.facturation);
        hash = 71 * hash + Objects.hashCode(this.edit_facture);
        hash = 71 * hash + Objects.hashCode(this.edit_releve);
        hash = 71 * hash + Objects.hashCode(this.prelevement);
        hash = 71 * hash + Objects.hashCode(this.relance);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Mois other = (Mois) obj;
        if (!Objects.equals(this.fin_saisie_releve, other.fin_saisie_releve)) {
            return false;
        }
        if (!Objects.equals(this.facturation, other.facturation)) {
            return false;
        }
        if (!Objects.equals(this.edit_facture, other.edit_facture)) {
            return false;
        }
        if (!Objects.equals(this.edit_releve, other.edit_releve)) {
            return false;
        }
        if (!Objects.equals(this.prelevement, other.prelevement)) {
            return false;
        }
        if (!Objects.equals(this.relance, other.relance)) {
            return false;
        }
        if (!Objects.equals(this.mois, other.mois)) {
            return false;
        }
        if (!Objects.equals(this.mois_traitement, other.mois_traitement)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Mois{" + "mois=" + mois + ", mois_traitement=" + mois_traitement + ", fin_saisie_releve=" + fin_saisie_releve + ", facturation=" + facturation + ", edit_facture=" + edit_facture + ", edit_releve=" + edit_releve + ", prelevement=" + prelevement + ", relance=" + relance + '}';
    }
    
    public void calculerFacturation() {
        // Vérifiez si mois_traitement et annee sont non null
        if (mois_traitement != null && annee != 0) {
            // Créez une LocalDate pour le premier jour du mois de traitement
            LocalDate date = LocalDate.of(annee, mois_traitement, 1);

            // Formatez la date en "dd/MM" et mettez-la dans l'attribut facturation
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            this.setFacturation(date.format(formatter));
        } else {
            // Gérez le cas où mois_traitement ou annee est null
            facturation = null;
        }
    }
    
    public void calculerFinSaisieReleve() {
        // Vérifiez si facturation est non null
        if (facturation != null) {
            // Convertissez la chaîne de caractères facturation en LocalDate
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy"); // Ajoutez "yyyy" pour l'année
        LocalDate dateFacturation = LocalDate.parse(facturation, formatter); // Incluez l'année dans la chaîne

            // Soustrayez un jour pour calculer fin_saisie_releve
            LocalDate dateFinSaisieReleve = dateFacturation.minusDays(1);

            // Formatez la date en "dd/MM" et mettez-la dans l'attribut fin_saisie_releve
            this.setFin_saisie_releve(dateFinSaisieReleve.format(formatter));
        } else {
            // Gérez le cas où facturation est null
            fin_saisie_releve = null;
        }
    }
    
     public void calculerEditFacture() {
    // Vérifiez si facturation est non null
    if (facturation != null) {
        // Convertissez la chaîne de caractères facturation en LocalDate
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy"); // Utilisez le format "dd/MM/yyyy"
        LocalDate dateFacturation = LocalDate.parse(facturation, formatter);

        // Ajoutez un jour pour calculer edit_facture
        LocalDate dateEditFacture = dateFacturation.plusDays(1);

        // Formatez la date en "dd/MM/yyyy" et mettez-la dans l'attribut edit_facture
        this.setEdit_facture(dateEditFacture.format(formatter));
    } else {
        // Gérez le cas où facturation est null
        edit_facture = null;
    }
}


    
   public void calculerEditReleve() {
        // Vérifiez si mois_traitement et annee sont non null
        if (mois_traitement != null && annee != 0) {
            // Créez une LocalDate pour le 18ème jour du mois de traitement
            LocalDate date = LocalDate.of(annee, mois_traitement, 18);

            // Formatez la date en "dd/MM" et mettez-la dans l'attribut edit_releve
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy"); // Utilisez le format "dd/MM/yyyy"
            this.setEdit_releve(date.format(formatter));
        } else {
            // Gérez le cas où mois_traitement ou annee est null
            edit_releve = null;
        }
    }
    
     public void calculerPrelevement() {
        // Vérifiez si mois_traitement et annee sont non null
        if (mois_traitement != null && annee != 0) {
            // Créez une LocalDate pour le 21ème jour du mois de traitement
            LocalDate date = LocalDate.of(annee, mois_traitement, 21);

            // Formatez la date en "dd/MM" et mettez-la dans l'attribut prelevement
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy"); // Utilisez le format "dd/MM/yyyy"
            this.setPrelevement(date.format(formatter));
        } else {
            // Gérez le cas où mois_traitement ou annee est null
            prelevement = null;
        }
    }
    
    public void calculerRelance() {
        // Vérifiez si mois_traitement et annee sont non null
        if (mois_traitement != null && annee != 0) {
            // Créez une LocalDate pour le 26ème jour du mois de traitement
            LocalDate date = LocalDate.of(annee, mois_traitement, 26);

            // Formatez la date en "dd/MM" et mettez-la dans l'attribut relance
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy"); // Utilisez le format "dd/MM/yyyy"
            this.setRelance(date.format(formatter));
        } else {
            // Gérez le cas où mois_traitement ou annee est null
            relance = null;
        }
    }
    
    
    
    
}
