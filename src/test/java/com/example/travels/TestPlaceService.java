package com.example.travels;

import com.example.travels.entity.Place;
import com.example.travels.service.PlaceService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest(classes = TravelsApplication.class)
@RunWith(SpringRunner.class)
public class TestPlaceService {

    @Autowired
    PlaceService placeService;

    @Test
    public void TestFindAllPlace() {
        List<Place> places = placeService.findByProvinceIdPage(1,3,"1");
        for(Place p : places) {
            System.out.println(p);
        }
    }
}
