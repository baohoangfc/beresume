package com.bao.doan.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public interface UniConstant {

	public interface Message{
		final String INSERT_SUCCESS = "Thêm mới thành công";
		final String INSERT_FAIL = "Thêm mới thất bại";
		final String UPDATE_SUCCESS = "Cập nhật thành công";
		final String UPDATE_FAIL = "Cập nhật thất bại";
		final String DELETE_SUCCESS = "Xóa thành công";
		final String DELETE_FAIL = "Xóa thất bại";
		final String QUERY_SUCCESS = "Lấy dữ liệu thành công";
		final String QUERY_FAIL = "Lấy dữ liệu thất bại";
		final String PARAM_FAIL = "Tham số truyền vào không đúng";
		final String GENERATE_SUCCESS = "Sinh tự động tham số thành công";
		final String GENERATE_FAIL = "Sinh tự động tham số thất bại";
		
		final String SUCCESS_LAYDANHSACH = "Hiển thị danh sách thành công";
		final String ERROR_LAYDANHSACH = "Hiển thị dánh sách thất bại";
		final String SUCCESS_THEM = "Thêm thành công";
		final String ERROR_THEM = "Thêm thất bại";
		final String SUCCESS_CAPNHAP = "Cập nhập thành công";
		final String ERROR_CAPNHAP = "Cập nhập thất bại";
		final String SUCCESS_XOA = "Xóa thành công";
		final String ERROR_XOA = "Xóa thất bại";
		final String ERROR = "Lỗi không xác định";
		final String ERROR_TOKEN = "Đã có người thay đổi thông tin!";
		final String MACOQUAN_EXISTS = "Mã cơ quan đã tồn tại !";
		final String TENDANGNHAP_EXISTS ="Tên đăng nhập hoặc Email đã tồn tại ! ";
		final String MAPHONGBAN_EXISTS ="Mã phòng ban đã tồn tại trong cơ quan này!";
		final String MANHOM_EXISTS = "Mã nhóm đã tồn tại";
		final String EXISTS_NGUOIDUNG= "Vẫn còn người dùng";
		final String EXISTS_MACHUCNANGAPI= "Mã chức năng thuộc API này đã được sử dụng";
	}
	
	public interface ErrorCode{
		final int SUCCESS = 0;
		final int FAIL = 1;
		
		final int INSERT_FAIL = -1;
		final int UPDATE_FAIL = -2;
		final int SELECT_FAIL = -3;
		final int DELETE_FAIL = -4;
		final int PARAM_FAIL = -5;		// param is not correct
	}
	
	public interface XOA{
		final int DA_XOA = 1;
		final int CHUA_XOA = 0;
	}
	
	public interface CommonService {
		final String serviceTimNguoiDungTheoTen = "http://quantrihethong-service/nguoidung/timtheoten";
		final String serviceLayBieuMauTheoND = "http://qlbm-service/bieumau/laybieumautheonguoidung";
		final String serviceLayNhomTheoND = "http://qlbm-service/nhomnguoidung/layNhomNguoiDungTheoTenDangNhap";
		
		final String CODE = "code";
		final String MESSAGE = "message";
		final String RESULTDATA = "resultdata";
		
		final String nguoidung =  "nguoidung";
		final String macoquan = "macoquan";
		final String movie = "movie";
		
		static Map<String, Object> setResult(int code, String message, Object data ) {
			Map<String, Object> result = new HashMap<String, Object>();
			
			result.put(UniConstant.CommonService.CODE, code);
			result.put(UniConstant.CommonService.MESSAGE, message);
			result.put(UniConstant.CommonService.RESULTDATA, data);
			result.put("nothing", "I want to ask the user to put values in and then display total after the end of the week in console in javascript?I want to ask the user to put values in and then display total after the end of the week in console in javascript?I want to ask the user to put values in and then display total after the end of the week in console in javascript?I want to ask the user to put values in and then display total after the end of the week in console in javascript?I want to ask the user to put values in and then display total after the end of the week in console in javascript?I want to ask the user to put values in and then display total after the end of the week in console in javascript?I want to ask the user to put values in and then display total after the end of the week in console in javascript?I want to ask the user to put values in and then display total after the end of the week in console in javascript?I want to ask the user to put values in and then display total after the end of the week in console in javascript?I want to ask the user to put values in and then display total after the end of the week in console in javascript?");
			return result;
		}
	}
	
	public interface PARAM{
		final String STR_RESERVE_ID = "RESERVE_ID";
		final int DEFAULT_RESERVE_ID = 100;
	}
	
	public interface FORMAT{
		final String DateTime = "yyyy-MM-dd hh:mm:ss" ;
		static Date parsePosgresDate(String posgresDate) {
			Date result = new Date();
			try {
				if(null != posgresDate && !posgresDate.isEmpty()) {
					SimpleDateFormat df = new SimpleDateFormat(DateTime);
					result = df.parse(posgresDate);
				}
			} catch (ParseException e) {}
			return result;
		}
	}
}
