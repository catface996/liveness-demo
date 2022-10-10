package com.catface.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author catface
 * @since 2022/10/10
 */
@Slf4j
@RestController
public class ThreadController {

  private static List<Thread> threads = new ArrayList<>();


  @RequestMapping(value = "/createThreads")
  public String create(@RequestParam("num") Integer num, @RequestParam("depth") Integer depth) {
    if (num == null) {
      num = 100;
    }
    if (depth == null) {
      depth = 200;
    }
    final int maxDepth = depth;
    log.info("prepare create {} threads", num);
    for (int i = 0; i < num; i++) {
      Thread thread = new Thread(new Runnable() {
        @Override
        public void run() {
          d(maxDepth, 0);
        }
      });
      threads.add(thread);
      thread.start();
    }
    return "create finish";
  }

  private Long d(Integer maxDepth, Integer currentDepth) {
    int a = 1;
    int b = 2;
    int c = 3;
    int d = 4;
    if (currentDepth <= maxDepth) {
      d(maxDepth, currentDepth + 1);
    } else {
      try {
        TimeUnit.MINUTES.sleep(30);
      } catch (Exception e) {
        log.error("d sleep error", e);
      }
    }
    return a + b + c + d + 1L;
  }
}
