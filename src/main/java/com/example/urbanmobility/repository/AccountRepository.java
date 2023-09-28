package com.example.urbanmobility.repository;

import com.example.urbanmobility.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findByUsername(String username);
    Account save(Account account);
    List<Account> findAll();

}
