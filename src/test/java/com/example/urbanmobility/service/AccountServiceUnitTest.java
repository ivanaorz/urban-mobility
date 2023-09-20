package com.example.urbanmobility.service;

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

    private Account accountOne;

    @BeforeEach
    public void setUp() {
        //Creating a car object for carOne
        accountOne = Account.builder()
                .username("Tom")
                .role("user")
                .paymentInfo("3334 5566 8888 9090")
                .paymentHistory(4)
                .isPaymentSet(true)
                .phone("97850484783")
                .placedBookings("3")
                .build();
    }
    @Test
    void saveAccount() {

                // Arrange:
                when(accountRepository.save(any(Account.class))).thenReturn(accountOne);

                // Act:
                Account savedAccount = accountService.saveAccount(accountOne);

                // Assert:
                verify(accountRepository, times(1)).save(accountOne);

                // Assert:
                assertEquals(accountOne, savedAccount);
            }
        }


