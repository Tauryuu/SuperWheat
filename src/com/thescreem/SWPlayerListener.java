package com.thescreem;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.inventory.ItemStack;

public class SWPlayerListener extends PlayerListener {

    public SuperWheat plugin;
    public SWPlayerListener(SuperWheat instance){
	plugin = instance;
    }

    @SuppressWarnings("static-access")
    public void onPlayerInteract(PlayerInteractEvent event){
	
	//If the player left-clicked a block...\\
	if(event.getAction() == Action.LEFT_CLICK_BLOCK){
	    
	    //Get the block that was clicked\\
	    Block block = event.getClickedBlock();
	    
	    //If that block is a crop (Block ID #59)...\\
	    if(block.getType() == Material.CROPS){
	    
	    //Get the player who left-clicked it\\
	    Player player = event.getPlayer();
	    
	    //If the data for the crop isn't 7 (Isn't fully grown)
	    //And the player doesn't have the bypass permission...
	    if((byte)block.getData() != 7 && !plugin.hasPermission(player, "Wheaty.disallow.crop.destroying"))
	    
	        //Cancel the event, so the player never really left-clicked the block
		//and the crop doesn't get destroyed
		event.setCancelled(true);
					
		//Send a message to that player, notifying them that the crop isn't
		//fully grown so they can't destroy it
		player.sendMessage("�6[Wheaty] That crop isn't fully grown yet!");
		//block.setTypeIdAndData(59, (byte)block.getData(), true);
				
					
	    //Else, if the data for the block IS 7 (The crop is fully grown) and the player
	    //has the permission node so the crop automatically re-grows after being harvested...
	    } else if((byte)block.getData() == 7 && plugin.hasPermission(player, "Wheaty.allow.crop.regrowing")){
	        
		//Cancel the event, so the crop was never actually destroyed.
		//Instead of having the crop destroyed, we just set it's data value to 0
		//And have it drop a random amount of wheat.
		event.setCancelled(true);
					
		//Set the block to a data value of 0, which is what the crop looks
		//like right when you just plant it.
		block.setData((byte) 0);
		
		//Get a random number between 1 and 3
	        Random rand = new Random();
		int r = rand.nextInt(3) + 1;
		
		//Drop wheat from the crop. The amount of wheat is determined from the random number.
		block.getWorld().dropItemNaturally(block.getLocation(), new ItemStack(Material.WHEAT, r));
		}
	    }
	}
    }
}

//Source fully commented by thescreem