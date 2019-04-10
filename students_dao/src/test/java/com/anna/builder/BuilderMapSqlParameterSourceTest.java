package com.anna.builder;

import static com.anna.builder.BuilderMapSqlParameterSource.buildMapSqlParameterSource;
import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

public class BuilderMapSqlParameterSourceTest {

  @Test
  public void buildMapSqlParameterSourceTest() {
    Integer page = 1;
    Integer size = 6;
    String minParam = "2019-01-04";
    String maxParam = "2020-08-09";

    MapSqlParameterSource mapSqlParameterSource = buildMapSqlParameterSource(page, size, minParam,
        maxParam);
    assertEquals(mapSqlParameterSource.getValue("page"), 1);
    assertEquals(mapSqlParameterSource.getValue("size"), 6);
    assertEquals(mapSqlParameterSource.getValue("minParam"), "2019-01-04");
    assertEquals(mapSqlParameterSource.getValue("maxParam"), "2020-08-09");

  }
}