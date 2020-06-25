package com.scotsguy.flycreeperfly.flycreeperfly;

import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;


public class FlyCreeperFly implements ModInitializer {
    @Override
    public void onInitialize() {
        LogManager.getLogger().warn("WARNING: Fly Creeper Fly is installed, \033[1;5;4mCreepers are airborne!\033[0m");
    }
}
