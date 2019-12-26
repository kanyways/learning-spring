# Spring Boot Print MyBatis Log4jdbc Logs

When I use spring boot and mybatis, I need to print the complete SQL log. Therefore, log4jdbc is used to output logs.

Log4jdbc will not be updated in 2013, so it is recommended not to use it when selecting. It's better to use p6spy.


add log4jdbc.properties
```bash
# 是否自动注入驱动，默认为true
log4jdbc.auto.load.popular.drivers=false
log4jdbc.drivers=com.mysql.cj.jdbc.Driver
```

add maven pom.xml
```xml
<dependency>
    <groupId>com.googlecode.log4jdbc</groupId>
    <artifactId>log4jdbc</artifactId>
    <version>1.2</version>
</dependency>
```

edit application.yml
```yml
url: jdbc:log4jdbc:mysql://***
driver-class-name: net.sf.log4jdbc.DriverSpy
```