package com.catface.demo;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.springframework.util.ResourceUtils;

/**
 * @author catface
 * @since 2022/10/27
 */
@Slf4j
public class GraphResolve {

  public static final String SERVICE_TYPE_INSTANCE = "AWS::EC2::Instance";

  @Test
  public void test() throws Exception {
    File file = ResourceUtils.getFile("classpath:graph.json");
    String context = FileUtils.readFileToString(file, "UTF-8");
    JSONObject root = JSONObject.parseObject(context);
    // log.info("root:{}", root);
    JSONArray services = root.getJSONArray("Services");
    List<Object> list = new ArrayList<>(services);
    Set<String> types = new HashSet<>();
    Map<String, String> serviceIdMap = new HashMap<>();
    Map<String, Set<String>> serviceCallMap = new HashMap<>();
    int serviceNum = 0;
    for (Object o : list) {
      JSONObject service = (JSONObject) o;
      String type = service.getString("Type");
      types.add(type);
      if (SERVICE_TYPE_INSTANCE.equals(type)) {
        serviceNum++;

        String name = service.getString("Name");
        if (name.contains("amazonaws")){
          continue;
        }
        String serviceId = service.getString("ReferenceId");
        //log.info("type:{},name:{}", type, name);
        serviceIdMap.put(serviceId, name);
        Set<String> callService = serviceCallMap.computeIfAbsent(serviceId, k -> new HashSet<>());
        List<Object> edges = new ArrayList<>(service.getJSONArray("Edges"));
        for (Object e : edges) {
          JSONObject edge = (JSONObject) e;
          String callServiceId = edge.getString("ReferenceId");
          callService.add(callServiceId);
        }
      }
    }
    log.info("service num:{}", serviceNum);
    log.info("types:{}", types);
    log.info("service map:{}", serviceIdMap);
    log.info("call service map:{}", serviceCallMap);
    for (String s : serviceIdMap.keySet()) {
      String serviceName = serviceIdMap.get(s);
      Set<String> callServiceIds = serviceCallMap.get(s);
      Set<String> callName = new HashSet<>();
      for (String callServiceId : callServiceIds) {
        String callServiceName = serviceIdMap.get(callServiceId);
        if (callServiceName!=null){
          callName.add(callServiceName);
        }
      }
      log.info("service:{} --> {}", serviceName, callName);
    }
  }

}
