package server.terminal.mod;

import server.terminal.ChireCore;
import server.terminal.io.FileMod;
import server.terminal.io.FilePath;
import server.terminal.util.exception.ModRuntimeException;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

public class Mods {
    public List<FileMod> anyMod;

    public Mods(){
        List<FilePath> modPath = ChireCore.setting.getModDirectory().getFilePaths(".jar");
        this.anyMod = new ArrayList<>();

        for (var mp : modPath){
            this.anyMod.add(new FileMod(mp));
        }
    }

    /**获取所有的模块(返回待定)*/
    public List<FileMod> getMods(){
        return this.anyMod;
    }

    /**初始化所有模组*/
    public void initMods(){
        for (var am : this.anyMod){
            this.getMod(am).init();
        }
    }

    protected Class<?> loadClassLoader(FilePath path, String name) {
        try(URLClassLoader ClassLoader = new URLClassLoader(new URL[] { path.fileURL }, Thread.currentThread()
                .getContextClassLoader())) {
            return ClassLoader.loadClass(name);
        } catch (IOException | ClassNotFoundException e){
            throw new ModRuntimeException("模组加载包时出错!!!", e);
        }
    }

    protected Mod getMod(FileMod mod){
        try {
            return (Mod) loadClassLoader(mod.getFile(), mod.getMain()).getDeclaredConstructor().newInstance();
        } catch (InvocationTargetException | InstantiationException |
                 NoSuchMethodException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public String getReturn(FileMod fmod) {
        //Class<?> myClass1 = myClassLoader1.loadClass("server.mod.ChireCore");
        Mod action1 = this.getMod(fmod);

        return action1.modReturn();
    }
}
