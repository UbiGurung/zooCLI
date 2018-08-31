package com.company;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class WeatherDownloader {
    private static String streamToString(InputStream inputStream) {
        String text = new Scanner(inputStream, "UTF-8").useDelimiter("\\Z").next();
        return text;
    }

    public static String jsonGetRequest(String urlQueryString) {
        String json = null;
        try {
            URL url = new URL(urlQueryString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setInstanceFollowRedirects(false);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("charset", "utf-8");
            connection.connect();
            InputStream inStream = connection.getInputStream();
            json = streamToString(inStream); // input stream to string
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return json;
    }

    public static String getWeatherData(){
        Gson gson = new Gson();
        String jsonData = jsonGetRequest("http://api.openweathermap.org/data/2.5/weather?lat=28&lon=84.633331&APPID=09fda989752134ad557ce6b818a5dada");

        JsonObject weatherData = gson.fromJson(jsonData, JsonObject.class);

        String statusCode = weatherData.get("cod").getAsString();

        String weatherMessage = "";


        if(statusCode.equals("200")){
            JsonArray weatherJson = weatherData.getAsJsonArray("weather");
            JsonObject dayJson = weatherJson.get(0).getAsJsonObject();
            String dayWeather = dayJson.get("description").getAsString();

            JsonObject temperatureJson = weatherData.getAsJsonObject("main");
            String temperature = temperatureJson.get("temp").getAsString();

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");

            weatherMessage = "Weather: " + dayWeather + " | Temperature: " + temperature + " (Last updated: " + LocalDateTime.now().format(dtf) + ")";
        }else{
            return "Weather Details are currently unavailable";
        }

        return weatherMessage;
    };

    public static void printWeatherDetails(){
        new Thread(() -> {
            System.out.println(getWeatherData());
        }).start();
    }
}
