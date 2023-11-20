package server.terminal.http;

import server.terminal.io.FileHtml;
import server.terminal.io.FilePath;
import server.terminal.type.MapList;
import server.terminal.util.run.SeverRunnable;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Objects;

import static server.terminal.ChireCore.log;

public class Handler extends Thread {
    protected Socket socket;
    protected MapList<FileHtml> htmlList;
    protected MapList<InputStream> dataList;

    public Handler(Socket socket, MapList<FileHtml> htmlList, MapList<InputStream> data) {
        this.socket = socket;
        this.htmlList = htmlList;
        this.dataList = data;
    }

    @Override
    public void run() {
        try (InputStream input = this.socket.getInputStream()) {
            try (OutputStream output = this.socket.getOutputStream()) {
                handle(input, output);
            } catch (Exception e) {
                //这里是非截断式警报(防止有人通过访问破坏程序)
                log.warn("未知报错，网页访问出错", e);
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

    private void handle(InputStream input, OutputStream output) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(output, StandardCharsets.UTF_8));
        // 读取http请求
        boolean requestOk = false;
        byte[] bytes = new byte[1024];
        int len = 0;
        String first = reader.readLine();
        String data = null;
        InputStream datab = null;
        if (Objects.equals(first.split(" ")[0], "GET")) {
            requestOk = true;
            if (this.htmlList.get(first.split(" ")[1]) != null && this.dataList.getList().get(first.split(" ")[1]) == null) {
                data = this.htmlList.get(first.split(" ")[1]).getEmbedContent();
            } else if (this.dataList.getList().get(first.split(" ")[1]) != null) {
                datab = this.dataList.getList().get(first.split(" ")[1]);
            } else {
                log.warn(first + " 访问失败，请检查是否设置路径");
            }
        }
        for (; ; ) {
            String header = reader.readLine();
            // 读取头部信息为空时,HTTP Header读取完毕
            if (header.isEmpty()) {
                break;
            }
        }

        //System.out.println(requestOk);
        // 请求失败
        if (!requestOk) {
            // 发送错误响应:
            writer.write("HTTP/1.0 404 Not Found\r\n");
            writer.write("Content-Length: 0\r\n");
            writer.write("\r\n");
            writer.flush();
        } else {
            // 请求成功
            // 发送成功响应:
            if (data != null) {
                //data = "<html><body><h1>空</h1></body></html>";
                int length = data.getBytes(StandardCharsets.UTF_8).length;
                writer.write("HTTP/1.1 200 OK\r\n");
                writer.write("Connection: close\r\n");
                writer.write("Content-Type: text/html\r\n");
                writer.write("Content-Length: " + length + "\r\n");
                writer.write("\r\n"); // 空行标识Header和Body的分隔
                writer.write(data);
                writer.flush();
            } else if (datab != null) {

                writer.write("HTTP/1.1 200 OK\r\n");
                writer.write("Connection: close\r\n");
                writer.write("Content-Type: image/png\r\n");
                //writer.write("Content-Length: " + datab + "\r\n");
                writer.write("\r\n"); // 空行标识Header和Body的分隔
//                while ((len = datab.read(bytes)) != -1) {
//                    writer.write(len + "\r\n");//图片的内容
//                }
                //TODO 失败，我的http功底太差了，这个项目以后再说，我暂时在解决这个问题之前不会在这里下功夫了
                System.out.println(imgtoio("F:\\ServerTerminal\\build\\libs\\crashes\\chire.png"));
                //writer.write("x89PNG\r\n\\x1a\n"+imgtoio("F:\\ServerTerminal\\build\\libs\\crashes\\chire.png")+"\r\n");
                writer.flush();
            } else {
                log.warn("网页数据访问出现问题，返回为null");
            }
        }
    }

    public static String imgtoio(String imgaddress) {
        //图片转化为二进制
        byte[] imageBytes;
        try (FileInputStream fileInputStream = new FileInputStream(imgaddress);) {
            imageBytes = new byte[fileInputStream.available()];
            fileInputStream.read(imageBytes);
        } catch (IOException e) {
            System.out.println(e);
            return "null";
        }
        return UnicodeByteToStr(imageBytes);
    }

    public static String UnicodeByteToStr(byte[] b){
        StringBuilder sb = new StringBuilder();
        for (byte value : b) {
            sb.append("\\x").append(String.format("%02x", value));
        }
        return sb.toString();
    }
}

