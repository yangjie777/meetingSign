//package com.jmpt.yhn.entity.elasticEntity;
//import lombok.Data;
//import javax.persistence.Id;
//import java.util.Date;
//
///**
// * Created by yhn on 2017/9/29.
// */
//@Data
//@Document(indexName="meetSign",type="event",indexStoreType="fs",shards=5,replicas=1,refreshInterval="-1")
//public class Event {
//    @Id
//    private Integer id;   //排序作用
//    private String eventId;
//    private Date createTime;  //创建时间
//    private Date endTime;  //更新时间
//    private String eventContent;   //提醒事件内容
//    private String openid;  //用户id--->重构，废除第三张表
//}
