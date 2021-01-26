package wolt.summer2021.module.Restaurant;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Builder
public class Restaurant {

	@Id
	@GeneratedValue
	private Long id;

	private String blurhash;

	private String name;

	private boolean online;

	private double popularity;

	private LocalDate launchDate;

	private double longitude;

	private double latitude;
	
	public boolean opendFourMonthsAgo() {
		return this.launchDate.isBefore(LocalDate.now().minusMonths(4));
	}
	/*
	public boolean isTooFar() {
		calcDistance this.getLatitude()
		return return this.;
	}
	
	
	List<Restaurant> restaurantsInMyArea = new ArrayList<>();

		// Include only restaurants within 1.5km from user location
		for (Restaurant r : restaurants) {
			double distance = calcDistance(userLon, userLat, r.getLatitude(), r.getLongitude());
			if (distance <= 1.5) {
				restaurantsInMyArea.add(r);
			}
		}
		System.out.println(restaurantsInMyArea);
	 * 
	 * 
	 * */

	
}
