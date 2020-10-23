package grabber;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class ParseDate {
    private final String dateAndTime;
    private static final int ZERO_ASCII = 48;

    public ParseDate(String dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    private static Map<Integer, String> initializeDate() {
            Map<Integer, String> map = new HashMap<>();
            map.put(1, "янв");
            map.put(2, "фев");
            map.put(3, "мар");
            map.put(4, "апр");
            map.put(5, "май");
            map.put(6, "июн");
            map.put(7, "июл");
            map.put(8, "авг");
            map.put(9, "сен");
            map.put(10, "окт");
            map.put(11, "ноя");
            map.put(12, "дек");
            return map;
    }
    public LocalDateTime parseMethod() {
        LocalDateTime fulldate;
        String[] dat = dateAndTime.split(", "); {
            int[] date = dateMethod(dat[0]);
            int[] time = timeMethod(dat[1]);
            fulldate = LocalDateTime.of(date[2], date[1], date[0], time[0], time[1]);
        }
        return fulldate;
    }

    private int[] dateMethod(String date) {
        int[] result = new int[3];
        int year = -1;
        int month = -1;
        int day = -1;
        if (date.equals("вчера")) {
            LocalDate dateL = LocalDate.now();
            if (dateL.getDayOfMonth() == 1) {
                year = dateL.getYear();
                month = dateL.getMonthValue() - 1;
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.MONTH, -1);
                day = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
            } else if (dateL.getDayOfMonth() == 1 && dateL.getMonthValue() == 1) {
                year = dateL.getYear() - 1;
                month = 12;
                day = 31;
            } else {
                year = dateL.getYear();
                month = dateL.getMonthValue();
                day = dateL.getDayOfMonth() - 1;
            }
        } else if (date.equals("сегодня")) {
            LocalDate dateL = LocalDate.now();
            year = dateL.getYear();
            month = dateL.getMonthValue();
            day = dateL.getDayOfMonth();
        } else {
            String[] newDate = date.split(" "); // day / month(3symbol) / year (YY)
            for (Map.Entry<Integer, String> mp : initializeDate().entrySet()) {
                Integer keyMP = mp.getKey();
                String monthMP = mp.getValue();
                if (monthMP.equals(newDate[1])) {
                    month = keyMP;
                    break;
                }
            }
            year = 2000 + Integer.parseInt(newDate[2]);
            day = Integer.parseInt(newDate[0]);
        }
        result[0] = day;
        result[1] = month;
        result[2] = year;
        return result;
    }

    private int[] timeMethod(String time) {
        int[] result = new int[2];
        int hour = -1;
        int minute = -1;
        String[] times = time.split(":"); // [0] hours === [1] minutes
        if (times[0].charAt(0) == ZERO_ASCII) {
            if (times[1].charAt(0) == ZERO_ASCII) {
                String hourstr = this.zeroElementIs0(times[0]);
                String minutestr = this.zeroElementIs0(times[1]);
                hour = Integer.parseInt(hourstr);
                minute = Integer.parseInt(minutestr);
            } else {
                String hourstr = this.zeroElementIs0(times[0]);
                hour = Integer.parseInt(hourstr);
                minute = Integer.parseInt(times[1]);
            }
        } else if (times[1].charAt(0) == ZERO_ASCII) {
            hour = Integer.parseInt(times[0]);
            String minutestr = this.zeroElementIs0(times[1]);
            minute = Integer.parseInt(minutestr);
        } else {
            hour = Integer.parseInt(times[0]);
            minute = Integer.parseInt(times[1]);
        }
        result[0] = hour;
        result[1] = minute;
        return result;
    }

    private String zeroElementIs0(String time) {
        return time.replaceAll("(?<!a)","");
    }
}
