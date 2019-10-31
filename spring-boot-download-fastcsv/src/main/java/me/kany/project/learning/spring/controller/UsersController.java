package me.kany.project.learning.spring.controller;


import de.siegmar.fastcsv.writer.CsvAppender;
import de.siegmar.fastcsv.writer.CsvWriter;
import me.kany.project.learning.spring.entity.Users;
import me.kany.project.learning.spring.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Jason.Wang
 * @since 2019-10-25
 */
@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private UsersService usersService;

    /**
     * execute: 获取用户列表<br/>
     *
     * @return
     * @author Jason.Wang
     * @createTime 2019/10/31 10:06
     */
    @ResponseBody
    @RequestMapping("")
    public Object execute() {
        return usersService.list();
    }

    /**
     * query: 查询单个用户<br/>
     *
     * @param id
     * @return
     * @author Jason.Wang
     * @createTime 2019/10/31 10:06
     */
    @ResponseBody
    @RequestMapping(path = "{id}")
    public Object query(@PathVariable(name = "id") Long id) {
        return usersService.getById(id);
    }

    /**
     * download: 使用csvAppender.appendXXX添加数据<br/>
     *
     * @param response
     * @author Jason.Wang
     * @createTime 2019/10/31 10:06
     */
    @RequestMapping(path = "download")
    public void download(HttpServletResponse response) {
        String fileName = "user-list";
        CsvWriter csvWriter = new CsvWriter();
        try (CsvAppender csvAppender = csvWriter.append(response.getWriter())) {
            /**
             * FIXME 中文乱码
             */
            response.setCharacterEncoding("gbk");
            response.setContentType("text/csv;charset=gbk");
            response.addHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName + ".csv", "UTF-8"));
            //查询数据库的文件
            List<Users> users = usersService.list();
            //设置头部
            csvAppender.appendLine("用户Id", "登录名称", "手机号");
//            JDK1.8的写法
//            users.forEach(user -> {
//                try {
//                    csvAppender.appendLine(user.getUid().toString(),user.getLoginName(),user.getMobile());
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            });
            for (Users user : users) {
                csvAppender.appendLine(user.getUid().toString(), user.getLoginName(), user.getMobile());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * downloads: 使用csvWriter.write方法写数据<br/>
     *
     * @param response
     * @author Jason.Wang
     * @createTime 2019/10/31 11:04
     */
    @RequestMapping(path = "downloads")
    public void downloads(HttpServletResponse response) {
        String fileName = "用户列表";
        CsvWriter csvWriter = new CsvWriter();
        List<Users> users = usersService.list();
        try {
            // 设置response参数，可以打开下载页面
            response.reset();
            //设置响应文本格式
            response.setContentType("text/csv;charset=gbk");
            response.addHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode("用户列表.csv", "UTF-8"));
            Collection<String[]> data = new ArrayList<>();
            //添加头部
            data.add(new String[]{"用户Id", "登录名称", "手机号"});
            //转换格式
            users.forEach(user -> {
                data.add(new String[]{user.getUid().toString(), user.getLoginName(), user.getMobile()});
            });
            //这种方式只支持String[]的数据
            csvWriter.write(response.getWriter(), data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

