package main;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

import org.restlet.resource.ClientResource;
import org.restlet.resource.ResourceException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import qrreader.IQrDecoder;
import qrreader.IWebcamReader;
import qrreader.QrDecoder;
import qrreader.WebcamReader;

public class Main {

	public static void main(String[] args) {
		System.out.println("[Main] Initializing...");
		String[] nameAndTicket = readTicket().split("######");
		System.out.println("[Main] Loaded ticket...");

		boolean ticketIsValid = checkValidity(nameAndTicket[0], nameAndTicket[1]);
		System.out.println("[***] Ticket is valid: " + ticketIsValid);
	}

	public static String readTicket() {
		System.out.println("[Main] Reading ticket...");
		IWebcamReader webcamReader = new WebcamReader();
		IQrDecoder decoder = new QrDecoder();

		BufferedImage image = webcamReader.readImageFromCam();
		try {
			ImageIO.write(image, ".jpg", new File("C:\\deleteImg.jpg"));
			System.out.println("[Main] Image stored");
		} catch (IOException e) {
			System.out.println("[Main] Could not write image");
		}
		System.out.println("[Main] Decoding ticket...");

		String result = decoder.decode(image);
		System.out.println("[Main] Ticket: " + result);
		return result;
	}

	public static boolean checkValidity(String nameToCheck, String ticketToCheck) {
		System.out.println("[Main] Checking ticket: " + nameToCheck + ", " + ticketToCheck);
		URI uri = URI.create("http://test-score-1098.appspot.com/rest/guestbook/");
		ClientResource resource = new ClientResource(uri);
		try {
			String rawAnswer = resource.get().getText();
			InputStream is = new ByteArrayInputStream(rawAnswer.getBytes("UTF-8"));
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(is);
			XPath xpath = XPathFactory.newInstance().newXPath();
			// NodeList nodes = (NodeList) xpath.evaluate("//*/content/text()",
			// doc, XPathConstants.NODESET);
			NodeList nodes = doc.getElementsByTagName("user");
			for (int i = 0; i != nodes.getLength(); i++) {
				Element elem = (Element) nodes.item(i);
				String name = elem.getElementsByTagName("name").item(0).getFirstChild().getTextContent();
				String ticket = elem.getElementsByTagName("ticket").item(0).getFirstChild().getTextContent();
				if (name != nameToCheck)
					continue;
				return ticket.startsWith(ticketToCheck);
			}
		} catch (ResourceException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

}
