package me.tulio.edgeregulator.listener;

import me.tulio.edgeregulator.Main;
import me.tulio.edgeregulator.entry.Data;
import me.tulio.edgeregulator.utilities.CC;
import me.tulio.edgeregulator.utilities.Util;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class EventsListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onMove(PlayerMoveEvent event) {
        // To optimize PlayerMoveEvent
		if (event.getFrom().getBlockX() == event.getTo().getBlockX() &&
				event.getFrom().getBlockZ() == event.getTo().getBlockZ() &&
				event.getFrom().getBlockY() == event.getTo().getBlockY()) {
			return;
		}

		Player player = event.getPlayer();
		for (Data data : Main.get().getData()) {
			if (Util.getRegionList(data) != null && data.getCuboid().contains(event.getTo()) && Util.canEventWithEdge(player, data)) {
				event.setCancelled(true);
				for (String s : Main.get().getMainConfig().getStringList("DONT_JOIN_MESSAGE")) {
					player.sendMessage(CC.translate(s
							.replace("%region%", data.getRegion())
							.replace("%filo%", String.valueOf(Util.getEdge(data)-1))));
				}
				break;
			}
		}
    }

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onEntityAttack(EntityDamageByEntityEvent event) {
		if (event.getDamager() instanceof Player && event.getEntity() instanceof Player) {
			Player damager = (Player) event.getDamager();
			Player entity = (Player) event.getEntity();

			for (Data data : Main.get().getData()) {
				if (Util.getRegionList(data) != null &&
						(data.getCuboid().contains(damager.getLocation()) || data.getCuboid().contains(entity.getLocation())) &&
						Util.canEventWithEdge(damager, data)) {
					event.setCancelled(true);
					for (String s : Main.get().getMainConfig().getStringList("DONT_DAMAGE_MESSAGE")) {
						damager.sendMessage(CC.translate(s
								.replace("%region%", data.getRegion())
								.replace("%filo%", String.valueOf(Util.getEdge(data)-1))));
					}
					damager.sendMessage("Evento cancelado papu :v EntityDamageEvent");
					break;
				}
			}
		}
	}
}
