package wolt.summer2021.module;

import java.io.IOException;
import java.util.List;

import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class RestaurantController {

	private final RestaurantService restaurantService;
	
	@GetMapping("/rest-restaurants")
	public @ResponseBody String restRestaurants() throws IOException, ParseException {
		return restaurantService.getRestaurants().toString();
	}
	
	@GetMapping("/restaurants")
	public String showRestaurants(Model model) throws IOException, ParseException {
		RestaurantVO data = restaurantService.getRestaurants();
		List<Restaurant> restaurants = restaurantService.renderJson(data);		
		model.addAttribute("restaurants", restaurants);
		return "restaurants";
	}
	
	
}
