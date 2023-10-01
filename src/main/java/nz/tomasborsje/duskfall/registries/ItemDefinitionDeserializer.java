package nz.tomasborsje.duskfall.registries;

import com.google.gson.*;

import java.lang.reflect.Type;

public class ItemDefinitionDeserializer implements JsonDeserializer<ItemDefinition> {
    @Override
    public ItemDefinition deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();

        // If a customClass field exists, instantiate that by name instead
        if (jsonObject.has("customClass")) {
            String customClassName = jsonObject.get("customClass").getAsString();
            try {
                Class<?> customClass = Class.forName(customClassName);
                return context.deserialize(json, customClass);
            } catch (ClassNotFoundException e) {
                throw new JsonParseException("Unknown custom class: " + customClassName);
            }
        }

        // Otherwise, parse generic item definitions
        String itemType = jsonObject.get("type").getAsString();
        return switch (itemType) {
            case "item" -> {
                // Deserialize manually as base case
                ItemDefinition itemDefinition = new ItemDefinition();
                itemDefinition.id = jsonObject.get("id").getAsString();
                itemDefinition.name = jsonObject.get("name").getAsString();
                yield itemDefinition;
            }
            case "armor" -> context.deserialize(json, ArmourItemDefinition.class);
            case "weapon" -> context.deserialize(json, WeaponItemDefinition.class);
            default -> throw new JsonParseException("Unknown item type: " + itemType);
        };
    }
}