package com.jmpt.yhn.dao;
import com.jmpt.yhn.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by yhn on 2017/9/29.
 */
public interface EventRepository extends JpaRepository<Event,String> {
    Event findByEventId(String eventId);
}
