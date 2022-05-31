package com.bao.doan.user;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public class UserDao {

	@PersistenceContext
	private EntityManager em;

	public void saveUser(ApplicationUser applicationUser) {
		em.persist(applicationUser);
	}

}
