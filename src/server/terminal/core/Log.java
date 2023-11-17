package server.terminal.core;

import server.terminal.io.FilePath;

import java.io.PrintWriter;
import java.io.StringWriter;

import static server.terminal.ChireCore.setting;

public class Log {
    protected FilePath logFile;

    public Log() {
        this.logFile = setting.dataDirectory.child("last_log.txt");
        //TODO 好像我不应该删除
        this.logFile.deleteFile();
    }

    public final void initLog(){
        //info("操作系统:", System.getProperty("os.name"));
        //info("JAVA版本:", System.getProperty("java.version"));
        this.info(setting.projectName, " ", "开始加载");
        this.iLog("应用版本:", setting.version);
        this.iLog("操作系统:", System.getProperty("os.name"));
        this.iLog("JAVA版本:", System.getProperty("java.version"));
        this.iLog("JVM内存:",
                String.valueOf(Runtime.getRuntime().freeMemory() / 1024 / 1024),
                "/",
                String.valueOf(Runtime.getRuntime().totalMemory() / 1024 / 1024),
                " M");
    }

    public void print(String content){
        System.out.println(content);
    }
    public void write(String content){
        this.logFile.writeString(content, true);
    }

    public void log(String text, boolean p, boolean w){
        if (p) this.print(text);
        if (w) this.write(text);
    }

    protected void iLog(String... text){
        this.log(" | "+this.listStr(text), true, true);
    }

    public String tag(String... tag){
        StringWriter stringWriter = new StringWriter();
        for (var t : tag){
            stringWriter.append("[").append(t).append("] ");
        }
        return stringWriter.toString();
    }

    public void infoTag(String tag, String text){
        this.infoTag(tag, text, true);
    }

    public void infoTag(String tag, String text, boolean haveTime){
        String time = "114514";
        if (haveTime) time = setting.getTime();
        this.log("["+tag+"] ["+time+"] "+text, true, true);
    }

//    public void info(String content, String loaded){
//        infoTag(loaded, content);
//        this.write(content);
//    }

    public void info(String text){
        this.infoTag("I", text);
    }

    public void info(String... text){
        this.infoTag("I", this.listStr(text));
    }

    public void warn(String text){
        this.infoTag("W", text);
    }

    public void warn(String text, Throwable t){
        this.print(text);
        String time = setting.getTime();
        this.log("[E] ["+time+"] "+text+" "+this.getError(t), false, true);
    }

    public void err(String text){
        this.infoTag("E", text);
    }

    /**WWWW*/
    public void err(String text, Throwable t){
        this.print(text);
        String time = setting.getTime();
        this.log("[E] ["+time+"] "+text+" "+this.getError(t), false, true);
    }

    public String getError(Throwable throwable){
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        throwable.printStackTrace(pw);
        return sw.toString();
    }


    public String listStr(String... text){
        StringBuilder a = new StringBuilder();
        for (var t : text){
            a.append(t);
        }
        return a.toString();
    }
}
