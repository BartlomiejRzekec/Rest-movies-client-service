package com.example.resource;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.model.Movie;

@RestController
@RequestMapping("/client")
public class ClientResource {
	
	@Autowired
	private RestTemplate restTemplate;
	
	
	@GetMapping("/findMovie/{title}")
	public Movie findMovieByTitle(@PathVariable String title) {
		
		ResponseEntity<Movie> userResponse = restTemplate.getForEntity("http://localhost:8080/movies/findMovie/{title}", Movie.class, title);
		
		return userResponse.getBody();
		
	}
	
	@GetMapping("/findByGenre/{genre}")
	public List<Movie> findMovieByGenre(@PathVariable String genre){
		
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<Object> requestEntity = new HttpEntity<>(headers);
		
		ResponseEntity<List<Movie>> userResponse = restTemplate.exchange("http://localhost:8080/movies/findByGenre/{genre}",
																		 HttpMethod.GET,
																		 requestEntity,
																		 new ParameterizedTypeReference<List<Movie>>() {},
																		 genre);
		
		return userResponse.getBody();
		
	}
	
	@GetMapping("/findAll")
	public List<Movie> findAllMovies(){
		ResponseEntity<List<Movie>> userResponse = restTemplate.exchange("http://localhost:8080/movies/findAll",
				 HttpMethod.GET,
				 null,
				 new ParameterizedTypeReference<List<Movie>>() {});
		
		return userResponse.getBody();
	}
	
	@PostMapping("/save")
	public Movie saveMovie(@RequestBody Movie movie) {
		ResponseEntity<Movie> userResponse = restTemplate.postForEntity("http://localhost:8080/movies/save", movie, Movie.class);
		return userResponse.getBody();
	}
	@PostMapping("/saveWithLocation")
	public URI saveMovieWithLocation(@RequestBody Movie movie) {
		URI userResponse = restTemplate.postForLocation("http://localhost:8080/movies/save", movie);
		return userResponse;
	}
	
	@PostMapping("/saveMovies")
	public List<Movie> saveMovies(@RequestBody List<Movie> listOfmovies){
		
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<Object> requestEntity = new HttpEntity<Object>(listOfmovies, headers);
		
		ResponseEntity<List<Movie>> userRequest = restTemplate.exchange("http://localhost:8080/movies/saveMovies",
																		HttpMethod.POST,
																		requestEntity,
																		new ParameterizedTypeReference<List<Movie>>() {});
		
		return userRequest.getBody();
	}

}
