package com.bao.doan.controller;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bao.doan.service.SimilarityService;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

@RestController
@RequestMapping("/recommend")
public class SimilarityController {
	@Autowired
	private SimilarityService similarityService;
	
	@ResponseBody
	@PostMapping(value = "/movie", produces = { MediaType.APPLICATION_JSON_VALUE })
	@CrossOrigin(origins = { "*" })
	public Map<String, Object> getDanhSachGoiY(@RequestBody Map<String, Object> thongtin)
			throws JsonParseException, JsonMappingException, IOException {
		@SuppressWarnings("unchecked")
		Map<String, Object> data = (Map<String, Object>) thongtin.get("data");
		long userid = ((Number) data.get("userid")).longValue();
		return similarityService.getDanhSachGoiY(userid);
	}
}
