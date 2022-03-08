package com.evil.inc.account.common.events;

import com.evil.inc.cqrs.core.events.Event;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class AccountClosedEvent extends Event {
}
