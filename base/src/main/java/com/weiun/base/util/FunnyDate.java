package com.weiun.base.util;

import java.util.Calendar;
import java.util.Date;

/**
 * @description 日期转义类,调用trans()方法可获取如下日期表示:<br>
 *              与当前日期相差13个月之外的,一律返回形如yy年MM月dd日 HH:mm:ss的日期<br>
 *              相差大于12个月小于13个月的,返回1年前<br>
 *              相差大于1个月小于1年的,返回X个月前<br>
 *              相差大于2天小于1个月的,返回X天前<br>
 *              对于今天的时间,相差大于3小时小于小时6的,返回X小时前<br>
 *              相差大于一小时小于3小时的,返回X小时X分钟前<br>
 *              相差在一小时之内的,返回X分钟前<br>
 *              相差在一分钟之内的,返回X秒钟前<br>
 *              对于今天6小时前及昨天,前天的时间,返回上午、深夜等表示形式
 * 
 * @author Frank
 * @version 1.0
 * @create_time 2010-3-23
 */
public class FunnyDate {

	private Calendar calendar;

	public FunnyDate(Calendar calendar) {
		this.calendar = calendar;
	}

	public FunnyDate(long millis) {
		calendar = Calendar.getInstance();
		calendar.setTimeInMillis(millis);
	}

	public FunnyDate(Date date) {
		this(date.getTime());
	}

	public String trans() {
		if (calendar.getTimeInMillis() > System.currentTimeMillis()) {
			throw new IllegalArgumentException("The Date need to transfer must be earlier than now!");
		}

		Calendar current = Calendar.getInstance();
		long distance = current.getTimeInMillis() - calendar.getTimeInMillis();
		int day_distance = distanceOfDay(current, calendar), minute_distance;

		if (day_distance > 365 + 30) {
			return DateUtils.getDateToString("yy年MM月dd日 HH:mm:ss", calendar.getTimeInMillis());
		}
		if (day_distance > 365) {
			return "1年前";
		}

		if (day_distance > 30) {
			return day_distance / 30 + "个月前";
		}

		if (day_distance > 2) {
			return day_distance + "天前";
		}

		minute_distance = current.get(Calendar.MINUTE) - calendar.get(Calendar.MINUTE);

		if (distance > 3 * 60 * 60 * 1000L && distance < 6 * 60 * 60 * 1000L) {
			return "约" + (distance / (60 * 60 * 1000L)) + "小时前";
		}
		if (distance < 3 * 60 * 60 * 1000L && distance > 60 * 60 * 1000L) {
			int minute = (int) (distance / (60 * 1000L));
			return "约" + (minute / 60) + "小时" + (minute_distance == 0 ? "前" : (minute - (minute / 60) * 60) + "分钟前");
		}
		if (distance < 60 * 60 * 1000L && distance > 60 * 1000L) {
			return (distance / (60 * 1000L)) + "分钟前";
		}
		if (distance > 1000L && distance < 60 * 1000L) {
			return (distance / 1000L) + "秒钟前";
		}
		if (distance < 1000L) {
			return "1秒钟前";
		}

		String period = getPeriod(calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE));

		switch (day_distance) {
		case 1:
			return "昨天" + period;
		case 2:
			return "前天" + period;
		default:
			return "今天" + period;
		}
	}

	private String getPeriod(int hour, int minute) {

		int flag = hour * 100 + minute;

		if (flag > 0 && flag <= 400) {
			return "凌晨";
		}
		if (flag > 400 && flag <= 900) {
			return "早上";
		}
		if (flag > 900 && flag <= 1100) {
			return "上午";
		}
		if (flag > 1100 && flag <= 1300) {
			return "中午";
		}
		if (flag > 1300 && flag <= 1700) {
			return "下午";
		}
		if (flag > 1700 && flag <= 2000) {
			return "傍晚";
		}
		if (flag > 2000 && flag <= 2200) {
			return "晚上";
		} else {
			return "深夜";
		}

	}

	private int distanceOfDay(Calendar now, Calendar before) {

		long mill_now = DateUtils.getFutureInMillis(now.getTimeInMillis(), 0);
		long mill_before = DateUtils.getFutureInMillis(before.getTimeInMillis(), 0);

		return (int) ((mill_now - mill_before) / (24 * 60 * 60 * 1000L));
	}

	public static void main(String args[]) {

		Calendar c = Calendar.getInstance();
		c.set(2010, 1, 28, 1, 10, 0);
		System.out.println(DateUtils.getDateToString("", 1272281400000L));

		System.out.println(new FunnyDate(c).trans());

	}
}
