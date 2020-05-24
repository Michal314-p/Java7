package KlientC;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Klient_C extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        Parent root = FXMLLoader.load(getClass().getResource("Projekt_klient_C.fxml"));
        primaryStage.setTitle("KlientC");
        primaryStage.setScene(new Scene(root, 330, 640));
        primaryStage.show();
    }

    public static void main(String[] args)
    {
        Czytnik_C czytnikB = new Czytnik_C(String.valueOf(3));
        czytnikB.start();
        launch(args);
    }
}
