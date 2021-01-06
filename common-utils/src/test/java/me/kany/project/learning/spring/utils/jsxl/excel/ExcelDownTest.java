/**
 * Project Name:learning-spring
 * File Name:ExcelDownTest.java
 * Package Name:me.kany.project.learning.spring.utils.jsxl.excel
 * Date:2020年12月16日 2:04
 * Copyright (c) 2020, Kai.Wang All Rights Reserved.
 */
package me.kany.project.learning.spring.utils.jsxl.excel;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import lombok.Getter;
import lombok.Setter;
import lombok.var;
import org.apache.commons.beanutils.BeanUtils;
import org.junit.Test;
import org.jxls.common.Context;
import org.jxls.transform.poi.PoiContext;
import org.jxls.util.JxlsHelper;

import java.io.*;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

/**
 * ClassName:ExcelDownTest<br/>
 * Function: 测试JXSL导出数据<br/>
 * Date:2020年12月16日 2:04<br/>
 *
 * @author Kai.Wang
 * @version 1.0.0
 * @see
 * @since JDK 1.8
 */
public class ExcelDownTest {

    private static final DateTimeFormatter DMU = DateTimeFormatter.ofPattern("dd-MMM-yyyy", Locale.ENGLISH);
    private static final SimpleDateFormat FORMAT = new SimpleDateFormat("yyyyMMdd");

