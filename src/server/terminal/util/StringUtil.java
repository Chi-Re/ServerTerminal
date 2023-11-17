package server.terminal.util;

import server.terminal.type.MapList;

import java.util.ArrayList;
import java.util.List;

public class StringUtil {
    /**分隔符*/
    public final static String regexKey = "<~>";

    public final static String patternKey = "\\{\\{"+ regexKey + "\\}\\}";

    /**你可以通过<~>来设置需要寻找的关键词<br>例如 as<~>as<br>他会寻找满足的字符，例如 asddas 会返回dd*/
    public static List<String> findAll(String pattern, String string){
        String[] lm = split(pattern);
        List<String> sr = new ArrayList<>();
        for (var m2 : string.split(lm[0])){
            if (m2.contains(harmless(lm[1]))){
                for (int i = 0; i < m2.split(lm[1]).length; i+=2) {
                    sr.add(m2.split(lm[1])[i]);
                }
            }
        }

        return sr;
    }

    public static List<String> findAll(String string){
        return findAll(patternKey, string);
    }
    
    public static String[] split(String regex){
        return regex.split(regexKey);
    }

    public static String harmless(String text){
        return text.replace("\\{", "{")
                .replace("\\}", "}")
                .replace("\\[", "[")
                .replace("\\]", "]");
    }

    public static String findReplace(String pattern, String string, MapList<Object> replaceM){
        List<String> match = findAll(pattern, string);
        for (String s : match) {
            string = string.replace("{{" + s + "}}", replaceM.get(s).toString());
        }
        return string;
    }

    public static String findReplace(String string, MapList<Object> replaceM){
        return findReplace(patternKey, string, replaceM);
    }
}
