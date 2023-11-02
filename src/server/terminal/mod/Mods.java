package server.terminal.mod;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class Mods {
    public Mods(){
    }

    /**获取所有的模块(返回待定)*/
    public String getMods(){
        return null;
    }

    /**初始化所有模组*/
    public void initMods(){

    }

    public Class<?> loadClassLoader(URL file, String name) {
        try(URLClassLoader ClassLoader = new URLClassLoader(new URL[] { file }, Thread.currentThread()
                .getContextClassLoader())) {
            return ClassLoader.loadClass(name);
        } catch (IOException | ClassNotFoundException e){
            throw new RuntimeException(e);
        }
    }

    public String getReturn(URL fileURL) {
        try {
            //Class<?> myClass1 = myClassLoader1.loadClass("server.mod.ChireCore");
            Mod action1 = (Mod) loadClassLoader(fileURL, "server.mod.ChireCore").getDeclaredConstructor().newInstance();

            return action1.modReturn();
        } catch (NoSuchMethodException | InvocationTargetException |
                 InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

    }

    public String getReturn(File file) {
        try {
            return getReturn(file.toURI().toURL());
        } catch (MalformedURLException m){
            throw new RuntimeException(m);
        }
    }

    public String getReturn(String path) {
        return getReturn(new File(path));
    }
}
