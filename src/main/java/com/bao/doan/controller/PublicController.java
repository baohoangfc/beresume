package com.bao.doan.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bao.doan.entity.MovieComment;
import com.bao.doan.service.CategoryService;
import com.bao.doan.service.MovieService;

@RestController
@RequestMapping("/movie")
@Component
public class PublicController {
	@Autowired
	private MovieService movieService;
	
	@Autowired
	private CategoryService categoryService;
	
	@ResponseBody
	@GetMapping(value = "/ds", produces = { MediaType.APPLICATION_JSON_VALUE })
	@CrossOrigin(origins = { "*" })
	public Map<String, Object> getDanhSachMovie() {		
		return movieService.getDanhSachMovie();
	}
	
	@ResponseBody
	@GetMapping(value = "/ds/hot", produces = { MediaType.APPLICATION_JSON_VALUE })
	@CrossOrigin(origins = { "*" })
	public Map<String, Object> getDanhSachMovieHot() {
		return movieService.getDanhSachMovieHot();
	}
	
	@ResponseBody
	@GetMapping(value = "/id/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
	@CrossOrigin(origins = { "*" })
	public Map<String, Object> getMovieById(@PathVariable long id) {
		return movieService.getMovieById(id);
	}
	
	@PostMapping(value = "/comment", produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	@CrossOrigin(origins = { "*" })
	public Map<String, Object> commentMovie(@RequestBody MovieComment movieComment) {
		return movieService.commentMovie(movieComment);
	}
	
	@GetMapping(value = "/comment/{movieid}", produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	@CrossOrigin(origins = { "*" })
	public Map<String, Object> getCommentByMovieid(@PathVariable long movieid) {
		return movieService.getCommentByMovieid(movieid);
	}
	
	@ResponseBody
	@GetMapping(value = "/category/{cid}", produces = { MediaType.APPLICATION_JSON_VALUE })
	@CrossOrigin(origins = { "*" })
	public Map<String, Object> getMovieByCategoryId(@PathVariable long cid) {
		return movieService.getListMovieByCatId(cid);
	}
	
	@ResponseBody
	@GetMapping(value = "/category/ds", produces = { MediaType.APPLICATION_JSON_VALUE })
	@CrossOrigin(origins = { "*" })
	public Map<String, Object> getDanhSachCategory() {
		return categoryService.getDanhSachCategory();
	}
	
//	@ResponseBody
//	@GetMapping(value = "/search/{search}", produces = { MediaType.APPLICATION_JSON_VALUE })
//	@CrossOrigin(origins = { "*" })
//	public Map<String, Object> getMovieByCategoryId(@PathVariable String search) {
//		return movieService.searchPhim(search);
//	}
	
	@PostMapping(value = "/capnhat/view/week", produces = { MediaType.APPLICATION_JSON_VALUE })
	@CrossOrigin(origins = { "*" })
	@ResponseBody
	@Scheduled(cron = "0 0 0 * * ?")
	public Map<String, Object> viewPhimByWeek() {
		return movieService.capNhatViewByWeek();
	}
	
	@ResponseBody
	@GetMapping(value = "/ds/week", produces = { MediaType.APPLICATION_JSON_VALUE })
	@CrossOrigin(origins = { "*" })
	public Map<String, Object> getListWeek() {
		return movieService.getListWeek();
	}
	
	@ResponseBody
	@GetMapping(value = "/search/{param}", produces = { MediaType.APPLICATION_JSON_VALUE })
	@CrossOrigin(origins = { "*" })
	public Map<String, Object> searchMovie(@PathVariable String param) {
		return movieService.searchMovie(param);
	}

}
