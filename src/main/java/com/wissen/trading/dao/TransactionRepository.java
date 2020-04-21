package com.wissen.trading.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wissen.trading.model.Transaction;

@Repository
public interface TransactionRepository extends  JpaRepository<Transaction, Long> {
	public List<Transaction> findByUserIdAndFundId(final Long userId, final Long fundId);
}
