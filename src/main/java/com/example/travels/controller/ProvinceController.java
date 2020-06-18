package com.example.travels.controller;

import com.example.travels.entity.Province;
import com.example.travels.entity.Result;
import com.example.travels.service.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("province")
public class ProvinceController {

    @Autowired
    private ProvinceService provinceService;

    @GetMapping("findByPage")
    public Map<String, Object> findByPage(Integer page, Integer rows) {
        page = page == null ? 1 : page;
        rows = rows == null ? 4 : rows;
        HashMap<String, Object> map = new HashMap<>();
        List<Province> provinces = provinceService.findByPage(page, rows);
        Integer totals = provinceService.findTotals();
        Integer totalPage = totals % rows == 0 ? totals / rows : totals / rows + 1;
        map.put("provinces", provinces);
        map.put("totals", totals);
        map.put("totalPage", totalPage);
        map.put("page", page);
        return map;
    }

    //保存省份信息
    @PostMapping("save")
    public Result save(@RequestBody Province province) { //@RequestBody主要用来接收前端传递给后端的json字符串中的数据的(请求体中的数据的)；GET方式无请求体，所以使用@RequestBody接收数据时，前端不能使用GET方式提交数据，而是用POST方式进行提交。
        Result result = new Result();
        try {
            provinceService.save(province);
            result.setMsg("Province saved!");
        } catch (Exception e) {
            e.printStackTrace();
            result.setState(false).setMsg("Fail");
        }
        return result;
    }

    //删除省份
    @GetMapping("delete")
    public Result delete(String id) {
        Result result = new Result();
        try {
            provinceService.delete(id);
            result.setMsg("Province deleted!");
        } catch (Exception e) {
            e.printStackTrace();
            result.setState(false).setMsg("Fail");
        }
        return result;
    }

    //查询一个省份信息,回显在修改页面
    @GetMapping("findOne")
    public Province findOne(String id) {
        return provinceService.findOne(id);
    }

    @PostMapping("update")
    public Result update(@RequestBody Province province) {
        Result result = new Result();
        try {
            provinceService.update(province);
            result.setMsg("Province updated!");
        } catch (Exception e) {
            e.printStackTrace();
            result.setState(false).setMsg(e.getMessage());
        }
        return result;
    }
}
