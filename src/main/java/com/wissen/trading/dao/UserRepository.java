package com.wissen.trading.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wissen.trading.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
	public Optional<User> findByUserNameAndFundId(final String userName, final Long fundId);
	/*
	 * @Query("SELECT SUM(transactionUnit) from User " +
	 * "where transactionType=:transactionType" + " and userName =:userName " +
	 * "and security =:security") Integer getSoldUnitByUserName(String security,
	 * String userName, String transactionType);
	 * 
	 * @Query("SELECT SUM(transactionUnit) from User " +
	 * "where transactionType=:transactionType" + " and userName =:userName " +
	 * "and security =:security") Integer getPurchasedUnitByUserName(String
	 * security, String userName, String transactionType);
	 * 
	 * @Query("SELECT c from User c where c.userName=:userName AND c.security=:security ORDER BY c.createDateTime"
	 * ) Collection<User> getUserFundList(String userName, String security);
	 */
}
