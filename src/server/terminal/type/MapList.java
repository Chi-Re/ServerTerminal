package server.terminal.type;

import server.terminal.io.FileMod;

import java.util.HashMap;
import java.util.Map;

public class MapList<T> {
    public Map<String, T> list;

    public MapList(){
        this.list = new HashMap<>();
    }

    /**TODO 有些不太稳定*/
    public void put(ObjectType<String> so, ObjectType<T> to){
        for(int i = 0; i < so.length; i++){
            this.list.put(so.get(i), to.get(i));
        }
    }

    public void put(String key, T value){
        this.list.put(key, value);
    }

    public T get(String key){
        return this.list.get(key);
    }
}
