package com.bao.doan.service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bao.doan.dao.MovieDao;
import com.bao.doan.dao.NguoiDungDao;
import com.bao.doan.dao.RecommendDao;
import com.bao.doan.entity.Movie;
import com.bao.doan.entity.MovieRating;
import com.bao.doan.entity.Nguoidung;
import com.bao.doan.utils.UniConstant;

@Service
@Transactional
public class SimilarityService {
	
	@Autowired
	private MovieDao movieDao;
	
	@Autowired
	private NguoiDungDao nguoiDungDao;
	
	@Autowired
	private RecommendDao recommendDao;
	
	static int userID = 0;
	static int userNum;
	static int itemNum;
	static double[][] uiRating = new double[userNum][itemNum];
	static double[][] simResult = new double[itemNum][itemNum];
	
	public static void main(String[] args) {
		
	}
    
	public static double calculateCosineSimilarity(List<String> vector1, List<String> vector2) {
		double firstElement = 0;
		double secondElement = 0;
		double thirdElement = 0;
		
		if(vector1.size() != vector2.size()) {
			return 0;
		}

		for (int i = 0; i < vector1.size(); i++) {
			firstElement = firstElement + Double.valueOf(vector1.get(i)) * Double.valueOf(vector2.get(i));
			secondElement = secondElement + Double.valueOf(vector1.get(i)) * Double.valueOf(vector1.get(i));
			thirdElement = thirdElement + Double.valueOf(vector2.get(i)) * Double.valueOf(vector2.get(i));
		}

		if (Math.sqrt(secondElement) * Math.sqrt(thirdElement) == 0
				|| Double.isNaN(Math.sqrt(secondElement) * Math.sqrt(thirdElement))) {
			return 0;
		}
		double result = firstElement / (Math.sqrt(secondElement) * Math.sqrt(thirdElement));

		return result;
	}
	
	public Map<String, Object> getDanhSachGoiY(long userid) {
		int code = UniConstant.ErrorCode.SUCCESS;
		String message = UniConstant.Message.QUERY_SUCCESS;
		
		List<MovieRating> list = movieDao.getDanhSachMovieRating();
		List<Movie> listMovie = movieDao.getDanhSachPhim();
		List<Nguoidung> listUser = nguoiDungDao.getDanhSachNguoiDung();
		userNum = listUser.size();
		itemNum = listMovie.size();
		for (int i = 0; i < listUser.size(); i++) {
			if(userid == listUser.get(i).getId()) {
				userID = i;
			}
		}
		
		double[][] uRating = new double[listUser.size()][listMovie.size()];	
		
		int index = 0;
		for (int i = 0; i < uRating.length; i++) {
			for (int j = 0; j < uRating[0].length; j++) {
				uRating[i][j] = list.get(index).getRating();
				index++;
			}
		}
		uiRating = uRating;
		simResult = caculationSimilarity(uiRating);
//		for (int i = 0; i < uiRating.length; i++) {
//			System.out.println(Arrays.toString(uiRating[i]));
//		}
		
		for (int i = 0; i < uiRating[0].length; i++) {
			if(uRating[userID][i] == 0) {
				uRating[userID][i] = originalPrediction(userID, i);
			}	
		}
		
		//System.out.println(Arrays.toString(uiRating[userID]));
		List<Movie> listMovieRecommend = new ArrayList<Movie>();
		for (int i = 0; i < uiRating[userID].length; i++) {
			if(uiRating[userID][i] >= 4) {
				for (int j = 0; j < listMovie.size(); j++) {
					if(i == j) {
						listMovieRecommend.add(listMovie.get(i));
					}
				}
			}
		}
		
		String listId = "";
		for (int i = 0; i < listMovieRecommend.size(); i++) {
			listId = listId + String.valueOf(listMovieRecommend.get(i).getId()) + ",";
		}

		List<Movie> listMovieResult = recommendDao.getDanhSachPhimRecommend(listId.substring(0, listId.length() - 1));
		
		return UniConstant.CommonService.setResult(code, message, listMovieResult);
	}
	
	public double originalPrediction(int userID, int itemID){
		double den = 0.0, num = 0.0;
		for (int i = 0; i < uiRating[0].length; i++){
			if (i != itemID && uiRating[userID][i] > 0){
				den += Math.abs(simResult[i][itemID]);
				num += simResult[i][itemID] * uiRating[userID][i];
			}
		}

		double predictScore = 0.0;
		if (den != 0) {
			predictScore = num / den;
		}
		return predictScore;
	}
	
	public double[][] caculationSimilarity(double [][]uiRating) {
		int userNum = uiRating.length;
		int itemNum = uiRating[0].length;
		double[][] similarityResult = new double[itemNum][itemNum];
		
		for (int i = 0; i < itemNum; i++) {
			for (int j = 0; j < itemNum; j++) {
				double num = 0, den = 0;
				double den1 = 0, den2 = 0;
				for (int k = 0; k < userNum; k++) {
					double diff1 = 0, diff2 = 0;
					if (uiRating[k][i] > 0 && uiRating[k][j] > 0) {
						diff1 = uiRating[k][i];
						diff2 = uiRating[k][j];
						num += diff1 * diff2;
						den1 += diff1 * diff1;
						den2 += diff2 * diff2;
					}
				}
				den = Math.sqrt(den1) * Math.sqrt(den2);
				similarityResult[i][j] = Double.parseDouble(new DecimalFormat("##.####").format((den == 0) ? 0 : num / den));
				similarityResult[j][i] = Double.parseDouble(new DecimalFormat("##.####").format(similarityResult[i][j]));
			}
		}
		
		return similarityResult;
	}
}
