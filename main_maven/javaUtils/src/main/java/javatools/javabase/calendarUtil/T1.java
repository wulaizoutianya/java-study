package javatools.javabase.calendarUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class T1 {

	public static void main(String[] args) {
		try {
			/*SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date testDate = sdf.parse("2019-07-31");
			
			Calendar begin = Calendar.getInstance();// 得到一个Calendar的实例
			begin.setTime(testDate); // 设置时间为指定时间
			begin.add(Calendar.MONTH, -1);// 月份减1
			begin.add(Calendar.DATE, 1);// 日期加1
			Long startTime = begin.getTimeInMillis();
			Long time = startTime;
			
			Calendar end = Calendar.getInstance();
			end.setTime(testDate); // 设置时间为指定时间
			Long endTime = end.getTimeInMillis();
			Long oneDay = 1000 * 60 * 60 * 24l;// 一天的时间转化为ms
			List<String> dates = new ArrayList<>();
			
			while (time <= endTime) {
				Date d = new Date(time);
				dates.add(sdf.format(d));
				time += oneDay;
			}
			
			System.out.println(dates.size());
			for (int j = 0; j < dates.size(); j++) {
				System.out.println(dates.get(j));
			}*/
			
			
			/*SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
			Date testDate = sdf.parse("2019-10-29");
			
			Calendar begin = Calendar.getInstance();// 得到一个Calendar的实例
			begin.setTime(testDate); // 设置时间为指定时间
			begin.add(Calendar.YEAR, -1);// 年份减1
			List<String> dates = new ArrayList<>();
			for (int i = 0; i < 12; i++) {
				begin.add(Calendar.MONTH, 1);
				dates.add(sdf.format(begin.getTimeInMillis()));
			}
			
			System.out.println(dates.size());
			for (int j = 0; j < dates.size(); j++) {
				System.out.println(dates.get(j));
			}*/
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date testDate = sdf.parse("2019-07-31");
			
			Calendar begin = Calendar.getInstance();// 得到一个Calendar的实例
			begin.setTime(testDate); // 设置时间为指定时间
			begin.add(Calendar.DATE, -6);// 月份减1
			Long startTime = begin.getTimeInMillis();
			Long time = startTime;
			
			Calendar end = Calendar.getInstance();
			end.setTime(testDate); // 设置时间为指定时间
			Long endTime = end.getTimeInMillis();
			Long oneDay = 1000 * 60 * 60 * 24l;// 一天的时间转化为ms
			List<String> dates = new ArrayList<>();
			
			while (time <= endTime) {
				Date d = new Date(time);
				dates.add(sdf.format(d));
				time += oneDay;
			}
			
			System.out.println(dates.size());
			for (int j = 0; j < dates.size(); j++) {
				System.out.println(dates.get(j));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
