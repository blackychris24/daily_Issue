package com.example.daily_issue.chatting.dao.repository;/**
 * - "portfolio" Project -
 * Created by blackychris24@gmail.com on 2019-12-13
 * Github : https://github.com/blackychris24
 *
 * @author : BlackyChris
 * @since : 0.0.1-SNAPSHOT (2019-12-13)
 */

import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Collections;
import java.util.List;

/**
 *
 */
@NoRepositoryBean
public class QueryDslBaseRepositorySupport<E> extends QuerydslRepositorySupport {
    /**
     * Creates a new {@link QuerydslRepositorySupport} instance for the given domain type.
     *
     * @param domainClass must not be {@literal null}.
     */
    public QueryDslBaseRepositorySupport(Class<E> domainClass) {
        super(domainClass);
    }

    protected Page<E> readPage(JPQLQuery<E> query, Pageable pageable) {

        if (pageable == null) {
            return readPage(query, QPageRequest.of(0, Integer.MAX_VALUE));
        }

        long total = query.fetchCount(); // need to clone to have a second query, otherwise all items would be in the list
        JPQLQuery pagedQuery = getQuerydsl().applyPagination(pageable, query);
        List<E> content = total > pageable.getOffset() ? pagedQuery.fetch() : Collections.<E>emptyList();
        return new PageImpl<E>(content, pageable, total);
    }
}
