package org.example;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.sample.Helper;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        OkHttpClient client = new OkHttpClient();
        String url = "https://jsonplaceholder.typicode.com/todos/1/";

        Request request = new Request.Builder().url(url).build();

        try (Response response = client.newCall(request).execute()) {
            if(!response.isSuccessful()) {
                System.out.println("Something went wring !!!!");
            }
            System.out.println(response.body().string());
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }
}