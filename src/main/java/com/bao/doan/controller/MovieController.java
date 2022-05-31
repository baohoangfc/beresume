package com.bao.doan.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bao.doan.entity.Movie;
import com.bao.doan.entity.MovieRating;
import com.bao.doan.service.MovieService;

@RestController
@RequestMapping("/admin/movie")
public class MovieController {
	@Autowired
	private MovieService movieService;

	@ResponseBody
	@GetMapping(value = "/ds", produces = { MediaType.APPLICATION_JSON_VALUE })
	@CrossOrigin(origins = { "*" })
	public Map<String, Object> getDanhSachMovie() {
		return movieService.getDanhSachMovie();
	}

	@PostMapping(value = "/themmoi", produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	@CrossOrigin(origins = { "*" })
	public Map<String, Object> themMoiMovie(@RequestBody Movie movie) {
		return movieService.themMoiMovie(movie);
	}

	@ResponseBody
	@PostMapping(value = "/xoa", produces = { MediaType.APPLICATION_JSON_VALUE })
	@CrossOrigin(origins = { "*" })
	public Map<String, Object> xoaMovie(@RequestBody Map<String, Object> ids) {
		String listId = ids.get("ids").toString();
		return movieService.xoaMovie(listId);
	}

	@ResponseBody
	@GetMapping(value = "/id/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
	@CrossOrigin(origins = { "*" })
	public Map<String, Object> getMovieById(@PathVariable long id) {
		return movieService.getMovieById(id);
	}

	@PostMapping(value = "/movierated", produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	@CrossOrigin(origins = { "*" })
	public Map<String, Object> getUserMovieRated(@RequestBody MovieRating movieRating) {
		return movieService.getUserMovieRated(movieRating.getMovieid(), movieRating.getUserid());
	}

	@PostMapping(value = "/rating", produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	@CrossOrigin(origins = { "*" })
	public Map<String, Object> danhgiaMovie(@RequestBody MovieRating movieRating) {
		return movieService.danhgiaMovie(movieRating);
	}

	@ResponseBody
	@PostMapping(value = "/listbyuser", produces = { MediaType.APPLICATION_JSON_VALUE })
	@CrossOrigin(origins = { "*" })
	public Map<String, Object> getListMovieByUserId(@RequestBody MovieRating movieRating) {
		return movieService.getListMovieByUsername(movieRating.getUserid());
	}

	@ResponseBody
	@PostMapping(value = "/listbynotuser", produces = { MediaType.APPLICATION_JSON_VALUE })
	@CrossOrigin(origins = { "*" })
	public Map<String, Object> getListMovieByNotUser(@RequestBody MovieRating movieRating) {
		return movieService.getListMovieByNotUser(movieRating.getUserid());
	}

	@ResponseBody
	@PostMapping(value = "/add/value", produces = { MediaType.APPLICATION_JSON_VALUE })
	@CrossOrigin(origins = { "*" })
	public Map<String, Object> addValue(@RequestBody Map<String, Object> thongtin) {
		String listId = thongtin.get("ids").toString();
		long userid = ((Number) thongtin.get("userid")).longValue();
		return movieService.addValue(listId, userid);
	}
	
	@PostMapping(value = "/capnhat", produces = { MediaType.APPLICATION_JSON_VALUE })
	@CrossOrigin(origins = { "*" })
	@ResponseBody
	public Map<String, Object> capNhatPhim(@RequestBody Movie movie) {
		return movieService.capNhatPhim(movie);
	}
	
	@PostMapping(value = "/capnhat/view", produces = { MediaType.APPLICATION_JSON_VALUE })
	@CrossOrigin(origins = { "*" })
	@ResponseBody
	public Map<String, Object> viewPhim(@RequestBody Movie movie) {
		return movieService.capNhatView(movie);
	}
	
//	@ResponseBody
//	@PostMapping(value = "/", produces = { MediaType.APPLICATION_JSON_VALUE })
//	@CrossOrigin(origins = { "*" })
//	public Map<String, Object> getUserRated(@RequestBody Map<String, Object> thongtin) {
//		String listId = thongtin.get("ids").toString();
//		long userid = ((Number) thongtin.get("userid")).longValue();
//		return movieService.addValue(listId, userid);
//	}

}
