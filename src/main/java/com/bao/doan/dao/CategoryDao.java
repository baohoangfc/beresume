package com.bao.doan.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bao.doan.dto.DataThongKe;
import com.bao.doan.entity.Category;
import com.bao.doan.utils.UniConstant;

@Transactional
@Repository
public class CategoryDao {
	
	@PersistenceContext
	private EntityManager em;

	public void saveCategory(Category category) {
		em.persist(category);
	}

	public void updateCategory(Category category) {
		em.merge(category);
		em.flush();
	}
	
	@SuppressWarnings("unchecked")
	public List<Category> getDanhSachCategory() {
		List<Category> list = new ArrayList<>();
		StringBuilder sqlQuery = new StringBuilder();
		sqlQuery.append(" FROM Category");
		sqlQuery.append(" WHERE daxoa = " + UniConstant.XOA.CHUA_XOA);
		sqlQuery.append(" ORDER BY id ASC");

		list = (List<Category>) em.createQuery(sqlQuery.toString()).getResultList();

		return list;
	}
	
	public int xoaCategory(String ids) {
		StringBuilder sql = new StringBuilder();
		sql.append(" UPDATE Category SET daxoa = " + UniConstant.XOA.DA_XOA);
		sql.append(" WHERE id IN (" + ids + ")");
		em.createQuery(sql.toString()).executeUpdate();
		return 1;
	}
	
	public Category getCategoryById(long id) {
		Category category = null;

		StringBuilder sqlQuery = new StringBuilder();
		sqlQuery.append(" FROM Category ");
		sqlQuery.append(" WHERE daxoa = " + UniConstant.XOA.CHUA_XOA);
		sqlQuery.append(" AND id = :id");
		try {
			category = (Category) em.createQuery(sqlQuery.toString()).setParameter("id", id)
					.getSingleResult();
		} catch (Exception e) {
			return null;
		}
		return category;
	}
	
	public Category getCategoryByName(String name) {
		Category category = null;

		StringBuilder sqlQuery = new StringBuilder();
		sqlQuery.append(" FROM Category ");
		sqlQuery.append(" WHERE daxoa = " + UniConstant.XOA.CHUA_XOA);
		sqlQuery.append(" AND name = :name");
		try {
			category = (Category) em.createQuery(sqlQuery.toString()).setParameter("name", name)
					.getSingleResult();
		} catch (Exception e) {
			return null;
		}
		return category;
	}

	
	@SuppressWarnings("unchecked")
	public List<DataThongKe> thongke() {
		List<DataThongKe> listData = new ArrayList<>();

		StringBuilder sqlQuery = new StringBuilder();
		sqlQuery.append(" SELECT category.name, COUNT(category.id) \r\n" + 
				"FROM category LEFT JOIN movie ON category.id = movie.cid\r\n" + 
				"WHERE category.daxoa = 0 GROUP BY category.id");
		Query query = em.createNativeQuery(sqlQuery.toString());
		List<Object[]> lstObj = query.getResultList();
		for (Object[] obj : lstObj) {
			DataThongKe dataThongKe = new DataThongKe();
			dataThongKe.setName(String.valueOf(obj[0].toString()));
			dataThongKe.setCount(Long.parseLong(obj[1].toString()));
			listData.add(dataThongKe);
		}
		return listData;
	}
}
