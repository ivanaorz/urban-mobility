package com.example.urbanmobility.service;

//import com.example.urbanmobility.model.Account;
import com.example.urbanmobility.model.Account;
import com.example.urbanmobility.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    @Autowired
    public AccountService(AccountRepository accountRepository){

        this.accountRepository = accountRepository;
    }


    public Account saveAccount(Account account) {
        // Check if a car with the same plate already exists
//        if (carRepository.existsByCarPlate(car.getCarPlate())) {
//            throw new DuplicateCarException("A car with the same plate already exists.");
//        }
        // If no duplicate is found, proceed with saving the car
        return accountRepository.save(account);
    }

}
