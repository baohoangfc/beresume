package com.bao.doan.service;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bao.doan.dao.CategoryDao;
import com.bao.doan.dto.DataThongKe;
import com.bao.doan.entity.Category;
import com.bao.doan.utils.UniConstant;
import com.bao.doan.utils.UniUtil;

@Service
@Transactional
public class CategoryService {

	@Autowired
	private CategoryDao categoryDao;

	private static final Log log = LogFactory.getLog(CategoryService.class);
	
	public Map<String, Object> getDanhSachCategory() {
		int code = UniConstant.ErrorCode.SUCCESS;
		String message = UniConstant.Message.QUERY_SUCCESS;
		List<Category> list = categoryDao.getDanhSachCategory();

		if (null == list || list.isEmpty()) {
			code = UniConstant.ErrorCode.FAIL;
			message = UniConstant.Message.QUERY_FAIL;
		}
		return UniConstant.CommonService.setResult(code, message, list);
	}
	
	public Map<String, Object> getCategoryById(long id) {
		int code = UniConstant.ErrorCode.SUCCESS;
		String message = UniConstant.Message.QUERY_SUCCESS;
		Category category = categoryDao.getCategoryById(id);
		if (null == category) {
			log.error("Không tìm thấy category có id : " + id);
		}
		return UniConstant.CommonService.setResult(code, message, category);
	}
	
	public Map<String, Object> xoaCategory(String ids) {
		int code = UniConstant.ErrorCode.SUCCESS;
		String message = UniConstant.Message.DELETE_SUCCESS;

		try {
			categoryDao.xoaCategory(ids);
		} catch (Exception e) {
			code = UniConstant.ErrorCode.FAIL;
			message = UniConstant.Message.DELETE_FAIL;
		}
		return UniConstant.CommonService.setResult(code, message, ids);
	}
	
	public Map<String, Object> themMoiCategory(Category category) {
		int code = UniConstant.ErrorCode.SUCCESS;
		String message = UniConstant.Message.INSERT_SUCCESS;
		Category check = categoryDao.getCategoryByName(category.getName());
		Category ct = new Category();
		if(null != check) {
			code = UniConstant.ErrorCode.FAIL;
			message = UniConstant.Message.INSERT_FAIL;
		} else {	
			try {
				ct.setId(UniUtil.getTableId());
				ct.setName(category.getName());
				ct.setDaxoa((short) 0);
				categoryDao.saveCategory(ct);
				message = UniConstant.Message.INSERT_SUCCESS;
				code = UniConstant.ErrorCode.SUCCESS;
			} catch (Exception e) {
				log.error("them moi movie:" + e.getMessage());
				code = UniConstant.ErrorCode.FAIL;
				message = UniConstant.Message.INSERT_FAIL;
			}
		}
		
		return UniConstant.CommonService.setResult(code, message, ct);
	}
	
	public Map<String, Object> capNhatCategory(Category category) {
		int code = UniConstant.ErrorCode.SUCCESS;
		String message = UniConstant.Message.UPDATE_SUCCESS;
		Category categoryUpdate = categoryDao.getCategoryById(category.getId());
		try {
			categoryUpdate.setName(category.getName());
			categoryDao.updateCategory(categoryUpdate);
		} catch (Exception e) {
			code = UniConstant.ErrorCode.FAIL;
			message = UniConstant.Message.UPDATE_FAIL;
		}
		return UniConstant.CommonService.setResult(code, message, categoryUpdate);
	}
	
	public Map<String, Object> thongkeCategory() {
		int code = UniConstant.ErrorCode.SUCCESS;
		String message = UniConstant.Message.QUERY_SUCCESS;
		List<DataThongKe> list = categoryDao.thongke();

		if (null == list || list.isEmpty()) {
			code = UniConstant.ErrorCode.FAIL;
			message = UniConstant.Message.QUERY_FAIL;
		}
		return UniConstant.CommonService.setResult(code, message, list);
	}
	
}
