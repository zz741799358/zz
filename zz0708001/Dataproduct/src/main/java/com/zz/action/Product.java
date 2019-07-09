package com.zz.action;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;
import java.util.Random;

public class Product {
    //随机ip
    public static String getIp() {
        String ip = "58.67.160.0,58.67.160.255,58.83.66.0,58.83.127.255,58.242.0.0," +
                "58.243.255.255,59.110.0.0,59.110.0.255,60.166.0.0,60.175.255.255," +
                "61.132.128.0,61.133.191.255,61.190.0.0,61.191.255.255,61.232.117.0," +
                "61.232.117.255,61.235.36.0,61.235.47.255,61.237.13.0,61.237.185.255," +
                "61.241.128.0,61.241.159.255,116.60.8.0,116.60.8.255,117.57.0.0,117.57.255.255," +
                "117.64.0.0,117.71.255.255,117.75.0.0,117.75.0.255,121.36.176.0,121.36.176.255," +
                "121.46.224.0,121.46.250.255,121.68.0.0,121.68.0.255,121.251.0.0,121.251.47.255," +
                "121.255.0.0,121.255.255.255,122.13.129.0,122.13.223.255,122.92.0.0,122.92.209.255";
        //随机获取ip
        String[] ips = ip.split(",");
        Random random = new Random();
        int i = random.nextInt(ips.length);
        return ips[i];
    }

    //随机等级
    public static String getLevel() {
        String[] lenvel = {"1", "2", "3", "4"};
        Random random = new Random();
        int i = random.nextInt(4);
        return lenvel[i];
    }

    //随机域名
    public static String getDomain() {
        String[] domain = { "www.xiudun1.com","www.xiudun2.com","www.xiudun3.com","www.xiudun4.com","www.xiudun5.com",
                "www.xiudun6.com","www.xiudun7.com","www.xiudun8.com","www.xiudun9.com","www.xiudun10.com",
                "www.xiudun11.com","www.xiudun12.com","www.xiudun13.com","www.xiudun14.com","www.xiudun15.com",
                "www.xiudun16.com"};
        Random random = new Random();
        int i = random.nextInt(domain.length);
        return domain[i];
    }

    //每秒访问量
    public static long getTraffic() {
        return new Random().nextInt(100000);
    }

    public static void main(String[] args) throws Exception {
        //配置kafka
        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", "master:9092");
        properties.setProperty("key.serializer", StringSerializer.class.getName());
        properties.setProperty("value.serializer", StringSerializer.class.getName());
        //主题
        String topic = "test";
        //生产者
        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(properties);
        //生成数据
        StringBuffer stringBuffer = new StringBuffer();
        while (true) {
            Thread.sleep(500);
            stringBuffer.append(getLevel()).append("\t").append(getIp()).append("\t");
            stringBuffer.append(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).append("\t");
            stringBuffer.append(getDomain()).append("\t").append(getTraffic());
            System.out.println(stringBuffer);
            //生产
            producer.send(new ProducerRecord<String, String>(topic, stringBuffer.toString()));
            System.out.println("----------------->>");
            //发送后清空
            stringBuffer.setLength(0);
        }
    }
}
