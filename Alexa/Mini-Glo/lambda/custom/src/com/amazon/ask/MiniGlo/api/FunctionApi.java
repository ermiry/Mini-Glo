package com.amazon.ask.MiniGlo.api;


import com.amazon.ask.MiniGlo.model.Constants;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.Response;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;


public class FunctionApi {

    private static FunctionApi sharedInstance;

    public static FunctionApi getSharedInstance(){
        if(sharedInstance==null) sharedInstance = new FunctionApi();
        return sharedInstance;
    }

    private  HttpURLConnection connection = null;
    private  final String USER_AGENT = "Mozilla/5.0";
    public  final String UNIVERSAL_URL = "https://ea52f4e7.ngrok.io/api/mini-glo";

    public  BufferedReader sendGet(String url, Map<String,String> params) throws IOException{
        if(params!=null && params.size()>0){
            url += "?";
            Iterator<String>paramsItertor = params.keySet().iterator();
            while(paramsItertor.hasNext()){
                String key = paramsItertor.next();
                String value = params.get(key);
                url += key + "=" + value;
                if(paramsItertor.hasNext()) url += "&";
                System.out.println(url);
            }
        }
        URL obj = new URL(url);
        connection = (HttpURLConnection)obj.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("User-Agent",USER_AGENT);
        connection.setRequestProperty("Content-Type","text/plain");
        connection.setRequestProperty("charset","utf-8");


        int responseCode = connection.getResponseCode();

        System.out.println("\nSending 'Get' request to URL: " + url);
        System.out.println("Response Code:" + responseCode );

        return new BufferedReader(
                new InputStreamReader(connection.getInputStream())
        );

    }

    public BufferedReader sendDelete(String url, Map<String,String> params) throws IOException{
        if(params!=null && params.size()>0){
            url += "?";
            Iterator<String>paramsItertor = params.keySet().iterator();
            while(paramsItertor.hasNext()){
                String key = paramsItertor.next();
                String value = params.get(key);
                url += key + "=" + value;
                if(paramsItertor.hasNext()) url += "&";
                System.out.println(url);
            }
        }
        URL obj = new URL(url);
        connection = (HttpURLConnection) obj.openConnection();
        connection.setUseCaches(false);
        connection.setDoInput(true);
        connection.setRequestMethod("DELETE");
        connection.setRequestProperty("User-Agent",USER_AGENT);
        connection.setRequestProperty("Content-Type","text/plain");
        connection.setRequestProperty("charset","utf-8");
        int responseCode = connection.getResponseCode();
        System.out.println("Sending 'Delete' to url: " + url);
        System.out.println("Response Code:" + responseCode);
        return new BufferedReader(
                new InputStreamReader(connection.getInputStream()));
    }

    public  void disconnect(){
            if(connection!=null) connection.disconnect();
        }

    public  BufferedReader sendPost(String url, Map<String,String> params)throws IOException{
            URL obj = new URL(url);
            connection = (HttpURLConnection) obj.openConnection();
            connection.setUseCaches(false);
            connection.setDoInput(true); //true indicates the server returns response
            connection.setRequestMethod("POST");
            StringBuffer requestParams = new StringBuffer();
            System.out.println("Sending 'POST' to " + url);
            if(params!=null && params.size()>0) {
                connection.setDoOutput(true); // true indicates POST request
                Iterator<String> paramIterator = params.keySet().iterator();
                while(paramIterator.hasNext()) {
                    String key = paramIterator.next();
                    String value = params.get(key);
                    requestParams.append(URLEncoder.encode(key, "UTF-8"));
                    requestParams.append("=");
                    requestParams.append(URLEncoder.encode(value, "UTF-8"));
                    if(paramIterator.hasNext()) requestParams.append("&");
                    System.out.println(requestParams);
                }
            }
            //SEND Post Data
            OutputStreamWriter writer = new OutputStreamWriter(
                    connection.getOutputStream());
            writer.write(requestParams.toString());
            writer.flush();
            System.out.println(connection.getResponseCode());
            return new BufferedReader(
                new InputStreamReader(connection.getInputStream()));
        }

    public String getAccessToken(){
        String token;
        try{
            JsonElement tokenElement;
            JsonObject obj = null;
            Map<String,String> params = new HashMap<>();
            BufferedReader in = sendGet(UNIVERSAL_URL+"/token",params);
            try {
                 obj = new JsonParser().parse(in).getAsJsonObject();
            }catch(IllegalStateException e){
                e.printStackTrace();
            }

            if(obj==null) throw new IOException();
            else tokenElement = obj.get("token");
            if(tokenElement==null) throw new IOException();
            else token = tokenElement.getAsString();
        }catch(IOException e){
            e.printStackTrace();
            System.out.println("Token wasnt founded or is null");
            token = "";
        }
        disconnect();
        return token;

    }


    public Optional<Response> badAuthentication(String accessToken,HandlerInput input){
        String currentAccessToken = getAccessToken();

        if(!accessToken.equals(currentAccessToken))

            return input.getResponseBuilder()
                    .withSpeech("You are not authenticated, please reconnect through the chrome extension")
                    .withReprompt(Constants.HELP_MESSAGE)
                    .withShouldEndSession(true)
                    .build();
        else
            return Optional.empty();
    }

    public String reAuthenticate(){
        return getAccessToken();
    }


}
