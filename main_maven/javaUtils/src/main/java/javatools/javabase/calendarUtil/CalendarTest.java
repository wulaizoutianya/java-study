package javatools.javabase.calendarUtil;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CalendarTest {

	public static void main(String[] args) {
		try {
			String planStartTime = "2018-03-31";
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			java.util.Date date = new java.util.Date();// sdf.parse(planStartTime);
			Calendar calendarDay = Calendar.getInstance();
			calendarDay.setTime(date);
			calendarDay.add(Calendar.DAY_OF_MONTH, -7);
			System.out.println(sdf.format(calendarDay.getTime()));
			Calendar calendarMonth = Calendar.getInstance();
			calendarMonth.setTime(date);
			calendarMonth.add(Calendar.MONTH, -1);
			System.out.println(sdf.format(calendarMonth.getTime()));

			Calendar calendarYear = Calendar.getInstance();
			calendarYear.setTime(date);
			calendarYear.add(Calendar.YEAR, -1);
			System.out.println(sdf.format(calendarYear.getTime()));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