    @Test
    public void generateFile() throws ParseException {
        JSONObject data = JSONObject.parseObject("{\"invoice_period\":\"201901\",\"create_time\":1608881093542,\"roaming_type\":2,\"invoice_bound\":{\"generate_time\":1608881093504,\"invoice_period\":\"201901\",\"records\":[{\"file_number\":\"0000007\",\"record_items\":[{\"tax_amount\":0.22048,\"unit_name\":\"Minutes\",\"amount\":20.8,\"service\":\"GSM\",\"usage\":40,\"unit_type\":301},{\"tax_amount\":0.33072,\"unit_name\":\"Minutes\",\"amount\":31.200000000000003,\"service\":\"GSM\",\"usage\":60,\"unit_type\":301},{\"tax_amount\":0.5512,\"unit_name\":\"Minutes\",\"amount\":52,\"service\":\"GSM\",\"usage\":100,\"unit_type\":301},{\"tax_amount\":5.5120000000000005,\"unit_name\":\"Minutes\",\"amount\":520,\"service\":\"GSM\",\"usage\":1000,\"unit_type\":301}]},{\"file_number\":\"0000006\",\"record_items\":[{\"tax_amount\":0.22048,\"unit_name\":\"Minutes\",\"amount\":20.8,\"service\":\"GSM\",\"usage\":40,\"unit_type\":301},{\"tax_amount\":0.33072,\"unit_name\":\"Minutes\",\"amount\":31.200000000000003,\"service\":\"GSM\",\"usage\":60,\"unit_type\":301},{\"tax_amount\":0.5512,\"unit_name\":\"Minutes\",\"amount\":52,\"service\":\"GSM\",\"usage\":100,\"unit_type\":301},{\"tax_amount\":5.5120000000000005,\"unit_name\":\"Minutes\",\"amount\":520,\"service\":\"GSM\",\"usage\":1000,\"unit_type\":301}]},{\"file_number\":\"0000005\",\"record_items\":[{\"tax_amount\":0.22048,\"unit_name\":\"Minutes\",\"amount\":20.8,\"service\":\"GSM\",\"usage\":40,\"unit_type\":301},{\"tax_amount\":0.33072,\"unit_name\":\"Minutes\",\"amount\":31.200000000000003,\"service\":\"GSM\",\"usage\":60,\"unit_type\":301},{\"tax_amount\":0.5512,\"unit_name\":\"Minutes\",\"amount\":52,\"service\":\"GSM\",\"usage\":100,\"unit_type\":301},{\"tax_amount\":5.5120000000000005,\"unit_name\":\"Minutes\",\"amount\":520,\"service\":\"GSM\",\"usage\":1000,\"unit_type\":301}]},{\"file_number\":\"0000004\",\"record_items\":[{\"tax_amount\":0.22048,\"unit_name\":\"Minutes\",\"amount\":20.8,\"service\":\"GSM\",\"usage\":40,\"unit_type\":301},{\"tax_amount\":0.33072,\"unit_name\":\"Minutes\",\"amount\":31.200000000000003,\"service\":\"GSM\",\"usage\":60,\"unit_type\":301},{\"tax_amount\":0.5512,\"unit_name\":\"Minutes\",\"amount\":52,\"service\":\"GSM\",\"usage\":100,\"unit_type\":301},{\"tax_amount\":5.5120000000000005,\"unit_name\":\"Minutes\",\"amount\":520,\"service\":\"GSM\",\"usage\":1000,\"unit_type\":301}]}],\"roaming_name\":\"GSM\",\"invoice_tax_amount\":26.457600000000003,\"invoice_amount\":2496,\"file_hash\":\"\",\"start_file_no\":\"0000004\",\"roaming_type\":2,\"start_file_number\":4,\"currency\":\"USD\",\"end_file_no\":\"0000007\",\"end_file_number\":7,\"invoice_number\":\"INV-GSM-CHNCT-HKGPP-2019-01\",\"served_party\":\"HKGPP\",\"serving_party\":\"CHNCT\"},\"doc_type\":1,\"served_party\":\"HKGPP\",\"serving_party\":\"CHNCT\",\"settlement\":{\"doc_out_doc_value\":0,\"doc_in_doc_no\":\"\",\"doc_in_doc_value\":0,\"invoice_period\":\"\",\"netting_currency_value\":0,\"doc_in_doc_tax_value\":0,\"doc_out_doc_no\":\"\",\"subtotal_no\":\"\",\"doc_out_doc_tax_value\":0,\"file_hash\":\"\",\"doc_in_doc_type\":0,\"netting_settlement_currency\":\"\",\"doc_out_doc_type\":0,\"served_party\":\"\",\"serving_party\":\"\"}}");
        JSONObject invoice = data.getJSONObject("invoice_bound");
        JSONArray records = invoice.getJSONArray("records");
        Map<String, Object> object = new HashMap<>(16);
        List<Record> recordList = records.toJavaList(Record.class);
        recordList.sort(Comparator.comparing(Record::getFile_number));

        List<Map<String, Object>> totalMap = new ArrayList<>();
        List<Map<String, Object>> fileNumber = new ArrayList<>();
        int index = 0;
        for (Record record : recordList) {
            for (RecordItem recordItem : record.getRecord_items()) {
                String serviceName = recordItem.getService();
                Map<String, Object> item = new HashMap<>(4);
                boolean find = false;
                for (Map<String, Object> itemMap : totalMap) {
                    if (itemMap.get("service_name").equals(serviceName)) {
                        itemMap.put("usage", new BigDecimal(itemMap.get("usage").toString()).add(recordItem.getUsage()));
                        itemMap.put("total_amount", new BigDecimal(itemMap.get("total_amount").toString()).add(recordItem.getAmount()));
                        itemMap.put("total_tax_amount", new BigDecimal(itemMap.get("total_tax_amount").toString()).add(recordItem.getTax_amount()));
                        find = true;
                    }
                }
                if (!find) {
                    item.put("service_name", serviceName);
                    item.put("usage", recordItem.getUsage());
                    item.put("total_amount", recordItem.getAmount());
                    item.put("total_tax_amount", recordItem.getTax_amount());
                    totalMap.add(item);
                }
            }
            Map<String, Object> fileNumberItem = new HashMap<>(2);
            fileNumberItem.put("no", ++index);
            fileNumberItem.put("file_number", record.getFile_number());
            fileNumber.add(fileNumberItem);
        }

        index = 0;
        BigDecimal total_original_amount = BigDecimal.ZERO;
        BigDecimal all_total_amount = BigDecimal.ZERO;
        for (Map<String, Object> item : totalMap) {
            List<Map<String, Object>> list = new ArrayList<>();
            for (Record record : recordList) {
                // 获取当前一个record记录
                Map<String, Object> itemDetail = new HashMap<>();
                itemDetail.put("file_number", record.getFile_number() + ":" + index);
                itemDetail.put("usage", BigDecimal.ZERO);
                itemDetail.put("amount", BigDecimal.ZERO);
                itemDetail.put("tax_amount", BigDecimal.ZERO);
                list.add(itemDetail);
                for (RecordItem recordItem : record.getRecord_items()) {
                    String serviceName = recordItem.getService();
                    if (item.get("service_name").equals(serviceName)) {
                        for (Map<String, Object> currentItem : list) {
                            if (currentItem.get("file_number").equals(record.getFile_number() + ":" + index)) {
                                currentItem.put("usage", new BigDecimal(currentItem.get("usage").toString()).add(recordItem.getUsage()));
                                currentItem.put("amount", new BigDecimal(currentItem.get("amount").toString()).add(recordItem.getAmount()));
                                currentItem.put("tax_amount", new BigDecimal(currentItem.get("tax_amount").toString()).add(recordItem.getTax_amount()));
                                currentItem.put("unit_name", recordItem.getUnit_name());
                            }
                        }
                    }
                }
            }
            item.put("items", list);
            all_total_amount = all_total_amount.add(new BigDecimal(item.get("total_amount").toString())).add(new BigDecimal(item.get("total_tax_amount").toString()));
            total_original_amount = total_original_amount.add(new BigDecimal(item.get("total_amount").toString()));
        }

        object.put("roaming_type", data.getIntValue("roaming_type"));
        object.put("roaming_name", invoice.getString("roaming_name"));
        object.put("serving_party", data.get("serving_party"));
        object.put("served_party", data.getString("served_party"));
        object.put("create_time", DMU.format(LocalDateTime.ofInstant(Instant.ofEpochMilli(data.getLongValue("create_time")), ZoneId.systemDefault())));
        object.put("invoice_number", invoice.get("invoice_number"));
        String invoiceDate = data.getString("invoice_period");
        final LocalDateTime localDateTime = toLocalDateTime(FORMAT.parse(invoiceDate+"00000000"));
        LocalDateTime firstDay = localDateTime.with(TemporalAdjusters.firstDayOfMonth());
        LocalDateTime lastDay = localDateTime.with(TemporalAdjusters.lastDayOfMonth());
        object.put("invoice_period", String.format("%02d", firstDay.getDayOfMonth()) + "/" + invoiceDate.substring(4, 6) + "/" + invoiceDate.substring(0, 4) + "-" +  String.format("%02d", lastDay.getDayOfMonth()) + "/" + invoiceDate.substring(4, 6) + "/" + invoiceDate.substring(0, 4));
        if (invoice.getString("start_file_no").equals(invoice.getString("end_file_no"))) {
            object.put("bce_file_range", invoice.getString("start_file_no"));
        } else {
            object.put("bce_file_range", invoice.getString("start_file_no") + "-" + invoice.getString("end_file_no"));
        }
        object.put("netting_settlement_currency", invoice.getString("currency"));
        object.put("bank_name", "");
        object.put("bank_code", "");
        object.put("bank_account_number", "");
        object.put("bank_account_name", "");
        object.put("totalMaps", totalMap);
        object.put("fileNumber", fileNumber);
        object.put("all_total_amount", all_total_amount);
        object.put("total_original_amount", total_original_amount);
        System.out.println(JSONObject.toJSONString(object));
        try (InputStream is = new FileInputStream("test.xlsx")) {
            //output 为导出的Excel路径，有一个坑需要注意的是，在springboot项目打包成jar包之后，
            //导出文件路径可这样表示： System.getProperty("user.dir") + "/export_leadshow.xls"，这种"static/export_leadshow.xls"路径会失效
            try (OutputStream os = new FileOutputStream("texsss.xlsx")) {
                Context context = new PoiContext();
                context.putVar("object", object);
                JxlsHelper.getInstance().processTemplate(is, os, context);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Setter
    @Getter
    static class Record {
        // 文件的类型
        private String file_number;
        // 记录的数组
        private List<RecordItem> record_items;
    }

    @Setter
    @Getter
    static class RecordItem {
        private String service;
        private String unit_name;
        private BigDecimal usage;
        private BigDecimal amount;
        private BigDecimal tax_amount;
    }

    public static LocalDateTime toLocalDateTime(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }
}