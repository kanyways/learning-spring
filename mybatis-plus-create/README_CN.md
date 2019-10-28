# MyBatis-Plus Create [English](./README.md)

采用MyBatis-Plus作为数据库底层framework的配置，动态创建数据库的Mapper文件等

## 使用框架

- spring-boot:2.2.0.RELEASE
- mybatis-plus:3.2.0
- velocity:1.7
- velocity-tools:2.0
- jdk:1.8
- lombok:1.18.10

## 数据库配置文件

> 数据库的配置文件位于：**src/main/resources/mysql.properties**

```properties
driver=com.mysql.cj.jdbc.Driver
database=peison_v1
url=jdbc:mysql://119.29.185.228:3306/peison_v1?characterEncoding=UTF-8&autoReconnect=true&zeroDateTimeBehavior=convertToNull&useSSL=true
user=root
password=密码
```

## 修改输出目录

> 主要修改文件**me.kany.project.learning.spring.mybatis.plus.utils.CodeGenerator**的**initGlobalConfig**方法。

```java
private static GlobalConfig initGlobalConfig(String author, String packageName) {
    GlobalConfig gc = new GlobalConfig();
    String tmp = CodeGenerator.class.getResource("").getPath();
    String codeDir = tmp.substring(0, tmp.indexOf("/target"));
    basePath = codeDir + "/src/main/java";
    mapperPath = codeDir + "/src/main/resources/mapper";
    // 配置的文件路径为
    log.debug("basePath = {}", basePath);
    log.debug("mapperPath = {}", mapperPath);
    gc.setOutputDir(basePath);
    gc.setAuthor(author);
    gc.setOpen(false);
    gc.setServiceName("%sService");
    gc.setFileOverride(true);
    return gc;
}
```