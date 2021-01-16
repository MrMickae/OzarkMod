package me.travis.wurstplus.wurstplustwo.hacks.misc;

import me.travis.wurstplus.WurstplusRPC;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;

public class RPCModule extends WurstplusHack
{
    public RPCModule() {
        super(WurstplusCategory.WURSTPLUS_MISC);
        this.name = "DiscordRPC";
        this.tag = "DiscordRPC";
        this.description = "show people how cool you are";
    }
    
    @Override
    protected void enable() {
        WurstplusRPC.init();
    }

    @Override
    protected void disable() {
        WurstplusRPC.stop();
    }	
	
}
