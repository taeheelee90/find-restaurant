package wolt.summer2021.module.Restaurant;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.modelmapper.ModelMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import wolt.summer2021.module.Restaurant.RestaurantVO.RestaurantData;
import wolt.summer2021.module.user.User;

@Service
@Transactional
@RequiredArgsConstructor
public class RestaurantService {

	private final ObjectMapper objectMapper;
	private final ModelMapper modelMapper;
	private final RestaurantRepository repository;

	// Automatically save restaurant data from 'restaurants.json' to DB
	@PostConstruct
	public void initRestaurantData() throws IOException {
		// 1. Read data from file
		File resource = new ClassPathResource("restaurants.json").getFile();
		String restaurants = new String(Files.readAllBytes(resource.toPath()));

		// 2. Deserialize JSON content to RestaurantVO class
		RestaurantVO restaurantVO = objectMapper.readValue(restaurants, RestaurantVO.class);

		// 3. Save restaurant data to DB
		RestaurantData[] restaurantData = restaurantVO.getRestaurants();
		for (RestaurantData data : restaurantData) {
			Restaurant restaurant = modelMapper.map(data, Restaurant.class);
			restaurant.setLongitude(data.getLocation().get(0));
			restaurant.setLatitude(data.getLocation().get(1));
			repository.save(restaurant);
		}
		// For TEST
		System.out.println("Data saved.");

	}

	// FOR TEST: Return final lists to request
	public List<Restaurant> restaurantsInMyArea(User user) {
		// 1. Get user location
		final double userLon = user.getLon();
		final double userLat = user.getLat();

		List<Restaurant> popularRestaurants = popularList(userLon, userLat);
		List<Restaurant> newRestaurants = newList(userLon, userLat);
		List<Restaurant> nearByRestaurants = nearByList(userLon, userLat);

		return popularRestaurants;

	}

	// Return 10 popular restaurants in descending order
	public List<Restaurant> popularList(double userLon, double userLat) {
		List<Restaurant> restaurants = repository.findByOnlineOrderByPopularityDesc(true);

		// Remove too far (>1.5km) restaurants
		List<Restaurant> toRemove = new ArrayList();
		// DELETE
		System.out.println("Popular-first size" + restaurants.size());

		for (Restaurant r : restaurants) {
			if (calcDistance(userLon, userLat, r.getLongitude(), r.getLatitude()) >= 1.5) {		
				toRemove.add(r);
			}
		}
		// DELETE
		System.out.println("Popular-to remove " + toRemove.size());
		restaurants.removeAll(toRemove);
		// DELETE
		System.out.println("Popular-after remove " + restaurants.size());
		// ToDO: limit size
		return restaurants;
	}

	// Return 10 newest restaurants in descending order
	public List<Restaurant> newList(double userLon, double userLat) {
		List<Restaurant> restaurants = repository.findByOnlineOrderByLaunchDateDesc(true);

		// Remove restaurants launched more than 4 months ago.
		restaurants.removeIf(r -> r.opendFourMonthsAgo());

		// Remove too far (>1.5km) restaurants
		List<Restaurant> toRemove = new ArrayList();
		// DELETE
		System.out.println("New-first size" + restaurants.size());
		for (Restaurant r : restaurants) {
			if (calcDistance(userLon, userLat, r.getLongitude(), r.getLatitude()) >= 1.5) {
				toRemove.add(r);
			}
		}
		// DELETE
		System.out.println("New-to remove " + toRemove.size());
		restaurants.removeAll(toRemove);
		// DELETE
		System.out.println("New-after remove " + restaurants.size());
		// ToDO: limit size
		return restaurants;

	}

	// Return 10 newest restaurants in ascending order
	public List<Restaurant> nearByList(double userLon, double userLat) {
		List<Restaurant> restaurants = repository.findByOnline(true);
		// Remove too far (>1.5km) restaurants
		List<Restaurant> toRemove = new ArrayList();
		// DELETE
		System.out.println("Near-first size" + restaurants.size());
		for (Restaurant r : restaurants) {
			if (calcDistance(userLon, userLat, r.getLongitude(), r.getLatitude()) >= 1.5) {
				toRemove.add(r);
			}
		}
		// DELETE
		System.out.println("Near-to remove " + toRemove.size());
		restaurants.removeAll(toRemove);
		// DELETE
		System.out.println("Near-after remove " + restaurants.size());

		// ToDo: Make order & limit size
		return restaurants;

	}

	// Calculate distance between User location and Restaurant
	private double calcDistance(double userLong, double userLat, double restaurantLong, double restaurantLat) {

		userLat = Math.toRadians(userLat);
		userLong = Math.toRadians(userLong);
		restaurantLat = Math.toRadians(restaurantLat);
		restaurantLong = Math.toRadians(restaurantLong);

		double earthRadius = 6371.01; // for KM
		return earthRadius * Math.acos(Math.sin(userLat) * Math.sin(restaurantLat)
				+ Math.cos(userLat) * Math.cos(restaurantLat) * Math.cos(userLong - restaurantLong));
	}

}
