package com.comments.service;

import com.comments.domain.Comment;
import org.geonames.Toponym;

import java.util.List;

/**
 * Created by joshua on 2016-07-12.
 */
public interface LocationService {
    public List<String> findLocation(String initLocation);
    public float findTemperature(double latitude, double longitude) throws Exception;
}

