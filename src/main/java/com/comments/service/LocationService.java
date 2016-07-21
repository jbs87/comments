package com.comments.service;

import com.comments.domain.Comment;
import com.comments.domain.Location;
import org.geonames.Toponym;

import java.util.List;

/**
 * Created by joshua on 2016-07-12.
 */
public interface LocationService {
    public List<Location> findLocation(String initLocation);
    public Float findTemperature(double latitude, double longitude);
}

