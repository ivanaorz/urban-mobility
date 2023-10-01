package com.example.urbanmobility.service;

import com.example.urbanmobility.exception.AccountCreationFailedException;
import com.example.urbanmobility.exception.ResourceNotFoundException;
import com.example.urbanmobility.exception.UsernameAlreadyExistsException;
import com.example.urbanmobility.model.Account;
import com.example.urbanmobility.repository.AccountRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class AccountServiceIntegrationTest {
    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountRepository accountRepository;

    private Account account;


    @BeforeEach
    public void setUp() {
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
    @Transactional
    void createAccount_Should_ThrowException_When_DuplicateUsernameExists() {
        // Creating the initial account
        accountRepository.save(account);

        // Creating an account with the same username
        assertThrows(UsernameAlreadyExistsException.class, () -> {
            accountService.createAccount(account);
        });

        Account retrievedAccount = accountRepository.findByUsername("Tom");

        assertNotNull(retrievedAccount);
    }

    @Test
    @Transactional
    void createAccount_Should_RollbackTransactionOnError() {
        // Simulating an error condition by providing a username that triggers an exception
        account.setUsername("error");

        // Checking that the exception is thrown
        assertThrows(AccountCreationFailedException.class, () -> {
            accountService.createAccount(account);
        });

        // Ensuring that the transaction is rolled back
        Account retrievedAccount = accountRepository.findByUsername("error");
        assertNull(retrievedAccount);
    }

    //METHOD: deleteAccount

    @Test
    @Transactional
    void deleteAccount_Should_GetTheDatabaseEmpty_AfterDeleteAccount() {
        // Arrange
        accountRepository.save(account);

        // Act
        accountService.deleteAccount(1L);

        // Assert
        assertEquals(0, accountRepository.findAll().size());
    }

    @Test
    @Transactional
    void deleteAccount_Should_HandleLowestBoundaryAccountId() {
        long lowestBoundaryAccountId = 1L; //Lowest account ID
        Account lowestBoundaryAccount = Account.builder()
                .id(lowestBoundaryAccountId)
                .username("UserWithLowestBoundaryId")
                .role("User")
                .paymentInfo("1234 5678 9012 3456")
                .paymentHistory(5)
                .isPaymentSet(true)
                .phone("1234567890")
                .activeBookings("2")
                .build();

        // Saving the account to the database
        accountRepository.save(lowestBoundaryAccount);

        // Verifying that the account exists before deletion
        assertTrue(accountRepository.existsById(lowestBoundaryAccountId), "Account should exist before deletion");

        // Attempting to delete the account with the lowest ID
        accountService.deleteAccount(lowestBoundaryAccountId);

        // Ensuring the account is deleted
        assertFalse(accountRepository.existsById(lowestBoundaryAccountId), "Account should not exist after deletion");
    }

//    @Test
//    @Transactional
//    void deleteAccount_Should_HandleHighestBoundaryAccountId() {
//        long highestBoundaryAccountId = 1000L;
//        Account highestBoundaryAccount = Account.builder()
//                .id(highestBoundaryAccountId)
//                .username("Tom")
//                .role("User")
//                .paymentInfo("3334 5566 3432 9090")
//                .paymentHistory(4)
//                .isPaymentSet(true)
//                .phone("0722946563")
//                .activeBookings("3")
//                .build();
//
//        accountRepository.save(highestBoundaryAccount);
//
//        // Verifying that the account exists before deletion
//        assertTrue(accountRepository.existsById(highestBoundaryAccountId), "Account should exist before deletion");
//        accountService.deleteAccount(highestBoundaryAccountId);
//
//        // Ensuring the account is deleted
//        assertFalse(accountRepository.existsById(highestBoundaryAccountId), "Account should not exist after deletion");
//    }



    //METHOD: updateAccount

    @Test
    @Transactional
    void updateAccount_Should_UpdateAccountInformation() {
        // Arrange
        accountRepository.save(account);

        Account updatedAccount = Account.builder()
                .id(1L)
                .username("UpdatedUser")
                .role("Admin")
                .paymentInfo("1234 5678 9012 3456")
                .paymentHistory(5)
                .isPaymentSet(false)
                .phone("9876543210")
                .activeBookings("6")
                .build();

        // Act
        Account result = accountService.updateAccount(1L, updatedAccount);

        // Assert
        assertEquals(updatedAccount, result);
    }
    @Test
    @Transactional
    public void updateAccount_Should_HandleNonExistentAccount() {
        // Arrange
        Account updatedAccount = Account.builder()
                .id(2L) // Assuming this account doesn't exist
                .username("UpdatedUser")
                .build();

        // Act and Assert
        assertThrows(ResourceNotFoundException.class,
                () -> accountService.updateAccount(2L, updatedAccount));
    }

    @Test
    public void updateAccount_Should_HandleInvalidData() {
        // Arrange
        accountRepository.save(account);
        Account updatedAccount = Account.builder()
                .id(1L)
                .username("") // Invalid username
                .role("Admin")
                .paymentInfo("1234 5678 9012 3456")
                .paymentHistory(-1) // Negative payment history
                .isPaymentSet(false)
                .phone("9876543210")
                .activeBookings("6")
                .build();

        // Act and Assert
        assertThrows(IllegalArgumentException.class,
                () -> accountService.updateAccount(1L, updatedAccount));
    }

}
