package com.evil.inc.account.query.infrastructure;

import com.evil.inc.account.query.domain.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<BankAccount, String> {

    Optional<BankAccount> findByAccountHolder(String accountHolder);

    List<BankAccount> findAllByBalanceGreaterThan(double balance);

    List<BankAccount> findAllByBalanceLessThan(double balance);
}
