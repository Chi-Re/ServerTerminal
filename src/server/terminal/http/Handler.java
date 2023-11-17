package server.terminal.http;

import server.terminal.io.FileHtml;
import server.terminal.io.FilePath;
import server.terminal.type.MapList;
import server.terminal.util.run.SeverRunnable;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

import static server.terminal.ChireCore.log;

public class Handler extends Thread{
    protected Socket socket;
    protected MapList<FileHtml> htmlList;
    public  Handler(Socket socket, MapList<FileHtml> htmlList) {
        this.socket=socket;
        this.htmlList = htmlList;
    }

    @Override
    public void run() {
        try(InputStream input=this.socket.getInputStream()){
            try(OutputStream output=this.socket.getOutputStream()) {
                handle(input,output);
            } catch (Exception e) {
                log.err("socket.getOutputStream()失败", e);
            }
        } catch (Exception e) {
            try {
                log.warn("socket.getInputStream()的Exception警报", e);
                this.socket.close();
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                log.err("网页run时出错", e1);
            }
        }
    }

    private void handle(InputStream input, OutputStream output) {
        try {
            // TODO Auto-generated method stub
            BufferedReader reader=new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8)) ;
            BufferedWriter writer=new BufferedWriter(new OutputStreamWriter(output,StandardCharsets.UTF_8));
            // 读取http请求
            boolean requestOk=false;
            String first=reader.readLine();
            System.out.println(first);
            //if (first.startsWith("GET / HTTP/")) {
            //    requestOk=true;
            //}
            String data = null;
            if (Objects.equals(first.split(" ")[0], "GET")){
                requestOk=true;
                data = this.htmlList.get(first.split(" ")[1]).getContent(new SeverRunnable() {
                    @Override
                    public void run() {
                        this.setVar("testdata", 1233);
                    }
                });
                //System.out.println(first.split(" ")[1]);
            }
            for(;;){
                String header=reader.readLine();
                // 读取头部信息为空时,HTTP Header读取完毕
                if (header.isEmpty()) {
                    break;
                }
                //System.out.println(header);
            }

            System.out.println(requestOk);
            // 请求失败
            if (!requestOk) {
                // 发送错误响应:
                writer.write("HTTP/1.0 404 Not Found\r\n");
                writer.write("Content-Length: 0\r\n");
                writer.write("\r\n");
                writer.flush();
            }else {
                // 请求成功
                // 发送成功响应:
                if (data == null) data = "<html><body><h1>Hello, world!</h1></body></html>";
                int length = data.getBytes(StandardCharsets.UTF_8).length;
                writer.write("HTTP/1.1 200 OK\r\n");
                writer.write("Connection: close\r\n");
                writer.write("Content-Type: text/html\r\n");
                writer.write("Content-Length: " + length + "\r\n");
                writer.write("\r\n"); // 空行标识Header和Body的分隔
                writer.write(data);
                writer.flush();
            }
        } catch (IOException e){
            log.err("网页handle时出错", e);
        }
    }
}

