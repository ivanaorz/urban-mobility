package com.example.urbanmobility.service;

import com.example.urbanmobility.exception.InvalidPermissionException;
import com.example.urbanmobility.exception.ResourceNotFoundException;
import com.example.urbanmobility.model.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class AuthenticationServiceUnitTest {

    @Mock
    private AccountService accountService; //Class we are mocking

    @InjectMocks
    private AuthenticationService authenticationService; //Class we are testing

    private Account supplierAccount;
    private Account nonSupplierAccount;
    private Account  supplierAccountDifferentCase;

    @BeforeEach
    public void setup() {
        // Supplier Account
        supplierAccount = Account.builder()
                .id(1L)
                .username("John")
                .role("supplier")
                .paymentInfo("1234 5678 9123 4567")
                .paymentHistory(5)
                .isPaymentSet(true)
                .phone("0722946570")
                .activeBookings("2")
                .build();

        // Non-Supplier Account
        nonSupplierAccount = Account.builder()
                .id(2L)
                .username("Jane")
                .role("user")
                .paymentInfo("2234 5678 9123 4567")
                .paymentHistory(6)
                .isPaymentSet(true)
                .phone("0722946580")
                .activeBookings("1")
                .build();

        // Supplier Account with different case
        supplierAccountDifferentCase = Account.builder()
                .id(3L)
                .username("Alex")
                .role("SuPpLiEr")
                .paymentInfo("3344 5678 9123 4567")
                .paymentHistory(7)
                .isPaymentSet(true)
                .phone("0722946590")
                .activeBookings("3")
                .build();
    }
    @Test
    void authenticateSupplier_Should_ThrowException_When_AccountDoesNotExist() {
        long nonExistentAccountId = 3L;
        Mockito.when(accountService.getAccountById(nonExistentAccountId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> authenticationService.authenticateSupplier(nonExistentAccountId));
    }

    @Test
    void authenticateSupplier_Should_ThrowException_When_AccountExistsButNotSupplier() {
        Mockito.when(accountService.getAccountById(nonSupplierAccount.getId())).thenReturn(Optional.of(nonSupplierAccount));

        assertThrows(InvalidPermissionException.class,
                () -> authenticationService.authenticateSupplier(nonSupplierAccount.getId()));
    }
    @Test
    void authenticateSupplier_Should_AuthenticateSuccessfully_When_AccountExistsAndIsSupplier() {
        Mockito.when(accountService.getAccountById(supplierAccount.getId())).thenReturn(Optional.of(supplierAccount));

        String response = authenticationService.authenticateSupplier(supplierAccount.getId());

        assertEquals("Authenticated successfully as a supplier!", response);
    }
    @Test
    void authenticateSupplier_Should_Pass_AccountRoleCaseInsensitiveCheck() {
        Mockito.when(accountService.getAccountById(supplierAccountDifferentCase.getId())).thenReturn(Optional.of(supplierAccountDifferentCase));

        String response = authenticationService.authenticateSupplier(supplierAccountDifferentCase.getId());

        assertEquals("Authenticated successfully as a supplier!", response);
    }



    }
