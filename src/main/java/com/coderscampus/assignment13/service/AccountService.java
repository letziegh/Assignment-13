package com.coderscampus.assignment13.service;

import com.coderscampus.assignment13.domain.Account;
import com.coderscampus.assignment13.domain.User;
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


    public Account findAccountByAccountId(Long accountId) {
        return accountRepository.findById (accountId).orElse(null);
    }


   public Account updateAccountName(Long accountId, String newName){
       Account account = accountRepository.findById(accountId).orElse(null);
       if (account != null) {
           account.setAccountName(newName);
           accountRepository.save(account);
       }
       return account;
   }


    public void createNewAccount(Long userId, String name) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            Account account = new Account();
            account.setAccountName(name);
            account.getUsers().add(user);
            user.getAccounts().add(account);
            accountRepository.save(account);
            //user.setAccounts(user.getAccounts());
            userRepository.save(user);
        }

    }
}
