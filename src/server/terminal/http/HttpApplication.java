package server.terminal.http;

import server.terminal.ChireCore;
import server.terminal.io.FileHtml;
import server.terminal.io.FilePath;
import server.terminal.type.MapList;
import server.terminal.util.exception.SeverRuntimeException;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpApplication {
    private Thread thread;

    private final MapList<FileHtml> htmlList;

    private final MapList<InputStream> dataList;

    public HttpApplication(int port){
        this.htmlList = new MapList<>();
        this.dataList = new MapList<>();

        new Thread(()-> {
            try (ServerSocket serverSocket = new ServerSocket(port)) {
                ChireCore.log.info("服务器http模块开始运行...");
                for (; ; ) {
                    Socket socket = serverSocket.accept();// 监听
                    //System.out.println("connected from " + socket.getRemoteSocketAddress());
                    this.thread = new Handler(socket, this.htmlList, this.dataList);
                    this.thread.start();
                }
            } catch (IOException e) {
                throw new SeverRuntimeException("服务器http应用运行出错", e);
            }
        }).start();
    }

    public void addHtml(String path, FileHtml FileHtml){
        this.htmlList.put(path, FileHtml, "text/html");
    }

    public void addData(String name, InputStream b, String listClass){
        this.dataList.put(name, b, listClass);
    }

    public void addData(String name, InputStream b){
        this.dataList.put(name, b);
    }
}
