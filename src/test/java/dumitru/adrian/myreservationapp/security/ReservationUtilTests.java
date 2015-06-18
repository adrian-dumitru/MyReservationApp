package dumitru.adrian.myreservationapp.security;

import dumitru.adrian.myreservationapp.service.util.ReservationUtil;
import org.junit.Test;

import java.util.Date;

/**
 * Created by Adrian.D on 6/11/2015.
 */
public class ReservationUtilTests {

    @Test
    public void getDayOfWeekTest(){
        Date date = new Date();
        System.out.println(ReservationUtil.getDayOfWeek(new Date()).toLowerCase());
    }

}
