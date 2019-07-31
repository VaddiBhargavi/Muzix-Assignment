package com.stackroute.MuzixAssignment.MuzixAppMysql.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)

//Result class has a dependency on TrackMatches class
public class Result {
    public TrackMatches results;
}
