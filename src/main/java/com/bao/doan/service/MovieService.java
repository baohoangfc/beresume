package com.bao.doan.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bao.doan.dao.MovieDao;
import com.bao.doan.dao.NguoiDungDao;
import com.bao.doan.dto.DataComment;
import com.bao.doan.dto.DataUserRated;
import com.bao.doan.entity.Movie;
import com.bao.doan.entity.MovieComment;
import com.bao.doan.entity.MovieRating;
import com.bao.doan.entity.Nguoidung;
import com.bao.doan.entity.Numberview;
import com.bao.doan.utils.UniConstant;
import com.bao.doan.utils.UniUtil;

@Service
@Transactional
public class MovieService {
	@Autowired
	private MovieDao movieDao;
	@Autowired
	private NguoiDungDao nguoiDungDao;

	private static final Log log = LogFactory.getLog(MovieService.class);

	public Map<String, Object> getDanhSachMovie() {
		int code = UniConstant.ErrorCode.SUCCESS;
		String message = UniConstant.Message.QUERY_SUCCESS;
		List<Movie> list = movieDao.getDanhSachPhim();

		if (null == list || list.isEmpty()) {
			code = UniConstant.ErrorCode.FAIL;
			message = UniConstant.Message.QUERY_FAIL;
		}
		return UniConstant.CommonService.setResult(code, message, list);
	}
	
	public Map<String, Object> getDanhSachMovieHot() {
		int code = UniConstant.ErrorCode.SUCCESS;
		String message = UniConstant.Message.QUERY_SUCCESS;
		List<Movie> list = movieDao.getDanhSachPhimHot();

		if (null == list || list.isEmpty()) {
			code = UniConstant.ErrorCode.FAIL;
			message = UniConstant.Message.QUERY_FAIL;
		}
		return UniConstant.CommonService.setResult(code, message, list);
	}

	public Map<String, Object> themMoiMovie(Movie movie) {
		int code = UniConstant.ErrorCode.SUCCESS;
		String message = UniConstant.Message.INSERT_SUCCESS;
		Movie mv = new Movie();
		Numberview numberview = new Numberview();
		List<Nguoidung> listNguoiDung = nguoiDungDao.getAllNguoiDung();		
		
		try {
			long id = UniUtil.getTableId();
			mv.setId(id);
			mv.setCodemovie(movie.getCodemovie());
			mv.setCaster(movie.getCaster());
			mv.setPlot(movie.getPlot());
			mv.setPoster(movie.getPoster());
			mv.setTitle(movie.getTitle());
			mv.setYear(movie.getYear());
			mv.setTrailer(movie.getTrailer());
			mv.setRating(movie.getRating());
			mv.setRatingvotes(movie.getRatingvotes());
			mv.setLinkfilm(movie.getLinkfilm());
			mv.setDaxoa((short) 0);
			mv.setNumview((short) 0);
			mv.setCid(movie.getCid());
			numberview.setId(id);
			numberview.setMovieid(id);
			numberview.setNgaybatdau(new Date());
			numberview.setNgaycapnhat(null);
			movieDao.saveMovie(mv);
			movieDao.saveView(numberview);
			for (int i = 0; i < listNguoiDung.size(); i++) {
				MovieRating movieRating = new MovieRating();
				movieRating.setId(UniUtil.getTableId());
				movieRating.setMovieid(id);
				movieRating.setUserid(listNguoiDung.get(i).getId());
				movieRating.setRating(0);
				movieDao.saveMovieRating(movieRating);
			}	
			message = UniConstant.Message.INSERT_SUCCESS;
			code = UniConstant.ErrorCode.SUCCESS;
		} catch (Exception e) {
			log.error("them moi movie:" + e.getMessage());
			code = UniConstant.ErrorCode.FAIL;
			message = UniConstant.Message.INSERT_FAIL;
		}
		return UniConstant.CommonService.setResult(code, message, mv);
	}
	
