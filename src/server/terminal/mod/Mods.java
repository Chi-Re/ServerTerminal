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

    public String getReturn(URL fileURL) {
        try(URLClassLoader myClassLoader1 = new URLClassLoader(new URL[] { fileURL }, Thread.currentThread()
                .getContextClassLoader())) {
            Class<?> myClass1 = myClassLoader1.loadClass("server.mod.ChireCore");
            Mod action1 = (Mod) myClass1.getDeclaredConstructor().newInstance();

            return action1.modReturn();
        } catch (IOException | NoSuchMethodException | ClassNotFoundException | InvocationTargetException |
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
