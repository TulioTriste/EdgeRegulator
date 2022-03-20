package me.tulio.edgeregulator.utilities;

import lombok.experimental.UtilityClass;
import me.tulio.edgeregulator.Main;
import me.tulio.edgeregulator.entry.Data;
import org.bukkit.Bukkit;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.concurrent.atomic.AtomicBoolean;

@UtilityClass
public class Util {

    public void consoleLogger(String message) {
        Bukkit.getConsoleSender().sendMessage(CC.translate(message));
    }

    public String getRegionList(Data data) {
        for (String s : Main.get().getMainConfig().getStringList("ZONAS_FILOS")) {
            if (s.startsWith(data.getRegion())) {
                return data.getRegion();
            }
        }
        return null;
    }

    public boolean canEventWithEdge(Player player, Data data) {
        for (String s : Main.get().getMainConfig().getStringList("ZONAS_FILOS")) {
            if (s.startsWith(data.getRegion())) {
                int edge = Integer.parseInt(s.split(":")[1]);
                for (ItemStack content : player.getInventory().getContents()) {
                    if (content != null && content.getType().name().contains("_SWORD")) {
                        if (content.getItemMeta() != null && content.getItemMeta().hasEnchant(Enchantment.DAMAGE_ALL)) {
                            AtomicBoolean canJoin = new AtomicBoolean(false);
                            content.getItemMeta().getEnchants().forEach((enchantment, integer) -> {
                                if (enchantment.equals(Enchantment.DAMAGE_ALL) && integer >= edge) {
                                    canJoin.set(true);
                                }
                            });
                            return canJoin.get();
                        }
                    }
                }
            }
        }
        return false;
    }

    public int getEdge(Data data) {
        for (String s : Main.get().getMainConfig().getStringList("ZONAS_FILOS")) {
            if (s.startsWith(data.getRegion())) {
                return Integer.parseInt(s.split(":")[1]);
            }
        }
        return 0;
    }
}
