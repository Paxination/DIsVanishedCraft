package pax;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.event.EventHandler;
import org.kitteh.vanish.VanishManager;
import org.kitteh.vanish.event.VanishStatusChangeEvent;
import org.kitteh.vanish.staticaccess.VanishNoPacket;
import org.kitteh.vanish.staticaccess.VanishNotLoadedException;

import pgDev.bukkit.DisguiseCraft.DisguiseCraft;
import pgDev.bukkit.DisguiseCraft.api.DisguiseCraftAPI;
import pgDev.bukkit.DisguiseCraft.api.PlayerDisguiseEvent;

@SuppressWarnings("deprecation")
public class dvc extends JavaPlugin implements Listener{

	public DisguiseCraftAPI dcAPI;
	public VanishManager vm;
	
	public void setupDependencies() {
		dcAPI = DisguiseCraft.getAPI();
		try {
			vm = VanishNoPacket.getManager();
		} catch (VanishNotLoadedException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void onEnable(){
		getServer().getPluginManager().registerEvents(this,  this);
		setupDependencies();
	}
	
	@Override
	public void onDisable(){
		
	}
	
	@EventHandler
	public void OnStateChange(VanishStatusChangeEvent event){
		if(event.isVanishing()){
			if (dcAPI.isDisguised(event.getPlayer())) {
				dcAPI.undisguisePlayer(event.getPlayer());
				event.getPlayer().sendMessage("&6You've been undisguised!");
				return;
			}
		}
	}
	
	@EventHandler
	public void OnDisguiseEvent(PlayerDisguiseEvent event){
		if(vm.isVanished(event.getPlayer())){
			vm.toggleVanish(event.getPlayer());
			return;
		}
	}
}
