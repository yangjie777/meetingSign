package com.jmpt.yhn.entity.mapper;

import com.jmpt.yhn.entity.Event;
import org.apache.ibatis.annotations.*;

import java.util.Map;

/**
 * Created by yhn on 2017/10/17.
 */
public interface EventMapper {
    @Insert("insert into event(id,event_id,event_content,openid) values(#{id,jdbcType=INTEGER},#{event_id,jdbcType=VARCHAR},#{event_content,jdbcType=VARCHAR},#{openid,jdbcType=VARCHAR})")
    int insertByMap(Map<String,Object> map);
    @Insert("insert into event(id,event_id,event_content,openid,create_time,end_time) values(#{id,jdbcType=INTEGER},#{eventId,jdbcType=VARCHAR},#{eventContent,jdbcType=VARCHAR},#{openid,jdbcType=VARCHAR},#{createTime,jdbcType=DATE},#{endTime,jdbcType=DATE})")
    int insertByObject(Event event);


    @Select("select * from event where id = #{id}")
    @Results({
            @Result(column = "id",property = "id"),
            @Result(column = "openid",property = "openid"),
            @Result(column = "event_id",property = "eventId"),
            @Result(column = "event_content",property = "eventContent"),
    })
    Event findByEventId(Integer id);



    @Update("update event set event_content = #{eventContent} where id = #{id}")
    int updateEvent(@Param("eventContent") String content,@Param("id") Integer id);
    @Update("update event set event_content = #{eventContent} where id = #{id}")
    int updateByObject(Event event);
}
