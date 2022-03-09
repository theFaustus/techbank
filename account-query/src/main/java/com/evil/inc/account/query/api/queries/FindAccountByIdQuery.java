package com.evil.inc.account.query.api.queries;

import com.evil.inc.cqrs.core.queries.Query;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class FindAccountByIdQuery extends Query {
    private String id;
}
