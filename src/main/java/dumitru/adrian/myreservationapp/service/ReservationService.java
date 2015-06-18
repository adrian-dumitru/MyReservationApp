package dumitru.adrian.myreservationapp.service;

import dumitru.adrian.myreservationapp.domain.*;
import dumitru.adrian.myreservationapp.repository.*;
import dumitru.adrian.myreservationapp.service.util.ReservationUtil;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Adrian.D on 6/17/2015.
 */

@Service
@Transactional
public class ReservationService {

    @Inject
    private ReservationRepository reservationRepository;

    @Inject
    private ProgramRepository programRepository;

    @Inject
    private RestaurantRepository restaurantRepository;

    @Inject
    private UserRepository userRepository;

    @Inject
    private Reservation_tablesRepository reservation_tablesRepository;

    public ArrayList<Integer> getNumberOfAvailableTables(List<Reservation> reservations, Restaurant_tables restaurant_tables){
        ArrayList<Integer> tables = new ArrayList<>();

        Integer two_persons_table  = 0;
        Integer four_persons_table = 0;
        Integer six_persons_table  = 0;

        if(reservations.size() == 0){
            tables.add(restaurant_tables.getTwo_persons_table());
            tables.add(restaurant_tables.getFour_persons_table());
            tables.add(restaurant_tables.getSix_persons_table());
        }else{
            for(Reservation reservation : reservations){
                two_persons_table += reservation.getReservation_tables().getTwo_persons_table();
                four_persons_table += reservation.getReservation_tables().getFour_persons_table();
                six_persons_table += reservation.getReservation_tables().getSix_persons_table();
            }

            tables.add(restaurant_tables.getTwo_persons_table() - two_persons_table);
            tables.add(restaurant_tables.getFour_persons_table() - four_persons_table);
            tables.add(restaurant_tables.getSix_persons_table() - six_persons_table);

        }

        return tables;

    }

    public void checkReservation(Reservation reservation) throws Exception {

        String day_of_week = ReservationUtil.getDayOfWeek(reservation.getDay());
        User current_user = userRepository.findOneByLogin(reservation.getUser().getLogin()).get();
        Long user_id = current_user.getId();
        Restaurant restaurant = restaurantRepository.findOneByUserId(user_id);
        Long restaurant_id = restaurant.getId();
        Program program = programRepository.findOneByRestaurantIdAndDay(restaurant_id, day_of_week);

        Date reservation_start_hour = ReservationUtil.createDate(reservation.getDay(), reservation.getStart_hour());
        Date reservation_end_hour = ReservationUtil.createDate(reservation.getFinish(), reservation.getEnd_hour());
        Date program_start_hour = ReservationUtil.createDate(reservation.getDay(), program.getStart_hour());
        Date program_end_hour = ReservationUtil.createDate(reservation.getDay(), program.getEnd_hour());

        if(program_end_hour.before(program_start_hour))
            program_end_hour = new Date(program_end_hour.getTime() + (1000 * 60 * 60 * 24));

        if(reservation_start_hour.before(program_start_hour) || reservation_end_hour.after(program_end_hour))
            throw new Exception("NOT IN PROGRAM PERIOD") ;

        List<Reservation> reservations = reservationRepository.findAllBetweenDates(ReservationUtil.convertDateToString(reservation_start_hour),
            ReservationUtil.convertDateToString(reservation_end_hour));

        ArrayList<Integer> available_tables = this.getNumberOfAvailableTables(reservations, restaurant.getRestaurant_tables());

        Integer persons = available_tables.get(0) * 2;
        persons = persons + available_tables.get(1) * 4;
        persons = persons + available_tables.get(2) * 6;

        ArrayList<Integer> reserved_tables;

        if(persons >= reservation.getPersons()) {
            reserved_tables = ReservationUtil.scheduleRestaurantTables(available_tables, reservation.getPersons());
            Reservation_tables reservation_tables = new Reservation_tables(reserved_tables);
            reservation_tablesRepository.save(reservation_tables);
            reservation.setReservation_tables(reservation_tables);
            reservationRepository.save(reservation);
        }else
            throw new Exception("NOT ENOUGH TABLES");
    }

}
