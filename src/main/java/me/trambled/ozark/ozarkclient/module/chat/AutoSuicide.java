package me.trambled.ozark.ozarkclient.module.chat;

import me.trambled.ozark.ozarkclient.module.Category;
import me.trambled.ozark.ozarkclient.module.Module;

public class AutoSuicide extends Module {

    public AutoSuicide() {
        super(Category.CHAT);
      
        this.name = "Auto Suicide";
        this.tag = "AutoSuicide";
        this.description = "allahu akbar";
      
    }
  
    public void onEnable() {
        mc.player.sendChatMessage("/kill");
        this.toggle();
    }
}

