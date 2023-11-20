package server.terminal.http.website.content;

import server.terminal.http.website.world.SeverWebHome;
import server.terminal.io.FileHtml;
import server.terminal.util.run.SeverRunnable;

public class SeverWebs {
    public static SeverWebHome severHome;

    public static void load(){
        severHome = new SeverWebHome(
                "/", "F:\\ServerTerminal\\build\\libs\\crashes\\test.html", new SeverRunnable() {
            @Override
            public void run() {
                this.setVar("name", "这里是首页");
            }
        });
    }
}
