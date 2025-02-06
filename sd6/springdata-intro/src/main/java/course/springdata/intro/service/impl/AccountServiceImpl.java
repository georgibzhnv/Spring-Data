package course.springdata.intro.service.impl;

import course.springdata.intro.dao.AccountRepository;
import course.springdata.intro.entity.Account;
import course.springdata.intro.entity.User;
import course.springdata.intro.exception.InvalidAccountOperationException;
import course.springdata.intro.exception.NonexistingEntityException;
import course.springdata.intro.service.AccountService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Transactional
@Service
public class AccountServiceImpl implements AccountService {
    private AccountRepository accountRepo;

    @Autowired
    public void setAccountRepo(AccountRepository accountRepo) {
        this.accountRepo = accountRepo;
    }

    @Override
    public Account createUserAccount(User user, Account account) {
        account.setId(null);
        account.setUser(user);
        user.getAccounts().add(account);
        return accountRepo.save(account);
    }

    @Override
    public void withdrawMoney(BigDecimal amount, Long accountId) {
        Account account =accountRepo.findById(accountId).orElseThrow(()->
                new NonexistingEntityException(
                        String.format("Entity with ID:%s does not exist.")
                ));
        if(account.getBalance().compareTo(amount)<0){
            throw new InvalidAccountOperationException(
                    String.format("Error withdrawing amount: %s. Insufficient amount:%s in account ID:%s.%n",
                            amount,account.getBalance(),accountId)
            );
        }
        account.setBalance(account.getBalance().subtract(amount));
    }

    @Override
    public void depositMoney(BigDecimal amount, Long accountId) {
        Account account =accountRepo.findById(accountId).orElseThrow(()->
                new NonexistingEntityException(
                        String.format("Entity with ID:%s does not exist.")
                ));
        account.setBalance(account.getBalance().add(amount));
    }

    @Override
    public void transferMoney(BigDecimal amount, Long fromAccountId, Long toAccountId) {
        depositMoney(amount,toAccountId);
        withdrawMoney(amount,fromAccountId);
    }

    @Override
    public List<Account> getAllAcounts() {
        return accountRepo.findAll();
    }

}
