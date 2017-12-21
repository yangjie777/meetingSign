//package com.jmpt.yhn.a_test_server;
//
//import com.jmpt.yhn.VO.EventVO;
//import com.jmpt.yhn.entity.Event;
//import com.jmpt.yhn.service.EventService;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
///**
// * Created by yhn on 2017/11/26.
// */
//@Slf4j
//@RestController
//@RequestMapping("/test")
//public class ControllerTest {
//    @Autowired
//    private EventService eventService;
//    @GetMapping("/server")
//    public EventVO reoloadManyContent(@RequestParam("openid") String openid,
//                                      @RequestParam(value = "page") String page,
//                                      @RequestParam(value = "size") String size,
//                                      HttpServletResponse response)throws IOException {
//        response.setCharacterEncoding("UTF-8");
//        PageRequest request = new PageRequest(Integer.valueOf(page)-1,Integer.valueOf(size));
//        Page<Event> result = eventService.findEventByOpenid(openid,request);
//        PageImpl
//        EventVO eventVO = new EventVO();
//        eventVO.setEventList(result.getContent());
//        eventVO.setPage(page);
//        eventVO.setSize(size);
//        if((Integer.valueOf(page)-1)<result.getTotalPages()){
//            eventVO.setIsHasPage("1");
//        }
//        else{
//            eventVO.setIsHasPage("0");
//        }
//        log.info("eventVO={}",eventVO);
//
//        return eventVO;
//    }
//    @GetMapping("/server2")
//    public Page<Event> reoloadManyContent2(@RequestParam("openid") String openid,
//                                      @RequestParam(value = "page") String page,
//                                      @RequestParam(value = "size") String size,
//                                      HttpServletResponse response)throws IOException {
//        response.setCharacterEncoding("UTF-8");
//        PageRequest request = new PageRequest(Integer.valueOf(page)-1,Integer.valueOf(size));
//        Page<Event> result = eventService.findEventByOpenid(openid,request);
//        return result;
//    }
//}
