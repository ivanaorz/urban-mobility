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
        // Check if the phone number is invalid
        if (!isValidPhoneNumber(account.getPhone())) {
            throw new InvalidPhoneNumberException("Invalid phone number. Please enter a correct phone number.");
        }

        return accountRepository.save(account);
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.isEmpty()) {
            return false;
        }
        return phoneNumber.matches("[0-9]{10}");

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
