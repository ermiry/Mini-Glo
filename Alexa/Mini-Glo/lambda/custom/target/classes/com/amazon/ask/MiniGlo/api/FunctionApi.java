package com.amazon.ask.MiniGlo.api;


import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;



public class FunctionApi {

    private static HttpURLConnection connection = null;
    private static final String USER_AGENT = "Mozilla/5.0";


    private static JsonObject sendGet(String url) throws IOException{
            URL obj = new URL(url);
            connection = (HttpURLConnection)obj.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-Agent",USER_AGENT);
            connection.setRequestProperty("Content-Type","text/plain");
            connection.setRequestProperty("charset","utf-8");
            int responseCode = connection.getResponseCode();
            System.out.println("\nSending 'Get' request to URL: " + url);
            System.out.println("Response Code:" + responseCode );

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(connection.getInputStream())
            );

            JsonObject object =  new JsonParser().parse(in).getAsJsonObject();
            in.close();
            return object;
        }

    public static void disconnect(){
            if(connection!=null) connection.disconnect();
        }

    private static JsonObject sendPost(Map<String,String> params)throws Exception{
            URL url = new URL("http://18540928.ngrok.io/api/mini-glo/test");
            connection = (HttpURLConnection) url.openConnection();
            connection.setUseCaches(false);
            connection.setDoInput(true); //true indicates the server returns response

            StringBuffer requestParams = new StringBuffer();

            if(params!=null && params.size()>0) {
                connection.setDoOutput(true); // true indicates POST request
                Iterator<String> paramIterator = params.keySet().iterator();
                while(paramIterator.hasNext()) {
                    String key = paramIterator.next();
                    String value = params.get(key);
                    requestParams.append(URLEncoder.encode(key, "UTF-8"));
                    requestParams.append("=");
                    requestParams.append(URLEncoder.encode(value, "UTF-8"));
                    requestParams.append("&");
                    System.out.println(requestParams);
                }
            }
            //SEND Post Data

            OutputStreamWriter writer = new OutputStreamWriter(
                    connection.getOutputStream());
            writer.write(requestParams.toString());
            writer.flush();


            BufferedReader in = new BufferedReader(
                new InputStreamReader(connection.getInputStream()));

            JsonObject object =  new JsonParser().parse(in).getAsJsonObject();
            in.close();
            return object;
        }

    public String getAccessToken(){
        String token;
        try{
            JsonElement tokenElement;
            JsonObject obj = sendGet("http://18540928.ngrok.io/api/mini-glo/token");
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

    public boolean lookForBoard(String boardName){
        String board;
        try {
            JsonObject obj= sendGet("http://aaf1b450.ngrok.io/api/mini-glo/test");
            board = obj.get("boardName").getAsString();
            if(board==null)
                throw new IOException();
            else System.out.println("BoardName: " + board);
        }catch(IOException e) {
            e.printStackTrace();
            board = null;
        }
        disconnect();

        if(board!=null && !board.toUpperCase().equals(boardName.toUpperCase())) return false;

        return true;
    }

    public boolean addColumnToBoard(String columnName,String board){
        Map<String,String> params = new HashMap<>();
        JsonObject obj;
        String success;
        params.put("columnName",columnName);

        params.put("board",board);
        try {
            obj = sendPost(params);
            success = obj.get("status").getAsString();
        }catch(Exception e){
            e.printStackTrace();
            success = "Failure";
        }
        disconnect();
        if(success.equals("Failure")) return false;

        return true;
    }

    public boolean addCardtoColumn(String columnName, String cardName, String description){


        return true;
    }


}
