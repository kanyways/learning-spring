# Spring Boot 之 Undertow 优雅关机方式


```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId><!-- Remove default Tomcat container-->
    <exclusions>
        <exclusion>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-tomcat</artifactId>
        </exclusion>
    </exclusions>
</dependency>
<!-- Add Jetty container -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-undertow</artifactId>
</dependency>
```

## 总结

Undertow 目前在使用上未出现Bug，等待对比请求负载后观察。
根据**[后续之《SpringBoot服务器压测对比（jetty、tomcat、undertow）》](https://my.oschina.net/shyloveliyi/blog/2980868)**的描述，Undertow性能稍高。请合理验证并选择。

## 参考资料

- [Undertow使用&优化](https://www.jianshu.com/p/e625b8aa0e80)
- [spring boot内置容器性能比较(Jetty、Tomcat、Undertow)](https://blog.csdn.net/syx1065001748/article/details/98883727)
- [后续之《SpringBoot服务器压测对比（jetty、tomcat、undertow）》](https://my.oschina.net/shyloveliyi/blog/2980868)
