package de.MasterCake.PartSleep;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBedLeaveEvent;

public class Listeners implements Listener{
	
	SleepFactory sleepFactory;
	
	public Listeners(SleepFactory sleepFactory) {
		this.sleepFactory = sleepFactory;
	}

	@EventHandler
	public void onSleep(PlayerBedEnterEvent e){
		sleepFactory.putInSleep(e.getPlayer());
	}
	
	@EventHandler
	public void onWake(PlayerBedLeaveEvent e){
		sleepFactory.removeFromSleep(e.getPlayer());
	}
}
