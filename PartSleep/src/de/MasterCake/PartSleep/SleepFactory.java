package de.MasterCake.PartSleep;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.craftbukkit.v1_16_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_16_R2.EntityHuman;

public class SleepFactory {
	
	public double sleeperPerc;
	public boolean alertEnabled;
	public String onSleepText;
	public String onWakeText;
	public String onMorningText;
	
	HashMap<String, List<String>> sleeping = new HashMap<String, List<String>>();
	
	public SleepFactory(FileConfiguration config) {
		sleeperPerc = config.getDouble("SleeperPerc");
		alertEnabled = config.getBoolean("AlertEnabled");
		onSleepText = config.getString("OnSleepText").replace("&", "§");
		onWakeText = config.getString("OnWakeText").replace("&", "§");
		onMorningText = config.getString("OnMorningText").replace("&", "§");
	}
	
	public int getSleepingPlayers(World w){
		if(sleeping.containsKey(w.getName())){
			return sleeping.get(w.getName()).size();
		}
		return 0;
	}
	
	public int getRequiredPlayers(World w){
		return (int) Math.ceil(w.getPlayers().size() * sleeperPerc);
	}
	
	public void putInSleep(Player p){
		if(!sleeping.containsKey(p.getWorld().getName())){
			List<String> players = new ArrayList<String>();
			players.add(p.getName());
			sleeping.put(p.getWorld().getName(), players);
		}else{
			sleeping.get(p.getWorld().getName()).add(p.getName());
		}
		if(alertEnabled){
			alertOnSleep(p);
		}
		checkForEnoughPlayers(p.getWorld());
	}
	

	public void removeFromSleep(Player p) {
		if(!sleeping.containsKey(p.getWorld().getName())) return;
		sleeping.get(p.getWorld().getName()).remove(p.getName());
		if(alertEnabled){
			alertOnWake(p);
		}
	}
	
	public void checkForEnoughPlayers(World w){
		if(getSleepingPlayers(w) >= w.getPlayers().size() * sleeperPerc){
			sleeping.remove(w.getName());
			
			skipToNextDay(w);
			
	        if(alertEnabled){
	        	alertOnMorning(w);
	        }
		}
	}
	
	private void skipToNextDay(World w) {
        
		for(Player player : w.getPlayers()) {
			EntityHuman entityHuman = ((CraftPlayer) player).getHandle();
			entityHuman.fauxSleeping = true;
			player.setSleepingIgnored(true);
			
		}
	}
	
	public void alertOnMorning(World w){
		String msg = onMorningText;
		for(Player p2 : w.getPlayers()){
			p2.sendMessage(msg);
		}
	}
	
	public void alertOnSleep(Player p){
		String msg = String.format(onSleepText, p.getName()) + String.format(" (%d/%d)", getSleepingPlayers(p.getWorld()), getRequiredPlayers(p.getWorld()));
		for(Player p2 : p.getWorld().getPlayers()){
			p2.sendMessage(msg);
		}
	}
	
	public void alertOnWake(Player p){
		String msg = String.format(onWakeText, p.getName()) + String.format(" (%d/%d)", getSleepingPlayers(p.getWorld()), getRequiredPlayers(p.getWorld()));
		for(Player p2 : p.getWorld().getPlayers()){
			p2.sendMessage(msg);
		}
	}

}
