package com.comments.web;

import com.comments.domain.Comment;
import com.comments.service.CommentService;
import com.comments.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by joshua on 2016-07-11.
 */
@RestController
@RequestMapping(value = "/locations")
public class LocationsController {


    private LocationService locationService;

    @Autowired
    public void setLocationService(LocationService locationService) {
        this.locationService = locationService;
    }

    @RequestMapping(value = "/check/{location}")
    public List<String> checkLocation(@PathVariable String location) {
        return locationService.findLocation(location);
    }

    @RequestMapping(value = "/temperature/{latitude}/{longitude}")
    public float checkTemperature(@PathVariable Double latitude, @PathVariable Double longitude) throws Exception {
        return locationService.findTemperature(latitude,longitude);
    }


}