# Mybatis plus create [中文](./README_CN.md)

Mybatis plus is used as the configuration of the underlying framework of the database, and mapper files of the database are created dynamically.

## Using framework

- spring-boot:2.2.0.RELEASE
- mybatis-plus:3.2.0
- velocity:1.7
- velocity-tools:2.0
- jdk:1.8
- lombok:1.18.10

## Database profile

> The configuration file of the database is located at * * Src / main / resources / mysql.properties**

```properties
driver=com.mysql.cj.jdbc.Driver
database=peison_v1
url=jdbc:mysql://119.29.185.228:3306/peison_v1?characterEncoding=UTF-8&autoReconnect=true&zeroDateTimeBehavior=convertToNull&useSSL=true
user=root
password=your database password
```

## Modify output directory

> Mainly modify the * * initglobalconfig * * method of the file * * me. Kany. Project. Learning. Spring. Mybatis. Plus. Utils. Codegenerator * *.

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