package server.terminal.type;

import java.util.List;

/**TODO 历史遗留问题*/
public class ObjectType<T> {
    public int length;

    public List<T> list;

    @SafeVarargs
    public ObjectType(T... data){
        this.length = data.length;
        this.list = List.of(data);
    }
    
    public T get(int key){
        return this.list.get(key);
    }
}
