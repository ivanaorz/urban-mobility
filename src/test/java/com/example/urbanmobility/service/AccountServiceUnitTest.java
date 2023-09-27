package com.example.urbanmobility.service;
import com.example.urbanmobility.exception.*;

import com.example.urbanmobility.model.Account;
import com.example.urbanmobility.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceUnitTest {
    @Mock
    private AccountRepository accountRepository;

    //Class we are testing
    @InjectMocks
    private AccountService accountService;

    private Account accountToCreate;

    @BeforeEach
    public void setUp() {
        //Creating an account object for accountToCreate
        accountToCreate = Account.builder()
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
    void createAccount_Should_Return_TheCreatedAccount() {

                // Arrange:
                when(accountRepository.save(accountToCreate)).thenReturn(accountToCreate);

                // Act:
                Account createdAccount = accountService.createAccount(accountToCreate);

                // Assert:
                verify(accountRepository, times(1)).save(accountToCreate);
                assertEquals(accountToCreate, createdAccount);
            }
    @Test
    void createAccount_Should_ThrowException_If_AccountWithTheSameUsernameAlreadyExists() {

        // Mocking the behavior of findByUsername to return an existing account with the same username

        when(accountRepository.findByUsername(accountToCreate.getUsername())).thenReturn(accountToCreate);


        // Verifying that the saveAccount method throws an exception
        assertThrows(UsernameAlreadyExistsException.class, () -> {
            accountService.createAccount(accountToCreate);
        });

        // Verify that findByUsername was called
        verify(accountRepository, times(1)).findByUsername(accountToCreate.getUsername());
    }

    @Test
    void createAccount_Should_ReturnErrorMessage_If_InvalidPhoneNumber() {
        accountToCreate.setPhone("123456789"); // Invalid phone number (less than 10 digits)
        // Act and Assert:
        Exception exception = assertThrows(InvalidPhoneNumberException.class, () -> {
            accountService.createAccount(accountToCreate);
        });
        // Verifying that the error message is as expected
        String expectedErrorMessage = "Invalid phone number. Please enter a correct phone number.";
        String actualErrorMessage = exception.getMessage();
        assertEquals(expectedErrorMessage, actualErrorMessage);

        // Ensuring that accountRepository.save() was not called
        verify(accountRepository, never()).save(any(Account.class));
    }
    @Test
    void createAccount_Should_ReturnErrorMessage_If_PhoneNumber_Contains_LettersAndOrSymbols() {
        accountToCreate.setPhone("-1234567890p"); // Invalid phone number (contains letter and symbol)
        // Act and Assert:
        Exception exception = assertThrows(InvalidPhoneNumberException.class, () -> {
            accountService.createAccount(accountToCreate);
        });
        // Verifying that the error message is as expected
        String expectedErrorMessage = "Invalid phone number. Please enter a correct phone number.";
        String actualErrorMessage = exception.getMessage();
        assertEquals(expectedErrorMessage, actualErrorMessage);

        // Ensuring that accountRepository.save() was not called
        verify(accountRepository, never()).save(any(Account.class));
    }
    @Test
    void createAccount_Should_ReturnErrorMessage_If_InvalidCardNumberDigitsLength() {
        accountToCreate.setPaymentInfo("3477 8567 3477 7"); //Invalid card number format (less than 16 digits)

        // Act and Assert:
        Exception exception = assertThrows(InvalidCardNumberException.class, () -> {
            accountService.createAccount(accountToCreate);
        });

        // Verifying that the error message is as expected
        String expectedErrorMessage = "Invalid card number format. Card number must have 16 digits.";
        String actualErrorMessage = exception.getMessage();
        assertEquals(expectedErrorMessage, actualErrorMessage);

        // Ensuring that accountRepository.save() was not called
        verify(accountRepository, never()).save(any(Account.class));
    }
    @Test
    void createAccount_Should_ReturnErrorMessage_If_CardNumber_HasLettersAndOrSymbols() {
        accountToCreate.setPaymentInfo("3477 8567 3477 7875p."); //Invalid card number format (contains letter and symbol)

        // Act and Assert:
        Exception exception = assertThrows(InvalidCardNumberException.class, () -> {
            accountService.createAccount(accountToCreate);
        });

        // Verifying that the error message is as expected
        String expectedErrorMessage = "Invalid card number format. Card number must have 16 digits.";
        String actualErrorMessage = exception.getMessage();
        assertEquals(expectedErrorMessage, actualErrorMessage);

        // Ensuring that accountRepository.save() was not called
        verify(accountRepository, never()).save(any(Account.class));
    }

    //METHOD: deleteAccount

    @Test
    void deleteAccount_Should_DeleteById_AnExistingAccount() {

        // Arrange
        Long accountId = accountToCreate.getId();

        // Mocking the behavior of accountRepository.existsById to return false
        when(accountRepository.existsById(accountId)).thenReturn(true);
        doNothing().when(accountRepository).deleteById(accountId);

        // Act
        accountService.deleteAccount(accountId);

        // Assert
        verify(accountRepository, times(1)).deleteById(accountId);
    }


    @Test
    void deleteAccount_Should_ReturnAccountNotFound_WhenAccountDoesNotExist() {
        // Arrange
        Long nonExistentAccountId = 2L; // Assuming this ID does not exist in the repository
        // Simulating that the account does not exist
        when(accountRepository.existsById(nonExistentAccountId)).thenReturn(false);

        // Act and Assert
        assertThrows(EntityNotFoundException.class, () -> {
            accountService.deleteAccount(nonExistentAccountId);
        });
    }

    //METHOD: updateAccount

    @Test
    void updateAccount_Should_Return_UpdatedAccount() {
        long accountId = accountToCreate.getId();
        // Creating an updated account with the desired changes
        Account updatedAccount = Account.builder()
                .id(accountId)
                .username("UpdatedTom")
                .role("Admin")
                .paymentInfo("1234 5678 9012 3456")
                .paymentHistory(5)
                .isPaymentSet(false)
                .phone("1234567890")
                .activeBookings("5")
                .build();

        // Mocking the behavior of accountRepository.findById to return the existing account
        when(accountRepository.existsById(accountId)).thenReturn(true);

        // Mocking the behavior of accountRepository.save to return the updated account
        when(accountRepository.save(updatedAccount)).thenReturn(updatedAccount);
        Account result = accountService.updateAccount(accountId, updatedAccount);

        // Assert
        verify(accountRepository, times(1)).existsById(accountId);
        verify(accountRepository, times(1)).save(updatedAccount);
        assertEquals(updatedAccount, result);
    }
    @Test
    void updateAccount_Should_ThrowException_If_UsernameDataIsMissing() {
        long accountId = accountToCreate.getId();

        // Creating an updating account with an empty username (invalid data)
        Account updatedAccount = new Account();
        updatedAccount.setId(accountId);
        updatedAccount.setUsername(""); // Invalid: Empty username

        // Mocking the behavior of accountRepository.existsById to return true
        when(accountRepository.existsById(accountId)).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> {
            accountService.updateAccount(accountId, updatedAccount);
        });
        verify(accountRepository, never()).save(any(Account.class));
    }
    @Test
    void updateAccount_Should_ThrowResourceNotFoundException_On_NonExistentAccount() {
        long nonExistentAccountId = 999L; // An ID that doesn't exist in the repository
        Account updatedAccount = Account.builder()
                .id(nonExistentAccountId)
                .username("UpdatedTom")
                .role("Admin")
                .paymentInfo("1234 5678 9012 3456")
                .paymentHistory(5)
                .isPaymentSet(false)
                .phone("1234567890")
                .activeBookings("5")
                .build();

        // Mocking the behavior of accountRepository.existsById to return false (non-existent account)
        when(accountRepository.existsById(nonExistentAccountId)).thenReturn(false);

        // Act and Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            accountService.updateAccount(nonExistentAccountId, updatedAccount);
        });
        verify(accountRepository, times(1)).existsById(nonExistentAccountId);
    }
    @Test
    void updateAccount_WithEmptyData_ShouldThrowException() {
        long accountId = accountToCreate.getId();
        // Updating account with empty (null) data
        Account updatedAccount = Account.builder()
                .id(accountId)
                .username("")
                .role(null)
                .paymentInfo("")
                .paymentHistory(0)
                .isPaymentSet(false)
                .phone(null)
                .activeBookings("")
                .build();

        // Mock the behavior of accountRepository.existsById to return true
        when(accountRepository.existsById(accountId)).thenReturn(true);

        // Act and Assert
        assertThrows(
                IllegalArgumentException.class,
                () -> {accountService.updateAccount(accountId, updatedAccount);});
    }
}
































