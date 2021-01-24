package wolt.summer2021.module.user;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;
import wolt.summer2021.module.Restaurant.Restaurant;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

	@Autowired private final HttpServletRequest request;
	private final RestTemplate restTemplate;

	final String IP_RENDER_API = "http://ip-api.com/json/{query}?fields=lat,lon";

	public User getUserLocation() {
		String userIP = "94.237.113.214";
		// request.getRemoteAddr();

		String IP_RENDER_API = "http://ip-api.com/json/" + userIP + "?fields=lat,lon";
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<>(headers);
		System.out.println(IP_RENDER_API);
		
		User user = restTemplate.exchange(IP_RENDER_API, HttpMethod.GET, entity, User.class).getBody();
	
		System.out.println(user.getLon());
		System.out.println(user.getLat());
		return user;
	}
}
