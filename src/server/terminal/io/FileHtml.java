package server.terminal.io;

import server.terminal.util.StringUtil;
import server.terminal.util.run.SeverRunnable;

public class FileHtml {
    private final FilePath htmlFile;

    private SeverRunnable init;

    public FileHtml(FilePath file){
        this.htmlFile = file;
        this.init = null;
    }

    public FileHtml(String path){
        this.htmlFile = new FilePath(path);
        this.init = null;
    }

    public void setRunnable(SeverRunnable runnable){
        this.init = runnable;
    }

    public FilePath getFile() {
        return this.htmlFile;
    }

    public String getContent(){
        return this.htmlFile.readStr();
    }

    /**在某种意义上实现了html嵌入java，会在*/
    public String getEmbedContent(){
        String htmlContent = this.getContent();
        if (init != null){
            init.run();
            if (init.getReturn().getList() != null){
                htmlContent = StringUtil.findReplace(htmlContent, init.getReturn());
            }
        }
        return htmlContent;
    }
}
