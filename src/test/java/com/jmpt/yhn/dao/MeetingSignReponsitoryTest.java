package com.jmpt.yhn.dao;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.jmpt.yhn.entity.Meeting;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.file.Path;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by yhn on 2017/9/7.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MeetingSignReponsitoryTest {
    @Autowired
    private MeetingSignReponsitory reponsitory;
    @Test
    public void save(){
        Meeting meeting = new Meeting();
        meeting.setCreateTime(new Date());
        meeting.setUpdateTime(new Date());
        meeting.setMeetingId("654321");
        meeting.setMeetingName("二个会议");
        meeting.setOpenid("zxcvbnm");
        reponsitory.save(meeting);
        Assert.assertNotNull(meeting);
    }
    @Test
    public void findByMeetingId() throws Exception {
        System.out.println(reponsitory.findByMeetingId("123456"));
    }
    @Test
    public void findByOpenid(){
       List<Meeting> meetingList = reponsitory.findByOpenid("zxcvbnm");
        for(Meeting meeting:meetingList){
            System.out.println(meeting);
        }
    }
    @Test
    public void testDemo(){
//       String str =  URLEncoder.encode("姚浩楠");
        String str =   URLDecoder.decode("%E5%A7%9A%E6%B5%A9%E6%A5%A0");
        System.out.println(str);
    }
    @Test
    public void createEr(){
        int width = 500;   //图片的大小
        int height = 500;
        String format = "png";
        String content = "楠尼玛是66666";
        //定义二维码参数
        HashMap hints = new HashMap();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");  //定义内容的编码
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);   //误差校正:一般M
        hints.put(EncodeHintType.MARGIN, 2);//设置边距:如边框空白
        try {
            BitMatrix bitMatrix  = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height,hints);
            Path file = new File("D:/imgDemo.png").toPath();
            MatrixToImageWriter.writeToPath(bitMatrix, format, file);
        } catch (WriterException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}