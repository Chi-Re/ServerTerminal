package server.terminal;

import server.terminal.core.Log;
import server.terminal.core.Setting;
import server.terminal.http.HttpApplication;
import server.terminal.mod.Mods;
import server.terminal.type.MapList;
import server.terminal.util.StringUtil;

public class ChireCore {
    public static Mods mods;

    public static Setting setting;

    public static Log log;

    public static HttpApplication http;

    public static void main(String[] args) {
        new ChireCore();
    }

    public ChireCore() {
        this.initStockpile();
        //log.info("基础组件加载完成，开始初始化");内容
        this.init();
    }

    public void initStockpile(){
        //基础加载
        setting = new Setting();
        log = new Log();

        //组件加载
        mods = new Mods();

        log.initLog();
        mods.initMods();

        //TODO 为了方便模组更改，不过这样真的没问题吗
        http = new HttpApplication(setting.getPort());
    }

    public void init() {
        //System.out.println(mods.getReturn(new FileMod("F:\\TerminalMod\\build\\libs\\TerminalMod.jar")));

        //http.addHtml(new FilePath("F:\\CrucialCentre\\assets\\templates\\index.html"));
        MapList<Object> test = new MapList<>();
        test.put("测试", "更改");
        test.put("太长了", "泰库辣");
        System.out.println(StringUtil.findReplace("这是{{测试}}用的，我就不写{{太长了}}，下面是测试用的", test));
    }
}
