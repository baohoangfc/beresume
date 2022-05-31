package com.bao.doan.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bao.doan.entity.Nguoidung;
import com.bao.doan.utils.UniConstant;

@Transactional
@Repository
public class NguoiDungDao {

	@PersistenceContext
	private EntityManager em;

	public void saveNguoiDung(Nguoidung nguoidung) {
		em.persist(nguoidung);
	}

	public void updateNguoiDung(Nguoidung nguoidung) {
		em.merge(nguoidung);
	}

	@SuppressWarnings("unchecked")
	public List<Nguoidung> getDanhSachNguoiDung() {
		List<Nguoidung> list = new ArrayList<>();
		StringBuilder sqlQuery = new StringBuilder();
		sqlQuery.append(" FROM Nguoidung");
		sqlQuery.append(" WHERE vaitro != 'ROLE_USER' AND daxoa = " + UniConstant.XOA.CHUA_XOA);

		list = (List<Nguoidung>) em.createQuery(sqlQuery.toString()).getResultList();

		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<Nguoidung> getAllNguoiDung() {
		List<Nguoidung> list = new ArrayList<>();
		StringBuilder sqlQuery = new StringBuilder();
		sqlQuery.append(" FROM Nguoidung");
		sqlQuery.append(" WHERE daxoa = " + UniConstant.XOA.CHUA_XOA);

		list = (List<Nguoidung>) em.createQuery(sqlQuery.toString()).getResultList();

		return list;
	}

	public Nguoidung layThongTinNguoiDungTheoTenDangNhap(String tendangnhap) {
		StringBuilder sql = new StringBuilder();
		Nguoidung nguoidung = null;
		sql.append(" select nd from Nguoidung nd where nd.tendangnhap = :tendangnhap and nd.daxoa = 0 ");
		try {
			nguoidung = (Nguoidung) em.createQuery(sql.toString()).setParameter("tendangnhap", tendangnhap.trim())
					.getSingleResult();
		} catch (Exception e) {
			return null;
		}
		return nguoidung;
	}

	public Nguoidung getUserIdByEmail(String email) {
		StringBuilder sql = new StringBuilder();
		Nguoidung nguoidung = null;
		sql.append(" select nd from Nguoidung nd where nd.email = :email and nd.daxoa = 0 ");
		try {
			nguoidung = (Nguoidung) em.createQuery(sql.toString()).setParameter("email", email.trim())
					.getSingleResult();
		} catch (Exception e) {
			return null;
		}
		return nguoidung;
	}

	@SuppressWarnings("unchecked")
	public int checkTontaiTenDangNhapEmail(String tendangnhap, String email, long id) {
		StringBuilder sql = new StringBuilder();
		sql.append(" select nd.id from Nguoidung nd ");
		sql.append(" where ( lower(nd.tendangnhap) = '" + tendangnhap.toLowerCase().trim() + "' or nd.email = '"
				+ email.trim() + "' ) and nd.daxoa = 0  ");
		if (id > 0) {
			sql.append(" and nd.id != " + id + " ");
		}
		List<Long> list = em.createQuery(sql.toString()).getResultList();
		int count = list.size();
		return count;
	}
}
