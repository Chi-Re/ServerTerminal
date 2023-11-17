package server.terminal.io;

import server.terminal.util.exception.ModRuntimeException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Properties;

import static server.terminal.ChireCore.setting;

public class FileMod {
    /**文件管理*/
    private final FilePath mod;
    /**加载的类(继承{@link server.terminal.mod.Mod}的主类)*/
    private String className;
    /**模组的基础信息*/
    public String name, version, author, description, minVersion;

    public FileMod(FilePath mod){
        this.mod = mod;
        this.setProperties();
    }

    public FileMod(File file){
        this.mod = new FilePath(file);
        this.setProperties();
    }

    public FileMod(String path){
        this.mod = new FilePath(path);
        this.setProperties();
    }

//    protected void setInformation(){
//        URLClassLoader classMod = this.loadClass();
//        URL inputStream = classMod.getResource("mod.properties");
//        System.out.println(inputStream);
//    }

    public FilePath getFile(){
        return this.mod;
    }

    public String getMain(){
        return this.className;
    }

    public Properties loadProperties(){
        try(URLClassLoader ClassLoader = new URLClassLoader(new URL[] { this.mod.fileURL }, Thread.currentThread()
                .getContextClassLoader())) {
            InputStream inputStream = ClassLoader.getResourceAsStream("mod.properties");
            Properties properties = new Properties();
            properties.load(inputStream);
            return properties;
        } catch (IOException e){
            throw new ModRuntimeException("模组加载配置文件时出错", e);
        }
    }

    public final void setProperties(){
        Properties modData = this.loadProperties();
        this.name = modData.getProperty("name");
        this.version = modData.getProperty("version");
        this.author = modData.getProperty("author");
        this.description = modData.getProperty("description");
        this.minVersion = modData.getProperty("minVersion");

        this.className = modData.getProperty("main");

        setting.isNull(this.className, ()->{
            throw new ModRuntimeException("模组类加载不能为null!");
        });

        setting.isNull(this.name, ()->{
            throw new ModRuntimeException("基础信息不能为null!");
        });
        setting.isNull(this.version, ()->{
            throw new ModRuntimeException("基础信息不能为null!");
        });
        setting.isNull(this.author, ()->{
            throw new ModRuntimeException("基础信息不能为null!");
        });
        setting.isNull(this.description, ()-> this.description = "");

        setting.isNull(this.minVersion, ()->{
            //throw new ModRuntimeException("基础信息不能为null!");
            //这个确实没有用
        });
    }
}
