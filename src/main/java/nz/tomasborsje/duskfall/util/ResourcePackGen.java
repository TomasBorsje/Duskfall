package nz.tomasborsje.duskfall.util;

import nz.tomasborsje.duskfall.Duskfall;
import nz.tomasborsje.duskfall.definitions.ItemDefinition;
import nz.tomasborsje.duskfall.registries.ItemRegistry;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ResourcePackGen {
    public static void GenerateResourcePack() {
        Duskfall.logger.info("Generating resource pack...");

        // Get and create the required folders
        File outputFolder = Paths.get("target", "DuskfallResourcePack").toFile();
        File mcOverrideModels = new File(outputFolder, "assets/minecraft/models/item");
        File duskfallModels = new File(outputFolder, "assets/duskfall/models/item");
        File duskfallTextures = new File(outputFolder, "assets/duskfall/textures/item");
        outputFolder.mkdirs();
        mcOverrideModels.mkdirs();
        duskfallModels.mkdirs();
        duskfallTextures.mkdirs();

        HashMap<String, Set<ItemDefinition>> itemsByMaterial = new HashMap<String, Set<ItemDefinition>>();

        // Get all items and sort them into a dictionary of sets based on their material
        for (ItemDefinition item : ItemRegistry.GetAllItems()) {
            // First, check if a texture override exists in textures/id.png
            File textureFile = Paths.get("textures", item.id.toLowerCase() + ".png").toFile();
            if (!textureFile.exists()) {
                continue;
            }
            // Texture file exists, so track the item and also copy over the texture file
            try {
                Files.copy(textureFile.toPath(), new File(duskfallTextures, item.id.toLowerCase() + ".png").toPath(), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            if (!itemsByMaterial.containsKey(item.material)) {
                itemsByMaterial.put(item.material, new HashSet<ItemDefinition>());
            }
            itemsByMaterial.get(item.material).add(item);
        }

        // For each material, create a json model file that overrides the default minecraft item model using custom model predicates
        for (String material : itemsByMaterial.keySet()) {
            // Get the set of items that override this material
            Set<ItemDefinition> items = itemsByMaterial.get(material);

            // For all items in this set, create a generic item model stored in duskfall:models/item/id.json
            for (ItemDefinition item : items) {
                // Create the model json
                StringBuilder modelJson = new StringBuilder("{\n" +
                        "  \"parent\": \"item/generated\",\n" +
                        "  \"textures\": {\n" +
                        "    \"layer0\": \"duskfall:item/" + item.id.toLowerCase() + "\"\n" +
                        "  }\n" +
                        "}");

                // Write the model json to a file
                try {
                    File modelFile = new File(outputFolder, "assets/duskfall/models/item/" + item.id.toLowerCase() + ".json");
                    modelFile.createNewFile();
                    FileWriter writer = new FileWriter(modelFile);
                    writer.write(modelJson.toString());
                    writer.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            // Create the model json
            StringBuilder modelJson = new StringBuilder("{\n" +
                    "  \"parent\": \"item/generated\",\n" +
                    "  \"textures\": {\n" +
                    "    \"layer0\": \"item/" + material.toLowerCase() + "\"\n" +
                    "  },\n" +
                    "  \"overrides\": [\n");
            int i = 0;
            for (ItemDefinition item : items) {
                // Model overrides
                modelJson.append("    {\"predicate\": {\"custom_model_data\": ").append((int) ((float) (item.id.toLowerCase().hashCode() % 1_000_000))).append("}, \"model\": \"duskfall:item/").append(item.id.toLowerCase()).append("\"}");
                if (i < items.size() - 1) {
                    modelJson.append(",");
                }
                modelJson.append("\n");
                i++;
            }
            modelJson.append("  ]\n" + "}");

            // Write the model override json to a file
            try {
                File modelFile = new File(outputFolder, "assets/minecraft/models/item/" + material.toLowerCase() + ".json");
                modelFile.createNewFile();
                FileWriter writer = new FileWriter(modelFile);
                writer.write(modelJson.toString());
                writer.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Log
            Duskfall.logger.info("Generated model for " + material + " with " + items.size() + " custom model overrides.");
        }

        // Add pack.mcmeta to the output folder
        try {
            File packMcmeta = new File(outputFolder, "pack.mcmeta");
            packMcmeta.createNewFile();
            FileWriter writer = new FileWriter(packMcmeta);
            writer.write("{\n" +
                    "  \"pack\": {\n" +
                    "    \"pack_format\": 22,\n" +
                    "    \"description\": \"Duskfall Resource Pack Auto-Generated\"\n" +
                    "  }\n" +
                    "}");
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Finally, zip up the assets folder to make the zip file
        try {
            File resourcePackZip = Paths.get("target", "DuskfallResourcePack.zip").toFile();
            FileOutputStream fos = new FileOutputStream(resourcePackZip);
            ZipOutputStream zipOut = new ZipOutputStream(fos);

            //File assetsFolder = new File(outputFolder, "assets");
            zipFile(outputFolder, outputFolder.getName(), zipOut, true);
            zipOut.close();
            fos.close();
            Duskfall.logger.info("Zip file has been created at " + resourcePackZip.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static void zipFile(File fileToZip, String fileName, ZipOutputStream zipOut, boolean root) throws IOException {
        if (fileToZip.isHidden()) {
            return;
        }
        if (fileToZip.isDirectory()) {
            File[] children = fileToZip.listFiles();
            for (File childFile : children) {
                String childFileName = root ? childFile.getName() : fileName + "/" + childFile.getName();
                zipFile(childFile, childFileName, zipOut, false);
            }
            return;
        }
        FileInputStream fis = new FileInputStream(fileToZip);
        ZipEntry zipEntry = new ZipEntry(fileName);
        zipOut.putNextEntry(zipEntry);
        byte[] bytes = new byte[1024];
        int length;
        while ((length = fis.read(bytes)) >= 0) {
            zipOut.write(bytes, 0, length);
        }
        fis.close();
    }
}
