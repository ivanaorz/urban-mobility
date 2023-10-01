package com.example.urbanmobility.controller;

import com.example.urbanmobility.exception.ResourceNotFoundException;
import com.example.urbanmobility.model.Account;
import com.example.urbanmobility.service.AccountService;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/account")

public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/{accountId}")
    public Optional<Account> getAccountById(@PathVariable("accountId") long accountId) {
        return accountService.getAccountById(accountId);
    }
    @GetMapping
    public ResponseEntity<List<Account>> getAllAccounts() {
        List<Account> accounts = accountService.getAllAccounts();
        if (accounts == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return ResponseEntity.status(HttpStatus.OK).body(accounts);
    }




    @PostMapping
    public ResponseEntity<Account> createAccount(@RequestBody Account account) {
        if (account == null || StringUtils.isBlank(account.getUsername())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        accountService.createAccount(account);
        return new ResponseEntity<>(account, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Account> updateAccount(@PathVariable("id") long accountId, @RequestBody Account updatedAccount) {
        accountService.updateAccount(accountId, updatedAccount);
        return new ResponseEntity<>(updatedAccount, HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAccount(@PathVariable("id") long id) {
        accountService.deleteAccount(id);
        return new ResponseEntity<>("Account was deleted successfully", HttpStatus.OK);
    }


}
//   @PostMapping
//    public ResponseEntity<Account> createAccount(@RequestBody Account account) {
//        accountService.createAccount(account);
//        return new ResponseEntity<>(account, HttpStatus.CREATED);
//    }



//    @GetMapping()
//    public List<Account> getAllAccounts() {
//        return accountService.getAllAccounts();
//    }

