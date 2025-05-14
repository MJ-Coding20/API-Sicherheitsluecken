package de.seminar.services;

import com.google.gson.Gson;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.font.FontProvider;
import de.seminar.objects.Invoice;
import de.seminar.objects.PDF;
import de.seminar.objects.Person;
import de.seminar.utils.RandomPeople;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;
import java.util.Arrays;

public class APIService {
    // Mockup for login-check
    private static String pwd = "12345";
    private static String usr = "Testuser";

    // Mockup for IDOR
    private static Person[] people = RandomPeople.generate(20);


    public static int calculateFibonacci(int index){
        if (index <= 1) {
            return index;
        }
        return calculateFibonacci(index - 1) + calculateFibonacci(index - 2);
    }

    public static boolean checkLogin(String username, String password){
        if(usr.equals(username) && pwd.equals(password)) {
            return true;
        }
        return false;
    }

    public static Person getProfile(int id){
        return people[id];
    }

    public static byte[] getPicture(String url) throws IOException, InterruptedException {
        try(HttpClient client = HttpClient.newHttpClient()) {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<byte[]> response = client.send(request, HttpResponse.BodyHandlers.ofByteArray());

            if (response.statusCode() != 200) {
                return new byte[0];
            }
            return response.body();
        }
    }

    public static byte[] getRechnung(Invoice inv) throws IOException, InterruptedException {
        PDF pdf = new PDF();
        pdf.setTitle(inv.getTitel());
        StringBuilder stringBuilder = new StringBuilder();
        pdf.setContent(stringBuilder.append("Dear ").append(inv.getRecipient()).append(", ").append(inv.getContent()).append(", with love ").append(inv.getSender()).toString());

        String url = "http://192.168.5.3:8080/api2/pdfGenerator";

        try(HttpClient client = HttpClient.newHttpClient()) {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Content-Type", "application/json")
                    .header("Accept", "application/pdf")
                    .POST(HttpRequest.BodyPublishers.ofString(new Gson().toJson(pdf)))
                    .build();
            HttpResponse<byte[]> response = client.send(request, HttpResponse.BodyHandlers.ofByteArray());

            if (response.statusCode() != 200) {
                System.out.println(response.statusCode());
                System.out.println(Arrays.toString(response.body()));
                return new byte[0];
            }

            return response.body();
        }
    }

    public static byte[] getPDF(PDF pdf) {
        // Extract content from JSON
        String titel = pdf.getTitle();
        String content = pdf.getContent();

        // Generate the PDF from the content
        ByteArrayOutputStream pdfOutputStream = new ByteArrayOutputStream();
        // Create the PDF document
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(pdfOutputStream));
        Document doc = new Document(pdfDoc);

        // Add title and content to the PDF
        doc.add(new Paragraph(titel).setFontSize(18).setFontColor(ColorConstants.DARK_GRAY));
        doc.add(new Paragraph(content).setFontSize(12));

        // Close the canvas and PDF document
        doc.close();

        return pdfOutputStream.toByteArray();
    }
}
