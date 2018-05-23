package de.MasterCake.PartSleep;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin{
	
	SleepFactory sleepFactory;
	
	@Override
	public void onEnable(){
		this.saveDefaultConfig();
		
		sleepFactory = new SleepFactory(this.getConfig());
		Bukkit.getPluginManager().registerEvents(new Listeners(sleepFactory), this);
	}
}
