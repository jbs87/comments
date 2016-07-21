package com.comments.web;

import com.comments.domain.Comment;
import com.comments.domain.Location;
import com.comments.service.CommentService;
import com.comments.service.LocationService;
import org.geonames.Toponym;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @CrossOrigin(origins = "http://localhost:8000")
    @RequestMapping(value = "/check", method = RequestMethod.GET)
    public List<Location> checkLocation(@RequestParam String placeName) {
        return locationService.findLocation(placeName);
    }

    @RequestMapping(value = "/temperature/{latitude}/{longitude}")
    public float checkTemperature(@PathVariable Double latitude, @PathVariable Double longitude) throws Exception {
        return locationService.findTemperature(latitude,longitude);
    }


}