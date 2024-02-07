/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.util.Objects;


/**
 *
 * @author Oussama
 */

public class Jour {
    public static final String jour_de_l_an = "01/01";
    public static final String fete_de_travail = "01/05";
    public static final String fete_de_l_independance = "20/03";
    public static final String fete_de_la_revolution = "17/12";
    public static final String jour_des_martyrs ="09/04";
    public static final String fete_de_la_republique  ="25/07";
    public static final String fete_de_la_femme ="13/08";
    public static final String fete_de_l_evacuation ="15/10";
    public String  le_mouled;
    public String aid_al_fitr;
    public String aid_al_idha;
    public String jour_de_l_an_hegerien;
    public String annee;

    public Jour(String le_mouled, String aid_al_fitr, String aid_al_idha, String jour_de_l_an_hegerien, String annee) {
        this.le_mouled = le_mouled;
        this.aid_al_fitr = aid_al_fitr;
        this.aid_al_idha = aid_al_idha;
        this.jour_de_l_an_hegerien = jour_de_l_an_hegerien;
        this.annee = annee;
    }
    
    public Jour(String jour_de_l_an, String fete_de_travail,String fete_de_l_independance,String fete_de_la_revolution,String jour_des_martyrs,String fete_de_la_republique,String fete_de_la_femme,String fete_de_l_evacuation, String le_mouled, String aid_al_fitr, String aid_al_idha, String jour_de_l_an_hegerien, String annee) {
        this.le_mouled = le_mouled;
        this.aid_al_fitr = aid_al_fitr;
        this.aid_al_idha = aid_al_idha;
        this.jour_de_l_an_hegerien = jour_de_l_an_hegerien;
        this.annee = annee;
    }
    
    

    public Jour() {
    }

    public String getLe_mouled() {
        return le_mouled;
    }

    public void setLe_mouled(String le_mouled) {
        this.le_mouled = le_mouled;
    }

    public String getAid_al_fitr() {
        return aid_al_fitr;
    }

    public void setAid_al_fitr(String aid_al_fitr) {
        this.aid_al_fitr = aid_al_fitr;
    }

    public String getAid_al_idha() {
        return aid_al_idha;
    }

    public void setAid_al_idha(String aid_al_idha) {
        this.aid_al_idha = aid_al_idha;
    }

    public String getJour_de_l_an_hegerien() {
        return jour_de_l_an_hegerien;
    }

    public void setJour_de_l_an_hegerien(String jour_de_l_an_hegerien) {
        this.jour_de_l_an_hegerien = jour_de_l_an_hegerien;
    }

    public String getAnnee() {
        return annee;
    }

    public void setAnnee(String annee) {
        this.annee = annee;
    }

    @Override
    public String toString() {
        return "Jour{" + "le_mouled=" + le_mouled + ", aid_al_fitr=" + aid_al_fitr + ", aid_al_idha=" + aid_al_idha + ", jour_de_l_an_hegerien=" + jour_de_l_an_hegerien + ", annee=" + annee + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.le_mouled);
        hash = 53 * hash + Objects.hashCode(this.aid_al_fitr);
        hash = 53 * hash + Objects.hashCode(this.aid_al_idha);
        hash = 53 * hash + Objects.hashCode(this.jour_de_l_an_hegerien);
        hash = 53 * hash + Objects.hashCode(this.annee);
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
        final Jour other = (Jour) obj;
        if (!Objects.equals(this.le_mouled, other.le_mouled)) {
            return false;
        }
        if (!Objects.equals(this.aid_al_fitr, other.aid_al_fitr)) {
            return false;
        }
        if (!Objects.equals(this.aid_al_idha, other.aid_al_idha)) {
            return false;
        }
        if (!Objects.equals(this.jour_de_l_an_hegerien, other.jour_de_l_an_hegerien)) {
            return false;
        }
        if (!Objects.equals(this.annee, other.annee)) {
            return false;
        }
        return true;

    
    }

    
    
    
    
    
    
   
    







}

    
    

