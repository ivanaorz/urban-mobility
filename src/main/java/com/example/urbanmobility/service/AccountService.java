package com.example.urbanmobility.service;

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
        // Check if the card number is invalid
        if (!isValidCardNumber(account.getPaymentInfo())) {
            throw new InvalidCardNumberException("Invalid card number format. Card number must have 16 digits.");
        }
        return accountRepository.save(account);
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.isEmpty()) {
            return false;
        }
        return phoneNumber.matches("[0-9]{10}");
    }

        private boolean isValidCardNumber(String cardNumber) {
            if (cardNumber == null || cardNumber.isEmpty()) {
                return false;
            }
            //Checking for 16 digits with spaces, disallowing letters and symbols
            return cardNumber.matches("^[0-9 ]+$") && cardNumber.replaceAll(" ", "").matches("^[0-9]{16}$");

        }




    public Optional <Account> getAccountById(long id) {

        return accountRepository.findById(id);
    }


    public Account updateAccount(Long accountId,Account updatedAccount) {
        if (!accountRepository.existsById(accountId)) {
            throw new ResourceNotFoundException("Account with ID" + " " + accountId + " " + "does not exist");
        }
        if (updatedAccount.getUsername() == null || updatedAccount.getUsername().isEmpty()) {
            throw new IllegalArgumentException("Username field cannot be empty");
        }
        if (allFieldsNullOrEmpty(updatedAccount)) {
            throw new IllegalArgumentException("Updated account must contain at least one non-empty field");
        }
        updatedAccount.setId(accountId); // Setting the ID of the updated account
        return accountRepository.save(updatedAccount);
    }
    private boolean allFieldsNullOrEmpty(Account account) {
        return
                account.getUsername() == null || account.getUsername().isEmpty() &&
                        account.getRole() == null || account.getRole().isEmpty() &&
                        account.getPaymentInfo() == null || account.getPaymentInfo().isEmpty() &&
                        account.getPaymentHistory() == 0 &&
                        account.getPhone() == null || account.getPhone().isEmpty() &&
                        account.getActiveBookings() == null || account.getActiveBookings().isEmpty();
    }

    public void deleteAccount(long id) {
        // Check if the account exists
        if (accountRepository.existsById(id)) {
            accountRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Account not found");
        }

        accountRepository.deleteById(id);
    }

}
