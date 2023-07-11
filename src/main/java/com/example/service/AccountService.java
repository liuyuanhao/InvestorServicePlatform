package com.example.service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;


@Service
public class AccountService {
    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account createAccount(Long userId) {
        Account account = new Account();
        account.setUserId(userId);
        account.setBalance(0.0);
        return accountRepository.save(account);
    }

    public void transfer(Long fromUserId, Long toUserId, Double amount) {
        Account fromAccount = accountRepository.findByUserId(fromUserId);
        Account toAccount = accountRepository.findByUserId(toUserId);

        if (fromAccount.getBalance() < amount) {
            throw new IllegalArgumentException("Insufficient balance.");
        }

        fromAccount.setBalance(fromAccount.getBalance() - amount);
        toAccount.setBalance(toAccount.getBalance() + amount);

        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);
    }

    @Transactional
    public void distributeDividends(Long profitAccountId, Double totalDividends) {
        Account profitAccount = accountRepository.findById(profitAccountId)
                .orElseThrow(() -> new IllegalArgumentException("Profit account not found."));

        if (profitAccount.getBalance() < totalDividends) {
            throw new IllegalArgumentException("Insufficient balance in profit account.");
        }

        Iterable<Account> allAccounts = accountRepository.findAll();
        long count = StreamSupport.stream(allAccounts.spliterator(), false).count();
        Double dividendsPerAccount = totalDividends / count;

        for (Account account : allAccounts) {
            if (!account.getId().equals(profitAccountId)) {
                account.setBalance(account.getBalance() + dividendsPerAccount);
                accountRepository.save(account);
            }
        }

        profitAccount.setBalance(profitAccount.getBalance() - totalDividends);
        accountRepository.save(profitAccount);
    }

    @Transactional
    public void liquidate(Long accountId, Long targetAccountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found."));
        Account targetAccount = accountRepository.findById(targetAccountId)
                .orElseThrow(() -> new IllegalArgumentException("Target account not found."));

        targetAccount.setBalance(targetAccount.getBalance() + account.getBalance());
        accountRepository.save(targetAccount);

        accountRepository.delete(account);
    }

    @Transactional
    public void closeAccount(Long accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found."));

        // Here we assume that the remaining balance of the account is handled in some way,
        // for example, transferred to a designated account.

        accountRepository.delete(account);
    }
}
