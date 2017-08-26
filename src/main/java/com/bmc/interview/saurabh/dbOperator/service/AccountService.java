package com.bmc.interview.saurabh.dbOperator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bmc.interview.saurabh.dbOperator.repository.AccountRepository;

@Service
public class AccountService {

	@Autowired
	AccountRepository repository;
	
	public Double get(String accountID) {
		return repository.getAccountBalance(accountID);
	}

	public Boolean update(String accountID, Double balance) {
		return repository.updateAccount(accountID, balance);
	}

}
