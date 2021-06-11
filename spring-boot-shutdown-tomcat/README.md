# Spring Boot 之 Tomcat 优雅关机方式

## 总结

Tomcat方式实现了，但是要注意内置Tomcat的版本，在启动时报错：

```bash
org.apache.tomcat.jni.LibraryNotFoundError: Can't load library: ../libtcnative-1.so, Can't load library: ../liblibtcnative-1.so, no tcnative-1 in java.library.path, no libtcnative-1 in java.library.path
```

```bash
org.apache.tomcat.util.compat.Jre9Compat : Class not found so assuming code is running on a pre-Java 9 JVM
```