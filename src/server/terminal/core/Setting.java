package server.terminal.core;

import server.terminal.io.FilePath;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class Setting {
    public final String author = "炽热S";

    public final String projectName = "ServerTerminal";

    public final String version = "1.0.0";

    protected FilePath dataDirectory;

    protected FilePath modDirectory;

    protected FilePath crashesDirectory;

    protected Map<Object, Object> data;

    protected int port = 8081;

    public Setting(){
        this.init();
    }

    private void init(){
        data = new HashMap<>();

        //TODO jar在测试时会将项目判断为根，所以我选择用这个奇怪的方法暂时修复
        dataDirectory = new FilePath("F:\\ServerTerminal\\build\\libs");
        modDirectory = dataDirectory.child("mods/");
        crashesDirectory = dataDirectory.child("crashes/");
    }

    /**包所在文件夹*/
    public String getUserDir(){
        //java.class.path
        return System.getProperty("user.dir");
    }

    /**注意！！！这个虽然简单，但十分重要！请不要随意修改*/
    public final String getTime(){
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    /**判断是否为null并且运行指定程序*/
    public final Boolean isNull(Object any, Runnable run){
        if (any == null && run != null){
            run.run();
        }
        return any == null;
    }

    public final Boolean isNull(Object any){
        return this.isNull(any, null);
    }

    public FilePath getDataDirectory(){
        if (this.dataDirectory != null){
            return this.dataDirectory;
        } else {
            throw new RuntimeException("文件路径dataDirectory为null！！！\n你最好在初始化后使用");
        }
    }

    /**TODO 太怪了，好像没什么用*/
    public void setDataDirectory(FilePath fp){
        this.dataDirectory = fp;
    }

    public FilePath getModDirectory(){
        return this.modDirectory;
    }

    public FilePath getCrashesDirectory(){
        return this.crashesDirectory;
    }

    public int getPort() {
        return this.port;
    }

    public void setPort(int port){
        this.port = port;
    }
}
