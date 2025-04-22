package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        String url = "https://api.nasa.gov/planetary/apod?api_key=yTnHb8kR2jb49E2BXF3spNiYF4vNUiqB6DTqeFtU";

        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse response = httpclient.execute(httpGet);

       // Scanner scanner = new Scanner(response.getEntity().getContent());
       // while (scanner.hasNext()) {
        //    System.out.println(scanner.nextLine());
        //}

        ObjectMapper mapper = new ObjectMapper();
        NasaAnswer answer = mapper.readValue(response.getEntity().getContent(), NasaAnswer.class);
        String imageUrl = answer.url;
        String[] separetedUrl = imageUrl.split("/");
        String filename = separetedUrl[separetedUrl.length - 1];


        HttpGet imageLoad = new HttpGet(imageUrl);

        CloseableHttpResponse image = httpclient.execute(imageLoad);
        FileOutputStream fos = new FileOutputStream("images/" + filename);
        image.getEntity().writeTo(fos);
    }
}