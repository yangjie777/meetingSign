package com.jmpt.yhn.dao;
import com.jmpt.yhn.entity.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by yhn on 2017/9/29.
 */
public interface EventRepository extends JpaRepository<Event,String> {
    Event findByEventId(String eventId);
    List<Event> findByEventContentLike(String eventContent);
    Page<Event> findByEventContentLike(String eventContent, Pageable pageable);  //分页查询
}
