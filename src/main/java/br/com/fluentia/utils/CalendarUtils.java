package br.com.fluentia.utils;

import br.com.fluentia.model.Aula;
import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.component.VAlarm;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.*;
import net.fortuna.ical4j.util.RandomUidGenerator;
import net.fortuna.ical4j.util.UidGenerator;
import net.fortuna.ical4j.validate.ValidationException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class CalendarUtils {
	
	public static File generateCalendar(Aula aula, String tipoNome, String idUUID) {
//		2021-12-15T18:00
//        System.out.println();
//		
//		System.out.println(dataAula.substring(0, 4));
//		System.out.println(dataAula.substring(5, 7));
//		System.out.println(dataAula.substring(8, 10));
//		System.out.println(dataAula.substring(11, 13));
//		System.out.println(dataAula.substring(14, 16));
		
        java.util.Calendar calendarStartTime = new GregorianCalendar();
        calendarStartTime.set(java.util.Calendar.AM_PM, java.util.Calendar.AM);
        calendarStartTime.set(java.util.Calendar.YEAR, Integer.parseInt(aula.getDataAula().toString().substring(0, 4)));
        calendarStartTime.set(java.util.Calendar.MONTH,Integer.parseInt(aula.getDataAula().toString().substring(5, 7))-1);
        calendarStartTime.set(java.util.Calendar.DAY_OF_MONTH, Integer.parseInt(aula.getDataAula().toString().substring(8, 10)));
        calendarStartTime.set(java.util.Calendar.HOUR_OF_DAY, Integer.parseInt(aula.getDataAula().toString().substring(11, 13)));
        calendarStartTime.set(java.util.Calendar.MINUTE, Integer.parseInt(aula.getDataAula().toString().substring(14, 16)));
        
        java.util.Calendar calendarEndTime = new GregorianCalendar();
        calendarEndTime.setTimeInMillis(calendarStartTime.getTimeInMillis());
        calendarEndTime.add(java.util.Calendar.MINUTE, aula.getDuracao());


        // Time zone info
        TimeZone tz = calendarStartTime.getTimeZone();
        ZoneId zid = tz.toZoneId();


        /* Generate unique identifier */
        UidGenerator ug = new RandomUidGenerator();
        Uid uid = ug.generateUid();

        /* Create the event */
        String eventSummary = tipoNome;
        LocalDateTime start = LocalDateTime.ofInstant(calendarStartTime.toInstant(), zid);
        
        LocalDateTime end = LocalDateTime.ofInstant(calendarEndTime.toInstant(), zid);
        Action action = Action.DISPLAY;
        Location location = new Location(aula.getJoinUrl());
        VEvent event = new VEvent(start, end, eventSummary);
        VAlarm alarm = new VAlarm(java.time.Duration.ofHours(-1));
        VAlarm alarm1 = new VAlarm(java.time.Duration.ofMinutes(-15));
        alarm.add(action);
        alarm1.add(action);
        event.add(alarm);
        event.add(alarm1);
        event.add(uid);
        event.add(location);
        

        /* Create calendar */
        Calendar calendar = new Calendar();
        calendar.add(new ProdId("-//Ben Fortuna//iCal4j 1.0//EN"));
        calendar.add(Version.VERSION_2_0);
        calendar.add(CalScale.GREGORIAN);
        /* Add event to calendar */
        calendar.add(event);
      

        /* Create a file */
        String filePath = idUUID + ".ics";
        FileOutputStream fout = null;
        try {

            fout = new FileOutputStream(filePath);
            CalendarOutputter outputter = new CalendarOutputter();
            outputter.output(calendar, fout);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ValidationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fout != null) {
                try {
                    fout.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            
        }
        

		return new File(idUUID + ".ics");
	}
}
