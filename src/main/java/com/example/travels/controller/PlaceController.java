package com.example.travels.controller;

import com.example.travels.entity.Place;
import com.example.travels.entity.Result;
import com.example.travels.service.PlaceService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("place")
public class PlaceController {

    @Autowired
    private PlaceService placeService;

    @Value("${upload.dir}")
    private String realPath;   //从配置文件中注入保存图片的路径

    @GetMapping("findAllPlace")
    public Map<String,Object> findAllPlace(Integer page,Integer rows,String provinceId) {
        HashMap<String, Object> result = new HashMap<>();
        page = page == null ? 1 : page;
        rows = rows == null ? 3 : rows;
        //景点集合
        List<Place> places = placeService.findByProvinceIdPage(page, rows, provinceId);
        //处理分页
        Integer counts = placeService.findByProvinceIdCounts(provinceId);
        //计算总页数
        Integer totalPage = counts % rows == 0 ? counts / rows : counts / rows + 1;
        result.put("places", places);
        result.put("page", page);
        result.put("counts", counts);
        result.put("totalPage", totalPage);
        return result;
    }

    @PostMapping("save")
    public Result save(MultipartFile file, Place place) throws IOException {

        System.out.println(file.getOriginalFilename());
        System.out.println(place);
        Result result = new Result();
        try{
            //文件上传
            String extension = FilenameUtils.getExtension(file.getOriginalFilename());
            String newFilename = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+extension;
            //base64
            place.setPicpath(Base64Utils.encodeToString(file.getBytes()));
            file.transferTo(new File(realPath,newFilename));
            //place对象的保存
            placeService.save(place);
            result.setMsg("Place has been saved successfully");
        }catch (Exception e){
            result.setState(false).setMsg(e.getMessage());
        }
        return result;
    }

    @GetMapping("delete")
    public Result delete(String id) {
        Result result = new Result();
        try {
            placeService.delete(id);
            result.setMsg("Place has been deleted successfully");
        } catch (Exception e) {
            e.printStackTrace();
            result.setState(false).setMsg(e.getMessage());
        }
        return result;
    }

    @GetMapping("findOne")
    public Place findOne(String id) {
        return placeService.findOne(id);
    }

    @PostMapping("update")
    public Result update(MultipartFile pic, Place place) throws IOException{
        Result result = new Result();
        System.out.println(pic);
        System.out.println(place);

        try{
            //base64
            String picpath = Base64Utils.encodeToString(pic.getBytes());
            place.setPicpath(picpath);
            //文件上传
            String extension = FilenameUtils.getExtension(pic.getOriginalFilename());
            String newFilename = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+extension;
            pic.transferTo(new File(realPath,newFilename));
            //修改景点信息
            placeService.update(place);
            result.setMsg("修改景点信息成功！");
        }catch (Exception e){
            e.printStackTrace();
            result.setState(false).setMsg(e.getMessage());
        }

        return  result;
    }
}