	public Map<String, Object> danhgiaMovie(MovieRating movieRating) {
		int code = UniConstant.ErrorCode.SUCCESS;
		String message = UniConstant.Message.INSERT_SUCCESS;
		MovieRating mrating = new MovieRating();
		MovieRating mvrated = movieDao.getUserMovieRated(movieRating.getMovieid(), movieRating.getUserid());
		if(null != mvrated) {
			try {
				movieDao.updateMovieRating(movieRating.getRating(), movieRating.getMovieid(), movieRating.getUserid());
				message = UniConstant.Message.UPDATE_SUCCESS;
				code = UniConstant.ErrorCode.SUCCESS;
			} catch (Exception e) {
				log.error("danhgia:" + e.getMessage());
				code = UniConstant.ErrorCode.FAIL;
				message = UniConstant.Message.INSERT_FAIL;
			}
		} else {
			try {
				mrating.setId(UniUtil.getTableId());
				mrating.setMovieid(movieRating.getMovieid());
				mrating.setRating(movieRating.getRating());
				mrating.setUserid(movieRating.getUserid());
				mrating.setDaxoa((short) 0);
				movieDao.saveMovieRating(mrating);
				message = UniConstant.Message.INSERT_SUCCESS;
				code = UniConstant.ErrorCode.SUCCESS;
			} catch (Exception e) {
				log.error("danhgia:" + e.getMessage());
				code = UniConstant.ErrorCode.FAIL;
				message = UniConstant.Message.INSERT_FAIL;
			}
		}
		return UniConstant.CommonService.setResult(code, message, mrating);
	}
	
	public Map<String, Object> xoaMovie(String ids) {
		int code = UniConstant.ErrorCode.SUCCESS;
		String message = UniConstant.Message.DELETE_SUCCESS;

		try {
			movieDao.xoaMovie(ids);
		} catch (Exception e) {
			code = UniConstant.ErrorCode.FAIL;
			message = UniConstant.Message.DELETE_FAIL;
		}
		return UniConstant.CommonService.setResult(code, message, ids);
	}
	
	public Map<String, Object> getMovieById(long id) {
		int code = UniConstant.ErrorCode.SUCCESS;
		String message = UniConstant.Message.QUERY_SUCCESS;
		Movie movie = movieDao.getMovieByID(id);
		if (null == movie) {
			log.error("Không tìm thấy phim có id : " + id);
		}
		return UniConstant.CommonService.setResult(code, message, movie);
	}
	
	public Map<String, Object> searchMovie(String param) {
		int code = UniConstant.ErrorCode.SUCCESS;
		String message = UniConstant.Message.QUERY_SUCCESS;
		List<Movie> movie = movieDao.searchMovie(param);
		if (null == movie) {
			log.error("Không tìm thấy danh sách phim có param : " + param);
		}
		return UniConstant.CommonService.setResult(code, message, movie);
	}
	
	public Map<String, Object> getUserMovieRated(long idMovie, long idUser) {
		int code = UniConstant.ErrorCode.SUCCESS;
		String message = UniConstant.Message.QUERY_SUCCESS;
		MovieRating movieRating = movieDao.getUserMovieRated(idMovie, idUser);
		if (null == movieRating) {
			log.error("Không tìm thấy phim có id : " + idMovie);
		}
		return UniConstant.CommonService.setResult(code, message, movieRating);
	}

	public Map<String, Object> getListMovieByUsername(long userid) {
		int code = UniConstant.ErrorCode.SUCCESS;
		String message = UniConstant.Message.QUERY_SUCCESS;
		List<MovieRating> list = movieDao.getListMovieByUsername(userid);

		if (null == list || list.isEmpty()) {
			code = UniConstant.ErrorCode.FAIL;
			message = UniConstant.Message.QUERY_FAIL;
		}
		return UniConstant.CommonService.setResult(code, message, list);
	}
	
	public Map<String, Object> getListMovieByNotUser(long userid) {
		int code = UniConstant.ErrorCode.SUCCESS;
		String message = UniConstant.Message.QUERY_SUCCESS;
		List<MovieRating> list = movieDao.getListMovieByNotUser(userid);

		if (null == list || list.isEmpty()) {
			code = UniConstant.ErrorCode.FAIL;
			message = UniConstant.Message.QUERY_FAIL;
		}
		return UniConstant.CommonService.setResult(code, message, list);
	}
	
