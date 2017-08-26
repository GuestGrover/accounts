package com.bmc.interview.saurabh.dbOperator.repository;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import org.springframework.stereotype.Component;

import com.bmc.interview.saurabh.dbOperator.util.LRUCacheusingLinkedHashMap;

/**
 * Responsible to load the account details if it is not already present and keep those loaded
 * account details in a data structure.. 
 * This class should be the only place from where a single account will be exposed to outer
 * world. Entire Data Structure will not be exposed to avoid any parallel modification.
 * 
 * @author Saurabh
 */

@Component
public class AccountRepository {
	
	private LRUCacheusingLinkedHashMap<String, Double> lruMap = new LRUCacheusingLinkedHashMap<String, Double>(10);
	private AccountLoader loader = new AccountLoader();
	
	public synchronized Double getAccountBalance(String accountID){
		
		if(lruMap.containsKey(accountID))
			return lruMap.get(accountID);
		
		Double balance = new Double(loader.load(accountID));
		lruMap.putIfAbsent(accountID, balance);
		return balance;
	}
	
	public synchronized boolean updateAccount(String accountID, Double balance){
		
		if(lruMap.containsKey(accountID)){
			lruMap.put(accountID, balance);
		}else{
			lruMap.putIfAbsent(accountID, balance);
		}
		loader.updateDB(accountID, balance);
		return true;
	}
	
	private class AccountLoader{
		
		private Properties prop = null;
		private InputStream is = null;
		private OutputStream os = null;
		
		public AccountLoader() {
			try {
	            this.prop = new Properties();
	            is = this.getClass().getResourceAsStream("account-data.properties");
	            os = new FileOutputStream("account-data.properties");
	            prop.load(is);
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
		}
		
		public double load(String accountID) {
	        return Double.parseDouble(prop.getProperty(accountID + ".balance"));
		}

		public void updateDB(String accountID, Double balance) {
			prop.setProperty(accountID + ".balance", String.valueOf(balance));
			prop.store(os, null);
		}
		
	}
	
	public static void main(String[] args) {
		
		AccountRepository repo = new AccountRepository();
		System.out.println(repo.getAccountBalance("account1"));
		repo.updateAccount("account1", new Double(101.22));
		System.out.println(repo.getAccountBalance("account1"));
		
	}
}
