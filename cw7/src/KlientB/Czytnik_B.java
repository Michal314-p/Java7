package KlientB;

import SOAP_wiadomosci.Obsluga_wiadomosci;

import javax.xml.soap.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Czytnik_B extends Thread
{

    public Socket gniazdo_accept;
    public static Socket gniazdo;
    public static ServerSocket gniazdo_serwer;
    public static String port_wlasciwy;
    public static String port_trasa;
    private BufferedReader buffered_reader;
    public static Controller_B controller_b;
    public static String odbiorca_czytnik;
    public static String nadawca_czytnik;
    public static String wiadomosc_czytnik;
    public static String rodzaj_wiadomosci_czytnik;
    public static String pobierana_wiadomosc;
    public static String zapisana_wiadomosc;

    public Czytnik_B(String port_wlasciwy) {
        this.port_trasa = String.valueOf(Integer.valueOf(port_wlasciwy)+1);
        this.port_wlasciwy = port_wlasciwy;
    }

    public void run()
    {
        try {
            gniazdo_serwer = new ServerSocket(Integer.parseInt(port_wlasciwy));
            while (true)
            {
                gniazdo_accept = gniazdo_serwer.accept();
                buffered_reader = new BufferedReader(new InputStreamReader(gniazdo_accept.getInputStream()));
                if (!gniazdo_accept.isClosed())
                {
                    zapisana_wiadomosc = buffered_reader.readLine();

                    while ((pobierana_wiadomosc = buffered_reader.readLine()) != null)
                    {
                        zapisana_wiadomosc += pobierana_wiadomosc;
                    }
                    if (zapisana_wiadomosc != null)
                    {
                        Obsluga_wiadomosci soap_message_handler = new Obsluga_wiadomosci();
                        soap_message_handler.odczyt_wiadomosci(zapisana_wiadomosc);
                        odbiorca_czytnik = soap_message_handler.getOdbiorca();
                        nadawca_czytnik = soap_message_handler.getNadawca();
                        wiadomosc_czytnik = soap_message_handler.getWiadomosc();
                        rodzaj_wiadomosci_czytnik = soap_message_handler.getRodzaj_wiadomosci();
                        if(rodzaj_wiadomosci_czytnik.equals("Broadcast"))
                        {
                            przeslij_broadcast();
                        }
                        if(rodzaj_wiadomosci_czytnik.equals("Unicast"))
                        {
                            przeslij_unicast();
                        }
                    }
                }
                buffered_reader.close();
            }
        } catch (IOException | SOAPException e)
        {
            e.printStackTrace();
        }
    }
    public static void przeslij(SOAPMessage soap_message) throws SOAPException, IOException
    {
        gniazdo = new Socket("127.0.0.1", Integer.parseInt(port_trasa));
        PrintStream out = new PrintStream(gniazdo.getOutputStream(), true);
        soap_message.writeTo(out);
        out.close();
    }


    public void przeslij_unicast() throws SOAPException, IOException
    {

        Obsluga_wiadomosci soap_message_handler = new Obsluga_wiadomosci();
        soap_message_handler.odczyt_wiadomosci(zapisana_wiadomosc);
        rodzaj_wiadomosci_czytnik = soap_message_handler.getRodzaj_wiadomosci();
        if(odbiorca_czytnik.equals(port_wlasciwy))
        {
            controller_b.pokaz_dane_diagnostyczne("Otrzymano wiadomość od " + nadawca_czytnik);
            controller_b.pokaz_wiadomosc(nadawca_czytnik + ": " + wiadomosc_czytnik);
        }  else if (!nadawca_czytnik.equals(port_wlasciwy)) {
            przeslij(soap_message_handler.getWiadomosc_SOAP());
            controller_b.pokaz_dane_diagnostyczne("Przekazano wiadomość od " + nadawca_czytnik + " do " + odbiorca_czytnik);
        }

    }

    public void przeslij_broadcast() throws  SOAPException, IOException
    {
        Obsluga_wiadomosci soap_message_handler = new Obsluga_wiadomosci();
        soap_message_handler.odczyt_wiadomosci(zapisana_wiadomosc);
        odbiorca_czytnik = soap_message_handler.getOdbiorca();
        nadawca_czytnik = soap_message_handler.getNadawca();
        wiadomosc_czytnik = soap_message_handler.getWiadomosc();
        rodzaj_wiadomosci_czytnik = soap_message_handler.getRodzaj_wiadomosci();
        if (!nadawca_czytnik.equals(port_wlasciwy))
        {
            controller_b.skrzynka_odbiorcza.getItems().add(wiadomosc_czytnik);
            controller_b.pokaz_dane_diagnostyczne("Otrzymano wiadomość od " + nadawca_czytnik);
            controller_b.pokaz_wiadomosc(nadawca_czytnik + ": " + wiadomosc_czytnik);
            if(!port_trasa.equals(nadawca_czytnik))
            {
                przeslij(soap_message_handler.getWiadomosc_SOAP());
            }
        }
    }

}
