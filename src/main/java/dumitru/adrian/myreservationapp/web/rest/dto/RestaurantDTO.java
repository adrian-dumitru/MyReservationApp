package dumitru.adrian.myreservationapp.web.rest.dto;

import dumitru.adrian.myreservationapp.domain.Restaurant;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Adrian.D on 6/4/2015.
 */
public class RestaurantDTO {

    private String name;

    private String phone;

    private String email;

    private String city;

    private Long user_id;

    public static ArrayList<RestaurantDTO> toRestaurantDTO(List<Restaurant> restaurants){
        ArrayList<RestaurantDTO> result = new ArrayList<>();
        RestaurantDTO restaurantDTO;
        for(Restaurant restaurant : restaurants){
            restaurantDTO = new RestaurantDTO();
            restaurantDTO.setName(restaurant.getName());
            restaurantDTO.setPhone(restaurant.getPhone());
            restaurantDTO.setEmail(restaurant.getEmail());
            if(restaurant.getLocation() != null)
                restaurantDTO.setCity(restaurant.getLocation().getCity());
            restaurantDTO.setUser_id(restaurant.getUser().getId());
            result.add(restaurantDTO);
        }
        return result;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
