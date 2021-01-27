package wolt.summer2021.module;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.AllArgsConstructor;
import wolt.summer2021.module.Restaurant.Restaurant;
import wolt.summer2021.module.Restaurant.RestaurantService;
import wolt.summer2021.module.user.User;
import wolt.summer2021.module.user.UserService;

@Controller
@AllArgsConstructor
public class MainController {

	private final RestaurantService restaurantService;
	private final UserService userService;

	@GetMapping("/")
	public String main(Model model) {
		User user = userService.getUserLocation();
		System.out.println("UserLat: " + user.getLat());
		System.out.println("UserLon: " + user.getLon());
		List<Restaurant> popularRestaurants = restaurantService.popularList(user.getLon(), user.getLat());
		List<Restaurant> newRestaurants = restaurantService.newList(user.getLon(), user.getLat());
		List<Restaurant> closeRestaurants = restaurantService.nearByList(user.getLon(), user.getLat());
		model.addAttribute("restaurants", popularRestaurants);
		return "restaurants";

	}
}
