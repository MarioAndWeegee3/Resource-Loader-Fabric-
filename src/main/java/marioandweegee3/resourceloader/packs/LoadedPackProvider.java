package marioandweegee3.resourceloader.packs;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import marioandweegee3.resourceloader.ResourceLoader;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.resource.DirectoryResourcePack;
import net.minecraft.resource.ResourcePackProfile;
import net.minecraft.resource.ResourcePackProfile.Factory;
import net.minecraft.resource.ResourcePackProvider;

@Environment(EnvType.CLIENT)
public class LoadedPackProvider implements ResourcePackProvider {
    private final File packFolder;
    
    public LoadedPackProvider(){
        packFolder = new File(FabricLoader.getInstance().getGameDirectory(), "resources");
    }

    @Override
    public <T extends ResourcePackProfile> void register(Map<String, T> registry, Factory<T> factory) {
        if (!this.packFolder.isDirectory()) {
            this.packFolder.mkdirs();
            
            File metaFile = new File(packFolder, "pack.mcmeta");
            JsonObject object = new JsonObject();
            JsonObject pack = new JsonObject();
            pack.addProperty("pack_format", 5);
            pack.addProperty("description", "Resource Loader pack");
            object.add("pack", pack);

            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            try {
                FileWriter metaWriter = new FileWriter(metaFile);
                metaWriter.write(gson.toJson(object));
                metaWriter.close();
            } catch (IOException e) {
                ResourceLoader.log(e);
            }
        }

        String name = "Resource Loader pack";
        T container = ResourcePackProfile.of(name, true, () -> new DirectoryResourcePack(packFolder), factory, ResourcePackProfile.InsertionPosition.TOP);
        if(container != null){
            registry.put(name, container);
        } else {
            ResourceLoader.log("Error loading resources");
        }
    }

}