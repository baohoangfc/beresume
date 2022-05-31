package com.bao.doan.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class UniUtil {
	// log
	private static final Log log = LogFactory.getLog(UniUtil.class);

	@SuppressWarnings("rawtypes")
	public static List safe(List other) {
		return other == null ? Collections.EMPTY_LIST : other;
	}
	public static long getTableId() {
		return NextId.getNextId();
	}
	public static class NextId{
		public static Integer l = 0;
		public synchronized static long getNextId(){
			long r = 0;
			l++;
			Random rnd = new Random();
			Calendar calendar1 = Calendar.getInstance();
			r = calendar1.getTimeInMillis()*1000+l*10+rnd.nextInt(9);
			if(l>99){
				try        
				{
				    Thread.sleep(2);
				} 
				catch(InterruptedException ex) 
				{
				    Thread.currentThread().interrupt();
				}
				l=0;
			}
			return r;
		}
	}
	
	public static String formatPostgresDate(Date date) {
		String dateFormat = "";
		try {
			SimpleDateFormat df = new SimpleDateFormat(UniConstant.FORMAT.DateTime);
			dateFormat = df.format(date);
		}catch (Exception e) {
			log.error("formatPostgresDate:" + date + ";" + e.getMessage());
		}
		return dateFormat;
	}

}
