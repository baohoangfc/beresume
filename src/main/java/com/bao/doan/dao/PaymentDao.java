package com.bao.doan.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bao.doan.dto.DataPayment;
import com.bao.doan.entity.Orders;
import com.bao.doan.entity.Payment;
import com.bao.doan.utils.UniConstant;

@Transactional
@Repository
public class PaymentDao {
	@PersistenceContext
	private EntityManager em;

	public void savePayment(Payment payment) {
		em.persist(payment);
	}

	public void updatePayment(Payment payment) {
		em.merge(payment);
		em.flush();
	}
	
	public void saveOrder(Orders order) {
		em.persist(order);
	}

	public void updateOrder(Orders order) {
		em.merge(order);
		em.flush();
	}
	
	@SuppressWarnings("unchecked")
	public List<DataPayment> getListDataPayment() {
		List<DataPayment> listDataPayment = new ArrayList<>();

		StringBuilder sqlQuery = new StringBuilder();
		sqlQuery.append(" SELECT userid, paymentid, hoten as username, email, name as namepayment, amount, orders.ngaytao, ngayhethan ");
		sqlQuery.append(" FROM payment ");
		sqlQuery.append(" INNER JOIN orders on orders.paymentid = payment.id ");
		sqlQuery.append(" INNER JOIN nguoidung on orders.userid = nguoidung.id ");
		sqlQuery.append(" WHERE orders.daxoa = " + UniConstant.XOA.CHUA_XOA);
		sqlQuery.append(" ORDER BY orders.ngaytao DESC");
		Query query = em.createNativeQuery(sqlQuery.toString());
		List<Object[]> lstObj = query.getResultList();
//		Date ngaytao = new Date();
//		Calendar cal = Calendar.getInstance();
//		cal.setTime(ngaytao);
//		cal.add(Calendar.MONTH, 1);
//		Date ngayhethan = cal.getTime();
		for (Object[] obj : lstObj) {
			DataPayment dataPayment = new DataPayment();
			dataPayment.setUserid(Long.valueOf(obj[0].toString()));
			dataPayment.setPaymentid(Long.valueOf(obj[1].toString()));
			dataPayment.setUsername(String.valueOf(obj[2].toString()));
			dataPayment.setEmail(String.valueOf(obj[3].toString()));
			dataPayment.setNamepayment(String.valueOf(obj[4].toString()));
			dataPayment.setAmount(Double.valueOf(obj[5].toString()));
			dataPayment.setNgaytao(UniConstant.FORMAT.parsePosgresDate(obj[6].toString()));
			dataPayment.setNgayhethan(UniConstant.FORMAT.parsePosgresDate(obj[7].toString()));
			listDataPayment.add(dataPayment);
		}
		return listDataPayment;
	}
	
	@SuppressWarnings("unchecked")
	public DataPayment getOrderByUser(String email) {
		DataPayment dataPayment = new DataPayment();

		StringBuilder sqlQuery = new StringBuilder();
		sqlQuery.append(" SELECT userid, paymentid, hoten as username, email, name as namepayment, amount, orders.ngaytao, ngayhethan ");
		sqlQuery.append(" FROM payment ");
		sqlQuery.append(" INNER JOIN orders on orders.paymentid = payment.id ");
		sqlQuery.append(" INNER JOIN nguoidung on orders.userid = nguoidung.id ");
		sqlQuery.append(" WHERE orders.daxoa = " + UniConstant.XOA.CHUA_XOA);
		sqlQuery.append(" AND email = :email");
		sqlQuery.append(" ORDER BY orders.ngaytao DESC");
		Query query = em.createNativeQuery(sqlQuery.toString()).setParameter("email", email);
		List<Object[]> lstObj = query.getResultList();
		for (Object[] obj : lstObj) {
			dataPayment.setUserid(Long.valueOf(obj[0].toString()));
			dataPayment.setPaymentid(Long.valueOf(obj[1].toString()));
			dataPayment.setUsername(String.valueOf(obj[2].toString()));
			dataPayment.setEmail(String.valueOf(obj[3].toString()));
			dataPayment.setNamepayment(String.valueOf(obj[4].toString()));
			dataPayment.setAmount(Double.valueOf(obj[5].toString()));
			dataPayment.setNgaytao(UniConstant.FORMAT.parsePosgresDate(obj[6].toString()));
			dataPayment.setNgayhethan(UniConstant.FORMAT.parsePosgresDate(obj[7].toString()));
		}
		return dataPayment;
	}
	
	@SuppressWarnings("unchecked")
	public int checkExitsPayment(long userid) {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT pm.userid from Payment nd ");
		sql.append(" WHERE pm.userid = '" + userid + "' AND nd.daxoa = 0  ");
		if (userid > 0) {
			sql.append(" AND pm.userid != " + userid + " ");
		}
		List<Long> list = em.createQuery(sql.toString()).getResultList();
		int count = list.size();
		return count;
	}
}
