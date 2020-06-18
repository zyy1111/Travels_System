package com.example.travels;

import com.example.travels.entity.Province;
import com.example.travels.service.ProvinceService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest(classes = TravelsApplication.class)
@RunWith(SpringRunner.class)
public class TestProvinceService {
    @Autowired
    private ProvinceService provinceService;

    @Test
    public void testFindByPage() {
        List<Province> list = provinceService.findByPage(1, 5);
        for(Province p : list) {
            System.out.println(p);
        }
    }
}
