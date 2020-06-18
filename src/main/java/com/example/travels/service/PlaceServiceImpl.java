package com.example.travels.service;

import com.example.travels.dao.PlaceDAO;
import com.example.travels.entity.Place;
import com.example.travels.entity.Province;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PlaceServiceImpl implements PlaceService{

    @Autowired
    private PlaceDAO placeDAO;

    @Autowired
    private ProvinceService provinceService;

    @Override
    public List<Place> findByProvinceIdPage(Integer page, Integer rows, String provinceId) {
        int start = (page - 1) * rows;
        return placeDAO.findByProvinceIdPage(start, rows, provinceId);
    }

    @Override
    public Integer findByProvinceIdCounts(String provinceId) {
        return placeDAO.findByProvinceIdCounts(provinceId);
    }

    @Override
    public void save(Place place) {
        // 在保存景点的同时还需要将对应省份的景点数量+1，因此需要注入provinceService
        placeDAO.save(place);

        Province province = provinceService.findOne(place.getProvinceid());
        province.setPlacecounts(province.getPlacecounts() + 1);
        provinceService.update(province);
    }

    @Override
    public void delete(String id) {
        //先更新对应省份的景点个数
        Place place = placeDAO.findOne(id);
        Province province = provinceService.findOne(place.getProvinceid());
        province.setPlacecounts(province.getPlacecounts() - 1);
        provinceService.update(province);

        placeDAO.delete(id);
    }
}
