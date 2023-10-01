package nz.tomasborsje.duskfall.registries;

import com.google.gson.*;

import java.lang.reflect.Type;

/**
 * Custom deserializer for ItemDefinitions that allows us to use Gson to parse item schemas.
 */
public class ItemDefinitionDeserializer implements JsonDeserializer<ItemDefinition> {
    @Override
    public ItemDefinition deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();

        // If a customClass field exists, instantiate that by its fully qualified name instead
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
                itemDefinition.material = jsonObject.get("material").getAsString();
                yield itemDefinition;
            }
            case "armor" -> context.deserialize(json, ArmourDefinition.class);
            case "weapon" -> context.deserialize(json, MeleeWeaponDefinition.class);
            default -> throw new JsonParseException("Unknown item type: " + itemType);
        };
    }
}