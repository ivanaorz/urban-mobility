package com.example.urbanmobility.service;

import com.example.urbanmobility.exception.UsernameAlreadyExistsException;
import com.example.urbanmobility.model.Account;
import com.example.urbanmobility.repository.AccountRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AccountServiceIntegrationTest {
    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountRepository accountRepository;

    private Account account;


    @BeforeEach
    public void setUp(){
        // Creating an Account object
         account = Account.builder()
                 .id(1L)
                 .username("Tom")
                 .role("User")
                 .paymentInfo("3334 5566 3432 9090")
                 .paymentHistory(4)
                 .isPaymentSet(true)
                 .phone("0722946563")
                 .activeBookings("3")
                 .build();
    }

    //METHOD: createAccount

    @Test
    @Transactional
    void createAccount_Should_SaveAccountSuccessfullyInDatabase() {
        // Calling the createAccount method from the accountService
        Account savedAccount = accountService.createAccount(account);

        // Retrieving the account from the database
        Account retrievedAccount = accountRepository.findById(savedAccount.getId()).orElse(null);

        assertNotNull(retrievedAccount);
    }

    @Test
    void getAccountById() {
    }

    @Test
    void updateAccount() {
    }

    @Test
    void deleteAccount() {
    }
}