	public Map<String, Object> addValue(String listId, long userid) {
		int code = UniConstant.ErrorCode.SUCCESS;
		String message = UniConstant.Message.INSERT_SUCCESS;
		
		String[] ids = listId.split(",");
		try {
			for (int i = 0; i < ids.length; i++) {
				MovieRating movieRating = new MovieRating();
				movieRating.setId(UniUtil.getTableId());
				movieRating.setMovieid(0);
				movieRating.setRating(0);
				movieRating.setUserid(userid);
				movieRating.setDaxoa((short) 0);
				movieDao.saveMovieRating(movieRating);
			}
			//movieDao.saveMovieRating(movieRating);
			message = UniConstant.Message.INSERT_SUCCESS;
			code = UniConstant.ErrorCode.SUCCESS;
		} catch (Exception e) {
			log.error("them moi value:" + e.getMessage());
			code = UniConstant.ErrorCode.FAIL;
			message = UniConstant.Message.INSERT_FAIL;
		}
		return UniConstant.CommonService.setResult(code, message, null);
	}



	public Map<String, Object> commentMovie(MovieComment movieComment) {
		int code = UniConstant.ErrorCode.SUCCESS;
		String message = UniConstant.Message.INSERT_SUCCESS;
		MovieComment mc = new MovieComment();
		try {
			mc.setId(UniUtil.getTableId());
			mc.setUserid(movieComment.getUserid());
			mc.setMovieid(movieComment.getMovieid());
			mc.setContent(movieComment.getContent());
			mc.setNgaytao(new Date());
			mc.setDaxoa((short) 0);
			movieDao.saveComment(mc);
			message = UniConstant.Message.INSERT_SUCCESS;
			code = UniConstant.ErrorCode.SUCCESS;
		} catch (Exception e) {
			log.error("themmoicomment:" + e.getMessage());
			code = UniConstant.ErrorCode.FAIL;
			message = UniConstant.Message.INSERT_FAIL;
		}
		return UniConstant.CommonService.setResult(code, message, mc);
	}
	
	public Map<String, Object> getCommentByMovieid(long id) {
		int code = UniConstant.ErrorCode.SUCCESS;
		String message = UniConstant.Message.QUERY_SUCCESS;
		List<DataComment> dataComment = movieDao.getCommentByMovieid(id);
		if (null == dataComment) {
			log.error("Không tìm thấy phim có id : " + id);
		}
		return UniConstant.CommonService.setResult(code, message, dataComment);
	}
	
	public Map<String, Object> capNhatPhim(Movie movie) {
		int code = UniConstant.ErrorCode.SUCCESS;
		String message = UniConstant.Message.UPDATE_SUCCESS;
		Movie movieUpdate = movieDao.getMovieByID(movie.getId());
		try {
			movieUpdate.setCaster(movie.getCaster());
			movieUpdate.setLinkfilm(movie.getLinkfilm());
			movieUpdate.setPlot(movie.getCaster());
			movieUpdate.setPoster(movie.getPoster());
			movieUpdate.setRating(movie.getRating());
			movieUpdate.setRatingvotes(movie.getRatingvotes());
			movieUpdate.setTitle(movie.getTitle());
			movieUpdate.setTrailer(movie.getTrailer());
			movieUpdate.setYear(movie.getYear());
			movieUpdate.setCodemovie(movie.getCodemovie());
			movieUpdate.setCid(movie.getCid());
			movieDao.updateMovie(movieUpdate);
		} catch (Exception e) {
			code = UniConstant.ErrorCode.FAIL;
			message = UniConstant.Message.UPDATE_FAIL;
		}
		return UniConstant.CommonService.setResult(code, message, movieUpdate);
	}
	
	public Map<String, Object> capNhatView(Movie movie) {
		int code = UniConstant.ErrorCode.SUCCESS;
		String message = UniConstant.Message.UPDATE_SUCCESS;
		Movie movieUpdate = movieDao.getMovieByID(movie.getId());
		try {
			movieUpdate.setNumview(movieUpdate.getNumview() + 1);
			movieDao.updateMovie(movieUpdate);
		} catch (Exception e) {
			code = UniConstant.ErrorCode.FAIL;
			message = UniConstant.Message.UPDATE_FAIL;
		}
		return UniConstant.CommonService.setResult(code, message, movieUpdate);
	}
	
	public Map<String, Object> getListMovieByCatId(long cid) {
		int code = UniConstant.ErrorCode.SUCCESS;
		String message = UniConstant.Message.QUERY_SUCCESS;
		List<Movie> list = movieDao.getListMovieByCatId(cid);

		if (null == list || list.isEmpty()) {
			code = UniConstant.ErrorCode.FAIL;
			message = UniConstant.Message.QUERY_FAIL;
		}
		return UniConstant.CommonService.setResult(code, message, list);
	}
	
