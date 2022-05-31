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

import com.bao.doan.entity.Category;
import com.bao.doan.service.CategoryService;

@RestController
@RequestMapping("/admin/category")
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;

	@ResponseBody
	@GetMapping(value = "/ds", produces = { MediaType.APPLICATION_JSON_VALUE })
	@CrossOrigin(origins = { "*" })
	public Map<String, Object> getDanhSachCategory() {
		return categoryService.getDanhSachCategory();
	}
	
	@ResponseBody
	@PostMapping(value = "/xoa", produces = { MediaType.APPLICATION_JSON_VALUE })
	@CrossOrigin(origins = { "*" })
	public Map<String, Object> xoaCategory(@RequestBody Map<String, Object> ids) {
		String listId = ids.get("ids").toString();
		return categoryService.xoaCategory(listId);
	}

	@ResponseBody
	@GetMapping(value = "/id/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
	@CrossOrigin(origins = { "*" })
	public Map<String, Object> getCategoryById(@PathVariable long id) {
		return categoryService.getCategoryById(id);
	}
	
	@PostMapping(value = "/themmoi", produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	@CrossOrigin(origins = { "*" })
	public Map<String, Object> themMoiCategory(@RequestBody Category category) {
		return categoryService.themMoiCategory(category);
	}
	
	@PostMapping(value = "/capnhat", produces = { MediaType.APPLICATION_JSON_VALUE })
	@CrossOrigin(origins = { "*" })
	@ResponseBody
	public Map<String, Object> capNhatCategory(@RequestBody Category category) {
		return categoryService.capNhatCategory(category);
	}
	
	@ResponseBody
	@GetMapping(value = "/thongke", produces = { MediaType.APPLICATION_JSON_VALUE })
	@CrossOrigin(origins = { "*" })
	public Map<String, Object> thongke() {
		return categoryService.thongkeCategory();
	}
}
