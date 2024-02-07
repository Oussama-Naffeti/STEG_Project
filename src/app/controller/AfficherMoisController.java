package app.controller;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import entities.Mois;
import java.awt.Color;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import services.MoisCRUD;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import utils.DB;

public class AfficherMoisController implements Initializable {

    @FXML
    private TableView<Mois> Table_Mois;
    @FXML
    private TableColumn<Mois, Integer> column_mois;
    @FXML
    private TableColumn<Mois, Integer> column_mois_traitement;
    @FXML
    private TableColumn<Mois, String> column_fin_saisie_releve;
    @FXML
    private TableColumn<Mois, String> column_facturation;
    @FXML
    private TableColumn<Mois, String> column_edit_facture;
    @FXML
    private TableColumn<Mois, String> column_edit_releves;
    @FXML
    private TableColumn<Mois, String> column_prelevement;
    @FXML
    private TableColumn<Mois, String> column_relance;

    private final MoisCRUD moisCRUD = new MoisCRUD();
    @FXML
    private TextField champ_annee;
    @FXML
    private Button btn_retour;
    @FXML
    private Button btn_exporter;
    @FXML
    private Button btn_supprimer;
    

 @Override
public void initialize(URL url, ResourceBundle rb) {
    // Initialiser votre interface utilisateur ici, le cas échéant.

    // Ajouter un écouteur de changement de texte au champ champ_annee
    champ_annee.textProperty().addListener((observable, oldValue, newValue) -> {
        // Lorsque le texte du champ change, appelez la méthode handleAfficherButtonAction
        handleAfficherButtonAction(null);
    });

    // Configurez la politique de redimensionnement des colonnes du TableView
    Table_Mois.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
}


    

private void handleAfficherButtonAction(ActionEvent event) {
    try {
        // Récupérez une connexion à la base de données
        Connection cnx = DB.getInstance().getConnection();

        // Récupérez l'année à partir du champ texte champ_annee
        String anneeText = champ_annee.getText();
        if (!anneeText.isEmpty()) {
            int annee = Integer.parseInt(anneeText);

            // Appelez la méthode getMois de votre classe MoisCRUD avec l'année spécifique
            List<Mois> moisList = moisCRUD.getMois(cnx, annee);

            // Créez une ObservableList à partir de la liste des mois
            ObservableList<Mois> observableMoisList = FXCollections.observableArrayList(moisList);

            // Associez l'ObservableList à votre TableView
            Table_Mois.setItems(observableMoisList);

            // Configurez les cellules de chaque colonne pour afficher les données appropriées
            column_mois.setCellValueFactory(new PropertyValueFactory<>("mois"));
            column_mois_traitement.setCellValueFactory(new PropertyValueFactory<>("mois_traitement"));
            column_fin_saisie_releve.setCellValueFactory(new PropertyValueFactory<>("fin_saisie_releve"));
            column_facturation.setCellValueFactory(new PropertyValueFactory<>("facturation"));
            column_edit_facture.setCellValueFactory(new PropertyValueFactory<>("edit_facture"));
            column_edit_releves.setCellValueFactory(new PropertyValueFactory<>("edit_releve"));
            column_prelevement.setCellValueFactory(new PropertyValueFactory<>("prelevement"));
            column_relance.setCellValueFactory(new PropertyValueFactory<>("relance"));
        } else {
            // Gérez le cas où le champ_annee est vide ou l'année n'est pas valide
            // Affichez un message d'erreur ou effectuez une autre action appropriée.
        }

    } catch (SQLException e) {
        // Gérez les exceptions SQLException ici (par exemple, affichez un message d'erreur).
        e.printStackTrace();
    } catch (NumberFormatException e) {
        // Gérez le cas où l'utilisateur entre une année non valide (non numérique).
        // Affichez un message d'erreur ou effectuez une autre action appropriée.
    }
}


@FXML
private void handleExportButtonAction(ActionEvent event) {
    try {
        // Créez un fichier PDF avec iTextPDF
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, new FileOutputStream("Calendrier.pdf"));
        document.open();

        // Ajoutez le titre au-dessus du tableau
        String anneeText = champ_annee.getText();
        if (!anneeText.isEmpty()) {
            Paragraph titre = new Paragraph("Calendrier " + anneeText, new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD));
            titre.setAlignment(Element.ALIGN_CENTER);
            document.add(titre);
        }

