//package com.jmpt.yhn.dao;
//
//import com.jmpt.yhn.entity.Event;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Sort;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.util.List;
//
//import static org.junit.Assert.*;
//
///**
// * Created by yhn on 2017/12/21.
// */
//@RunWith(SpringRunner.class)
//@SpringBootTest
//@Slf4j
//public class EventRepositoryTest {
//    @Autowired
//    private EventRepository repository;
//    @Test
//    public void findByEventContentLike2() throws Exception {
//        Sort sort = new Sort(Sort.Direction.DESC,"createTime");
//        PageRequest request = new PageRequest(0,2,sort);  //第0页，取一条
//        Page<Event> result = repository.findByEventContentLike("%你好%",request);
//        log.warn("result={}",result.getContent());
//        log.warn("总页数={}",result.getTotalPages());
//    }
//
//
//    @Test
//    public void findByEventContentLike() throws Exception {
//       List<Event> eventList = repository.findByEventContentLike("%你好%");
//       log.info("event={}",eventList);
//    }
//}