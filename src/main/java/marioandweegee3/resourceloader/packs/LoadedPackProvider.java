package marioandweegee3.resourceloader.packs;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.function.Consumer;

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
import net.minecraft.resource.ResourcePackSource;

@Environment(EnvType.CLIENT)
public class LoadedPackProvider implements ResourcePackProvider {
    private final File packFolder;

    public LoadedPackProvider(){
        packFolder = new File(FabricLoader.getInstance().getGameDir().toFile(), "resources");
    }

    @Override
    public void register(Consumer<ResourcePackProfile> consumer, Factory factory) {
        if (!this.packFolder.isDirectory()) {
            this.packFolder.mkdirs();

            File metaFile = new File(packFolder, "pack.mcmeta");
            JsonObject object = new JsonObject();
            JsonObject pack = new JsonObject();
            pack.addProperty("pack_format", 8);
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
        ResourcePackProfile container = ResourcePackProfile.of(name, true, () -> new DirectoryResourcePack(packFolder), factory, ResourcePackProfile.InsertionPosition.TOP, ResourcePackSource.PACK_SOURCE_BUILTIN);
        if (container != null) {
            consumer.accept(container);
        } else {
            ResourceLoader.log("Error loading resources");
        }

    }
}
