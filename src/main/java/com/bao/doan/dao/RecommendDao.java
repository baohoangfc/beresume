package com.bao.doan.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bao.doan.entity.Movie;
import com.bao.doan.entity.MovieRating;
import com.bao.doan.utils.UniConstant;

@Transactional
@Repository
public class RecommendDao {
	
	@PersistenceContext
	private EntityManager em;
	
	@SuppressWarnings("unchecked")
	public List<MovieRating> getListMovieRecommend(long idUser) {
		List<MovieRating> list = new ArrayList<>();
		StringBuilder sqlQuery = new StringBuilder();
		sqlQuery.append(" FROM MovieRating");
		sqlQuery.append(" WHERE daxoa = " + UniConstant.XOA.CHUA_XOA);
		sqlQuery.append(" AND userid != :userid ORDER BY movieid");

		list = (List<MovieRating>) em.createQuery(sqlQuery.toString()).setParameter("userid", idUser).getResultList();

		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<Movie> getDanhSachPhimRecommend(String listId) {
		List<Movie> list = new ArrayList<>();
		StringBuilder sqlQuery = new StringBuilder();
		sqlQuery.append(" FROM Movie");
		sqlQuery.append(" WHERE id IN (" + listId + ") AND daxoa = " + UniConstant.XOA.CHUA_XOA);

		list = (List<Movie>) em.createQuery(sqlQuery.toString()).getResultList();

		return list;
	}
}
