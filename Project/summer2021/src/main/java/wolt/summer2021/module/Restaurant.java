package wolt.summer2021.module;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.ElementCollection;
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

	private LocalDate launch_date;

	private double longitude;

	private double langitude;
}
