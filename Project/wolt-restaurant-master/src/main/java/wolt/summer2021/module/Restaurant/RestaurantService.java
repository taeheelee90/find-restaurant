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

	@PostConstruct
	public void initRestaurantData() throws IOException {
		File resource = new ClassPathResource("restaurants.json").getFile();
		String restaurants = new String(Files.readAllBytes(resource.toPath()));
		RestaurantVO restaurantVO = objectMapper.readValue(restaurants, RestaurantVO.class);

		RestaurantData[] restaurantData = restaurantVO.getRestaurants();

		for (RestaurantData data : restaurantData) {
			Restaurant restaurant = modelMapper.map(data, Restaurant.class);
			restaurant.setLongitude(data.getLocation().get(0));
			restaurant.setLatitude(data.getLocation().get(1));
			repository.save(restaurant);
		}

		System.out.println("Data saved.");

	}

	public List<Restaurant> showRestaurants() {
		return repository.findAll();
	}

	public void restaurantsInMyArea(User user) {
		final double longitude = user.getLon();
		final double latitude = user.getLat();

		List<Restaurant> restaurants = repository.findAll();
		List<Restaurant> restaurantsInMyArea = new ArrayList<>();
		for (Restaurant r : restaurants) {
			double distance = calcDistance(latitude, longitude, r.getLatitude(), r.getLongitude());
			if (distance <= 1.5) {
				restaurantsInMyArea.add(r);
			}
		}

		showFinalList(restaurantsInMyArea);
		
		System.out.println(restaurantsInMyArea.size());
		for(Restaurant r : restaurantsInMyArea) {
			
			System.out.println(r.getName());
			System.out.println(r.getPopularity());
			System.out.println(r.getLatitude());
			System.out.println(r.getLongitude());
			System.out.println(r.getLaunch_date());
			System.out.println("============================================");
		
		}
		System.out.println(restaurantsInMyArea);
	}

	private void showFinalList(List<Restaurant> restaurantsInMyArea) {
		/*
		 * “Popular Restaurants”: highest popularity value first (descending order) 
		 * “New Restaurants”: Newest launch_date first (descending). This list has also a special rule: 
		 * launch_date must be no older than 4 months. 
		 * “Nearby Restaurants”: Closest to the given location first (ascending).
		 * 
		 */
		popularList(restaurantsInMyArea);
		newList(restaurantsInMyArea);
		nearByList(restaurantsInMyArea);
	}

	private void newList(List<Restaurant> restaurantsInMyArea) {
		// TODO Auto-generated method stub
		
	}

	private void nearByList(List<Restaurant> restaurantsInMyArea) {
		// TODO Auto-generated method stub
		
	}

	private void popularList(List<Restaurant> restaurantsInMyArea) {
		// TODO Auto-generated method stub
		
	}

	private double calcDistance(double userLat, double userLong, double restaurantLat, double restaurantLong) {
		if ((userLat == restaurantLat) && (userLong == restaurantLong)) {
			return 0;
		} else {
			double theta = userLong - restaurantLong;
			double dist = Math.sin(Math.toRadians(userLat)) * Math.sin(Math.toRadians(restaurantLat))
					+ Math.cos(Math.toRadians(userLat)) * Math.cos(Math.toRadians(restaurantLat))
							* Math.cos(Math.toRadians(theta));
			dist = Math.acos(dist);
			dist = Math.toDegrees(dist);
			dist = dist * 60 * 1.1515 * 1.609344;

			return (dist);
		}
	}

}
