package com.example.urbanmobility.service;

//import com.example.urbanmobility.model.Account;
import com.example.urbanmobility.exception.*;
import com.example.urbanmobility.model.Account;
import com.example.urbanmobility.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    @Autowired
    public AccountService(AccountRepository accountRepository){

        this.accountRepository = accountRepository;
    }


    public Account createAccount(Account account) {
        // Check if the username already exists
        if (accountRepository.findByUsername(account.getUsername()) != null) {
            throw new UsernameAlreadyExistsException("This username already exists. Try another username.");
        }
        // Check if a car with the same plate already exists
//        if (carRepository.existsByCarPlate(car.getCarPlate())) {
//            throw new DuplicateCarException("A car with the same plate already exists.");
//        }
        // If no duplicate is found, proceed with saving the car
        return accountRepository.save(account);
    }
    public Optional <Account> getAccountById(long id) {

        return accountRepository.findById(id);
    }


    public Account updateAccount(Account updatedAccount) {
        return accountRepository.save(updatedAccount);
    }

    public void deleteAccount(long id) {

        accountRepository.deleteById(id);
    }

}
