package com.dre.betterlamps;

import java.util.logging.Level;
import java.util.logging.Logger;

import net.milkbowl.vault.permission.Permission;

import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;


public class BetterLamps extends JavaPlugin{
	public static BetterLamps p;
	
	public BlockListener blocklistener;
	
	@Override 
	public void onEnable()
	{
		p=this;
		this.blocklistener = new BlockListener();
		
		Bukkit.getServer().getPluginManager().registerEvents(blocklistener,this);
		
		//Commands
		getCommand("bl").setExecutor(new CommandListener());
		
		
		//Permissions
		if(!setupPermissions()) this.log("Vault is missing. Only OP's can use BetterLamps!");
		
		// -------------------------------------------- //
		// Update Sheduler
		// -------------------------------------------- //
		
		p.getServer().getScheduler().scheduleSyncRepeatingTask(p, new Runnable() {
		    public void run() {
		        BLWorld.update();
		    }
		}, 10L, 10L);
		
		//Load
		this.load();
		
		//MSG
		this.log(this.getDescription().getName()+" enabled!");
	}
	
	@Override
	public void onDisable()
	{
		this.save();
		//MSG
		this.log(this.getDescription().getName()+" disabled!");
	}
	
	
	//Permissions
	public static Permission permission = null;

    private Boolean setupPermissions()
    {
        RegisteredServiceProvider<Permission> permissionProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.permission.Permission.class);
        if (permissionProvider != null) {
            permission = permissionProvider.getProvider();
        }
        return (permission != null);
    }
	
	
	// -------------------------------------------- //
	// Save & Load
	// -------------------------------------------- //
	
	public void save(){
		Lamp.save();
	}
	
	
	public void load(){
		Lamp.load();
	}
	
    // -------------------------------------------- //
 	// LOGGING
 	// -------------------------------------------- //
 	public void log(Object msg)
 	{
 		log(Level.INFO, msg);
 	}

 	public void log(Level level, Object msg)
 	{
 		Logger.getLogger("Minecraft").log(level, "["+this.getDescription().getFullName()+"] "+msg);
 	}
}
