package com.evil.inc.account.query.infrastructure.handlers;

import com.evil.inc.account.common.events.AccountClosedEvent;
import com.evil.inc.account.common.events.AccountOpenedEvent;
import com.evil.inc.account.common.events.FundsDepositedEvent;
import com.evil.inc.account.common.events.FundsWithdrawnEvent;

public interface EventHandler {
    void on(AccountOpenedEvent event);
    void on(FundsDepositedEvent event);
    void on(FundsWithdrawnEvent event);
    void on(AccountClosedEvent event);
}
