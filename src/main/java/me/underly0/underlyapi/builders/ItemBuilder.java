package me.underly0.underlyapi.builders;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import me.underly0.underlyapi.api.replace.ListReplace;
import me.underly0.underlyapi.utils.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@NoArgsConstructor
public class ItemBuilder extends ItemStack {
    private ListReplace replacer;

    private ConfigurationSection section;

    public ItemBuilder(ConfigurationSection section) {
        this.section = section;
    }

    public ItemBuilder(ItemStack item) {
        this.setItemMeta(item.getItemMeta());
    }

    public ItemBuilder(ConfigurationSection section, ItemStack item) {
        this.section = section;
        this.setItemMeta(item.getItemMeta());
    }

    public ItemBuilder addReplacer(ListReplace replacer) {
        this.replacer = replacer;
        return this;
    }

    @SneakyThrows
    public void skull(String str) {
        super.setType(Material.SKULL_ITEM);
        super.setDurability((short) 3);
        SkullMeta meta = (SkullMeta) this.getItemMeta();

        GameProfile profile = new GameProfile(UUID.randomUUID(), "");
        profile.getProperties().put("textures", new Property("textures", str));

        Field profileField = meta.getClass().getDeclaredField("profile");
        profileField.setAccessible(true);
        profileField.set(meta, profile);

        super.setItemMeta(meta);
    }


    public void setMaterial(String[] material) {
        if (StringUtils.isNumeric(material[0]))
            super.setType(Material.getMaterial(Integer.parseInt(material[0])));

        else if (material[0].length() > 30)
            skull(material[0]);

        else
            super.setType(Material.valueOf(material[0].toUpperCase()));

        if (material.length == 2) {
            super.setDurability(Short.parseShort(material[1]));
        }
    }

    public void setName(String name) {
        ItemMeta meta = super.getItemMeta();

        if (replacer != null) {
            List<String> lines = new ArrayList<>();
            lines.add(name);
            replacer.replace(lines);
            name = lines.get(0);
        }

        meta.setDisplayName(StringUtil.color(name));
        super.setItemMeta(meta);

    }

    public void setLore(List<String> lore) {
        ItemMeta meta = super.getItemMeta();

        replacer.replace(lore);
        meta.setLore(StringUtil.mapList(lore, StringUtil::color));
        super.setItemMeta(meta);
    }

    public void setEnchants(List<String> enchants) {
        ItemMeta meta = super.getItemMeta();
        enchants.forEach(enchant -> {
            String[] args = enchant.toUpperCase().split(":");
            meta.addEnchant(Enchantment.getByName(args[0]), Integer.parseInt(args[1]), true);
        });
        super.setItemMeta(meta);
    }

    public void setPotionColor(String color) {
        ItemMeta meta = super.getItemMeta();
        PotionMeta potionMeta = (PotionMeta) meta;
        java.awt.Color hex = java.awt.Color.decode(color);
        potionMeta.setColor(Color.fromRGB(hex.getRed(), hex.getGreen(), hex.getBlue()));
        super.setItemMeta(meta);
    }

    public void setGlowing() {
        ItemMeta meta = super.getItemMeta();
        meta.addEnchant(Enchantment.MENDING, 0, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        super.setItemMeta(meta);
    }

    public void setHideEnchants() {
        ItemMeta meta = getItemMeta();
        super.getItemMeta().addItemFlags(ItemFlag.HIDE_ENCHANTS);
        super.setItemMeta(meta);
    }

    public void setHideAttributes() {
        ItemMeta meta = getItemMeta();
        super.getItemMeta().addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        super.setItemMeta(meta);
    }

    public void setHidePotionEffects() {
        ItemMeta meta = getItemMeta();
        super.getItemMeta().addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        super.setItemMeta(meta);
    }

    public ItemBuilder build() {
        if (getType() == Material.AIR || getType() == null) {
            super.setType(Material.STONE);
        }

        section.getKeys(false)
                .forEach(key -> {
                    switch (key.toLowerCase()) {
                        case "material": {
                            setMaterial(section.getString(key).split(";"));
                            return;
                        }
                        case "name": {
                            setName(section.getString(key));
                            return;
                        }
                        case "lore": {
                            setLore(section.getStringList(key));
                            return;
                        }
                        case "enchants": {
                            setEnchants(section.getStringList(key));
                            return;
                        }
                        case "potion_color": {
                            setPotionColor(section.getString(key));
                            return;
                        }
                        case "glowing": {
                            if (!section.getBoolean(key))
                                return;
                            setGlowing();
                            return;
                        }
                        case "hide_enchants": {
                            if (!section.getBoolean(key))
                                return;
                            setHideEnchants();
                            return;
                        }
                        case "hide_attributes": {
                            if (!section.getBoolean(key))
                                return;
                            setHideAttributes();
                            return;
                        }
                        case "hide_potion_effects": {
                            if (!section.getBoolean(key))
                                return;
                            setHidePotionEffects();
                            return;
                        }
                        case "amount": {
                            setAmount(section.getInt(key));
                            return;
                        }
                    }
                });


        if (super.getAmount() == 0)
            super.setAmount(1);

        return this;

    }
}
