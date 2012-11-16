package com.dre.betterlamps;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPistonRetractEvent;
import org.bukkit.event.block.BlockRedstoneEvent;

public class BlockListener implements Listener{
	@EventHandler(priority = EventPriority.HIGH)
	public void onBlockRedstoneChange (BlockRedstoneEvent event){
		Block block=event.getBlock();
		for(Lamp lamp:Lamp.lamps){
			if(block.equals(lamp.block)){
				event.setNewCurrent(event.getOldCurrent());
				return;
			}
		}
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onBlockBreak(BlockBreakEvent event){
		Block block=event.getBlock();
		Player player=event.getPlayer();
		if(block.getTypeId()==123||block.getTypeId()==124){
			for(Lamp lamp:Lamp.lamps){
				if(lamp.block.equals(block)){
					if(player.isOp() || BetterLamps.permission.has(player, "betterlamps.remove")){
						if(player.isOp() || BetterLamps.permission.has(player, "betterlamps.removeother") || player.getName().equalsIgnoreCase(lamp.player)){
							if(player.isSneaking()){
								lamp.delete();
								player.sendMessage(ChatColor.GREEN+"BetterLamp gelöscht!");
							}else{
								event.setCancelled(true);
								player.sendMessage(ChatColor.RED+"[BetterLamps] Du musst die Lampe geduckt abbauen!");
								return;
							}
						}else{
							event.setCancelled(true);
							player.sendMessage(ChatColor.RED+"Du kannst die Lampen anderer nicht entfernen!");
							return;
						}
					}else{
						event.setCancelled(true);
						player.sendMessage(ChatColor.RED+"Du hast keine Erlaubnis dies zu tun!");
						return;
					}
				}
			}
		}
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onPistonExtend(BlockPistonExtendEvent event){
		Block piston=event.getBlock();
		Block block=piston.getRelative(event.getDirection());
		
		if(block.getTypeId()==123||block.getTypeId()==124){
			for(Lamp lamp:Lamp.lamps){
				if(lamp.block.equals(block)){
					event.setCancelled(true);
				}
			}
		}
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onPistonRetract(BlockPistonRetractEvent  event){
		Block block=event.getRetractLocation().getBlock();
		
		if(block.getTypeId()==123||block.getTypeId()==124){
			for(Lamp lamp:Lamp.lamps){
				if(lamp.block.equals(block)){
					event.setCancelled(true);
				}
			}
		}
	}
	
	 
}
