package com.lululombard.kingnewlog.listeners;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Jukebox;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import de.diddiz.LogBlock.Actor;
import de.diddiz.LogBlock.Consumer;

@SuppressWarnings("deprecation")
public class JukeboxLogger implements Listener {
	
	private Consumer lbconsumer;

	public JukeboxLogger(Consumer lbconsumer) {
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
			byte data = e.getClickedBlock().getData();
			if (e.getClickedBlock().getType().equals(Material.JUKEBOX)) {
				Jukebox jbox = (Jukebox)e.getClickedBlock().getState();
				Material playing = jbox.getPlaying();
				short playingid = (short)playing.getId();
				if (playing.equals(Material.AIR) && item != null && item.getType() != null && item.getType().isRecord()) lbconsumer.queueChestAccess(new Actor(p.getName(), p.getUniqueId()), loc, e.getClickedBlock().getType().getId(), id, (short)1, data);
				else if (!playing.equals(Material.AIR)) lbconsumer.queueChestAccess(new Actor(p.getName(), p.getUniqueId()), loc, e.getClickedBlock().getType().getId(), playingid, (short)-1, (byte)0);
			}
		}
	}
}
