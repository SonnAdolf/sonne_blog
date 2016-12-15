package sonn.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
* @ClassName: TimeUtils 
* @Description: 时间工具类
* @author sonne
* @date 2016-12-14 下午8:05:14 
* @version 1.0
 */
public final class TimeUtils {
	
	private TimeUtils() {
	}
	
	/*
	 * 获取用户注册至今过了多少年-月
	 */
	public static int[] getHowLongFromNow(Date oldDate) throws ParseException {
		//最开始的用户没有这个字段，date为null
		if (oldDate == null) {
			DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
			oldDate = dateFormat1.parse("2016-11-06");
		}
        
        Calendar startCalendar = Calendar.getInstance();
        Calendar endCalendar = Calendar.getInstance();

        startCalendar.setTime(oldDate);
        endCalendar.setTime(new Date());
        
        int day = endCalendar.get(Calendar.DAY_OF_MONTH)
                - startCalendar.get(Calendar.DAY_OF_MONTH);
        int month = endCalendar.get(Calendar.MONTH)
                - startCalendar.get(Calendar.MONTH);
        int year = endCalendar.get(Calendar.YEAR)
                - startCalendar.get(Calendar.YEAR);

        if (day < 0) {
            month--;
        }

        if (month < 0) {
            month += 12;
            year--;
        }
        
        int[] RET_ARR = new int[3];
        /*注册超过一年的用户*/
        RET_ARR[0] = year;
        RET_ARR[1] = month;
		return RET_ARR;
	}

}
