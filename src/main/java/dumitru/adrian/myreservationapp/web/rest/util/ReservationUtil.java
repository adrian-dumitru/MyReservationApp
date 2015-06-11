package dumitru.adrian.myreservationapp.web.rest.util;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Adrian.D on 6/11/2015.
 */
public class ReservationUtil {

    public static ArrayList<Integer> scheduleRestaurantTables(Integer two_persons_table, Integer four_persons_table,
                                                Integer six_persons_table, Integer persons){

        Integer t2 = 0;
        Integer t4 = 0;
        Integer t6 = 0;

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

}
