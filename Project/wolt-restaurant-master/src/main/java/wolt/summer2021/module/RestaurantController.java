package wolt.summer2021.module;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.AllArgsConstructor;
import wolt.summer2021.module.Restaurant.Restaurant;
import wolt.summer2021.module.Restaurant.RestaurantService;
import wolt.summer2021.module.user.User;
import wolt.summer2021.module.user.UserService;

@Controller
@AllArgsConstructor
public class RestaurantController {

	private final RestaurantService restaurantService;
	private final UserService userService;

	@GetMapping("/")
	public String main() {	
		User user = userService.getUserLocation();
		restaurantService.restaurantsInMyArea(user);
		return "index";
	}

	
	@PostMapping("/restaurants")
	public String myRestaurants(User user, Model model) {
		model.addAttribute(user);
		return "restaurants";
	}

	

	@GetMapping("/restaurants")
	public String showRestaurants(Model model) {
		List<Restaurant> restaurants = restaurantService.showRestaurants();
		model.addAttribute("restaurants", restaurants);
		return "restaurants";
	}
}
