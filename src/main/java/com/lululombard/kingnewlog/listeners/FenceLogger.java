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

@SuppressWarnings("deprecation")
public class FenceLogger implements Listener {
	
	Material[] newfence = {
		Material.SPRUCE_FENCE_GATE,
		Material.BIRCH_FENCE_GATE,
		Material.JUNGLE_FENCE_GATE,
		Material.ACACIA_FENCE_GATE,
		Material.DARK_OAK_FENCE_GATE
	};

	private Consumer lbconsumer = null;
	
	public FenceLogger(Consumer lbconsumer) {
		this.lbconsumer = lbconsumer;
	}
	
	@EventHandler (priority = EventPriority.MONITOR)
	public void onRightClick(final PlayerInteractEvent e) {
		if (!e.isCancelled() && e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			if (e.getClickedBlock() == null || e.getClickedBlock().getType() == null || e.getClickedBlock().getType().equals(Material.AIR)) return;
			Player p = e.getPlayer();
			Location loc = e.getClickedBlock().getLocation();
			byte data = e.getClickedBlock().getData();
			if (Arrays.asList(newfence).contains(e.getClickedBlock().getType())) lbconsumer.queueBlock(new Actor(p.getName(), p.getUniqueId()), loc, 107, 107, data);
		}
	}
}
