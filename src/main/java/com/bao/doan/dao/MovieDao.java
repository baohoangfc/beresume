package com.bao.doan.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bao.doan.dto.DataComment;
import com.bao.doan.dto.DataUserRated;
import com.bao.doan.entity.Movie;
import com.bao.doan.entity.MovieComment;
import com.bao.doan.entity.MovieRating;
import com.bao.doan.entity.Numberview;
import com.bao.doan.utils.UniConstant;

@Transactional
@Repository
public class MovieDao {
	@PersistenceContext
	private EntityManager em;

	public void saveMovie(Movie movie) {
		em.persist(movie);
	}

	public void updateMovie(Movie movie) {
		em.merge(movie);
		em.flush();
	}
	
	public void saveMovieRating(MovieRating movieRating) {
		em.persist(movieRating);
	}
	
	public void saveComment(MovieComment movieComment) {
		em.persist(movieComment);
	}
	
	public void saveView(Numberview numberview) {
		em.persist(numberview);
	}

	public void updateView(Numberview numberview) {
		em.merge(numberview);
		em.flush();
	}

	@SuppressWarnings("unchecked")
	public List<Movie> getDanhSachPhim() {
		List<Movie> list = new ArrayList<>();
		StringBuilder sqlQuery = new StringBuilder();
		sqlQuery.append(" FROM Movie");
		sqlQuery.append(" WHERE daxoa = " + UniConstant.XOA.CHUA_XOA);

		list = (List<Movie>) em.createQuery(sqlQuery.toString()).getResultList();

		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<Movie> getDanhSachPhimHot() {
		List<Movie> list = new ArrayList<>();
		StringBuilder sqlQuery = new StringBuilder();
		sqlQuery.append(" FROM Movie");
		sqlQuery.append(" WHERE daxoa = " + UniConstant.XOA.CHUA_XOA);
		sqlQuery.append(" ORDER BY numview DESC");

		list = (List<Movie>) em.createQuery(sqlQuery.toString()).setMaxResults(4).getResultList();

		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<MovieRating> getDanhSachMovieRating() {
		List<MovieRating> list = new ArrayList<>();
		StringBuilder sqlQuery = new StringBuilder();
		sqlQuery.append(" FROM MovieRating");
		sqlQuery.append(" WHERE daxoa = " + UniConstant.XOA.CHUA_XOA);
		sqlQuery.append(" ORDER BY userid ASC, movieid ASC");

		list = (List<MovieRating>) em.createQuery(sqlQuery.toString()).getResultList();

		return list;
	}
	
	public int xoaMovie(String ids) {
		StringBuilder sql = new StringBuilder();
		sql.append(" UPDATE Movie SET daxoa = " + UniConstant.XOA.DA_XOA);
		sql.append(" WHERE id IN (" + ids + ")");
		em.createQuery(sql.toString()).executeUpdate();
		return 1;
	}
	
	public Movie getMovieByID(long id) {
		Movie movie = new Movie();

		StringBuilder sqlQuery = new StringBuilder();
		sqlQuery.append(" FROM Movie ");
		sqlQuery.append(" WHERE daxoa = " + UniConstant.XOA.CHUA_XOA);
		sqlQuery.append(" AND id = :id");
		try {
			movie = (Movie) em.createQuery(sqlQuery.toString()).setParameter("id", id)
					.getSingleResult();
		} catch (Exception e) {
			return null;
		}
		return movie;
	}
	
	public MovieRating getUserMovieRated(long idMovie, long idUser) {
		MovieRating movieRating = new MovieRating();

		StringBuilder sqlQuery = new StringBuilder();
		sqlQuery.append(" FROM MovieRating ");
		sqlQuery.append(" WHERE daxoa = " + UniConstant.XOA.CHUA_XOA);
		sqlQuery.append(" AND movieid = :movieid AND userid = :userid");
		try {
			movieRating = (MovieRating) em.createQuery(sqlQuery.toString()).setParameter("movieid", idMovie).setParameter("userid", idUser)
					.getSingleResult();
		} catch (Exception e) {
			return null;
		}
		return movieRating;
	}
	
	public int updateMovieRating(double rating, long idMovie, long idUser) {
		StringBuilder sql = new StringBuilder();
		sql.append(" UPDATE MovieRating SET rating = " + rating);
		sql.append(" WHERE movieid = " + idMovie + "AND userid =" + idUser);
		em.createQuery(sql.toString()).executeUpdate();
		return 1;
	}

	@SuppressWarnings("unchecked")
	public List<MovieRating> getListMovieByUsername(long idUser) {
		List<MovieRating> list = new ArrayList<>();
		StringBuilder sqlQuery = new StringBuilder();
		sqlQuery.append(" FROM MovieRating");
		sqlQuery.append(" WHERE daxoa = " + UniConstant.XOA.CHUA_XOA);
		sqlQuery.append(" AND userid = :userid ORDER BY movieid");

		list = (List<MovieRating>) em.createQuery(sqlQuery.toString()).setParameter("userid", idUser).getResultList();

		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<MovieRating> getListMovieByNotUser(long idUser) {
		List<MovieRating> list = new ArrayList<>();
		StringBuilder sqlQuery = new StringBuilder();
		sqlQuery.append(" FROM MovieRating");
		sqlQuery.append(" WHERE daxoa = " + UniConstant.XOA.CHUA_XOA);
		sqlQuery.append(" AND userid != :userid ORDER BY movieid");

		list = (List<MovieRating>) em.createQuery(sqlQuery.toString()).setParameter("userid", idUser).getResultList();

		return list;
	}
	
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
	
//	@SuppressWarnings("unchecked")
//	public List<Movie> getDanhSachPhimRecommend(String listId) {
//		List<Movie> list = new ArrayList<>();
//		StringBuilder sqlQuery = new StringBuilder();
//		sqlQuery.append(" FROM Movie");
//		sqlQuery.append(" WHERE id IN (" + listId + ") AND daxoa = " + UniConstant.XOA.CHUA_XOA);
//
//		list = (List<Movie>) em.createQuery(sqlQuery.toString()).getResultList();
//
//		return list;
//	}
	
	@SuppressWarnings("unchecked")
	public List<DataComment> getCommentByMovieid(long movieid) {
		List<DataComment> listDataComment = new ArrayList<>();

		StringBuilder sqlQuery = new StringBuilder();
		sqlQuery.append(" SELECT nguoidung.id, movieid, hoten, content, movie_comment.ngaytao FROM movie_comment inner join nguoidung on nguoidung.id = movie_comment.userid ");
		sqlQuery.append(" WHERE movie_comment.daxoa = " + UniConstant.XOA.CHUA_XOA);
		sqlQuery.append(" AND movieid = " + movieid + "ORDER BY movie_comment.ngaytao DESC");
		Query query = em.createNativeQuery(sqlQuery.toString());
		List<Object[]> lstObj = query.getResultList();
		for (Object[] obj : lstObj) {
			DataComment dataComment = new DataComment();
			dataComment.setUserid(Long.valueOf(obj[0].toString()));
			dataComment.setMovieid(Long.valueOf(obj[1].toString()));
			dataComment.setHoten(String.valueOf(obj[2].toString()));
			dataComment.setContent(String.valueOf(obj[3].toString()));
			dataComment.setNgaytao(new Date());
			listDataComment.add(dataComment);
		}
		return listDataComment;
	}
	
	@SuppressWarnings("unchecked")
	public List<Movie> getListMovieByCatId(long cid) {
		List<Movie> list = new ArrayList<>();
		StringBuilder sqlQuery = new StringBuilder();
		sqlQuery.append(" FROM Movie");
		sqlQuery.append(" WHERE daxoa = " + UniConstant.XOA.CHUA_XOA);
		sqlQuery.append(" AND cid = :cid");

		list = (List<Movie>) em.createQuery(sqlQuery.toString()).setParameter("cid", cid).getResultList();

		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<Movie> searchMovie(String search) {
		List<Movie> list = new ArrayList<>();
		StringBuilder sqlQuery = new StringBuilder();
		sqlQuery.append(" FROM Movie");
		sqlQuery.append(" WHERE daxoa = " + UniConstant.XOA.CHUA_XOA);
		sqlQuery.append(" AND LOWER(title) LIKE '%" + search + "%'");

		list = (List<Movie>) em.createQuery(sqlQuery.toString()).getResultList();

		return list;
	}
	
	public Numberview getNumberViewByIdMovie(long id) {
		Numberview numberview = new Numberview();

		StringBuilder sqlQuery = new StringBuilder();
		sqlQuery.append(" FROM Numberview ");
		sqlQuery.append(" WHERE daxoa = " + UniConstant.XOA.CHUA_XOA);
		sqlQuery.append(" AND id = :id");
		try {
			numberview = (Numberview) em.createQuery(sqlQuery.toString()).setParameter("id", id)
					.getSingleResult();
		} catch (Exception e) {
			return null;
		}
		return numberview;
	}
	
	@SuppressWarnings("unchecked")
	public List<Numberview> getListNumberView() {
		List<Numberview> list = new ArrayList<>();
		StringBuilder sqlQuery = new StringBuilder();
		sqlQuery.append(" FROM Numberview");
		sqlQuery.append(" WHERE daxoa = " + UniConstant.XOA.CHUA_XOA);
		sqlQuery.append(" ORDER BY numberview DESC");

		list = (List<Numberview>) em.createQuery(sqlQuery.toString()).setMaxResults(4).getResultList();

		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<Numberview> getListNumberViewByMovieId(long movieid) {
		List<Numberview> list = new ArrayList<>();
		StringBuilder sqlQuery = new StringBuilder();
		sqlQuery.append(" FROM Numberview");
		sqlQuery.append(" WHERE daxoa = " + UniConstant.XOA.CHUA_XOA);
		sqlQuery.append(" AND movieid = :movieid");

		list = (List<Numberview>) em.createQuery(sqlQuery.toString()).setParameter("movieid", movieid).getResultList();

		return list;
	}
	
	public int xoaNumberView(String ids) {
		StringBuilder sql = new StringBuilder();
		sql.append(" UPDATE Numberview SET daxoa = " + UniConstant.XOA.DA_XOA);
		sql.append(" WHERE id IN (" + ids + ")");
		em.createQuery(sql.toString()).executeUpdate();
		return 1;
	}
	
	@SuppressWarnings("unchecked")
	public List<DataUserRated> getMovieRated(long userid) {
		List<DataUserRated> listDataUserRated = new ArrayList<>();

		StringBuilder sqlQuery = new StringBuilder();
		sqlQuery.append("select movie.id,title,poster,movie_rating.rating from movie inner join movie_rating on movie.id = movie_rating.movieid where userid = " + userid);
		Query query = em.createNativeQuery(sqlQuery.toString());
		List<Object[]> lstObj = query.getResultList();
		for (Object[] obj : lstObj) {
			DataUserRated dataUserRated = new DataUserRated();
			dataUserRated.setId(Long.valueOf(obj[0].toString()));
			dataUserRated.setTitle(String.valueOf(obj[1].toString()));
			dataUserRated.setPoster(String.valueOf(obj[2].toString()));
			dataUserRated.setRating(Double.valueOf(obj[3].toString()));
			listDataUserRated.add(dataUserRated);
		}
		return listDataUserRated;
	}
	
}
