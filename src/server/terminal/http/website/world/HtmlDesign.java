package server.terminal.http.website.world;

import server.terminal.ChireCore;
import server.terminal.io.FileHtml;
import server.terminal.util.run.SeverRunnable;

public abstract class HtmlDesign {
    private String url;
    private final FileHtml content;

    public HtmlDesign(String URLPath, FileHtml content, SeverRunnable runnable){
        this.url = URLPath;
        this.content = content;
        this.content.setRunnable(runnable);
        ChireCore.content.addHtmlDesign(this);
        this.init();
    }

    public void init(){}

    public void setRunnable(SeverRunnable runnable){
        this.content.setRunnable(runnable);
    }

    public String getUrl(){
        return this.url;
    }

    public void setUrl(String url){
        this.url = url;
    }

    public FileHtml getContent(){
        return this.content;
    }

    @Override
    public String toString(){
        //TODO ?
        return "{\"url:\""+url+"\",\"path\":\""+content.getFile()+"\"}";
    }
}
