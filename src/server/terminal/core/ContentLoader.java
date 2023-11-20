package server.terminal.core;

import server.terminal.http.website.world.HtmlDesign;

import java.util.ArrayList;
import java.util.List;

public class ContentLoader {
    private final List<HtmlDesign> anyDesign = new ArrayList<>();

    public ContentLoader(){
    }

    public void addHtmlDesign(HtmlDesign youDesign){
        this.anyDesign.add(youDesign);
    }

    public List<HtmlDesign> getHtmlDesign(){
        return this.anyDesign;
    }
}
