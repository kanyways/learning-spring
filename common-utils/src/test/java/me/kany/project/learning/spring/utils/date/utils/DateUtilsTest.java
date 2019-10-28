/**
 * Project Name:ethereum-parent
 * File Name:DateUtilsTest.java
 * Package Name:me.kany.project.learning.spring.utils.date.utils
 * Date:2019年09月20日 11:58
 * Copyright (c) 2019, Jason.Wang All Rights Reserved.
 */
package me.kany.project.learning.spring.utils.date.utils;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

/**
 * ClassName:DateUtilsTest<br/>
 * Function: 日期的测试类<br/>
 * Date:2019年09月20日 11:58<br/>
 *
 * @author Jason.Wang
 * @version 1.0.0.0
 * @see
 * @since JDK1.8
 */
@Slf4j
public class DateUtilsTest {
    /**
     * LocalDateTest: 不包含具体时间的日期<br/>
     *
     * @author Jason.Wang
     * @createTime 2019/9/20 12:03
     */
    @Test
    public void LocalDateTest() {
        //初始化
        LocalDate nowDate = LocalDate.now();
        log.debug("今天的日期：{}", nowDate);
        //获取当前年份
        log.debug("当前日期年份：{}", nowDate.getYear());
        //获取当月年份
        log.debug("当前日期月份：{}", nowDate.getMonthValue());
        //获取当前天数
        log.debug("当前天数在日期中属于第几天：{}", nowDate.getDayOfMonth());
        //当前日期是今年的第几天
        log.debug("当前日期是今年的第几天：{}", nowDate.getDayOfYear());

        //当前日期在当前属于星期几
        log.debug("今天是星期几：{}", nowDate.getDayOfWeek());
        //当前日期在当前属于星期几，数值
        log.debug("今天是星期几：{}", nowDate.getDayOfWeek().getValue());

        //当前月份在全年属于几月
        log.debug("今天是几月：{}", nowDate.getMonth());
        //当前月份在全年属于几月，数值
        log.debug("今天是几月：{}", nowDate.getMonth().getValue());
    }

    /**
     * LocalTimeTest: LocalTime类获取时间信息<br/>
     *
     * @author Jason.Wang
     * @createTime 2019/9/20 14:06
     */
    @Test
    public void LocalTimeTest() {
        LocalTime nowTime = LocalTime.now();
        //当前时间，格式：HH:mm:ss.SSS
        log.debug("今天的时间：{}", nowTime);
        //当前小时
        log.debug("当前是多少时：{}", nowTime.getHour());
        //当前分钟
        log.debug("当前是多少分：{}", nowTime.getMinute());
        //当前秒
        log.debug("当前是多少秒：{}", nowTime.getSecond());
        //当前毫秒
        log.debug("当前是多少毫秒：{}", nowTime.getNano() / 1000 / 1000);
        //当前微秒
        log.debug("当前是多少微秒：{}", nowTime.getNano() / 1000);
        //当前纳秒
        log.debug("当前是多少纳秒：{}", nowTime.getNano());
    }

    /**
     * LocalDateTimeTest: LocalDateTime类获取日期时间信息<br/>
     *
     * @author Jason.Wang
     * @createTime 2019/9/20 14:06
     */
    @Test
    public void LocalDateTimeTest() {
        LocalDateTime nowDateTime = LocalDateTime.now();
        log.debug("今天的日期：{}", nowDateTime);
        //获取当前年份
        log.debug("当前日期年份：{}", nowDateTime.getYear());
        //获取当月年份
        log.debug("当前日期月份：{}", nowDateTime.getMonth());
        //获取当月年份 数值
        log.debug("当前日期月份：{}", nowDateTime.getMonthValue());
        //获取当前天数
        log.debug("今天是 {} 月份的第 {} 天", nowDateTime.getMonthValue(), nowDateTime.getDayOfMonth());
        //当前日期是今年的第几天
        log.debug("今天是 {} 年的第 {} 天", nowDateTime.getYear(), nowDateTime.getDayOfYear());

        //当前日期在当前属于星期几
        log.debug("今天是星期几：{}", nowDateTime.getDayOfWeek());
        //当前日期在当前属于星期几，数值
        log.debug("今天是星期几：{}", nowDateTime.getDayOfWeek().getValue());

        //当前月份在全年属于几月
        log.debug("今天是几月：{}", nowDateTime.getMonth());
        //当前月份在全年属于几月，数值
        log.debug("今天是几月：{}", nowDateTime.getMonth().getValue());

        //当前小时
        log.debug("当前是多少时：{}", nowDateTime.getHour());
        //当前分钟
        log.debug("当前是多少分：{}", nowDateTime.getMinute());
        //当前秒
        log.debug("当前是多少秒：{}", nowDateTime.getSecond());
        //当前毫秒
        log.debug("当前是多少毫秒：{}", nowDateTime.getNano() / 1000 / 1000);
        //当前微秒
        log.debug("当前是多少微秒：{}", nowDateTime.getNano() / 1000);
        //在java.time.temporal.ChronoField里面有很多类型
        log.debug("当前是多少微秒：{}", nowDateTime.getLong(ChronoField.MICRO_OF_DAY));
        //当前纳秒
        log.debug("当前是多少纳秒：{}", nowDateTime.getNano());
    }

