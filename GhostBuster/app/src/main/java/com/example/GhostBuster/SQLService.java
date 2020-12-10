package com.example.GhostBuster;

import java.io.BufferedReader;

import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;


public class SQLService {
    private static String ip = "192.168.43.140";
    private static String path ="http://"+ip+":8080/MyWeb/";;

    public static String SignIn(String phone) {
        SignInThread myThread = new SignInThread(path+"SIGNIN",phone);
        try
        {
            myThread.start();
            myThread.join();
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }

        return myThread.getResult();
    }

    public static boolean SignUp(String phone, String password) {
        SignUpThread myThread = new SignUpThread(path+"SIGNUP", phone,password);
        try
        {
            myThread.start();
            myThread.join();
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        return myThread.getResult();
    }

    public static boolean MatchMac(String mac) {
        MatchMacThread myThread = new MatchMacThread(path +"MATCHMAC", mac);
        try
        {
            myThread.start();
            myThread.join();
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        if (myThread.getResult() == null) return false;
        else return true;
    }
}

class SignUpThread extends Thread
{
    private String path;
    private String phone;
    private String password;
    private boolean result = false;

    public SignUpThread(String path, String phone, String password)
    {
        this.path = path;
        this.phone = phone;
        this.password = password;
    }
    @Override
    public void run()
    {
        try {
            URL url = new URL(path);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(8000);//设置连接超时时间
            httpURLConnection.setReadTimeout(8000);//设置读取超时时间
            httpURLConnection.setRequestMethod("POST");//设置请求方法,post

            String data = "phone=" + URLEncoder.encode(phone, "utf-8") + "&password=" + URLEncoder.encode(password, "utf-8");//设置数据
            httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");//设置响应类型
            httpURLConnection.setRequestProperty("Content-Length", data.length() + "");//设置内容长度
            httpURLConnection.setDoOutput(true);//允许输出
            OutputStream outputStream = httpURLConnection.getOutputStream();
            outputStream.write(data.getBytes("utf-8"));//写入数据
            int res=httpURLConnection.getResponseCode();
            System.out.println(res);
            result = (res == 200);
            httpURLConnection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean getResult()
    {
        return result;
    }
}

class SignInThread extends Thread
{
    private String path;
    private String phone;
    private String result;

    public SignInThread(String path, String phone)
    {
        this.path = path;
        this.phone = phone;
    }
    @Override
    public void run() {
        try {
            URL url = new URL(path);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(8000);//设置连接超时时间
            httpURLConnection.setReadTimeout(8000);//设置读取超时时间
            httpURLConnection.setRequestMethod("POST");//设置请求方法,post


            String data = "phone=" + URLEncoder.encode(phone, "utf-8");//设置数据
            httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");//设置响应类型
            httpURLConnection.setRequestProperty("Content-Length", data.length() + "");//设置内容长度
            //conn.setRequestProperty("accept","*/*")此处为暴力方法设置接受所有类型，以此来防范返回415;
//            httpURLConnection.setRequestProperty("accept", "application/json");
            httpURLConnection.setDoOutput(true);//允许输出
            httpURLConnection.setDoInput(true);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            outputStream.write(data.getBytes("utf-8"));//写入数据

            int responseCode = httpURLConnection.getResponseCode();
//            System.out.println("返回码："+responseCode);
//            System.out.println("错误信息："+httpURLConnection.getErrorStream());
            if(responseCode == 200){
                //4.2获取响应正文
                BufferedReader reader = null;
                StringBuffer resultBuffer = new StringBuffer();
                String tempLine = null;

                reader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(),"utf-8"));
                while ((tempLine = reader.readLine()) != null) {
                    resultBuffer.append(tempLine);
                }
                //System.out.println(resultBuffer);
                reader.close();
                result = resultBuffer.toString();
                httpURLConnection.disconnect();
            }
//            else {
//                System.out.println("连接失败！");
//            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getResult()
    {
        return result;
    }
}

class MatchMacThread extends Thread
{
    private String path;
    private String mac;
    private String result;

    public MatchMacThread(String path, String mac)
    {
        this.path = path;
        this.mac = mac;
    }
    @Override
    public void run() {
        try {
            URL url = new URL(path);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(8000);//设置连接超时时间
            httpURLConnection.setReadTimeout(8000);//设置读取超时时间
            httpURLConnection.setRequestMethod("POST");//设置请求方法,post


            String data = "mac=" + URLEncoder.encode(mac, "utf-8");//设置数据
            httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");//设置响应类型
            httpURLConnection.setRequestProperty("Content-Length", data.length() + "");//设置内容长度
            httpURLConnection.setDoOutput(true);//允许输出
            OutputStream outputStream = httpURLConnection.getOutputStream();
            outputStream.write(data.getBytes("utf-8"));//写入数据

            int responseCode = httpURLConnection.getResponseCode();
            if(responseCode == 200){
                //4.2获取响应正文
                BufferedReader reader = null;
                StringBuffer resultBuffer = new StringBuffer();
                String tempLine = null;

                reader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(),"utf-8"));
                while ((tempLine = reader.readLine()) != null) {
                    resultBuffer.append(tempLine);
                }
                //System.out.println(resultBuffer);
                reader.close();
                result = resultBuffer.toString();
                httpURLConnection.disconnect();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getResult()
    {
        return result;
    }
}