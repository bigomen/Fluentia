package br.com.fluentia.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class DateUtils {
	
	private static SimpleDateFormat dataHora = new SimpleDateFormat("dd/MM HH:mm");
	private static SimpleDateFormat hora = new SimpleDateFormat("HH:mm");
	
	public static Timestamp parse(final String stringDate) {
		Timestamp resultTs;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS",new Locale("pt", "BR"));
		try {
			resultTs = new Timestamp(sdf.parse(stringDate).getTime());
		} catch (ParseException e) {
			e.printStackTrace();
			resultTs = new Timestamp(GregorianCalendar.getInstance().getTimeInMillis());
		}
		return resultTs;
	}
	public static Long formatInMilis(String string) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss",new Locale("pt", "BR"));
		var date = new Date();
		try {
			 date = sdf.parse(string);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date.getTime();
	}
	public static String formatInvite(String string) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss",new Locale("pt", "BR"));
		var date = new Date();
		try {
			 date = sdf.parse(string);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(date.getTime());
		var sdate = new SimpleDateFormat("EEE, d MMM yyyy HH:mm");
		return sdate.format(cal.getTime());
	}
	public static String formatarData (Date data) {
		return dataHora.format(data);
	}
	public static String formatarData (String data) {
		var dataAulaConv = LocalDateTime.parse(data);
		return dataHora.format(Date.from(dataAulaConv.atZone(ZoneId.systemDefault()).toInstant()));
	}
	
	public static String formatarHora (Date data) {
		return hora.format(data);
	}
	public static String formatDateSerpro(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS",new Locale("pt", "BR"));
		return sdf.format(date);
	}
	public static boolean isWeekDay (String data,Integer dayWeek) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd",new Locale("pt", "BR"));
		Calendar dataAulaConv = Calendar.getInstance();
		dataAulaConv.setTime(sdf.parse( data.substring(0,10)));
		
		return dayWeek+1 == dataAulaConv.get(Calendar.DAY_OF_WEEK);
	}
	
}
