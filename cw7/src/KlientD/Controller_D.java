package KlientD;

import SOAP_wiadomosci.Obsluga_wiadomosci;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller_D extends Thread implements Initializable {
    @FXML public TextField odbiorca;
    @FXML public TextField wiadomosc;
    @FXML public Button wyslij;
    @FXML public Label klient;
    @FXML public ListView diagnoza;
    @FXML public ListView skrzynka_odbiorcza;

    private static String typ_wiadomosci = null;
    private static String diagnoza_tekst ="";
    private static String wiadomosc_tekst ="";
    private static int port = 4;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {

        Czytnik_D.controller_d = this;
        klient.setText("Klient nr: " + port);
        skrzynka_odbiorcza.setEditable(false);
    }

    public void wyslij_wiadomosc() {

        try {
            SOAPMessage soapmessage = Obsluga_wiadomosci.tworzenie_wiadomosci(wiadomosc.getText(), odbiorca.getText(), String.valueOf(Czytnik_D.port_wlasciwy), typ_wiadomosci);
            Czytnik_D.przeslij(soapmessage);
            if(typ_wiadomosci.equals("Unicast")) {
                pokaz_dane_diagnostyczne("Wysłano wiadomość do " + odbiorca.getText());
            }
            else if(typ_wiadomosci.equals("Broadcast")){
                pokaz_dane_diagnostyczne("Wysłano do wszystkich");
            }
        } catch (SOAPException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        } catch (NullPointerException e)
        {
            e.printStackTrace();
        }
    }

    public void pokaz_dane_diagnostyczne(String message){
        diagnoza.getItems().clear();
        diagnoza_tekst +=message+"\n";
        diagnoza.getItems().add(diagnoza_tekst);
    }

    public void zamknij_trase()
    {
        Czytnik_D.port_trasa = "1";
        System.out.println("Połączono");
    }

    public void pokaz_wiadomosc(String message){
        skrzynka_odbiorcza.getItems().clear();
        wiadomosc_tekst +=message + "\n";
        skrzynka_odbiorcza.getItems().add(wiadomosc_tekst);
    }

    public void broadcast_ustaw()
    {
        typ_wiadomosci = "Broadcast";
    }

    public void unicast_ustaw()
    {
        typ_wiadomosci = "Unicast";
    }




}


