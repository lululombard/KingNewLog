package com.lululombard.kingnewlog.listeners;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.hanging.HangingBreakEvent;
import org.bukkit.event.hanging.HangingPlaceEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import de.diddiz.LogBlock.Actor;
import de.diddiz.LogBlock.Consumer;

@SuppressWarnings("deprecation")
public class ItemFrameLogger implements Listener {
	
	private Consumer lbconsumer = null;
	
	public ItemFrameLogger(Consumer lbconsumer) {
		this.lbconsumer = lbconsumer;
	}
	
	@EventHandler (priority = EventPriority.MONITOR)
	public void onItemFramePlace(final HangingPlaceEvent e) {
		if (!e.isCancelled()) {
			lbconsumer.queueBlock(new Actor(e.getPlayer().getName(), e.getPlayer().getUniqueId()), e.getEntity().getLocation(), 0, getMaterialId(e.getEntity().getType()), (byte) 0);
		}
	}
	
	@EventHandler (priority = EventPriority.MONITOR)
	public void onItemFrameBreak(final HangingBreakEvent e) {
		if (!e.isCancelled()) {
			if (e instanceof HangingBreakByEntityEvent) {
				final HangingBreakByEntityEvent evt = (HangingBreakByEntityEvent) e;
				if (evt.getRemover() instanceof Player)	{
					Player player = ((Player) evt.getRemover());
					lbconsumer.queueBlock(new Actor(player.getName(), player.getUniqueId()), e.getEntity().getLocation(), getMaterialId(e.getEntity().getType()), 0, (byte) 0);
				}
			}
		}
	}
	
	@EventHandler (priority = EventPriority.MONITOR)
	public void onItemFrameRemove(final EntityDamageByEntityEvent e) {
		if (!e.isCancelled() && e.getEntityType().equals(EntityType.ITEM_FRAME)) {
			if (e.getCause().equals(DamageCause.ENTITY_ATTACK) && e.getDamager() instanceof Player) {
				Player player = ((Player) e.getDamager());
				ItemFrame iframe = (ItemFrame) e.getEntity();
				lbconsumer.queueChestAccess(new Actor(player.getName(), player.getUniqueId()), e.getEntity().getLocation(), getMaterialId(e.getEntityType()), (short)iframe.getItem().getTypeId(), (short)-1, (byte) iframe.getItem().getDurability());
			}
		}
	}
	
	@EventHandler (priority = EventPriority.MONITOR)
	public void onItemFrameUse(final PlayerInteractEntityEvent e) {
		if (!e.isCancelled() && e.getRightClicked().getType().equals(EntityType.ITEM_FRAME)) {
			ItemFrame iframe = (ItemFrame) e.getRightClicked();
			if (!iframe.isEmpty() || e.getPlayer().getItemInHand() == null || e.getPlayer().getItemInHand().getType() == null || e.getPlayer().getItemInHand().getType().equals(Material.AIR)) return;
			lbconsumer.queueChestAccess(new Actor(e.getPlayer().getName(), e.getPlayer().getUniqueId()), e.getRightClicked().getLocation(), getMaterialId(e.getRightClicked().getType()), (short)e.getPlayer().getItemInHand().getTypeId(), (short)1, (byte) e.getPlayer().getItemInHand().getDurability());
		}
	}
	
	public short getMaterialId(EntityType entype) {
		if (entype.equals(EntityType.ITEM_FRAME)) return (short) Material.ENDER_PORTAL_FRAME.getId();
		else if (entype.equals(EntityType.PAINTING)) return (short) Material.WALL_SIGN.getId();
		else return entype.getTypeId();
	}
}
