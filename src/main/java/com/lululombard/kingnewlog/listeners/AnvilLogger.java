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
public class AnvilLogger implements Listener {
	
	private Consumer lbconsumer;

	public AnvilLogger(Consumer lbconsumer) {
		this.lbconsumer = lbconsumer;
	}
	
	@EventHandler (priority = EventPriority.MONITOR)
	public void onRightClick(final PlayerInteractEvent e) {
		if (!e.isCancelled() && e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			if (e.getClickedBlock() == null || e.getClickedBlock().getType() == null || e.getClickedBlock().getType().equals(Material.AIR)) return;
			Player p = e.getPlayer();
			Location loc = e.getClickedBlock().getLocation();
			Material clicked = e.getClickedBlock().getType();
			short id = (short)clicked.getId();
			if (clicked.equals(Material.ANVIL)) {
				lbconsumer.queueChestAccess(new Actor(p.getName(), p.getUniqueId()), loc, id, (short)Material.IRON_INGOT.getId(), (short)-1, (byte)0);
			}
		}
	}
}