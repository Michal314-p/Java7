package SOAP_wiadomosci;

import javax.xml.soap.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Obsluga_wiadomosci
{
    public String nadawca;
    public String odbiorca;
    public String wiadomosc;
    public String rodzaj_wiadomosci;
    public SOAPMessage wiadomosc_SOAP;
    public static MessageFactory message_factory;

    public Obsluga_wiadomosci() throws SOAPException
    {
        message_factory = MessageFactory.newInstance();
    }

    public String getNadawca()
    {
        return nadawca;
    }

    public String getOdbiorca()
    {
        return odbiorca;
    }

    public String getWiadomosc()
    {
        return wiadomosc;
    }

    public String getRodzaj_wiadomosci()
    {
        return rodzaj_wiadomosci;
    }

    public SOAPMessage getWiadomosc_SOAP()
    {
        return wiadomosc_SOAP;
    }


    public void odczyt_wiadomosci(String wiadomosc) throws IOException, SOAPException {
        InputStream input_stream = new ByteArrayInputStream(wiadomosc.getBytes());
        wiadomosc_SOAP = message_factory.createMessage(null, input_stream);

        SOAPHeader header = wiadomosc_SOAP.getSOAPPart().getEnvelope().getHeader();
        SOAPBody body = wiadomosc_SOAP.getSOAPPart().getEnvelope().getBody();

        Node odbiorca_tekst = (Node) header.getElementsByTagNameNS("uri", "odbiorca").item(0);
        Node nadawca_tekst = (Node) header.getElementsByTagNameNS("uri", "nadawca").item(0);
        Node rodzaj_wiadomosci_tekst = (Node) header.getElementsByTagNameNS("uri", "rodzaj_wiadomosci").item(0);
        Node wiadomosc_tekst = (Node) body.getElementsByTagName("wiadomosc").item(0);

        odbiorca = odbiorca_tekst.getFirstChild().getTextContent();
        nadawca = nadawca_tekst.getFirstChild().getTextContent();
        rodzaj_wiadomosci = rodzaj_wiadomosci_tekst.getFirstChild().getTextContent();
        this.wiadomosc = wiadomosc_tekst.getFirstChild().getTextContent();
    }

    public static SOAPMessage tworzenie_wiadomosci(String wiadomosc, String odbiorca, String nadawca, String rodzaj_wiadomosci) throws SOAPException {
        message_factory = MessageFactory.newInstance();
        SOAPMessage soap_message = message_factory.createMessage();
        SOAPPart soap_part = soap_message.getSOAPPart();
        SOAPEnvelope soap_envelope = soap_part.getEnvelope();
        SOAPBody soap_body = soap_envelope.getBody();
        SOAPHeader header = soap_envelope.getHeader();

        Name body_name =  SOAPFactory.newInstance().createName("wiadomosc");
        Name header_odbiorca = SOAPFactory.newInstance().createName("odbiorca", "pre", "uri");
        Name header_nadawca = SOAPFactory.newInstance().createName("nadawca", "pre", "uri");
        Name header_rodzaj_wiadomosci = SOAPFactory.newInstance().createName("rodzaj_wiadomosci", "pre", "uri");

        SOAPBodyElement soap_body_element = soap_body.addBodyElement(body_name);
        SOAPElement element_odbiorca = header.addChildElement(header_odbiorca);
        SOAPElement element_nadawca = header.addChildElement(header_nadawca);
        SOAPElement element_rodzaj_wiadomosci = header.addChildElement(header_rodzaj_wiadomosci);

        soap_body_element.addTextNode(wiadomosc);
        element_odbiorca.addTextNode(odbiorca);
        element_nadawca.addTextNode(nadawca);
        element_rodzaj_wiadomosci.addTextNode(rodzaj_wiadomosci);

        soap_message.saveChanges();
        return soap_message;
    }


}
