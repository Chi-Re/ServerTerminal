package server.terminal.util.run;

import server.terminal.type.MapList;

public interface SeverRunnable extends Runnable {
    MapList<Object> map = new MapList<>();

    /**自定义接口来实现，*/
    default void run(){
        this.setVar("CHIRE的SeverRunnable使用介绍", "请使用自定义接口(new SeverRunnable() {@Override public void run() {}})并且调用setVar");
    };

    default MapList<Object> getReturn(){
        return this.map;
    }

    default void setVar(String key, Object value){
        this.map.put(key, value);
    }
}
