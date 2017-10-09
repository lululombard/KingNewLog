package com.lululombard.kingnewlog.listeners;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import de.diddiz.LogBlock.Actor;
import de.diddiz.LogBlock.Consumer;

@SuppressWarnings("deprecation")
public class DaylightSensorLogger implements Listener {
	
	private Consumer lbconsumer;

	public DaylightSensorLogger(Consumer lbconsumer) {
		this.lbconsumer = lbconsumer;
	}
	
	@EventHandler (priority = EventPriority.MONITOR)
	public void onRightClick(final PlayerInteractEvent e) {
		if (!e.isCancelled() && e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			if (e.getClickedBlock() == null || e.getClickedBlock().getType() == null || e.getClickedBlock().getType().equals(Material.AIR)) return;
			Player p = e.getPlayer();
			Location loc = e.getClickedBlock().getLocation();
			Material clicked = e.getClickedBlock().getType();
			if (clicked.equals(Material.DAYLIGHT_DETECTOR) || clicked.equals(Material.DAYLIGHT_DETECTOR_INVERTED)) {
				Material after = Material.DAYLIGHT_DETECTOR_INVERTED;
				if (clicked.equals(Material.DAYLIGHT_DETECTOR_INVERTED)) after = Material.DAYLIGHT_DETECTOR;
				lbconsumer.queueBlock(new Actor(p.getName(), p.getUniqueId()), loc, clicked.getId(), after.getId(), (byte)0);
			}
		}
	}
}
