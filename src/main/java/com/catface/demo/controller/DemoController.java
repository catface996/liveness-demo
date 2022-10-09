package com.catface.demo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author catface
 * @since 2022/10/9
 */
@Slf4j
@RestController
public class DemoController {


  @RequestMapping(value = "/sayHello")
  public String sayHello(@RequestParam("duration") Integer duration) {
    if (duration == null) {
      duration = 20;
    }
    log.info("接收到请求,duration={}", duration);
    long start = System.currentTimeMillis();
    while (true) {
      long current = System.currentTimeMillis();
      if (current - start >= duration * 1000) {
        break;
      }
    }
    return "你好";
  }


}
