package com.example.daily_issue.chatting.domain.test;/**
 * - "portfolio" Project -
 * Created by blackychris24@gmail.com on 2019-12-12
 * Github : https://github.com/blackychris24
 *
 * @author : BlackyChris
 * @since : 0.0.1-SNAPSHOT (2019-12-12)
 */

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 *
 */
@Entity
@Getter
@BatchSize(size = 100)
public class B {

    @Id @GeneratedValue
    private Long id;

    @Setter
    private String name;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<A> listA = new ArrayList<>();
}
