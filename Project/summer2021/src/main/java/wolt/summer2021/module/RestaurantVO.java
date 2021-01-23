package wolt.summer2021.module;

import java.time.LocalDate;
import java.util.List;

import lombok.Data;

@Data
public class RestaurantVO {

	private RestaurantData[] restaurants;

	@Data
	static class RestaurantData {

		private String blurhash;

		private String name;

		private boolean online;

		private double popularity;

		private LocalDate launch_date;

		private List<Double> location;

	}

}
