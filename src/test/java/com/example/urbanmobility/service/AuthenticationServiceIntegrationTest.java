package com.example.urbanmobility.service;

import com.example.urbanmobility.exception.InvalidPermissionException;
import com.example.urbanmobility.exception.ResourceNotFoundException;
import com.example.urbanmobility.model.Account;
import com.example.urbanmobility.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class AuthenticationServiceIntegrationTest {
    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private AccountRepository accountRepository;

    private Account supplierAccount;
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
    }
    @Test
    public void authenticateSupplier_Should_SaveToDatabase_ValidSupplier_() {

        accountRepository.save(supplierAccount);

        // Authenticate
        String response = authenticationService.authenticateSupplier(supplierAccount.getId());

        assertEquals("Authenticated successfully as a supplier!", response);

        // Asserting that the account is present in the database
        assertTrue(accountRepository.findById(supplierAccount.getId()).isPresent());
    }
    @Test
    public void authenticateSupplier_Should_ThrowException_WithNonExistentAccountId() {
        assertThrows(ResourceNotFoundException.class,
                () -> authenticationService.authenticateSupplier(9999L) // Non-existent ID
        );
    }
    @Test
    public void authenticateSupplier_Should_ThrowException_WithNonSupplierRole() {
        Account nonSupplier = Account.builder()
                .username("Jane")
                .role("user")
                .paymentInfo("2234 5678 9123 4567")
                .paymentHistory(6)
                .isPaymentSet(true)
                .phone("0722946580")
                .activeBookings("1")
                .build();

        // Saving nonSupplier to the database
        accountRepository.save(nonSupplier);

        assertThrows(InvalidPermissionException.class,
                () -> authenticationService.authenticateSupplier(nonSupplier.getId())
        );

    }
}