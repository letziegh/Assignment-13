package com.coderscampus.assignment13.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.coderscampus.assignment13.domain.Account;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long>{

    List<Account> findAccountByAccountId(Long accountId );

}
