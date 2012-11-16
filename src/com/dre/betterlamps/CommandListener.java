package com.dre.betterlamps;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandListener implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command command, String arg, String[] args) {
		
		
		if(sender instanceof Player){
			Player player = (Player) sender;
			if(args.length > 0){
                String argu = args[0];

                if(argu.equalsIgnoreCase("create")){
                	
                	if(args.length == 2){
                		String type=args[1];
                		Block block=player.getTargetBlock(null, 10);
            			if(
            					block.getTypeId()==123||
            					block.getTypeId()==124
            			){
            				for(Lamp lamp:Lamp.lamps){
            					if(block.equals(lamp.block)){
            						player.sendMessage(ChatColor.RED+"Lampe wurde bereits erstellt!");
            						return false;
            					}
            				}
            				
            				if(type.equalsIgnoreCase("off")||type.equalsIgnoreCase("on")||type.equalsIgnoreCase("daynight")){
            					int typenr=1;
            					if(type.equalsIgnoreCase("off")){
            						if(player.isOp() || BetterLamps.permission.has(player, "betterlamps.create.off")){
            							typenr=1;
            						}else{
            	    					player.sendMessage(ChatColor.RED+"Du hast keine Erlaubnis dies zu tun!");
            	    					return false;
            	    				}
            							
            	        		}else if(type.equalsIgnoreCase("on")){
            	        			if(player.isOp() || BetterLamps.permission.has(player, "betterlamps.create.on")){
            							typenr=2;
            						}else{
            	    					player.sendMessage(ChatColor.RED+"Du hast keine Erlaubnis dies zu tun!");
            	    					return false;
            	    				}
            	        		}else if(type.equalsIgnoreCase("daynight")){
            	        			if(player.isOp() || BetterLamps.permission.has(player, "betterlamps.create.daynight")){
            							typenr=3;
            						}else{
            	    					player.sendMessage(ChatColor.RED+"Du hast keine Erlaubnis dies zu tun!");
            	    					return false;
            	    				}
            	        		}
            					
            					new Lamp(block,typenr,player.getName());
                				player.sendMessage(ChatColor.GREEN+"BetterLamp erfolgreich erstellt!");
                				return true;
            				}else{
            					player.sendMessage(ChatColor.RED+"Benutze nur "+ChatColor.GOLD+"'off'"+ChatColor.RED+", "+ChatColor.GOLD+"'on' "+ChatColor.RED+"oder "+ChatColor.GOLD+"'daynight'!");
            					return false;
            				}
            				
            				
            			}else{
            				player.sendMessage(ChatColor.RED+"Du musst eine Redstone Lampe anschauen!");
            				return false;
            			}
                	}else{
                		player.sendMessage(ChatColor.RED+"/bl create <type>");
                		return false;
                	}
            	}
                
                else if(argu.equalsIgnoreCase("remove")){
                	if(player.isOp() || BetterLamps.permission.has(player, "betterlamps.remove")){
	                	Block block=player.getTargetBlock(null, 10);
	                	for(Lamp lamp:Lamp.lamps){
	    					if(block.equals(lamp.block)){
	    						if(player.isOp() || BetterLamps.permission.has(player, "betterlamps.removeother") || player.getName().equalsIgnoreCase(lamp.player)){
		    						lamp.block.setTypeId(123);
		    						lamp.delete();
		    						player.sendMessage(ChatColor.GREEN+"BetterLamp gelöscht!");
		    						return true;
	    						}else{
	    							player.sendMessage(ChatColor.RED+"Du kannst die Lampen anderer nicht entfernen!");
	    							return false;
	    						}
	    					}
	    				}
	                	player.sendMessage(ChatColor.RED+"Keine BetterLamp gefunden!");
	                	return false;
                	}else{
    					player.sendMessage(ChatColor.RED+"Du hast keine Erlaubnis dies zu tun!");
    					return false;
    				}
                }
                
                else if(argu.equalsIgnoreCase("help")){
                	if(player.isOp() || BetterLamps.permission.has(player, "betterlamps.help")){
	                	player.sendMessage(ChatColor.GOLD+"-############[ BetterLamps ]############-");
	                	player.sendMessage(ChatColor.DARK_GREEN+"/bl help "+ChatColor.GOLD+"- Zeigt diese Hilfe Seite");
	                	player.sendMessage(ChatColor.DARK_GREEN+"/bl create <type> "+ChatColor.GOLD+"- erstellt eine BetterLamp");
	                	player.sendMessage(ChatColor.GOLD+"Für <type> kann man folgendes einsetzen:");
	                	player.sendMessage(ChatColor.GOLD+"   off - Lampe ist immer aus");
	                	player.sendMessage(ChatColor.GOLD+"   on - Lampe ist immer an");
	                	player.sendMessage(ChatColor.GOLD+"   daynight - Die Lampe ist nur nachts an");
	                	player.sendMessage(ChatColor.DARK_GREEN+"/bl remove "+ChatColor.GOLD+"- löscht eine BetterLamp");
                	}else{
    					player.sendMessage(ChatColor.RED+"Du hast keine Erlaubnis dies zu tun!");
    					return false;
    				}
                }
                
                else{
                	player.sendMessage(ChatColor.RED+"/bl help");
                	return false;
                }
                
            }else{
            	player.sendMessage(ChatColor.RED+"/bl help");
            	return false;
            }
		}
		return false;
	}
	
}