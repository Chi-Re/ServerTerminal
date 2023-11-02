package server.terminal.mod;

public abstract class Mod {
    /** Called after all plugins have been created and commands have been registered.*/
    public void init(){

    }

    /** Called on clientside mods. Load content here. */
    public void loadContent(){

    }

    public String modReturn(){
        return "NO";
    }
}
