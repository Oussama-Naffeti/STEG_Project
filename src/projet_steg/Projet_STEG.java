import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class Projet_STEG extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/FXML/AjouterJour.fxml"));

        // Créez une instance de l'icône que vous souhaitez utiliser
        Image icon = new Image(getClass().getResourceAsStream("/img/logo_steg.jpg"));

        // Appliquez l'icône à la fenêtre
        stage.getIcons().add(icon);

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle("Calendrier");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
