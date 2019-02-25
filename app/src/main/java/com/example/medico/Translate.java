package com.example.medico;
import java.io.*;
import java.net.*;
import java.util.*;
import com.google.gson.*;
import com.squareup.okhttp.*;
public class Translate {
    // Instantiates the OkHttpClient.
    OkHttpClient client = new OkHttpClient();
    // This function performs a POST request.
    String subscriptionKey = "ec743566bc594cc7aa6398212cfe8218";
    String url = "https://api.cognitive.microsofttranslator.com/translate?api-version=3.0&to=de,it";
    public String Post(String textToConvert) throws IOException {
        MediaType mediaType = MediaType.parse("application/json");

        String front = "[{'Text': ";
        String middle = textToConvert;
        String back = "n}]";
        String final_text = front+middle+back;
        RequestBody body = RequestBody.create(mediaType,
               final_text);
        Request request = new Request.Builder()
                .url(url).post(body)
                .addHeader("Ocp-Apim-Subscription-Key", subscriptionKey)
                .addHeader("Content-type", "application/json").build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }
    // This function prettifies the json response.
    public static String prettify(String json_text) {
        JsonParser parser = new JsonParser();
        JsonElement json = parser.parse(json_text);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(json);
    }
}
