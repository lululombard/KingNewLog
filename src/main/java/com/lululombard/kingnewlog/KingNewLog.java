package com.lululombard.kingnewlog;

import de.diddiz.LogBlock.Consumer;
import de.diddiz.LogBlock.LogBlock;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.lululombard.kingnewlog.listeners.AnvilLogger;
import com.lululombard.kingnewlog.listeners.CauldronLogger;
import com.lululombard.kingnewlog.listeners.DaylightSensorLogger;
import com.lululombard.kingnewlog.listeners.DoorLogger;
import com.lululombard.kingnewlog.listeners.FenceLogger;
import com.lululombard.kingnewlog.listeners.FlowerPotLogger;
import com.lululombard.kingnewlog.listeners.ItemFrameLogger;
import com.lululombard.kingnewlog.listeners.JukeboxLogger;

public class KingNewLog extends JavaPlugin {
	private Consumer lbconsumer = null;
	private ItemFrameLogger iflog = null;
	private FenceLogger flog = null;
	private DoorLogger dlog = null;
	private JukeboxLogger jlog = null;
	private CauldronLogger clog = null;
	private DaylightSensorLogger dslog = null;
	private AnvilLogger alog = null;
	private FlowerPotLogger fplog = null;
		
	public void onEnable() {
		final PluginManager pm = getServer().getPluginManager();
		final Plugin plugin = pm.getPlugin("LogBlock");
		if (plugin != null && plugin instanceof LogBlock) {
			LogBlock lb = (LogBlock) plugin;
			lbconsumer = lb.getConsumer();
			iflog = new ItemFrameLogger(lbconsumer);
			flog = new FenceLogger(lbconsumer);
			dlog = new DoorLogger(lbconsumer);
			jlog = new JukeboxLogger(lbconsumer);
			clog = new CauldronLogger(lbconsumer);
			dslog = new DaylightSensorLogger(lbconsumer);
			alog = new AnvilLogger(lbconsumer);
			fplog = new FlowerPotLogger(lbconsumer);
			pm.registerEvents(iflog, this);
			pm.registerEvents(flog, this);
			pm.registerEvents(dlog, this);
			pm.registerEvents(jlog, this);
			pm.registerEvents(clog, this);
			pm.registerEvents(dslog, this);
			pm.registerEvents(alog, this);
			pm.registerEvents(fplog, this);
		}
		else {
			getLogger().severe("This plugin requires LogBlock to work, but not present.");
			pm.disablePlugin(plugin);
		}
	}
}