    /**
     * setLocalDate: 常用的设定日期的方法<br/>
     *
     * @author Jason.Wang
     * @createTime 2019/9/20 14:46
     */
    @Test
    public void setLocalDate() {
        //采用数值模式创建指定日期
        LocalDate localDate = LocalDate.of(2019, 10, 1);
        log.debug("今天的日期：{}", localDate);
        //采用枚举设置月份
        localDate = LocalDate.of(2019, Month.OCTOBER, 1);
        log.debug("今天的日期：{}", localDate);
        //调用ofYearDay方法指定年
        log.debug("今天的日期：{}", LocalDate.ofYearDay(2018, localDate.getDayOfYear()));
        //调用ofEpochDay指定日期
        log.debug("今天的日期：{}", LocalDate.ofEpochDay(localDate.toEpochDay()));
        /**
         * 这个是指定时区的，可以看java.time.ZoneId中的静态方法static，CTT - Asia/Shanghai
         */
        log.debug("今天的日期：{}", LocalDate.now(Clock.system(ZoneId.of("Asia/Shanghai"))));
        //根据格式转换
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        log.debug("今天的日期：{}", LocalDate.parse(localDate.format(dateTimeFormatter), dateTimeFormatter));


    }

    /**
     * setLocalTime: 设定时间<br/>
     *
     * @author Jason.Wang
     * @createTime 2019/9/20 14:54
     */
    @Test
    public void setLocalTime() {
        //指定UTC时区
        log.debug("当前时间是：{}", LocalTime.now(Clock.systemUTC()));
        //指定上海时区
        LocalTime localTime = LocalTime.now(Clock.system(ZoneId.of("Asia/Shanghai")));
        log.debug("当前时间是：{}", localTime);
        //使用时、分创建，丢失秒及纳秒
        log.debug("当前时间是：{}", LocalTime.of(localTime.getHour(), localTime.getMinute()));
        //使用时、分、秒创建，丢失纳秒
        log.debug("当前时间是：{}", LocalTime.of(localTime.getHour(), localTime.getMinute(), localTime.getSecond()));
        //使用时、分、秒、纳秒创建。有没有皮秒不知道，但应该会丢失
        log.debug("当前时间是：{}", LocalTime.of(localTime.getHour(), localTime.getMinute(), localTime.getSecond(), localTime.getNano()));
        //使用秒创建，这个会丢失纳秒
        log.debug("当前时间是：{}", LocalTime.ofSecondOfDay(localTime.toSecondOfDay()));
        //使用纳秒创建
        log.debug("当前时间是：{}", LocalTime.ofNanoOfDay(localTime.toNanoOfDay()));
        //根据格式转换
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HHmmss.SSS");
        log.debug("今天的日期：{}", localTime.parse(localTime.format(dateTimeFormatter), dateTimeFormatter));
    }

