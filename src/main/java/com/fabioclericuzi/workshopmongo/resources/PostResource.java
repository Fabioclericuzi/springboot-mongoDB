package com.fabioclericuzi.workshopmongo.resources;

import java.net.URI;
import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fabioclericuzi.workshopmongo.domain.Post;
import com.fabioclericuzi.workshopmongo.domain.User;
import com.fabioclericuzi.workshopmongo.dto.UserDTO;
import com.fabioclericuzi.workshopmongo.resources.util.URL;
import com.fabioclericuzi.workshopmongo.services.PostService;

@RestController
@RequestMapping(value ="/posts")
public class PostResource {
	
	@Autowired
	private PostService service;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Post> findById(@PathVariable String id) {
		Post obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}
	
	@RequestMapping(value = "/titlesearch", method = RequestMethod.GET)
	public ResponseEntity<List<Post>> findByTitle(@RequestParam(value = "text", defaultValue ="") String text) {
		text = URL.decodeParam(text);
		List<Post> list = service.findByTitle(text);
		return ResponseEntity.ok().body(list);
	}
	
	@RequestMapping(value = "/fullsearch", method = RequestMethod.GET)
	public ResponseEntity<List<Post>> fullSearch(
			@RequestParam(value = "text", defaultValue ="") String text,
			@RequestParam(value = "minDate", defaultValue ="") String minDate,
			@RequestParam(value = "maxDate", defaultValue ="") String maxDate) {
		text = URL.decodeParam(text);
		Date min = (Date) URL.convertDate(minDate, new Date(0L));
		Date max = (Date) URL.convertDate(minDate, new Date(0));
		List<Post> list = service.fullSearch(text, min, max);
		return ResponseEntity.ok().body(list);
	}
}
