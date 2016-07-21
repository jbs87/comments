package com.comments.service;

import com.comments.domain.Location;
import net.aksingh.owmjapis.CurrentWeather;
import net.aksingh.owmjapis.OpenWeatherMap;
import org.geonames.ToponymSearchCriteria;
import org.geonames.ToponymSearchResult;
import org.geonames.*;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${openweather.api.key}")
    String apiKey;

    @Value("${geonames.username}")
    String userName;

    @Override
    public List<Location> findLocation(String initLocation) {
        WebService.setUserName(userName);
        ToponymSearchCriteria searchCriteria = new ToponymSearchCriteria();
        List<Location> locationList = new ArrayList<>();
        searchCriteria.setQ(initLocation);
        ToponymSearchResult searchResult = null;
        try {
            searchResult = WebService.search(searchCriteria);
        } catch (Exception e) {
            return null;
        }
        if (searchResult != null) {
            locationList = searchResult.getToponyms().stream().map(toponym -> new Location(toponym.getName(), toponym.getCountryName(), toponym.getLatitude(),toponym.getLongitude())).collect(Collectors.toList());
        }
        return locationList;
    }

    @Override
    public Float findTemperature(double latitude, double longitude) {
        OpenWeatherMap owm = new OpenWeatherMap("");
        owm.setApiKey(apiKey);
        CurrentWeather weather;
        try {
            weather = owm.currentWeatherByCoordinates((float) latitude, (float) longitude);
        } catch (JSONException e) {
            return null;
        }
        return weather.getMainInstance().getTemperature();
    }
}