    /**
     * setLocalDateTime: 设定日期和时间<br/>
     *
     * @author Jason.Wang
     * @createTime 2019/9/20 15:09
     */
    @Test
    public void setLocalDateTime() {
        //指定UTC时区
        log.debug("当前时间是：{}", LocalDateTime.now(Clock.systemUTC()));
        //指定上海时区
        LocalDateTime localDateTime = LocalDateTime.now(Clock.system(ZoneId.of("Asia/Shanghai")));
        log.debug("当前时间是：{}", localDateTime);
        //采用LocalDate、LocalTime作为参数
        log.debug("当前时间是：{}", LocalDateTime.of(LocalDate.now(), LocalTime.now()));
        //这个里就不多写什么的了直接用
        log.debug("当前时间是：{}", LocalDateTime.of(localDateTime.getYear(), localDateTime.getMonth(), localDateTime.getDayOfMonth(), localDateTime.getHour(), localDateTime.getMinute()));
        //一般来说不建议使用这个方法
        log.debug("当前时间是：{}", LocalDateTime.ofEpochSecond(localDateTime.toEpochSecond(ZoneOffset.UTC), localDateTime.getNano(), ZoneOffset.UTC));
        //根据格式转换
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss.SSS");
        log.debug("今天的日期：{}", localDateTime.parse(localDateTime.format(dateTimeFormatter), dateTimeFormatter));
        //对于格式的使用
        dateTimeFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL).withLocale(Locale.CHINESE);
        log.debug("今天的日期：{}", localDateTime.format(dateTimeFormatter));
    }

    /**
     * compareLocalDate: 比较日期<br/>
     *
     * @author Jason.Wang
     * @createTime 2019/9/20 15:51
     */
    @Test
    public void compareLocalDate() {
        LocalDate utcDate = LocalDate.now(Clock.systemUTC());
        LocalDate asiaDate = LocalDate.now(Clock.system(ZoneId.of("Asia/Shanghai")));
        log.debug("提问：今天是{}吗？回答：{} ", asiaDate, asiaDate.equals(utcDate) ? "是" : "否");
        //将UTC的时间减去一天，后面不介绍对于日期的加减等基本操作。这个地方记得重新赋值
        utcDate = utcDate.minusDays(1);
        //使用isBefore
        log.debug("今天 {} 是在 {} 之前吗？回答：{} ", asiaDate, utcDate, asiaDate.isBefore(utcDate) ? "是" : "否");
        //使用isAfter
        log.debug("今天 {} 是在 {} 之后吗？回答：{} ", asiaDate, utcDate, asiaDate.isAfter(utcDate) ? "是" : "否");
        //询问每一年的10月有多少天
        YearMonth yearMonth = YearMonth.of(asiaDate.getYear(), 10);
        log.debug("{} 有 {} 天", yearMonth.format(DateTimeFormatter.ofPattern("yyyy年MM月")), yearMonth.lengthOfMonth());
        //询问是润年还是平年
        log.debug("今年是什么年？ {}", asiaDate.isLeapYear() ? "闰年" : "平年");

        //采用ChronoUnit的枚举类进行加减
        log.debug("现在日期为：{}", asiaDate.plus(1, ChronoUnit.DAYS));
        log.debug("现在日期为：{}", asiaDate.minus(1, ChronoUnit.DAYS));
    }

    /**
     * PeriodTest: <br/>
     *
     * @author Jason.Wang
     * @createTime 2019/9/20 16:25
     */
    @Test
    public void PeriodTest() {
        LocalDate today = LocalDate.now();
        log.debug("当前日期为：{}", today);
        LocalDate weddingDay = LocalDate.of(2019, 2, 11);
        log.debug("纪念日时间为：{}", weddingDay);
        //第二个参数减第一个参数，开始时间，结束时间
        Period period = Period.between(weddingDay, today);
        log.debug("已婚时间：{} 年 {} 月 {} 日", period.getYears(), period.getMonths(), period.getDays());
    }

    @Test
    public void DurationTest() {
        LocalDateTime today = LocalDateTime.now();
        log.debug("当前日期为：{}", today);
        LocalDateTime weddingDay = LocalDateTime.of(2019, 2, 11, 10, 10);
        log.debug("纪念日时间为：{}", weddingDay);
        //第二个参数减第一个参数，开始时间，结束时间
        Duration duration = Duration.between(weddingDay, today);

        log.debug("已经经历：{} 天 ", duration.toDays());
        log.debug("已经经历：{} 时", duration.toHours());
        log.debug("已经经历：{} 分", duration.toMinutes());
        log.debug("已经经历：{} 秒", duration.toMillis() / 1000);
    }

    @Test
    public void ChronoUnitTest() {
        LocalDateTime today = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH时mm分ss秒");
        log.debug("当前日期为：{}", today.format(dateTimeFormatter));
        LocalDateTime weddingDay = LocalDateTime.of(2019, 2, 11, 10, 10);
        log.debug("纪念日时间为：{}", weddingDay.format(dateTimeFormatter));

        log.debug("已经经历：{} 年", ChronoUnit.YEARS.between(weddingDay, today));
        log.debug("已经经历：{} 月", ChronoUnit.MONTHS.between(weddingDay, today));
        log.debug("已经经历：{} 周", ChronoUnit.WEEKS.between(weddingDay, today));
        log.debug("已经经历：{} 天", ChronoUnit.DAYS.between(weddingDay, today));
        log.debug("已经经历：{} 时", ChronoUnit.HOURS.between(weddingDay, today));
        log.debug("已经经历：{} 分", ChronoUnit.MINUTES.between(weddingDay, today));
        log.debug("已经经历：{} 秒", ChronoUnit.SECONDS.between(weddingDay, today));
        log.debug("已经经历：{} 毫秒", ChronoUnit.MILLIS.between(weddingDay, today));
        log.debug("已经经历：{} 微秒", ChronoUnit.MICROS.between(weddingDay, today));
        log.debug("已经经历：{} 纳秒", ChronoUnit.NANOS.between(weddingDay, today));
        log.debug("已经经历：{} 半天", ChronoUnit.HALF_DAYS.between(weddingDay, today));
        log.debug("已经经历：{} 十年", ChronoUnit.DECADES.between(weddingDay, today));
        log.debug("已经经历：{} 世纪（百年）", ChronoUnit.CENTURIES.between(weddingDay, today));
        log.debug("已经经历：{} 千年", ChronoUnit.MILLENNIA.between(weddingDay, today));
        log.debug("已经经历：{} 纪元", ChronoUnit.ERAS.between(weddingDay, today));
    }
}
