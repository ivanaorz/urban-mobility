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
                .phone("97850484783")
                .activeBookings("3")
                .build();
    }
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


        }


