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
		List<Restaurant> restaurants = restaurantService.restaurantsInMyArea(user);
		model.addAttribute("restaurants", restaurants);
		return "restaurants";

	}
}
