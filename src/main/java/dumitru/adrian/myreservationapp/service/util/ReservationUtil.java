package dumitru.adrian.myreservationapp.service.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Adrian.D on 6/11/2015.
 */
public class ReservationUtil {


    public static ArrayList<Integer> scheduleRestaurantTables(ArrayList<Integer> tables, Integer persons){

        Integer t2 = 0;
        Integer t4 = 0;
        Integer t6 = 0;

        Integer two_persons_table = tables.get(0);
        Integer four_persons_table = tables.get(1);
        Integer six_persons_table = tables.get(2);

        ArrayList<Integer> result = new ArrayList<>();

        while(t6 <= six_persons_table && (persons >= 5 || (two_persons_table * 2 + four_persons_table * 4 < persons)) && six_persons_table != 0) {
            t6++;
            persons -= 6;
        }
        while(t4 <= four_persons_table && (persons >= 3 || (two_persons_table * 2 < persons)) && four_persons_table != 0){
            t4++;
            persons -= 4;
        }
        while(t2 <= two_persons_table && persons >= 1 && two_persons_table != 0){
            t2++;
            persons -= 2;
        }

        result.add(t2);
        result.add(t4);
        result.add(t6);

        return result;
    }

    public static Date createDate(Date day, Date time){

        Calendar calendar = Calendar.getInstance();
        calendar.set(1900 + day.getYear(), day.getMonth(), day.getDate(), time.getHours(), time.getMinutes(), time.getSeconds());

        Date result = calendar.getTime();
        return result;
    }

    public static String convertDateToString(Date date){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String resultDate = dateFormat.format(date);
        return resultDate;
    }

    public static String getDayOfWeek(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int day_of_week_nr = calendar.get(Calendar.DAY_OF_WEEK);
        String day_of_week = null;
        switch(day_of_week_nr){
            case 1:
                day_of_week = "sunday";
                break;
            case 2:
                day_of_week = "monday";
                break;
            case 3:
                day_of_week = "tuesday";
                break;
            case 4:
                day_of_week = "wednesday";
                break;
            case 5:
                day_of_week = "thursday";
                break;
            case 6:
                day_of_week = "friday";
                break;
            case 7:
                day_of_week = "saturday";
                break;
        }

        return day_of_week;
    }


}
