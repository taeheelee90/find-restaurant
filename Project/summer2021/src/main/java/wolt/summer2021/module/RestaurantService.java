package wolt.summer2021.module;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import org.json.simple.parser.ParseException;
import org.modelmapper.ModelMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import wolt.summer2021.module.RestaurantVO.RestaurantData;

@Service
@Transactional
@RequiredArgsConstructor
public class RestaurantService {

	private final ObjectMapper objectMapper;
	private final ModelMapper modelMapper;
	private final RestaurantRepository repository;

	// ToDo: initData - Save all to repository @PostConstrucot

	public RestaurantVO getRestaurants() throws IOException, ParseException {
		File resource = new ClassPathResource("restaurants.json").getFile();
		String restaurants = new String(Files.readAllBytes(resource.toPath()));
		RestaurantVO vo = objectMapper.readValue(restaurants, RestaurantVO.class);
		return vo;
	}

	public List<Restaurant> renderJson(RestaurantVO restaurantVO) {
		RestaurantData[] restaurantData = restaurantVO.getRestaurants();

		for (RestaurantData data : restaurantData) {
			Restaurant restaurant = modelMapper.map(data, Restaurant.class);
			restaurant.setLongitude(data.getLocation().get(0));
			restaurant.setLangitude(data.getLocation().get(1));
			repository.save(restaurant);
		}
		return repository.findAll();
	}
}
