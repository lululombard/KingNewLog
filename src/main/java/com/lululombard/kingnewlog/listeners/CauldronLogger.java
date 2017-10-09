package com.lululombard.kingnewlog.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.material.Cauldron;

import de.diddiz.LogBlock.Actor;
import de.diddiz.LogBlock.Consumer;

@SuppressWarnings("deprecation")
public class CauldronLogger implements Listener {
	
	private Consumer lbconsumer;

	public CauldronLogger(Consumer lbconsumer) {
		this.lbconsumer = lbconsumer;
	}
	
	@EventHandler (priority = EventPriority.MONITOR)
	public void onRightClick(final PlayerInteractEvent e) {
		if (!e.isCancelled() && e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			if (e.getClickedBlock() == null || e.getClickedBlock().getType() == null || e.getClickedBlock().getType().equals(Material.AIR)) return;
			Player p = e.getPlayer();
			Location loc = e.getClickedBlock().getLocation();
			ItemStack item = e.getPlayer().getItemInHand();
			short id = (short)Material.WATER.getId();
			byte data = 0;
			if (e.getClickedBlock().getType().equals(Material.CAULDRON)) {
				Cauldron caul = (Cauldron) e.getClickedBlock().getState().getData();
				if (caul.isEmpty() && item != null && item.getType() != null && item.getType().equals(Material.WATER_BUCKET)) lbconsumer.queueChestAccess(new Actor(p.getName(), p.getUniqueId()), loc, e.getClickedBlock().getType().getId(), id, (short)4, data);
				else if (item.getType() != null && (item.getType().equals(Material.GLASS_BOTTLE) || (item.hasItemMeta() && item.getItemMeta() instanceof LeatherArmorMeta))) {
					if (item.getItemMeta() instanceof LeatherArmorMeta && ((LeatherArmorMeta)item.getItemMeta()).getColor().equals(Bukkit.getItemFactory().getDefaultLeatherColor())) return;
					lbconsumer.queueChestAccess(new Actor(p.getName(), p.getUniqueId()), loc, e.getClickedBlock().getType().getId(), id, (short)-1, data);
				}
			}
		}
	}
}