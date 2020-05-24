package KlientD;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Klient_D extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        Parent root = FXMLLoader.load(getClass().getResource("Projekt_klient_D.fxml"));
        primaryStage.setTitle("KlientD");
        primaryStage.setScene(new Scene(root, 330, 640));
        primaryStage.show();
    }

    public static void main(String[] args)
    {
        Czytnik_D czytnikB = new Czytnik_D(String.valueOf(4));
        czytnikB.start();
        launch(args);
    }
}
