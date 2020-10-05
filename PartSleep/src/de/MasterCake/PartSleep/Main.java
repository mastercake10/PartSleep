package de.MasterCake.PartSleep;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin{
	
	SleepFactory sleepFactory;
	public static Main instance;
	
	@Override
	public void onEnable(){
		this.saveDefaultConfig();
		instance = this;
		
		sleepFactory = new SleepFactory(this.getConfig());
		Bukkit.getPluginManager().registerEvents(new Listeners(sleepFactory), this);
	}
	
}
