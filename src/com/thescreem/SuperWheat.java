package com.thescreem;

import java.util.logging.Logger;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;

public class SuperWheat extends JavaPlugin {
	
    //Getting the Logger so we can send messages to the console\\
    Logger log = Logger.getLogger("Minecraft");
	
    //Here we set pdfile as a PluginDescriptionFile variable\\
    PluginDescriptionFile pdfile;
	
    //Here we initialize the PlayerListener class\\
    SWPlayerListener playerListener = new SWPlayerListener(this);
	
    //Here we set permissionHandle as a PermissionHandler variable\\
    public static PermissionHandler permissionHandler;
    
    @Override
    public void onDisable() {
        //The variable pdfile is equal to the plugin.yml file, using the method 'getDescription()'
	pdfile = getDescription();
		
	//Log to the console that SuperWheat is disabled\\
	log.info("[" + pdfile.getFullName() + "] is now disabled!");
    }

    @Override
    public void onEnable() {
	//Getting the plugin manager\\
	PluginManager pm = getServer().getPluginManager();
	
	//Registering Events\\
	pm.registerEvent(Event.Type.PLAYER_INTERACT, playerListener, Event.Priority.Normal, this);
	
	//Permissions setup\\
	Plugin permissionsPlugin = getServer().getPluginManager().getPlugin("Permissions");
	if(permissionsPlugin == null){
	    log.info("[WheatWeGrow] Permissions system not detected, defaulting to OP permissions.");
	} else{
	    permissionHandler = ((Permissions) permissionsPlugin).getHandler();
	    log.info("[SuperWheat] Found and will use plugin " + ((Permissions)permissionsPlugin).getDescription().getFullName());
	}
	//End of Permissions setup\\
	
	//The variable pdfile is equal to the plugin.yml file, using the method 'getDescription()'
	pdfile = getDescription();
	
	//Log to the console that SuperWheat is enabled\\
	log.info("[" + pdfile.getFullName() + "] is now enabled!");
    }

    /*
     * Method to check if the player has permission to do something
     */
    public static boolean hasPermission(Player player, String permission){
    
    //If permissions is not installed, it will determine whether you can or
    //can't do something based on if you're an OP or not
    if(permissionHandler == null){
	return player.isOp();
			
    //Else, if permissions is installed, it will determine whether you can
    //or can't do something based on if you have the right permission node or not
    } else{
        return permissionHandler.has(player, permission);
	}
    }
}

//Source fully commented by thescreem