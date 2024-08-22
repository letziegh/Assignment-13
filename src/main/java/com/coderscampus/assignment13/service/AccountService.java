package com.coderscampus.assignment13.service;

import com.coderscampus.assignment13.domain.Account;
import com.coderscampus.assignment13.repository.AccountRepository;
import com.coderscampus.assignment13.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    @Autowired
    AccountRepository accountRepository;

   @Autowired
    UserRepository userRepository;


    public Account findAccountById(Long accountId) {
        return accountRepository.findById (accountId).orElse(null);
    }//Issue might here in this method


   public Account updateAccountName(Long accountId, String newName){
       Account account = accountRepository.findById(accountId).orElse(null);
       if (account != null) {
           account.setAccountName(newName);
           accountRepository.save(account);
       }
       return account;
   }





}
