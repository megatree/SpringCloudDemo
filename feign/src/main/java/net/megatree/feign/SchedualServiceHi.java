package net.megatree.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by wangzhe.bj on 2018/5/3.
 */

@FeignClient(value = "service-hi",fallback = SchedualServiceHi.SchedualServiceHiHystrix.class)
public interface SchedualServiceHi {

    @GetMapping("/hi")
    String sayHiFromClientOne(@RequestParam(value = "name")String name);


    @Component
    class SchedualServiceHiHystrix implements SchedualServiceHi{

        @Override
        public String sayHiFromClientOne(String name) {
            return "feign: sorry, "+name;
        }
    }
}
