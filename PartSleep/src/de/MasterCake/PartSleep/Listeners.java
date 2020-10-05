package de.MasterCake.PartSleep;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBedEnterEvent.BedEnterResult;

import net.minecraft.server.v1_15_R1.EntityHuman;

import org.bukkit.event.player.PlayerBedLeaveEvent;

public class Listeners implements Listener{
	
	SleepFactory sleepFactory;
	
	public Listeners(SleepFactory sleepFactory) {
		this.sleepFactory = sleepFactory;
	}

	@EventHandler
	public void onSleep(PlayerBedEnterEvent e){
		for(Player player : e.getBed().getWorld().getPlayers()) {
			player.setSleepingIgnored(false);
		}
		
		Bukkit.getScheduler().runTaskLater(Main.instance, () -> {
			if(e.getBedEnterResult() == BedEnterResult.OK)
			sleepFactory.putInSleep(e.getPlayer());
		}, 20L * 5);
	}
	
	@EventHandler
	public void onWake(PlayerBedLeaveEvent e){
		sleepFactory.removeFromSleep(e.getPlayer());
	}
}
