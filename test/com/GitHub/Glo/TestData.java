package com.GitHub.Glo;

public class TestData {
    public static String getRequestContent(){
        return "{\n" +
                "\t\"version\": \"1.0\",\n" +
                "\t\"session\": {\n" +
                "\t\t\"new\": false,\n" +
                "\t\t\"sessionId\": \"amzn1.echo-api.session.deecc942-48a7-4ecf-b359-2a48a820d349\",\n" +
                "\t\t\"application\": {\n" +
                "\t\t\t\"applicationId\": \"amzn1.ask.skill.6dc2466b-3a6d-45a7-98dc-6c68bfde4d27\"\n" +
                "\t\t},\n" +
                "\t\t\"user\": {\n" +
                "\t\t\t\"userId\": \"amzn1.ask.account.AHB3F2H3DIYDDK4CDAW6JMC2AF3TSRAP2KWD3LKD62OEJ4W2JPYBZWGXIJI64VSMJKM7CCQSSRZSMJHADJT6V7IRFS3WKG4KXL7IYH3SBAG43ZIX2UAGFOZ4L3I52IU4BHBUYV76JEX7H47EEIJU3YMATTJIPAOAXXCYHH4NKC6BMSDOP46OBNFCWBH66Y2GHDPUQJQDJMUFAWQ\"\n" +
                "\t\t}\n" +
                "\t},\n" +
                "\t\"context\": {\n" +
                "\t\t\"AudioPlayer\": {\n" +
                "\t\t\t\"playerActivity\": \"IDLE\"\n" +
                "\t\t},\n" +
                "\t\t\"System\": {\n" +
                "\t\t\t\"application\": {\n" +
                "\t\t\t\t\"applicationId\": \"amzn1.ask.skill.6dc2466b-3a6d-45a7-98dc-6c68bfde4d27\"\n" +
                "\t\t\t},\n" +
                "\t\t\t\"user\": {\n" +
                "\t\t\t\t\"userId\": \"amzn1.ask.account.AHB3F2H3DIYDDK4CDAW6JMC2AF3TSRAP2KWD3LKD62OEJ4W2JPYBZWGXIJI64VSMJKM7CCQSSRZSMJHADJT6V7IRFS3WKG4KXL7IYH3SBAG43ZIX2UAGFOZ4L3I52IU4BHBUYV76JEX7H47EEIJU3YMATTJIPAOAXXCYHH4NKC6BMSDOP46OBNFCWBH66Y2GHDPUQJQDJMUFAWQ\"\n" +
                "\t\t\t},\n" +
                "\t\t\t\"device\": {\n" +
                "\t\t\t\t\"deviceId\": \"amzn1.ask.device.AFWBOKNAUY2FK6NT4XQ65BJV7Z75YMIR75Y5S5KQXGFG3O6VZA3AGVNZSKBMGLAIYD2E42UBUJMLFWJLRBBJ5YCS6JPFP6PIS4TH2KAT2RFRR2ZJQN5KCDOBZVKYBL425PTKCBF57G364B2AOQYIEG6RHT7AUMBDLIKVHCAR5Y775A2XUBYH2\",\n" +
                "\t\t\t\t\"supportedInterfaces\": {\n" +
                "\t\t\t\t\t\"AudioPlayer\": {}\n" +
                "\t\t\t\t}\n" +
                "\t\t\t},\n" +
                "\t\t\t\"apiEndpoint\": \"https://api.amazonalexa.com\",\n" +
                "\t\t\t\"apiAccessToken\": \"eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImtpZCI6IjEifQ.eyJhdWQiOiJodHRwczovL2FwaS5hbWF6b25hbGV4YS5jb20iLCJpc3MiOiJBbGV4YVNraWxsS2l0Iiwic3ViIjoiYW16bjEuYXNrLnNraWxsLjZkYzI0NjZiLTNhNmQtNDVhNy05OGRjLTZjNjhiZmRlNGQyNyIsImV4cCI6MTU1MDg5MzAzNywiaWF0IjoxNTUwODkyNzM3LCJuYmYiOjE1NTA4OTI3MzcsInByaXZhdGVDbGFpbXMiOnsiY29uc2VudFRva2VuIjpudWxsLCJkZXZpY2VJZCI6ImFtem4xLmFzay5kZXZpY2UuQUZXQk9LTkFVWTJGSzZOVDRYUTY1QkpWN1o3NVlNSVI3NVk1UzVLUVhHRkczTzZWWkEzQUdWTlpTS0JNR0xBSVlEMkU0MlVCVUpNTEZXSkxSQkJKNVlDUzZKUEZQNlBJUzRUSDJLQVQyUkZSUjJaSlFONUtDRE9CWlZLWUJMNDI1UFRLQ0JGNTdHMzY0QjJBT1FZSUVHNlJIVDdBVU1CRExJS1ZIQ0FSNVk3NzVBMlhVQllIMiIsInVzZXJJZCI6ImFtem4xLmFzay5hY2NvdW50LkFIQjNGMkgzRElZRERLNENEQVc2Sk1DMkFGM1RTUkFQMktXRDNMS0Q2Mk9FSjRXMkpQWUJaV0dYSUpJNjRWU01KS003Q0NRU1NSWlNNSkhBREpUNlY3SVJGUzNXS0c0S1hMN0lZSDNTQkFHNDNaSVgyVUFHRk9aNEwzSTUySVU0QkhCVVlWNzZKRVg3SDQ3RUVJSlUzWU1BVFRKSVBBT0FYWENZSEg0TktDNkJNU0RPUDQ2T0JORkNXQkg2NlkyR0hEUFVRSlFESk1VRkFXUSJ9fQ.N2lPLV5t24ADXaWsBH1c8gOo6a8sQL01Zn7pDn7m8us0ZIMxPcumOqyQht8U2vz5B9Fbl4WPMao1coN_R1h6j4YUv3kaGaobdKBIUrl3hrpTKu-P2BAbR50O3S5xfiyg1xCuPWvqfzMwetUclrdSRx5npVmvftTKi1SoD4r6nghirU7fH5BI0kj0XaIHNQ_FXVtLVWGGCwFIngJaiUKOqQZuwZGA1YNa-WxJq3o2mmguKv28fuuoYRLDa1zLbgPU8Y28F4oJbbDTZZyMXhXdDcBlCStWa3Fq96sgJ12b_Qdy3hQGkfYqRH2Fq31_m8WmZs9u71KcCYlOVpjxwD14Hg\"\n" +
                "\t\t},\n" +
                "\t\t\"Viewport\": {\n" +
                "\t\t\t\"experiences\": [\n" +
                "\t\t\t\t{\n" +
                "\t\t\t\t\t\"arcMinuteWidth\": 246,\n" +
                "\t\t\t\t\t\"arcMinuteHeight\": 144,\n" +
                "\t\t\t\t\t\"canRotate\": false,\n" +
                "\t\t\t\t\t\"canResize\": false\n" +
                "\t\t\t\t}\n" +
                "\t\t\t],\n" +
                "\t\t\t\"shape\": \"RECTANGLE\",\n" +
                "\t\t\t\"pixelWidth\": 1024,\n" +
                "\t\t\t\"pixelHeight\": 600,\n" +
                "\t\t\t\"dpi\": 160,\n" +
                "\t\t\t\"currentPixelWidth\": 1024,\n" +
                "\t\t\t\"currentPixelHeight\": 600,\n" +
                "\t\t\t\"touch\": [\n" +
                "\t\t\t\t\"SINGLE\"\n" +
                "\t\t\t]\n" +
                "\t\t}\n" +
                "\t},\n" +
                "\t\"request\": {\n" +
                "\t\t\"type\": \"SessionEndedRequest\",\n" +
                "\t\t\"requestId\": \"amzn1.echo-api.request.deb87ea6-5ce6-4446-984d-0f1c80b95fc7\",\n" +
                "\t\t\"timestamp\": \"2019-02-23T03:32:17Z\",\n" +
                "\t\t\"locale\": \"en-US\",\n" +
                "\t\t\"reason\": \"ERROR\",\n" +
                "\t\t\"error\": {\n" +
                "\t\t\t\"type\": \"INVALID_RESPONSE\",\n" +
                "\t\t\t\"message\": \"An exception occurred while dispatching the request to the skill.\"\n" +
                "\t\t}\n" +
                "\t}\n" +
                "}";
    }
}
