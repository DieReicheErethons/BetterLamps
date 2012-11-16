package com.dre.betterlamps;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

import org.bukkit.World;

public class BLWorld {
	public static Set<BLWorld> blworlds=new HashSet<BLWorld>();
	
	private World world;
	private CopyOnWriteArrayList<Lamp> lampson=new CopyOnWriteArrayList<Lamp>();
	private CopyOnWriteArrayList<Lamp> lampsoff=new CopyOnWriteArrayList<Lamp>();
	
	public BLWorld(World world){
		this.world=world;
		blworlds.add(this);
	}
	
	public void addlamp(Lamp lamp,int type){
		switch(type){
		case 1:
			lampson.add(lamp);
		case 2:
			lampsoff.add(lamp);
		default:
		return;
		}
	}
	
	public void removelamp(Lamp lamp,int type){
		switch(type){
		case 1:
			lampson.remove(lamp);
		case 2:
			lampsoff.remove(lamp);
		default:
		return;
		}
	}
	
	public static void update(){
		for(BLWorld blworld:blworlds){
			if(blworld.world!=null){
				long time=blworld.world.getTime();
				long timenight=12000;
				
				int tick=0;
				
				if(time>timenight){
					if(!blworld.lampsoff.isEmpty()){
						for(Lamp lamp:blworld.lampsoff){
							tick++;
							lamp.update(false);
							if(tick>100) break;
						}
					}
				}else{
					if(!blworld.lampson.isEmpty()){
						for(Lamp lamp:blworld.lampson){
							tick++;
							lamp.update(true);
							if(tick>100) break;
						}
					}
				}
			}
		}
	}
	
	public static BLWorld get(World world){
		if(world!=null){
			for(BLWorld blworld:blworlds){
				if(blworld.world.equals(world)){
					return blworld;
				}
			}
		}
		return null;
	}
	
}
