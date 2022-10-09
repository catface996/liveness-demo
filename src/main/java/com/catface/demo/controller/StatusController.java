package com.catface.demo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author catface
 * @since 2022/10/9
 */
@Slf4j
@RestController
public class StatusController {

  @RequestMapping(value = "/status")
  public String status(){
    log.info("接收到status请求");
    return "status is health";
  }
}
