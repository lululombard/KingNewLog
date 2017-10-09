package com.lululombard.kingnewlog.listeners;

import java.util.Arrays;

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

public class DoorLogger implements Listener {
	
	Material[] newdoors = {
		Material.SPRUCE_DOOR,
		Material.BIRCH_DOOR,
		Material.JUNGLE_DOOR,
		Material.ACACIA_DOOR,
		Material.DARK_OAK_DOOR
	};
	
	private Consumer lbconsumer;
	
	public DoorLogger(Consumer lbconsumer) {
		this.lbconsumer = lbconsumer;
	}

	@EventHandler (priority = EventPriority.MONITOR)
	public void onRightClick(final PlayerInteractEvent e) {
		if (!e.isCancelled() && e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			if (e.getClickedBlock() == null || e.getClickedBlock().getType() == null || e.getClickedBlock().getType().equals(Material.AIR)) return;
			Player p = e.getPlayer();
			Location loc = e.getClickedBlock().getLocation();
			if (Arrays.asList(newdoors).contains(e.getClickedBlock().getType())) lbconsumer.queueBlock(new Actor(p.getName(), p.getUniqueId()), loc, 64, 64, (byte) 8);
		}
	}
}
