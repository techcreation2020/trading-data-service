package com.wissen.trading.controller;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.wissen.trading.dao.FundRepository;
import com.wissen.trading.dao.TransactionRepository;
import com.wissen.trading.dao.UserRepository;
import com.wissen.trading.enums.TransactionType;
import com.wissen.trading.model.Fund;
import com.wissen.trading.model.Transaction;
import com.wissen.trading.model.User;
import com.wissen.trading.utils.Message;
import com.wissen.trading.utils.MessageType;
import com.wissen.trading.utils.Result;

@RestController
@RequestMapping("/api")
public class ControllerClass {

	@Autowired 
	private UserRepository userRepository;
	
	@Autowired
	private TransactionRepository transactionRepo;

	@Autowired
	private FundRepository fundRepository;

	@GetMapping("/getFunds")
	@ResponseStatus(HttpStatus.OK)
	public Result<List<Fund>> getFunds() {
		Result<List<Fund>> result = new Result<>();
		try {
			result.setData(fundRepository.findAll());
		} catch(final Exception e) {
			e.printStackTrace();
			result.setMessages(Collections.singletonList(new Message("Error occured while fetching funds. Please retry.", MessageType.ERROR)));
		}
		
		return result;
	}
	
	@GetMapping("/getUsers")
	@ResponseStatus(HttpStatus.OK)
	public Result<List<User>> getUsers() {
		Result<List<User>> result = new Result<>();
		try {
			result.setData(userRepository.findAll());
		} catch(final Exception e) {
			e.printStackTrace();
			result.setMessages(Collections.singletonList(new Message("Error occured while fetching Users. Please retry.", MessageType.ERROR)));
		}
		return result;
	}
	
	@RequestMapping(value="/getUserTransaction/{userId}/{fundId}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public Result<List<Transaction>> getUserTransaction(@PathVariable Long userId, @PathVariable Long fundId) {
		Result<List<Transaction>> result = new Result<>();
		try {
			result.setData(transactionRepo.findByUserIdAndFundId(userId, fundId));
		} catch(final Exception e) {
			e.printStackTrace();
			result.setMessages(Collections.singletonList(new Message("Error occured while fetching Users. Please retry.", MessageType.ERROR)));
		}
		return result;
	}
	
	@PostMapping("/addTransaction")
	@ResponseStatus(HttpStatus.OK)
	public Result<Transaction> addTransaction(@RequestBody Transaction transaction) {
		
		final Result<Transaction> result = new Result<Transaction>(); 
		
		Integer transactionUnit = transaction.getTransactionUnit();
		if (transaction.getFund().getId() != null) {
			
			Optional<Fund> optionalFund = fundRepository.findById(transaction.getFund().getId());
			if(optionalFund.isPresent()) {
				Fund fund = optionalFund.get();
				
				if (fund.getUnits() < transactionUnit && transaction.getTransactionType().equals(TransactionType.BUY)) {
					
					result.setMessages(Collections.singletonList(new Message("Fund unit not available for " + fund.getFundName(), MessageType.ERROR)));					
					return result;					
				}
				
				User user = transaction.getUser();
				if (user != null && user.getUserName() != null && !user.getUserName().trim().isEmpty()) {
					final Optional<User> optional = userRepository.findByUserNameAndFundId(transaction.getUser().getUserName(), transaction.getFund().getId());
					
					if(optional.isPresent()) {						
						user = optional.get();
						
						if(user.getBalance() < transactionUnit && transaction.getTransactionType().equals(TransactionType.SELL)) {
							
							result.setMessages(Collections.singletonList(new Message("User does not have enough fund to sell.", MessageType.ERROR)));					
							return result;
						}
						
						if (transaction.getTransactionType().equals(TransactionType.BUY)) {
							user.setBalance(user.getBalance() + transactionUnit);
							fund.setUnits(fund.getUnits() - transactionUnit);
						}
						else { 
							user.setBalance(user.getBalance() - transactionUnit);
							fund.setUnits(fund.getUnits() + transactionUnit);
						}
						
						user.setFund(fund);
						fundRepository.save(fund);
						transaction.setUser(userRepository.save(user));
					} else {
						
						if (!transaction.getTransactionType().equals(TransactionType.SELL)) {
							user.setBalance(transaction.getTransactionUnit());
							fund.setUnits(fund.getUnits() - transactionUnit);
							user.setFund(fund);
							fundRepository.save(fund);
							transaction.setUser(userRepository.save(user));
						} else {
							result.setMessages(Collections.singletonList(new Message("User does not have any purchase history.", MessageType.ERROR)));					
							return result;
						}
					}
				} else {
					result.setMessages(Collections.singletonList(new Message("Bad request - User can not be null.", MessageType.ERROR)));
					return result;
				}
				Transaction t =  transactionRepo.save(transaction);
				result.setData(t);
				
			}
		} else {
			result.setMessages(Collections.singletonList(new Message("Fund does not exist.", MessageType.ERROR)));
			return result;
		}
		return result;
	}

