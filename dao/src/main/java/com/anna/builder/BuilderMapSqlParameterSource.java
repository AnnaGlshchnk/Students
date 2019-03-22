package com.anna.builder;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Component;

@Component
public class BuilderMapSqlParameterSource {

  private static String MIN_PARAM = "minParam";
  private static String MAX_PARAM = "maxParam";
  private static String PAGE = "page";
  private static String SIZE = "size";

  /**
   *Builder mapSqlParameterSource.
   */
  public static MapSqlParameterSource buildMapSqlParameterSource(Integer page, Integer size,
      String minParam, String maxParam) {
    MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
    mapSqlParameterSource.addValue(PAGE, page);
    mapSqlParameterSource.addValue(SIZE, size);
    mapSqlParameterSource.addValue(MIN_PARAM, minParam);
    mapSqlParameterSource.addValue(MAX_PARAM, maxParam);
    return mapSqlParameterSource;
  }

}
