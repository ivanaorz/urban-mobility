package com.example.urbanmobility.controller;

import com.example.urbanmobility.model.Account;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class AccountControllerEndToEndTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper; // ObjectMapper to convert Java objects to JSON

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

    @Test
    void createAccount_Should_ReturnCreated_OnSuccess() throws Exception {
        // Converting the Account object to JSON
        String accountJson = objectMapper.writeValueAsString(account);

        // Performing an HTTP POST request to create an account
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/account")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(accountJson))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    void createAccount_Should_ReturnBadRequest_OnValidationFailure() throws Exception {
        // Creating an invalid Account object
        Account invalidAccount = Account.builder()
                .id(1L)
                .username(" ") //Missing username data
                .role("User")
                .paymentInfo("3334 5566 3432 9090")
                .paymentHistory(4)
                .isPaymentSet(true)
                .phone("0722946563")
                .activeBookings("3")
                .build();

        // Converting the invalid Account object to JSON
        String invalidAccountJson = objectMapper.writeValueAsString(invalidAccount);

        // Performing an HTTP POST request to create an account
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/account")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidAccountJson))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

//    @Test
//    void deleteAccount_Success() throws Exception {
//        // Perform the deleteAccount test for a successful deletion
//        mockMvc.perform(deleteAccount("/api/account/{id}", account.getId()))
//                .andExpect(status().isOk())
//                .andExpect(MockMvcResultMatchers.content().string("Account was deleted successfully"));
//    }
//
//    @Test
//    void deleteAccount_NonExistent() throws Exception {
//        // Perform the deleteAccount test for a non-existent account
//        mockMvc.perform(deleteAccount("/api/account/{id}", 9999L))
//                .andExpect(status().isNotFound());
//    }


    @Test
    void getAccountById() {
    }

    @Test
    void getAllAccounts() {
    }



    @Test
    void updateAccount() {
    }


}
