package cn.itcase.ovo.utils;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpConnectionUtils {


    public static HttpURLConnection getConnection(String data) throws Exception {

        //通过URL对象获取联网对象
        URL url = new URL("http://42.159.72.121/ChangeDemo/LoginServlet");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");//设置post请求
        connection.setReadTimeout(5000);//设置5s的响应时间
        connection.setDoOutput(true);//允许输出
        connection.setDoInput(true);//允许输入
        //设置请求头，以键值对的方式传输（以下这两点在GET请求中不用设置）
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded ");
        connection.setRequestProperty("Content-Length", data.length() + "");//设置请求体的长度
        OutputStream outputStream = connection.getOutputStream();
        outputStream.write(data.getBytes());//进行传输操作
        //判断服务端返回的响应码，这里是http协议的内容
        return connection;
    }


    public static String sendFile(String urlPath, String filePath,
                                  String newName, OnUploadListener listener) throws Exception {
        String end = "\r\n";
        String twoHyphens = "--";
        String boundary = "******";

        URL url = new URL(urlPath);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        //下载需要将setDoInput方法的参数值设为true
        con.setDoInput(true);
        //上传需要将setDoOutput方法的参数值设为true
        con.setDoOutput(true);
        //禁止HttpURLConnection使用缓存
        con.setUseCaches(false);
        //使用POST请求，必须大写
        con.setRequestMethod("POST");
        //以下三行设置http请求头信息
        con.setRequestProperty("Connection", "Keep-Alive");
        con.setRequestProperty("Charset", "UTF-8");
        //在模拟web页面向服务器端上传文件时，每个文件的开头需要有一个分界符，
        //分界符需要在http请求头中指定。boundary是任意一个字符串，一般为******
        con.setRequestProperty("Content-Type", "multipart/form-data;boundary="
                + boundary);

        DataOutputStream ds = new DataOutputStream(con.getOutputStream());

        ds.writeBytes(twoHyphens + boundary + end);
        //上传文件相关信息，这些信息包括请求参数名，上传文件名，文件类型，但并不限于此
        ds.writeBytes("Content-Disposition: form-data; "
                + "name=\"file1\";filename=\"" + newName + "\"" + end);
        ds.writeBytes(end);

        //获得文件的输入流，通过流传输文件。在这里我重写了FileInputStream，为了监听上传进度
        CustomFileInputStream fStream = new CustomFileInputStream(filePath);
        fStream.setOnUploadListener(listener);
        /* 设置每次写入1024bytes */
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int length = -1;
        // 从文件读取数据至缓冲区
        while ((length = fStream.read(buffer)) != -1) {
            //将资料写入DataOutputStream中
            ds.write(buffer, 0, length);
        }
        ds.writeBytes(end);
        ds.writeBytes(twoHyphens + boundary + twoHyphens + end);

        fStream.close();
        ds.flush();

        //上传完成以后获取服务器的反馈
        InputStream is = con.getInputStream();
        int ch;
        StringBuffer b = new StringBuffer();
        while ((ch = is.read()) != -1) {
            b.append((char) ch);
        }

        ds.close();
        return b.toString();
    }

    public static HttpURLConnection getPersonInfo(String data) throws Exception{
        //通过URL对象获取联网对象
        URL url = new URL("http://42.159.72.121/ChangeDemo/PersonalCenter");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");//设置post请求
        connection.setReadTimeout(5000);
        //connection.setConnectTimeout(20000);//设置5s的响应时间
        connection.setDoOutput(true);//允许输出
        connection.setDoInput(true);//允许输入
        //设置请求头，以键值对的方式传输（以下这两点在GET请求中不用设置）
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded ");
        connection.setRequestProperty("Content-Length", data.length() + "");//设置请求体的长度
        OutputStream outputStream = connection.getOutputStream();
        outputStream.write(data.getBytes());//进行传输操作
        //判断服务端返回的响应码，这里是http协议的内容
        return connection;
    }
}