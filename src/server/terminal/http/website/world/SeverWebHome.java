package server.terminal.http.website.world;

import server.terminal.io.FileHtml;
import server.terminal.util.run.SeverRunnable;

import static server.terminal.ChireCore.log;

public class SeverWebHome extends HtmlDesign {
    public SeverWebHome(String URLPath, String path, SeverRunnable runnable) {
        super(URLPath, new FileHtml(path), runnable);
    }

    @Override
    public void init() {
        log.info("首页开始加载");
    }
}
