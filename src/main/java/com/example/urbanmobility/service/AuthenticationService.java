package com.example.urbanmobility.service;

import com.example.urbanmobility.exception.InvalidPermissionException;
import com.example.urbanmobility.exception.ResourceNotFoundException;
import com.example.urbanmobility.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Autowired
    private AccountService accountService;

    public AuthenticationService(AccountService accountService) {
        this.accountService = accountService;
    }

    public String authenticateSupplier(long accountId) {
        Account account = accountService.getAccountById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Account with id " + accountId + " not found!"));

        verifySupplierRole(account);

        return "Authenticated successfully as a supplier!";
    }

    private void verifySupplierRole(Account account) {
        if (!"supplier".equalsIgnoreCase(account.getRole())) {
            throw new InvalidPermissionException("You don't have the supplier role!");
        }
    }
}

