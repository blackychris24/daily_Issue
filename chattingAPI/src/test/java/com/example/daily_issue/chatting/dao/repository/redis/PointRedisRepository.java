package com.example.daily_issue.chatting.dao.repository.redis;/**
 * - "portfolio" Project -
 * Created by blackychris24@gmail.com on 2019-11-28
 * Github : https://github.com/blackychris24
 *
 * @author : BlackyChris
 * @since : 0.0.1-SNAPSHOT (2019-11-28)
 */

import com.example.daily_issue.chatting.domain.redis.Point;
import org.springframework.data.repository.CrudRepository;

/**
 *
 *
 */

public interface PointRedisRepository extends CrudRepository<Point, String> {
}
