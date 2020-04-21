package com.wissen.trading.dao;
  
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wissen.trading.model.Fund;
  
  @Repository  
  public interface FundRepository extends JpaRepository<Fund, Long> {
  
		/*
		 * @Query("Select units from Funds where securityName=:security") Integer
		 * getAvailableUnitBySecurity(String security);
		 * 
		 * @Modifying(clearAutomatically = true)
		 * 
		 * @Query("Update Funds SET units =:unit where securityName=:security") void
		 * updateAvailableUnit(String security, Integer unit);
		 */
	  
  
  }
 