	/*
	 * @GetMapping("/getUserFundList") public ResponseEntity<List<User>>
	 * getUserFundList(@RequestParam("name") String userName,
	 * 
	 * @RequestParam("security") String security) {
	 * 
	 * return new ResponseEntity<>( (List<User>)
	 * userRepository.getUserFundList(userName.toLowerCase(),
	 * security.toLowerCase()), HttpStatus.OK); }
	 */

/*	@GetMapping("/getUserBalance")
	public ResponseEntity<Integer> getUserBalance(@RequestParam("userName") String userName,
			@RequestParam("security") String security) {
		return new ResponseEntity<>(getBalance(userName, security), HttpStatus.OK);
	}

	@PostMapping("/submit")
	public ResponseEntity<Object> submit(@RequestBody User user) {
		
		User savedUsser = null;
		if (user.getTransactionType().toLowerCase().equalsIgnoreCase("buy")) {

			Integer getAvailaibleUnits = fundRepository.getAvailableUnitBySecurity(user.getSecurity().toLowerCase());
			
			if (getAvailaibleUnits != null && user.getTransactionUnit() <= getAvailaibleUnits) {
				savedUser = userRepository.save(user);
				fundRepository.updateAvailableUnit(user.getSecurity().toLowerCase(),
						getAvailaibleUnits - user.getTransactionUnit());
			} else {				
				return new ResponseEntity<>("Insufficient unit available for "+user.getSecurity()+" ." , HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} else {
			try {
				return new ResponseEntity<>(sell(user), HttpStatus.OK);
			} catch (Exception e) {
				e.printStackTrace();
				return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		return new ResponseEntity<>(savedUser, HttpStatus.OK);
	}

	private int getBalance(String userName, String security) {
		Integer purchasedUnits = userRepository.getPurchasedUnitByUserName(security.toLowerCase(),
				userName.toLowerCase(), "buy");
		purchasedUnits = purchasedUnits != null ? purchasedUnits : 0;
		Integer soldUnits = userRepository.getSoldUnitByUserName(security.toLowerCase(), userName.toLowerCase(),
				"sell");
		soldUnits = soldUnits != null ? soldUnits : 0;
		return (purchasedUnits - soldUnits);
	}

	private User sell(User user) {
		Integer purchasedUnits = userRepository.getPurchasedUnitByUserName(user.getSecurity().toLowerCase(),
				user.getUserName().toLowerCase(), "buy");
		purchasedUnits = purchasedUnits != null ? purchasedUnits : 0;

		if (purchasedUnits > 0) {
			Integer soldUnits = userRepository.getSoldUnitByUserName(user.getSecurity().toLowerCase(),
					user.getUserName().toLowerCase(), user.getTransactionType().toLowerCase());
			soldUnits = soldUnits != null ? soldUnits : 0;

			if (purchasedUnits >= (soldUnits + user.getTransactionUnit())) {
				Integer getAvailaibleUnits = fundRepository
						.getAvailableUnitBySecurity(user.getSecurity().toLowerCase());
				getAvailaibleUnits = getAvailaibleUnits != null ? getAvailaibleUnits : 0;
				fundRepository.updateAvailableUnit(user.getSecurity().toLowerCase(),
						getAvailaibleUnits + user.getTransactionUnit());
				return userRepository.save(user);
			} else {
				throw new UserException("Insufficient funds...");
			}
		} else {
			throw new UserException("Insufficient funds...");
		}
	}*/

}
