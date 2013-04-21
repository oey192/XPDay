package com.andoutay.xpday;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class XDUtil
{
	public static String ymdTimestamp(long timestamp)
	{
		DateFormat f = new SimpleDateFormat("yyyy-MM-dd");
		String ans = f.format(new Date(timestamp));
		return ans;
	}
	
	public static int round(double num)
	{
		return (int)(num + 0.5);
	}

}
