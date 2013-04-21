package com.andoutay.xpday;

import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class XPDay extends JavaPlugin
{
	public static Logger log = Logger.getLogger("Minecraft");
	public static String logPref = "[XPDay]";
	public static String chPref = ChatColor.GREEN + logPref + ' ' + ChatColor.RESET;
	private XDEventHandler evtHandler;
	
	public void onLoad()
	{
		new XDConfig(this);
	}
	
	public void onEnable()
	{
		XDConfig.onEnable();
		
		evtHandler = new XDEventHandler();
		
		getServer().getPluginManager().registerEvents(evtHandler, this);
		
		log.info(logPref + "Enabled");
	}
	
	public void onDisable()
	{
		log.info(logPref + "Disabled");
	}
	
	
	public boolean onCommand(CommandSender s, Command cmd, String label, String[] args)
	{
		if (cmd.getName().equalsIgnoreCase("xpday"))
		{
			if (args.length == 1)
			{
				if (args[0].equalsIgnoreCase("check") && hasPerm(s, "xpday.check"))
				{
					s.sendMessage(chPref + ChatColor.YELLOW + "The server's XP modifier rate is " + XDConfig.rate);
				}
				else if (args[0].equalsIgnoreCase("reset") && hasPerm(s, "xpday.modify"))
					return setXPRate(1.0);
				else if (args[0].equalsIgnoreCase("reload") && hasPerm(s, "xpday.reload"))
				{
					XDConfig.reload();
					s.sendMessage(chPref + "Config reloaded");
					return true;
				}
				else if (args[0].equalsIgnoreCase("version") && hasPerm(s, "xpday.version"))
				{
					s.sendMessage(chPref + "Version: " + getDescription().getVersion());
					return true;
				}
				else if (args[0].equalsIgnoreCase("help") && hasPerm(s, "xpday.help"))
					return showHelp(s);
			}
			else if (args.length == 2)
			{
				if (args[0].equalsIgnoreCase("set") && hasPerm(s, "xpday.modify"))
				{
					double rate;
					try
					{
						rate = Double.parseDouble(args[1]);
					} catch (NumberFormatException e) {
						s.sendMessage(ChatColor.RED + "Invalid rate \"" + args[1] + "\"");
						return true;
					}
					
					return setXPRate(rate);
				}
			}
					
		}
		
		return false;
	}
	
	private boolean setXPRate(double rate)
	{
		XDConfig.setRate(rate);
		
		tellAll(ChatColor.YELLOW, "Server XP Rate set to " + XDConfig.rate, "xpday.view");
		
		return true;
	}
	
	private boolean showHelp(CommandSender s)
	{
		s.sendMessage(chPref + "Help");
		s.sendMessage("No help yet");
		
		return true;
	}
	
	
	private void tellAll(ChatColor color, String msg, String perm)
	{
		for (Player p : getServer().getOnlinePlayers())
			if (p.hasPermission(perm))
				p.sendMessage(color + msg);
		log.info(logPref + msg);
	}

	private boolean hasPerm(CommandSender s, String perm)
	{
		if (s instanceof ConsoleCommandSender || (s instanceof Player && ((Player)s).hasPermission(perm)))
			return true;
		
		return false;
	}
}
