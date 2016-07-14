package com.comments.service;

import net.aksingh.owmjapis.CurrentWeather;
import net.aksingh.owmjapis.OpenWeatherMap;
import org.geonames.ToponymSearchCriteria;
import org.geonames.ToponymSearchResult;
import org.geonames.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import net.aksingh.*;

/**
 * Created by joshua on 2016-07-11.
 */
@Service
@Transactional
public class LocationServiceImpl implements LocationService{


    @Override
    public List<String> findLocation(String initLocation) {
        WebService.setUserName("jbs87");
        List<String> locationList = new ArrayList<>();
        ToponymSearchCriteria searchCriteria = new ToponymSearchCriteria();
        searchCriteria.setQ(initLocation);
        ToponymSearchResult searchResult = null;
        try {
            searchResult = WebService.search(searchCriteria);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (searchResult != null) {
            locationList = searchResult.getToponyms().stream().map(toponym -> toponym.getBoundingBox() + " " + toponym.getName() + ", " + toponym.getCountryName() + ","+toponym.getLatitude() + "," + toponym.getLongitude()).collect(Collectors.toList());
        }
        return locationList;
    }

    @Override
    public float findTemperature(double latitude, double longitude) throws Exception {
        OpenWeatherMap owm = new OpenWeatherMap("");
        owm.setApiKey("7821aac66321dc13ce0452bb050c5df1");
        CurrentWeather weather = owm.currentWeatherByCoordinates((float) latitude, (float) longitude);
        return weather.getMainInstance().getTemperature();
    }
}
