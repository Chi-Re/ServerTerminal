package server.terminal;

import server.terminal.core.ContentLoader;
import server.terminal.core.Log;
import server.terminal.core.Setting;
import server.terminal.http.HttpApplication;
import server.terminal.http.website.content.SeverWebs;
import server.terminal.mod.Mods;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ChireCore {
    public static ContentLoader content;

    public static Mods mods;

    public static Setting setting;

    public static Log log;

    public static HttpApplication http;

    public static void main(String[] args) throws InterruptedException {
        new ChireCore();
        log.info("初始化完成，开始运行主循环线程");
        while (true){
            Thread.sleep(1000);
            for (var hl : content.getHtmlDesign()){
                http.addHtml(hl.getUrl(), hl.getContent());
            }
        }
    }

    public ChireCore() {
        this.initStockpile();
        //log.info("基础组件加载完成，开始初始化");内容
        this.init();
        this.initContent();
    }

    public void initStockpile(){
        //基础加载
        setting = new Setting();
        log = new Log();

        //组件加载
        content = new ContentLoader();
        mods = new Mods();

        log.initLog();
        mods.initMods();

        //TODO 为了方便模组更改，不过这样真的没问题吗
        http = new HttpApplication(setting.getPort());
    }

    public void init() {
        //System.out.println(mods.getReturn(new FileMod("F:\\TerminalMod\\build\\libs\\TerminalMod.jar")));

        //http.addHtml(new FilePath("F:\\CrucialCentre\\assets\\templates\\index.html"));
        //MapList<Object> test = new MapList<>();
        //test.put("测试", "更改");
        //test.put("太长了", "泰库辣");
        //System.out.println(StringUtil.findReplace("这是{{测试}}用的，我就不写{{太长了}}，下面是测试用的", test));

//        try(URLClassLoader ClassLoader = new URLClassLoader(new URL[] { new FilePath("F:\\TerminalMod\\build\\libs\\TerminalMod.jar").fileURL}, Thread.currentThread()
//                .getContextClassLoader())) {
//            Method[] methods = ClassLoader.getClass().getDeclaredMethods();
//            if (methods.length > 0) {
//                for (Method method : methods) {
//                    ModCore peopleAnnotion = method.getAnnotation(ModCore.class);
//                    if (peopleAnnotion != null) {
//                        //可以做权限验证
//                        System.out.println("?");
//                    }
//                }
//            }
//        } catch (IOException e){
//            throw new ModRuntimeException("出错", e);
//        }

        try {
            http.addData("/chire.png", new FileInputStream("F:\\ServerTerminal\\build\\libs\\crashes\\chire.png"));
        } catch (FileNotFoundException f){

        }

    }

    public void initContent(){
        SeverWebs.load();

        System.out.println(content.getHtmlDesign());
    }
}
