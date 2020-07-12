package marioandweegee3.resourceloader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class ResourceLoader implements ClientModInitializer {
    private static final Logger logger = LogManager.getLogger("resourceloader");

    @Override
    public void onInitializeClient() {
        log("Resource Loader is installed.");
    }

    public static void log(Object message){
        logger.info("[Resource Loader]: "+message);
    }
}