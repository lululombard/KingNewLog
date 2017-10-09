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
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.FlowerPot;

import de.diddiz.LogBlock.Actor;
import de.diddiz.LogBlock.Consumer;

@SuppressWarnings("deprecation")
public class FlowerPotLogger implements Listener {
	
	Material[] flowers = {
		Material.getMaterial(37), 
		Material.getMaterial(38), 
		Material.getMaterial(175),
		Material.BROWN_MUSHROOM,
		Material.RED_MUSHROOM,
		Material.CACTUS,
		Material.SAPLING,
		Material.LONG_GRASS,
		Material.DEAD_BUSH
	};
	
	private Consumer lbconsumer;

	public FlowerPotLogger(Consumer lbconsumer) {
		this.lbconsumer = lbconsumer;
	}
	
	@EventHandler (priority = EventPriority.MONITOR)
	public void onRightClick(final PlayerInteractEvent e) {
		if (!e.isCancelled() && e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			if (e.getClickedBlock() == null || e.getClickedBlock().getType() == null || e.getClickedBlock().getType().equals(Material.AIR)) return;
			Player p = e.getPlayer();
			Location loc = e.getClickedBlock().getLocation();
			ItemStack item = e.getPlayer().getItemInHand();
			short id = (short)item.getTypeId();
			byte data = (byte)item.getDurability();
			if (e.getClickedBlock().getType().equals(Material.FLOWER_POT)) {
				FlowerPot fpot = (FlowerPot)e.getClickedBlock().getState().getData();
				if (fpot.getContents() == null && item != null && item.getType() != null && Arrays.asList(flowers).contains(item.getType())) {
					lbconsumer.queueChestAccess(new Actor(p.getName(), p.getUniqueId()), loc, e.getClickedBlock().getType().getId(), id, (short)1, data);
				}
				else {
					lbconsumer.queueChestAccess(new Actor(p.getName(), p.getUniqueId()), loc, e.getClickedBlock().getType().getId(), (short) Material.FLOWER_POT_ITEM.getId(), (short)-1, (short) 0);
				}
			} 
		}
	}
}