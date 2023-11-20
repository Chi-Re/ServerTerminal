package server.terminal.type;

import java.util.HashMap;
import java.util.Map;

public class MapList<T> {
    private Map<String, T> list;
    private String listClass;

    public MapList(){
        this.list = new HashMap<>();
        this.listClass = null;
    }

    /**TODO 有些不太稳定*/
    public void put(ObjectType<String> so, ObjectType<T> to){
        for(int i = 0; i < so.length; i++){
            this.list.put(so.get(i), to.get(i));
        }
    }

    public void put(String key, T value, String listClass){
        this.list.put(key, value);
        this.listClass = listClass;
    }

    public void put(String key, T value){
        this.put(key, value, null);
    }

    public T get(String key){
        return this.list.get(key);
    }

    public Map<String, T> getList(){
        return this.list;
    }
}
