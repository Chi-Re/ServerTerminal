package server.terminal.util.run;

import server.terminal.type.MapList;

public interface SeverRunnable extends Runnable{
    MapList<Object> map = new MapList<>();

    void run();

    default MapList<Object> getReturn(){
        return this.map;
    }

    default void setVar(String key, Object value){
        this.map.put(key, value);
    }
}
