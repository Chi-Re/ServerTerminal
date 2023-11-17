package server.terminal.io;

import server.terminal.util.StringUtil;
import server.terminal.util.run.SeverRunnable;

public class FileHtml {
    private final FilePath htmlFile;

    public FileHtml(FilePath file){
        this.htmlFile = file;
    }

    public FileHtml(String path){
        this.htmlFile = new FilePath(path);
    }

    public FilePath getFile() {
        return this.htmlFile;
    }

    public String getContent(){
        return this.htmlFile.readStr();
    }

    public String getContent(SeverRunnable init){
        String htmlContent = this.getContent();
        if (init != null){
            init.run();
            if (init.getReturn().list != null){
                String htm = StringUtil.findReplace(htmlContent, init.getReturn());
            }
        }
        return htmlContent;
    }
}
