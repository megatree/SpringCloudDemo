package net.megatree.servicehi;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
@EnableEurekaClient
@RestController
public class ServiceHiApplication {

    public static List<LeakBean> list = new ArrayList<>();
    static Object lock = new Object();
    static Object lock2 = new Object();


    @Value("${server.port}")
    String port;

    public static void main(String[] args) {
        SpringApplication.run(ServiceHiApplication.class, args);
    }

    @RequestMapping("/hi")
    public String home(String name) throws InterruptedException {
        TimeUnit.SECONDS.sleep(10);

        return "hi " + name + ",i am from port:" + port;
    }

    @RequestMapping("/heap")
    public Object heap(){
        for (int i = 0; i < 10000; i++) {
            list.add(new LeakBean(new StringBuffer(i).append("abcd").toString()));

        }
        return list;
    }

    @RequestMapping("dead1")
    public Object deadLock() throws InterruptedException {


        synchronized (lock){
            TimeUnit.SECONDS.sleep(10);

            synchronized (lock2){
                System.out.println("拿到了两把锁");
            }
        }

        return "成功返回";
    }

    @RequestMapping("dead2")
    public Object deadLock2() throws InterruptedException {

        synchronized (lock2){
            TimeUnit.SECONDS.sleep(1);

            synchronized (lock){
                System.out.println("哈哈");
            }
        }

        return "成功返回";
    }
}
