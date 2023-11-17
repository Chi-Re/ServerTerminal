package server.terminal.http;

import server.terminal.ChireCore;
import server.terminal.io.FileHtml;
import server.terminal.io.FilePath;
import server.terminal.type.MapList;
import server.terminal.util.exception.SeverRuntimeException;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpApplication {
    private Thread thread;

    private final MapList<FileHtml> htmlList;

    public HttpApplication(int port){
        this.htmlList = new MapList<>();

        new Thread(()-> {
            try (ServerSocket serverSocket = new ServerSocket(port)) {
                ChireCore.log.info("服务器http模块开始运行...");
                for (; ; ) {
                    Socket socket = serverSocket.accept();// 监听
                    //System.out.println("connected from " + socket.getRemoteSocketAddress());
                    this.thread = new Handler(socket, this.htmlList);
                    this.thread.start();
                }
            } catch (IOException e) {
                throw new SeverRuntimeException("服务器http应用运行出错", e);
            }
        }).start();
    }

    public void addHtml(FilePath FileHtml){
        this.htmlList.put("/", new FileHtml(FileHtml));
    }
}
