package com.catface.demo.controller;

import com.catface.demo.controller.request.StatusRequest;
import com.catface.demo.controller.vo.StatusVO;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author catface
 * @since 2022/10/9
 */
@Slf4j
@RestController
public class StatusController {

  @Value("${server.port}")
  private String port;

  @Autowired
  private HttpServletRequest httpServletRequest;

  @RequestMapping(value = "/status")
  public String status() {
    log.info("接收到status请求,port:{}", port);
    return "status is health:" + port;
  }

  @ResponseBody
  @PostMapping(value = "/status2")
  public StatusVO status(@RequestBody @Valid StatusRequest request) {
    Enumeration<String> headerNames = httpServletRequest.getHeaderNames();
    Map<String, Object> headers = new HashMap<>();
    while (headerNames.hasMoreElements()) {
      String headerName = headerNames.nextElement();
      headers.put(headerName, httpServletRequest.getHeader(headerName));
    }
    log.info("request:{},headers:{}", request, headers);
    StatusVO vo = new StatusVO();
    vo.setId(request.getId());
    vo.setStatus(request.getStatus());
    vo.setPort(port);
    return vo;
  }
}
