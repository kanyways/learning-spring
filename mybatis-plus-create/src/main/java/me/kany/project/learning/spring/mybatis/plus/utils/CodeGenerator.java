/**
 * Project Name:mybatis-plus-create
 * File Name:CodeGenerator.java
 * Package Name:me.kany.project.learning.spring.mybatis.plus.utils
 * Date:2019年10月18日 10:24
 * Copyright (c) 2019, Jason.Wang All Rights Reserved.
 */
package me.kany.project.learning.spring.mybatis.plus.utils;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ClassName:CodeGenerator<br/>
 * Function: 创建代码工具类<br/>
 * Date:2019年10月18日 10:24<br/>
 *
 * @author Jason.Wang
 * @version 1.0.0.0
 * @see
 * @since JDK1.8
 */
@Slf4j
public class CodeGenerator {

    private static String basePath = "";
    private static String mapperPath = "";

    /**
     * JDBC相关配置
     */
    private static String driverName;
    private static String url;
    private static String userName;
    private static String password;
    private static String database;

    static {
        Properties properties = new Properties();
        try {
            properties.load(new FileReader("src/main/resources/mysql.properties"));
            driverName = properties.getProperty("driver");
            url = properties.getProperty("url");
            userName = properties.getProperty("user");
            password = properties.getProperty("password");
            database = properties.getProperty("database");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 自动生成代码
     *
     * @param author      作者
     * @param packageName 包名
     * @param tableNames  表
     */
    public static void generate(String author, String packageName, String... tableNames) {
        // 全局配置
        GlobalConfig gc = initGlobalConfig(author, packageName);
        // 数据源配置
        DataSourceConfig dsc = initDataSourceConfig();
        // 包配置
        PackageConfig pc = new PackageConfig().setParent(packageName);
        // 模板引擎配置
        VelocityTemplateEngine templateEngine = new VelocityTemplateEngine();
        //每一个entity都需要单独设置InjectionConfig, StrategyConfig和TemplateConfig
        Map<String, String> names = new JdbcRepository().getEntityNames(tableNames);
        if (names == null || names.isEmpty()) {
            return;
        }
        for (String tableName : names.keySet()) {
            // 代码生成器
            AutoGenerator mpg = new AutoGenerator();
            mpg.setGlobalConfig(gc);
            mpg.setDataSource(dsc);
            mpg.setPackageInfo(pc);
            mpg.setTemplateEngine(templateEngine);
            // 自定义配置
            InjectionConfig cfg = initInjectionConfig(packageName);
            mpg.setCfg(cfg);
            // 策略配置
            StrategyConfig strategy = initStrategyConfig(tableName);
            mpg.setStrategy(strategy);
            // 模板配置
            // mapper文件
            String mapperFile = mapperPath + "/" + names.get(tableName) + "Mapper" + StringPool.DOT_XML;
            TemplateConfig tc = initTemplateConfig(mapperFile);
            mpg.setTemplate(tc);
            //开始执行
            mpg.execute();
        }
    }


    /**
     * initDataSourceConfig: 配置数据源<br/>
     *
     * @return
     * @author Jason.Wang
     * @createTime 2019/10/18 16:25
     */
    private static DataSourceConfig initDataSourceConfig() {
        return new DataSourceConfig()
                .setUrl(url)
                .setDriverName(driverName)
                .setUsername(userName)
                .setPassword(password);
    }

    /**
     * initGlobalConfig: 全局配置，这么有配置文输出的地方<br/>
     *
     * @param author
     * @param packageName
     * @return
     * @author Jason.Wang
     * @createTime 2019/10/18 16:25
     */
    private static GlobalConfig initGlobalConfig(String author, String packageName) {
        GlobalConfig gc = new GlobalConfig();
        String tmp = CodeGenerator.class.getResource("").getPath();
        String codeDir = tmp.substring(0, tmp.indexOf("/target"));
        basePath = codeDir + "/src/main/java";
        mapperPath = codeDir + "/src/main/resources/" + packageName.replace(".", "/") + "/mapper";
        // 配置的文件路径为
        log.debug("basePath = {}", basePath);
        log.debug("mapperPath = {}", mapperPath);
        gc.setOutputDir(basePath);
        gc.setAuthor(author);
        gc.setOpen(false);
        gc.setServiceName("I%sService");
        gc.setFileOverride(true);
        return gc;
    }

    /**
     * 自定义配置
     *
     * @param packageName
     * @return
     */
    private static InjectionConfig initInjectionConfig(String packageName) {
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };
        List<FileOutConfig> focList = new ArrayList<>();
        focList.add(new FileOutConfig("/templates/mapper.xml.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                //自定义输入文件名称
                return mapperPath + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        cfg.setFileOutConfigList(focList);
        return cfg;
    }

    /**
     * 策略配置
     *
     * @param tableName 数据库表名
     * @return
     */
    private static StrategyConfig initStrategyConfig(String tableName) {
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setEntityLombokModel(true);
        // strategy.setTablePrefix("tb");
        strategy.setInclude(tableName);
        strategy.setRestControllerStyle(true);

        return strategy;
    }

    /**
     * initTemplateConfig: 覆盖Entity以及xml<br/>
     *
     * @param mapperFile
     * @return
     * @author Jason.Wang
     * @createTime 2019/10/18 16:41
     */
    private static TemplateConfig initTemplateConfig(String mapperFile) {
        TemplateConfig tc = new TemplateConfig();
        tc.setXml(null);
        //如果当前Entity已经存在,那么仅仅覆盖Entity
        File file = new File(mapperFile);
        log.debug("文件： {}，是否存在：{}", mapperFile, file.exists());
        if (file.exists()) {
            tc.setController(null);
            tc.setMapper(null);
            tc.setService(null);
            tc.setServiceImpl(null);
            tc.setEntityKt(null);
        }

        return tc;
    }

    /**
     * JdbcRepository: 创建一个内部内，主要用来执行数据库连接等。<br/>
     *
     * @author Jason.Wang
     * @createTime 2019/10/18 16:31
     */
    public static class JdbcRepository {
        private static Pattern linePattern = Pattern.compile("_(\\w)");
        private JdbcOperations jdbcOperations;

        public JdbcRepository() {
            DataSource dataSource = DataSourceBuilder.create()
                    //如果不指定类型，那么默认使用连接池，会存在连接不能回收而最终被耗尽的问题
                    .type(DriverManagerDataSource.class)
                    .driverClassName(driverName)
                    .url(url)
                    .username(userName)
                    .password(password)
                    .build();
            this.jdbcOperations = new JdbcTemplate(dataSource);
        }

        /**
         * 获取所有实体类的名字,实体类由数据库表名转换而来.
         * 例如: 表前缀为auth,完整表名为auth_first_second,那么entity则为FirstSecond
         *
         * @param tableNameArray 数据库表名,可能为空
         * @return
         */
        public Map<String, String> getEntityNames(String... tableNameArray) {
            //该sql语句目前支持mysql
            String sql = "SELECT table_name FROM INFORMATION_SCHEMA.TABLES WHERE table_schema = '" + database + "'";
            if (tableNameArray != null && tableNameArray.length != 0) {
                sql += " and (";
                for (String name : tableNameArray) {
                    sql += " or table_name = '" + name + "'";
                }
                sql += ")";
            }
            sql = sql.replaceFirst("or", "");
            List<String> tableNames = jdbcOperations.query(sql, SingleColumnRowMapper.newInstance(String.class));
            if (CollectionUtils.isEmpty(tableNames)) {
                return new HashMap<>();
            }

            Map<String, String> result = new HashMap<>();
            tableNames.forEach(
                    tableName -> {
                        String entityName = underlineToCamel(tableName);
//                        String prefix = "tb";
//                        //如果有前缀,需要去掉前缀
//                        if (tableName.startsWith(prefix)) {
//                            String tableNameRemovePrefix = tableName.substring((prefix + "_").length());
//                            entityName = underlineToCamel(tableNameRemovePrefix);
//                            log.debug("******"+entityName+"******");
//                        }
                        result.put(tableName, entityName);
                    }
            );
            return result;
        }

        /**
         * 下划线转驼峰
         *
         * @param str
         * @return
         */
        private static String underlineToCamel(String str) {
            if (null == str || "".equals(str)) {
                return str;
            }
            str = str.toLowerCase();
            Matcher matcher = linePattern.matcher(str);
            StringBuffer sb = new StringBuffer();
            while (matcher.find()) {
                matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
            }
            matcher.appendTail(sb);
            str = sb.toString();
            str = str.substring(0, 1).toUpperCase() + str.substring(1);
            return str;
        }

    }
}
