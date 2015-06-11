package dumitru.adrian.myreservationapp.security;

import dumitru.adrian.myreservationapp.web.rest.util.ReservationUtil;
import org.junit.Test;

import java.util.ArrayList;

/**
 * Created by Adrian.D on 6/11/2015.
 */
public class ScheduleTablesTest {

    @Test
    public void scheduleTablesTest(){
        System.out.println("test1:");
        System.out.println(ReservationUtil.scheduleRestaurantTables(0, 0, 4, 20));
        System.out.println("test2:");
        System.out.println(ReservationUtil.scheduleRestaurantTables(3, 5, 0, 19));
        System.out.println("test3:");
        System.out.println(ReservationUtil.scheduleRestaurantTables(4,3,2,13));
    }

}
