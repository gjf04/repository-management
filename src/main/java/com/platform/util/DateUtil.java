package com.platform.util;

import org.apache.commons.lang.time.DateUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author gaojingfei
 * 20171207
 */
public class DateUtil {
	private StringBuffer buffer = new StringBuffer();
	private static String ZERO = "0";
	private static DateUtils date;
	public static SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
	public static SimpleDateFormat format1 = new SimpleDateFormat(
			"yyyyMMdd HH:mm:ss");
	public static SimpleDateFormat format2 = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm");
	public static SimpleDateFormat format3 = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
	public static SimpleDateFormat format4 = new SimpleDateFormat(
			"yyyy/MM/dd HH:mm:ss");
	public static SimpleDateFormat format5 = new SimpleDateFormat("yyyy-MM-dd");
	public static SimpleDateFormat format6 = new SimpleDateFormat("MM-dd HH:mm");
	public static SimpleDateFormat format7 = new SimpleDateFormat(
			"yyyyMMddHHmmss");
	public static SimpleDateFormat format_year = new SimpleDateFormat(
			"yyyy");
	public static SimpleDateFormat format_month = new SimpleDateFormat(
			"MM");

	public static Date parse(SimpleDateFormat format, String date) {
		Date d = null;
		try {
			synchronized (format) {
				d = format.parse(date);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return d;
	}

	public static String formatDuring(Long mss) {
		if(mss==null)
			return "";
		long days = mss / (1000 * 60 * 60 * 24);
		long hours = (mss % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
		long minutes = (mss % (1000 * 60 * 60)) / (1000 * 60);
		long seconds = (mss % (1000 * 60)) / 1000;
		StringBuffer sb=new StringBuffer();
		if(days>0) sb.append(days + " 天 ");
		if(hours>0) sb.append(hours + " 小时 ");
		if(minutes>0) sb.append(minutes + " 分 ");
		if(seconds>0) sb.append(seconds + " 秒 ");
		return sb.toString();
	}

	public String getNowString() {
		Calendar calendar = getCalendar();
		buffer.delete(0, buffer.capacity());
		buffer.append(getYear(calendar));

		if (getMonth(calendar) < 10) {
			buffer.append(ZERO);
		}
		buffer.append(getMonth(calendar));

		if (getDate(calendar) < 10) {
			buffer.append(ZERO);
		}
		buffer.append(getDate(calendar));
		if (getHour(calendar) < 10) {
			buffer.append(ZERO);
		}
		buffer.append(getHour(calendar));
		if (getMinute(calendar) < 10) {
			buffer.append(ZERO);
		}
		buffer.append(getMinute(calendar));
		if (getSecond(calendar) < 10) {
			buffer.append(ZERO);
		}
		buffer.append(getSecond(calendar));
		return buffer.toString();
	}

	private static int getDateField(Date date, int field) {
		Calendar c = getCalendar();
		c.setTime(date);
		return c.get(field);
	}

	public static int getYearsBetweenDate(Date begin, Date end) {
		int bYear = getDateField(begin, Calendar.YEAR);
		int eYear = getDateField(end, Calendar.YEAR);
		return eYear - bYear;
	}

	public static int getMonthsBetweenDate(Date begin, Date end) {
		int bMonth = getDateField(begin, Calendar.MONTH);
		int eMonth = getDateField(end, Calendar.MONTH);
		return eMonth - bMonth;
	}

	public static int getWeeksBetweenDate(Date begin, Date end) {
		int bWeek = getDateField(begin, Calendar.WEEK_OF_YEAR);
		int eWeek = getDateField(end, Calendar.WEEK_OF_YEAR);
		return eWeek - bWeek;
	}

	public static int getDaysBetweenDate(Date begin, Date end) {
		int bDay = getDateField(begin, Calendar.DAY_OF_YEAR);
		int eDay = getDateField(end, Calendar.DAY_OF_YEAR);
		return eDay - bDay;
	}

	public static void main(String args[]) {

	}

	private int getYear(Calendar calendar) {
		return calendar.get(Calendar.YEAR);
	}

	private int getMonth(Calendar calendar) {
		return calendar.get(Calendar.MONDAY) + 1;
	}

	private int getDate(Calendar calendar) {
		return calendar.get(Calendar.DATE);
	}

	private int getHour(Calendar calendar) {
		return calendar.get(Calendar.HOUR_OF_DAY);
	}

	private int getMinute(Calendar calendar) {
		return calendar.get(Calendar.MINUTE);
	}

	private int getSecond(Calendar calendar) {
		return calendar.get(Calendar.SECOND);
	}

	private static Calendar getCalendar() {
		return Calendar.getInstance();
	}

	public static DateUtils getDateInstance() {
		if (date == null) {
			date = new DateUtils();
		}
		return date;
	}

	public static Date dateAddSub(Date date, int n) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR)
				+ n);
		return calendar.getTime();
	}

	public static Date minuteAddSub(Date date, int n) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MINUTE, n);
		return calendar.getTime();
	}

	public static String format(SimpleDateFormat format, Date expiredTime) {
		if (expiredTime == null) {
			return "";
		}
		synchronized (format) {
			return format.format(expiredTime);
		}
	}

	@SuppressWarnings("unchecked")
	public static <T extends Date> T parse(String dateString,
			DateFormat dateFormat, Class<T> targetResultType) {

		try {
			long time = dateFormat.parse(dateString).getTime();
			Date t = targetResultType.getConstructor(long.class)
					.newInstance(time);
			return (T) t;
		} catch (ParseException e) {
			String errorInfo = "cannot use dateformat:" + dateFormat
					+ " parse datestring:" + dateString;
			throw new IllegalArgumentException(errorInfo, e);
		} catch (Exception e) {
			throw new IllegalArgumentException("error targetResultType:"
					+ targetResultType.getName(), e);
		}
	}

	public static String dateToString(Date date) {
        String str = format2.format(date);
        Date time = null;
        try {
            time = format2.parse( str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return format2.format(time);
	}

	public static String dateToString(Date date,SimpleDateFormat format) {
        String str = format.format(date);
        Date time = null;
        try {
            time = format.parse( str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return format.format(time);
	}
}
