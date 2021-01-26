package wolt.summer2021.infra;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {
	
	// DELETE: NOT IN USE
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	// FOR TEST: MAP JSON to OBJECT
	@Bean
	public ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper;
	}
}
