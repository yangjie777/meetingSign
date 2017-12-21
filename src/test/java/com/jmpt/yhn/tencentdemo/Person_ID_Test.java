package com.jmpt.yhn.tencentdemo;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import com.jmpt.yhn.tencentdemo.sign.TencentAISign;
import com.jmpt.yhn.tencentdemo.sign.TencentAISignSort;
import com.jmpt.yhn.tencentdemo.util.Base64Util;
import com.jmpt.yhn.tencentdemo.util.FileUtil;
import com.jmpt.yhn.tencentdemo.util.HttpsUtil4Tencent;
import com.jmpt.yhn.tencentdemo.util.TencentAPI;
import com.jmpt.yhn.utils.KeyUtil;
import com.jmpt.yhn.utils.UrlMethodUtil;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.imageio.stream.FileImageInputStream;

public class Person_ID_Test {
	public static void main(String[] args) throws Exception {
		//时间戳
		String time_stamp = System.currentTimeMillis()/1000+"";
		String nonce_str = TencentAISign.getRandomString(10);
//		byte [] imageData = UrlMethodUtil.local2byte("E:/demo.png");//本地图片
    	byte [] imageData = UrlMethodUtil.url2byte("https://yyb.gtimg.com/aiplat/static/ai-demo/large/imagetranslate-demo2.jpg");
		String img64 = Base64Util.encode(imageData);
		//随机字符串
		Map<String,String> person_Id_body = new HashMap<>();
		person_Id_body.put("app_id", String.valueOf(TencentAPI.APP_ID_AI));
		person_Id_body.put("time_stamp",time_stamp);
		person_Id_body.put("nonce_str", nonce_str);
		person_Id_body.put("image", img64);
		person_Id_body.put("session_id",KeyUtil.genUniqueKey());
		person_Id_body.put("scene","doc");  //识别类型（word-单词识别，doc-文档识别）
		person_Id_body.put("source","en");
		person_Id_body.put("target","zh");
		String sign = TencentAISignSort.getSignature(person_Id_body);
		person_Id_body.put("sign", sign);
		Map<String, String> headers = new HashMap<>();
		headers.put("Content-Type", "application/x-www-form-urlencoded");
		HttpResponse responseBD = HttpsUtil4Tencent.doPostTencentAI(TencentAPI.PHOTOFORTRANSLATE, headers, person_Id_body);
		String json = EntityUtils.toString(responseBD.getEntity());
		System.out.println(json);





	}
}
