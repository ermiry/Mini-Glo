package com.GitHub.Glo;

import com.amazonaws.util.StringInputStream;
import org.junit.Assert;

import java.io.ByteArrayOutputStream;
import java.io.IOException;



public class MiniGloRequestHandleTest {

    @org.junit.Test
    public void handleRequest() {
        MiniGloRequestHandle handler = new MiniGloRequestHandle();
        try{
            StringInputStream inputStream = new StringInputStream(TestData.getRequestContent());
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            handler.handleRequest(inputStream, outputStream,null);
            String respond = outputStream.toString();
            Assert.assertNotNull(respond);
            System.out.println(respond);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}