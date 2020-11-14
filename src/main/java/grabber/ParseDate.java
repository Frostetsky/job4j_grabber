package grabber;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public class ParseDate {
    private final String dateAndTime;

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
        String[] dat = dateAndTime.split(", ");
        int[] date = dateMethod(dat[0]);
        int[] time = timeMethod(dat[1]);
        fulldate = LocalDateTime.of(date[2], date[1], date[0], time[0], time[1]);
        return fulldate;
    }

    private int[] dateMethod(String date) {
        int[] result = new int[3];
        int year = -1;
        int month = -1;
        int day = -1;
        if (date.equals("вчера")) {
            LocalDate dateL = LocalDate.now();
            LocalDate yesterday = dateL.minusDays(1);
                year = yesterday.getYear();
                month = yesterday.getMonthValue();
                day = yesterday.getDayOfMonth();
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
        String[] times = time.split(":"); // [0] hours === [1] minutes
        LocalTime timeLC = LocalTime.of(Integer.parseInt(times[0]), Integer.parseInt(times[1]));
        result[0] = timeLC.getHour();
        result[1] = timeLC.getMinute();
        return result;
    }
}