	public Map<String, Object> searchPhim(String search) {
		int code = UniConstant.ErrorCode.SUCCESS;
		String message = UniConstant.Message.QUERY_SUCCESS;
		List<Movie> list = movieDao.searchMovie(search);

		if (null == list || list.isEmpty()) {
			code = UniConstant.ErrorCode.FAIL;
			message = UniConstant.Message.QUERY_FAIL;
		}
		return UniConstant.CommonService.setResult(code, message, list);
	}
	
	public Map<String, Object> capNhatViewByWeek() {
		int code = UniConstant.ErrorCode.SUCCESS;
		String message = UniConstant.Message.UPDATE_SUCCESS;
		List<Numberview> listNumberviews =  new ArrayList<>();
		Date today = new Date();
		List<Movie> listMovie = movieDao.getDanhSachPhim();
		List<Movie> liMovies = new ArrayList<>();
		for (int i = 0; i < listMovie.size(); i++) {
			Numberview numberview = movieDao.getNumberViewByIdMovie(listMovie.get(i).getId());
			log.error("Thời gian đang sử dụng : " + today.getHours() + ":" + today.getMinutes());
			if(null != numberview && today.getHours() == 0 && today.getMinutes() == 0) {
				Numberview nbview = new Numberview();
				long diffdays = Math.round((today.getTime() - numberview.getNgaycapnhat().getTime()) / (1000 * 3600 * 24));
				log.error("Số ngày hiện tại : " + diffdays + " ngày");
				Date ngaycapnhat = numberview.getNgaycapnhat();
				long movieid = numberview.getMovieid();
				long view = listMovie.get(i).getNumview();
				if(diffdays == 7) {
					log.error("Phim có id " + movieid + " đã 7 ngày");
					movieDao.xoaNumberView(String.valueOf(numberview.getId()));
					nbview.setId(UniUtil.getTableId());
					nbview.setMovieid(movieid);
					nbview.setNgaybatdau(ngaycapnhat);
					nbview.setNgaycapnhat(new Date());
					nbview.setNumberview(view - numberview.getNumberview());
					movieDao.saveView(nbview);
				} else {
					log.error("Không có lịch nào quá 7 ngày");
				}
//				listNumberviews = movieDao.getListNumberView();
//				for (int j = 0; j < listNumberviews.size(); j++) {
//					liMovies.add(movieDao.getMovieByID(listNumberviews.get(j).getMovieid()));
//				}
			} else {
//				List<Numberview> listNumview =  movieDao.getListNumberView();
//				for (int j = 0; j < listNumview.size(); j++) {
//					liMovies.add(movieDao.getMovieByID(listNumview.get(j).getMovieid()));
//				}
				log.error("Không tìm thấy phim");
				code = UniConstant.ErrorCode.FAIL;
				message = UniConstant.Message.UPDATE_FAIL;
			}
		}
		
		//listNumberviews = movieDao.getListNumberView();

		return UniConstant.CommonService.setResult(code, message, null);
	}
	
	public Map<String, Object> getListWeek() {
		int code = UniConstant.ErrorCode.SUCCESS;
		String message = UniConstant.Message.QUERY_SUCCESS;
		List<Movie> listMovie = new ArrayList<>();
		List<Numberview> listNumberviews = movieDao.getListNumberView();
		for (int i = 0; i < listNumberviews.size(); i++) {
			listMovie.add(movieDao.getMovieByID(listNumberviews.get(i).getMovieid()));
		}

		if (null == listMovie || listMovie.isEmpty()) {
			code = UniConstant.ErrorCode.FAIL;
			message = UniConstant.Message.QUERY_FAIL;
		}
		return UniConstant.CommonService.setResult(code, message, listMovie);
	}
	
	public Map<String, Object> getMovieRated(long userid) {
		int code = UniConstant.ErrorCode.SUCCESS;
		String message = UniConstant.Message.QUERY_SUCCESS;
		List<DataUserRated> list = movieDao.getMovieRated(userid);

		if (null == list || list.isEmpty()) {
			code = UniConstant.ErrorCode.FAIL;
			message = UniConstant.Message.QUERY_FAIL;
		}
		return UniConstant.CommonService.setResult(code, message, list);
	}

}
