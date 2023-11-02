package server.terminal;

import server.terminal.mod.Mods;

public class ChireCore {
    public static String author = "炽热S";

    public static String projectName = "ServerTerminal";

    public static Mods mods;

    public static void main(String[] args) {
        System.out.println("你好");
        new ChireCore();
    }

    public ChireCore() {
        this.init();
    }

    public void init() {
        mods = new Mods();

        System.out.println(mods.getReturn("F:\\TerminalMod\\build\\libs\\TerminalMod.jar"));
    }
}
