package com.bmc.interview.saurabh.dbOperator.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bmc.interview.saurabh.dbOperator.service.AccountService;

@RestController
@RequestMapping(value = "/account")
public class AccountController {

	@Autowired
	AccountService acService;

	@RequestMapping(method = RequestMethod.GET, path = "/{id}")
	public Double getBalance(@PathVariable(value = "id") String id) {
		return acService.get(id);
	}

	@RequestMapping(method = RequestMethod.PUT, path = "/{id}/{balance}")
	public Boolean updateBalance(@PathVariable(value = "id") String id, @PathVariable(value = "balance") String balance) {
		return acService.update(id, Double.parseDouble(balance));
	}


}