        // Créez un tableau pour les données
        PdfPTable table = new PdfPTable(8); // 8 colonnes
        table.setWidthPercentage(100);

        // Ajoutez de l'espace entre les lignes
        table.setSpacingBefore(10f); // Par exemple, 10 unités (peut être ajusté selon vos besoins)

        // Ajoutez les en-têtes de colonne
        String[] headers = { "Mois", "Mois de Traitement", "Fin de Saisie de Relevé", "Facturation", "Édition de Facture", "Édition de Relevés", "Prélèvement", "Relance" };
        for (String header : headers) {
            PdfPCell headerCell = new PdfPCell(new Phrase(header, new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD)));
            // Définissez ici le style pour les cellules d'en-tête (bleu clair)
            headerCell.setBackgroundColor(new BaseColor(173, 216, 230)); // Couleur bleu clair en RVB
            headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(headerCell);
        }

        // Ajoutez les données de la TableView au tableau PDF
        for (Mois mois : Table_Mois.getItems()) {
            table.addCell(new Phrase(mois.getMois().toString()));
            table.addCell(new Phrase(mois.getMois_traitement().toString()));
            table.addCell(new Phrase(mois.getFin_saisie_releve()));
            table.addCell(new Phrase(mois.getFacturation()));
            table.addCell(new Phrase(mois.getEdit_facture()));
            table.addCell(new Phrase(mois.getEdit_releve()));
            table.addCell(new Phrase(mois.getPrelevement()));
            table.addCell(new Phrase(mois.getRelance()));
        }

        document.add(table);
        document.close();

        // Ouvrez le fichier PDF généré
        File pdfFile = new File("Calendrier.pdf");
        if (pdfFile.exists()) {
            Desktop.getDesktop().open(pdfFile);
        }

    } catch (DocumentException | IOException e) {
        // Gérez les exceptions qui peuvent survenir lors de la génération du PDF
        e.printStackTrace();
    }
}

@FXML
private void redirigerFormulaire() {
    try {
        // Charger le fichier FXML de la page AfficherMois.fxml
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/AjouterJour.fxml"));
        Parent root = loader.load();

        // Créer une nouvelle scène
        Scene scene = new Scene(root);

        // Obtenir la fenêtre (stage) actuelle à partir du bouton ou d'un autre élément de la scène actuelle
        Stage stage = (Stage) btn_retour.getScene().getWindow();

        // Remplacer la scène actuelle par la nouvelle scène
        stage.setScene(scene);

        // Afficher la nouvelle scène
        stage.show();
    } catch (Exception e) {
        e.printStackTrace();
    }
}

    @FXML
private void handleSupprimerButtonAction(ActionEvent event) {
    try {
        // Récupérez une connexion à la base de données
        Connection cnx = DB.getInstance().getConnection();

        // Récupérez l'année à partir du champ texte champ_annee
        String anneeText = champ_annee.getText();
        if (!anneeText.isEmpty()) {
            int annee = Integer.parseInt(anneeText);

            // Appelez une méthode pour supprimer les mois de l'année spécifiée
            moisCRUD.supprimerMoisParAnnee(cnx, annee);

            // Affichez un message de confirmation
            afficherMessageConfirmation("Année " + annee + " supprimée avec succès!");

            // Rafraîchissez les données du tableau
            handleAfficherButtonAction(null);
        } else {
            // Gérez le cas où le champ_annee est vide ou l'année n'est pas valide
            // Affichez un message d'erreur ou effectuez une autre action appropriée.
        }
    } catch (SQLException e) {
        // Gérez les exceptions SQLException ici (par exemple, affichez un message d'erreur).
        e.printStackTrace();
    } catch (NumberFormatException e) {
        // Gérez le cas où l'utilisateur entre une année non valide (non numérique).
        // Affichez un message d'erreur ou effectuez une autre action appropriée.
    }
}


private void afficherMessageConfirmation(String message) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("Confirmation");
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
}




}

    

























