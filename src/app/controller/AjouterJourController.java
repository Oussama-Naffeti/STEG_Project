package app.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import services.JourCRUD;
import entities.Jour;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class AjouterJourController implements Initializable {

    @FXML
    private Label label_annee;
    @FXML
    private Label label_mouled;
    @FXML
    private Label label_aid_fitr;
    @FXML
    private Label label_aid_idha;
    @FXML
    private Label label_jour_de_l_an;
    @FXML
    private TextField champ_annee;
    @FXML
    private TextField champ_mouled;
    @FXML
    private TextField champ_aid_fitr;
    @FXML
    private TextField champ_aid_idha;
    @FXML
    private Button btn_ajouter;

    private JourCRUD jourCRUD;
    @FXML
    private TextField champ_jour_an_hegirien;
    @FXML
    private Button btn_tableau;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        jourCRUD = new JourCRUD();

        btn_ajouter.setOnAction(event -> {
            try {
                ajouterJour();
            } catch (SQLException ex) {
                Logger.getLogger(AjouterJourController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ParseException ex) {
                Logger.getLogger(AjouterJourController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

   @FXML
private void ajouterJour() throws SQLException, ParseException {
    String le_mouled = champ_mouled.getText();
    String aid_al_fitr = champ_aid_fitr.getText();
    String aid_al_idha = champ_aid_idha.getText();
    String jour_de_l_an_hegerien = champ_jour_an_hegirien.getText();
    String annee = champ_annee.getText();

    // Vérifier que tous les champs sont remplis
    if (le_mouled.isEmpty() || aid_al_fitr.isEmpty() || aid_al_idha.isEmpty() ||
        jour_de_l_an_hegerien.isEmpty() || annee.isEmpty()) {
        // Afficher un message d'erreur si un champ est vide
        afficherMessageErreur("Veuillez remplir tous les champs.");
        return; // Quitter la méthode si un champ est vide
    }

    // Vérifier le format des dates (dd/MM)
    if (!validerFormatDate(le_mouled) || !validerFormatDate(aid_al_fitr) ||
        !validerFormatDate(aid_al_idha) || !validerFormatDate(jour_de_l_an_hegerien)) {
        // Afficher un message d'erreur pour indiquer le format incorrect
        afficherMessageErreur("Veuillez saisir les dates au format dd/MM.");
        return; // Quitter la méthode si le format est incorrect
    }

    // Vérifier si l'année a exactement 4 chiffres
    if (!annee.matches("\\d{4}")) {
        afficherMessageErreur("L'année doit contenir exactement 4 chiffres.");
        return; // Quitter la méthode si l'année ne contient pas 4 chiffres
    }

    // Vérifier si l'année existe déjà
    if (jourCRUD.anneeExists(annee)) {
        // Afficher un message d'erreur
        afficherMessageErreur("L'année que vous avez entrée existe déjà dans la base de données.");
        return; // Quitter la méthode si l'année existe déjà
    }

    // Si tous les champs sont remplis, le format de date est valide et l'année est correcte, ajouter le jour
    Jour jour = new Jour("01/01", "01/05", "20/03", "17/12", "09/04", "25/07", "13/08", "15/10", le_mouled, aid_al_fitr, aid_al_idha, jour_de_l_an_hegerien, annee);
    jourCRUD.ajouterJour(jour);

    // Réinitialisation des champs après l'ajout
    champ_mouled.clear();
    champ_aid_fitr.clear();
    champ_aid_idha.clear();
    champ_jour_an_hegirien.clear();
    champ_annee.clear();

    // Afficher un message de confirmation
    Alert confirmationAlert = new Alert(AlertType.INFORMATION);
    confirmationAlert.setTitle("Confirmation");
    confirmationAlert.setHeaderText(null);
    confirmationAlert.setContentText("Les jours fériés ont été ajoutés avec succès.");
    confirmationAlert.showAndWait();
    redirigerVersAfficherMois();
}

// ...

// Méthode pour valider le format de date
private boolean validerFormatDate(String date) {
    return date.matches("\\d{2}/\\d{2}");
}

private void afficherMessageErreur(String message) {
    Alert alert = new Alert(AlertType.ERROR);
    alert.setTitle("Erreur");
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
}






// ...
@FXML
private void redirigerVersAfficherMois() {
    try {
        // Charger le fichier FXML de la page AfficherMois.fxml
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/AfficherMois.fxml"));
        Parent root = loader.load();

        // Créer une nouvelle scène
        Scene scene = new Scene(root);

        // Obtenir la fenêtre (stage) actuelle à partir du bouton ou d'un autre élément de la scène actuelle
        Stage stage = (Stage) btn_ajouter.getScene().getWindow();

        // Remplacer la scène actuelle par la nouvelle scène
        stage.setScene(scene);

        // Afficher la nouvelle scène
        stage.show();
    } catch (Exception e) {
        e.printStackTrace();
    }
}
@FXML
private void redirigerTableau() {
    try {
        // Charger le fichier FXML de la page AfficherMois.fxml
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/AfficherMois.fxml"));
        Parent root = loader.load();

        // Créer une nouvelle scène
        Scene scene = new Scene(root);

        // Obtenir la fenêtre (stage) actuelle à partir du bouton ou d'un autre élément de la scène actuelle
        Stage stage = (Stage) btn_tableau.getScene().getWindow();

        // Remplacer la scène actuelle par la nouvelle scène
        stage.setScene(scene);

        // Afficher la nouvelle scène
        stage.show();
    } catch (Exception e) {
        e.printStackTrace();
    }
}


}
