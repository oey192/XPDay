package com.andoutay.xpday;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockExpEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.FurnaceExtractEvent;
import org.bukkit.event.player.PlayerFishEvent;

public class XDEventHandler implements Listener
{
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent evt)
	{
		XPDay.log.info("block break xp");
		
	}
	
	@EventHandler
	public void onEntityDeath(EntityDeathEvent evt)
	{
		if (!shouldStop() && !evt.getEntityType().equals(EntityType.PLAYER));
		{
			XPDay.log.info("Death event: old xp: " + evt.getDroppedExp());
			evt.setDroppedExp(XDUtil.round(evt.getDroppedExp() * XDConfig.rate));
			XPDay.log.info("new xp: " + evt.getDroppedExp());
		}
	}
	
	@EventHandler
	public void onFurnaceExtract(FurnaceExtractEvent evt)
	{
		XPDay.log.info("FurnaceExtract xp");
		//evt.setExpToDrop((int)(evt.getExpToDrop() * XDConfig.rate));
	}
	
	@EventHandler
	public void onPLayerFish(PlayerFishEvent evt)
	{
		XPDay.log.info("fishing!");
		//evt.setExpToDrop((int)(evt.getExpToDrop() * XDConfig.rate));
	}
	
	@EventHandler
	public void onBlockExp(BlockExpEvent evt)
	{
		XPDay.log.info("XP event: old xp: " + evt.getExpToDrop());
		if (!shouldStop()) evt.setExpToDrop(XDUtil.round(evt.getExpToDrop() * XDConfig.rate));
		XPDay.log.info("new xp: " + evt.getExpToDrop());
	}
	
	private boolean shouldStop()
	{
		String ymd[] = XDConfig.disableDate.split("-");
		if (ymd.length > 3 || ymd.length == 0)
		{
			XPDay.log.warning(XPDay.logPref + "Error parsing date from config file");
			return true;
		}
		
		int year, month, day, y, m, d;
		long curTime = System.currentTimeMillis();
		
		try {
			year = Integer.parseInt(ymd[0]);
			month = Integer.parseInt(ymd[1]);
			day = Integer.parseInt(ymd[2]);
		} catch (NumberFormatException e) {
			XPDay.log.warning(XPDay.logPref + "Error parsing date from config file");
			return true;
		}
		
		DateFormat f = new SimpleDateFormat("yyyy");
		y = Integer.parseInt(f.format(curTime));
		f = new SimpleDateFormat("MM");
		m = Integer.parseInt(f.format(curTime));
		f = new SimpleDateFormat("dd");
		d = Integer.parseInt(f.format(curTime));
		
		if (year > y || (year == y && month > m) || (year == y && month == m && day > d ))
			return true;	
		
		return false;
	}
}
