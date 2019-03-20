package com.amazon.ask.MiniGlo.api;


import com.google.gson.JsonArray;
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
    private static final String UNIVERSAL_URL = "http://e7f42cd7.ngrok.io/api/mini-glo";

    private static BufferedReader sendGet(String url) throws IOException{
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

    public static void disconnect(){
            if(connection!=null) connection.disconnect();
        }

    private static BufferedReader sendPost(String url, Map<String,String> params)throws Exception{
            URL obj = new URL(url);
            connection = (HttpURLConnection) obj.openConnection();
            connection.setUseCaches(false);
            connection.setDoInput(true); //true indicates the server returns response
            connection.setRequestMethod("POST");
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
            return new BufferedReader(
                new InputStreamReader(connection.getInputStream()));
        }

    public String getAccessToken(){
        String token;
        boolean isArray = false;
        try{
            JsonElement tokenElement;
            JsonObject obj = null;
            JsonArray objArr;
            BufferedReader in = sendGet(UNIVERSAL_URL+"/token");
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

    public JsonObject createBoard(String boardName){
        Map<String,String> params = new HashMap<>();
        boolean isArray;
        try{
            JsonObject obj = null;
            JsonArray objArr;
            params.put("name",boardName);
            BufferedReader in = sendPost(UNIVERSAL_URL+"/boardPost",params);
            try {
                obj = new JsonParser().parse(in).getAsJsonObject();
            }catch(IllegalStateException e){
                e.printStackTrace();
            }
            if(obj==null) throw new Exception();
            else{ disconnect(); return obj;}
        }catch(Exception e){
            e.printStackTrace();
            disconnect();
            return null;
        }
    }

    public JsonObject lookForBoard(String boardName){
        int i=0;
        try {
            JsonObject obj = null;
            JsonArray objArr=null;
            BufferedReader in = sendGet(UNIVERSAL_URL+"/boards");
            objArr = new JsonParser().parse(in).getAsJsonArray();
            for(i=0; i<objArr.size(); i++){
                JsonObject obj1 = objArr.get(i).getAsJsonObject();
                JsonElement name = obj1.get("name");
                JsonElement id = obj1.get("id");
                if(name.getAsString().toUpperCase().equals(boardName.toUpperCase())){
                    System.out.println(obj1);
                    disconnect();
                    return obj1;
                }
            }
        }catch(IOException e) {
            e.printStackTrace();
        }
        disconnect();
        return null;
    }

    public JsonObject addColumnToBoard(String columnName,JsonObject board){
//        Map<String,String> params = new HashMap<>();
//        JsonObject obj;
//        String success;
//        params.put("columnName",columnName);
//
//        params.put("board",board);
//        try {
//            obj = sendPost("https://e7f42cd7.ngrok.io/api/mini-glo/columnPost",params);
//            if(obj==null)throw new Exception();
//            else {disconnect(); return obj;}
//        }catch(Exception e){
//            e.printStackTrace();
//
//        }
//        disconnect();
        return null;
    }

    public boolean addCardtoColumn(String columnName, String cardName, String description){


        return true;
    }


}
