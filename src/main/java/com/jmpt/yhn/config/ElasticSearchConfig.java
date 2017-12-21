package com.jmpt.yhn.config;

//
//import org.elasticsearch.client.transport.TransportClient;
//import org.elasticsearch.common.settings.Settings;
//import org.elasticsearch.common.transport.InetSocketTransportAddress;
//import org.elasticsearch.transport.client.PreBuiltTransportClient;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.net.InetAddress;
//
///**
// * Created by yhn on 2017/12/14.
// */
//@Configuration
//public class ElasticSearchConfig {
//    @Bean
//    private TransportClient client()throws Exception{
//        InetSocketTransportAddress node = new InetSocketTransportAddress(
//                InetAddress.getByName("localhost"),9200
//        );
//        Settings settings = Settings.builder().put("cluster.name","yhn").build();
//        TransportClient client = new PreBuiltTransportClient(settings);
//        client.addTransportAddress(node);
//        return client;
//    }
//}