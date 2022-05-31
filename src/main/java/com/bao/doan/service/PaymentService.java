package com.bao.doan.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bao.doan.dao.PaymentDao;
import com.bao.doan.dto.DataPayment;
import com.bao.doan.entity.Orders;
import com.bao.doan.entity.Payment;
import com.bao.doan.utils.UniConstant;
import com.bao.doan.utils.UniUtil;

@Service
@Transactional
public class PaymentService {
	
	@Autowired
	private PaymentDao paymentDao;

	private static final Log log = LogFactory.getLog(PaymentService.class);
	
	private static final Log logOrder = LogFactory.getLog(Orders.class);
	
	public Map<String, Object> themMoiPayment(Payment payment) {
		int code = UniConstant.ErrorCode.SUCCESS;
		String message = UniConstant.Message.INSERT_SUCCESS;
		Payment pm = new Payment();
		try {
			pm.setId(UniUtil.getTableId());
			pm.setName("PREMIUM");
			pm.setAmount(payment.getAmount());
			pm.setDaxoa((short) 0);
			paymentDao.savePayment(pm);
			message = UniConstant.Message.INSERT_SUCCESS;
			code = UniConstant.ErrorCode.SUCCESS;
		} catch (Exception e) {
			log.error("them moi payment:" + e.getMessage());
			code = UniConstant.ErrorCode.FAIL;
			message = UniConstant.Message.INSERT_FAIL;
		}
		return UniConstant.CommonService.setResult(code, message, pm);
	}
	
	public Map<String, Object> themMoiOrder(Orders order) {
		int code = UniConstant.ErrorCode.SUCCESS;
		String message = UniConstant.Message.INSERT_SUCCESS;
		Orders od = new Orders();
		Date ngaytao = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(ngaytao);
		cal.add(Calendar.MONTH, 1);
		Date ngayhethan = cal.getTime();
		try {
			od.setId(UniUtil.getTableId());
			od.setUserid(order.getUserid());
			od.setPaymentid(order.getPaymentid());
			od.setNgaytao(ngaytao);
			od.setNgayhethan(ngayhethan);
			od.setDaxoa((short) 0);
			paymentDao.saveOrder(od);
			message = UniConstant.Message.INSERT_SUCCESS;
			code = UniConstant.ErrorCode.SUCCESS;
		} catch (Exception e) {
			logOrder.error("them moi order:" + e.getMessage());
			code = UniConstant.ErrorCode.FAIL;
			message = UniConstant.Message.INSERT_FAIL;
		}
		return UniConstant.CommonService.setResult(code, message, od);
	}
	
	public Map<String, Object> getListOrder() {
		int code = UniConstant.ErrorCode.SUCCESS;
		String message = UniConstant.Message.QUERY_SUCCESS;
		List<DataPayment> dataPayments = paymentDao.getListDataPayment();
		if (null == dataPayments) {
			log.error("Không tìm thấy danh sách payment ");
		}
		return UniConstant.CommonService.setResult(code, message, dataPayments);
	}
	
	public Map<String, Object> getOrderByUser(String email) {
		int code = UniConstant.ErrorCode.SUCCESS;
		String message = UniConstant.Message.QUERY_SUCCESS;
		DataPayment dataPayments = paymentDao.getOrderByUser(email);
		if (null == dataPayments) {
			log.error("Không tìm thấy danh sách payment ");
		}

		return UniConstant.CommonService.setResult(code, message, dataPayments);
	}
}
