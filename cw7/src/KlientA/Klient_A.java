package KlientA;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Klient_A extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        Parent root = FXMLLoader.load(getClass().getResource("Projekt_klient_A.fxml"));
        primaryStage.setTitle("KlientA");
        primaryStage.setScene(new Scene(root, 330, 640));
        primaryStage.show();
    }

    public static void main(String[] args)
    {
        Czytnik_A czytnikA = new Czytnik_A(String.valueOf(1));
        czytnikA.start();
        launch(args);
    }
}
