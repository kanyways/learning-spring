# Spring Boot 默认的优雅关机方式

导入包：
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```

配置文件加入：

```yaml
# 优雅关机配置
management:
  endpoints:
    web:
      exposure:
        include: shutdown
  endpoint:
    shutdown:
      enabled: true
  server:
    port: 8888
```

关机请求地址：
```bash
curl -X POST http://localhost:3333/actuator/shutdown
```

## 总结

目前测试发现该方法功能存在Bug，所以不采用。