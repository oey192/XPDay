package com.andoutay.xpday;

import org.bukkit.configuration.Configuration;

public class XDConfig
{
private static Configuration config;
	
	public static boolean autoDisable;
	public static double minRate, maxRate, rate;
	public static String disableDate;
	private static XPDay plugin;
	
	XDConfig(XPDay plugin)
	{
		XDConfig.plugin = plugin;
		config = plugin.getConfig().getRoot();
		config.options().copyDefaults(true);
		plugin.saveConfig();
	}
	
	public static void onEnable()
	{
		loadConfigVals();
	}
	
	public static void reload()
	{
		plugin.reloadConfig();
		config = plugin.getConfig().getRoot();
		onEnable();
	}
	
	public static void loadConfigVals()
	{
		autoDisable = config.getBoolean("autoDisable");
		disableDate = config.getString("disableDate");
		minRate = config.getDouble("minRate");
		maxRate = config.getDouble("maxRate");
		rate = config.getDouble("rate");
		
		if (minRate > maxRate)
			XPDay.log.warning(XPDay.logPref + "The minimum rate value is larger than the maximum rate in the config. This may produce unexpexted results");
		
		if (rate > maxRate)
			rate = maxRate;
		else if (rate < minRate)
			rate = minRate;
			
	}

	public static void setRate(double rate)
	{
		config.set("rate", rate);
		config.set("disableDate", XDUtil.ymdTimestamp(System.currentTimeMillis() + 1000 * 3600 * 24));
		XDConfig.plugin.saveConfig();
		XDConfig.reload();
	}
}
