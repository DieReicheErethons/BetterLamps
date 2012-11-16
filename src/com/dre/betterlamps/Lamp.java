package com.dre.betterlamps;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class Lamp {
	public static Set<Lamp> lamps=new HashSet<Lamp>();
	
	public Block block;
	public int type;
	public BLWorld blworld;
	public String player;
	
	public Lamp(Block block,int type, String player){
		this.block=block;
		this.type=type;
		this.player=player;
		if(this.type==1){
			this.block.setTypeId(123);
		}else if(this.type==2){
			this.block.setTypeId(124);
		}else if(this.type==3){
			BLWorld blworld=BLWorld.get(block.getWorld());
			if(blworld==null){
				blworld=new BLWorld(block.getWorld());
			}
			
			blworld.addlamp(this, 1);
			
			this.blworld=blworld;
		}
		
		lamps.add(this);
		//BetterLamps.p.save();
	}
	
	public void update(boolean isday){
		
		if (isday){
			this.block.setTypeId(123);
			this.blworld.addlamp(this, 2);
			this.blworld.removelamp(this, 1);
		}else{
			this.block.setTypeId(124);
			this.blworld.addlamp(this, 1);
			this.blworld.removelamp(this, 2);
		}
		
	}
	
	public void delete(){
		if(this.blworld!=null){
			this.blworld.removelamp(this,1);
			this.blworld.removelamp(this,2);
		}
		Lamp.lamps.remove(this);
		//BetterLamps.p.save();
	}
	
	public static void save(){
		File dir = new File(BetterLamps.p.getDataFolder(), "lamps");
		if(!dir.exists()){
			dir.mkdir();
		}
		
		for(String player:getPlayers()){
			File file = new File(BetterLamps.p.getDataFolder()+"/lamps", player+".yml");
			FileConfiguration configFile = new YamlConfiguration();
			
			
			for(World world:BetterLamps.p.getServer().getWorlds()){
				Map<Integer,List<String>> typelists=new HashMap<Integer,List<String>>();
				typelists.put(1,new ArrayList<String>());
				typelists.put(2,new ArrayList<String>());
				typelists.put(3,new ArrayList<String>());
				
				
				for(Lamp lamp:lamps){
					if(lamp.block.getWorld()==world){
						if(lamp.player.equals(player)){
							List<String> list=typelists.get(lamp.type);
							list.add(lamp.block.getX()+","+lamp.block.getY()+","+lamp.block.getZ());
							typelists.put(lamp.type, list);
						}
					}
				}
				
				for(Integer key:typelists.keySet()){
					if(typelists.get(key).size()>0){
						configFile.set(world.getName()+"."+key,typelists.get(key));
					}
				}
			}
			
			try {
				configFile.save(file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private static List<String> getPlayers() {
		List<String> players=new ArrayList<String>();
		
		for(Lamp lamp:lamps){
			if(!players.contains(lamp.player)){
				players.add(lamp.player);
			}
			
		}
		
		return players;
	}

	public static void load(){
		File dir = new File(BetterLamps.p.getDataFolder(), "lamps");
		if(!dir.exists()){
			dir.mkdir();
		}
		
		String[] files=dir.list();
		
		for(String sfile:files){
			File file=new File(BetterLamps.p.getDataFolder()+"/lamps",sfile);
			
			if(file.isFile()){
				FileConfiguration configFile = YamlConfiguration.loadConfiguration(file);
				
				for(World world:BetterLamps.p.getServer().getWorlds()){
					if(configFile.contains(world.getName())){
						for(int i=1;i<=3;i++){
							@SuppressWarnings("unchecked")
							List<String> list=(List<String>) configFile.get(world.getName()+"."+i);
							if(list!=null){
								for(String position:list){
									String[] splitted=position.split(",");
									int x=Integer.parseInt(splitted[0]);
									int y=Integer.parseInt(splitted[1]);
									int z=Integer.parseInt(splitted[2]);
									if(world.getBlockAt(x,y,z).getTypeId()==123|world.getBlockAt(x,y,z).getTypeId()==124){
										new Lamp(world.getBlockAt(x,y,z), i, sfile);
									}
								}
							}
						}
					}
				}
			}
		}
	}
}
