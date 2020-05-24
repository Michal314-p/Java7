package KlientB;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Klient_B extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        Parent root = FXMLLoader.load(getClass().getResource("Projekt_klient_B.fxml"));
        primaryStage.setTitle("KlientB");
        primaryStage.setScene(new Scene(root, 330, 640));
        primaryStage.show();
    }

    public static void main(String[] args)
    {
        Czytnik_B czytnikB = new Czytnik_B(String.valueOf(2));
        czytnikB.start();
        launch(args);
    }
}
