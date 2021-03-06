package com.anna.dao;

import static com.anna.builder.BuilderMapSqlParameterSource.buildMapSqlParameterSource;

import com.anna.model.Group;
import com.anna.model.SaveGroup;
import com.anna.model.Student;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class GroupsDaoImpl implements GroupsDao {

  private static String GROUP_ID = "groupId";
  private static String GROUP_NAME = "groupName";
  private static String CREATE_DATE = "createDate";
  private static String FINISH_DATE = "finishDate";

  @Value("${StudentsDaoSql.getGroups}")
  private String getGroupsSql;
  @Value("${StudentsDaoSql.getGroupById}")
  private String getGroupByIdSql;
  @Value("${StudentsDaoSql.addGroup}")
  private String addGroupSql;
  @Value("${StudentsDaoSql.updateGroup}")
  private String updateGroupSql;
  @Value("${StudentsDaoSql.deleteGroup}")
  private String deleteGroupSql;

  private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

  @Autowired
  public GroupsDaoImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
    this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
  }

  @Override
  public List<Group> getGroups(Integer page, Integer size, String start, String finish) {
    MapSqlParameterSource mapSqlParameterSource = buildMapSqlParameterSource(page, size, start,
        finish);
    return namedParameterJdbcTemplate.query(getGroupsSql, mapSqlParameterSource, new GroupMapper());
  }

  @Override
  public Group getGroupById(Integer groupId) {
    MapSqlParameterSource parameterSource = new MapSqlParameterSource(GROUP_ID, groupId);
    return namedParameterJdbcTemplate.queryForObject(getGroupByIdSql, parameterSource,
        new GroupWithStudentMapper());
  }

  @Override
  public Integer addGroup(SaveGroup group) {
    final KeyHolder keyHolder = new GeneratedKeyHolder();
    MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
    mapSqlParameterSource.addValue(GROUP_NAME, group.getName());
    mapSqlParameterSource.addValue(CREATE_DATE, group.getCreateDate());
    mapSqlParameterSource.addValue(FINISH_DATE, group.getFinishDate());
    namedParameterJdbcTemplate
        .update(addGroupSql, mapSqlParameterSource, keyHolder, new String[]{"group_id"});
    return keyHolder.getKey().intValue();

  }

  @Override
  public Integer updateGroup(Integer groupId, SaveGroup group) {
    MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
    mapSqlParameterSource.addValue(GROUP_ID, groupId);
    mapSqlParameterSource.addValue(GROUP_NAME, group.getName());
    mapSqlParameterSource.addValue(CREATE_DATE, group.getCreateDate());
    mapSqlParameterSource.addValue(FINISH_DATE, group.getFinishDate());

    return namedParameterJdbcTemplate.update(updateGroupSql, mapSqlParameterSource);
  }

  @Override
  public Integer deleteGroup(Integer id) {
    MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
    mapSqlParameterSource.addValue(GROUP_ID, id);
    return namedParameterJdbcTemplate.update(deleteGroupSql, mapSqlParameterSource);
  }

  class GroupMapper implements RowMapper<Group> {

    @Override
    public Group mapRow(ResultSet resultSet, int i) throws SQLException {

      return new Group(resultSet.getInt("group_id"),
          resultSet.getString("group_name"),
          resultSet.getDate("create_date"),
          resultSet.getDate("finish_date"),
          resultSet.getInt("countOfStudent"),
          resultSet.getFloat("avgAge"));
    }
  }

  class GroupWithStudentMapper implements RowMapper<Group> {

    @Override
    public Group mapRow(ResultSet resultSet, int i) throws SQLException {

      Group group = new Group(resultSet.getInt("group_id"),
          resultSet.getString("group_name"),
          resultSet.getDate("create_date"),
          resultSet.getDate("finish_date"),
          new ArrayList<>());

      do {
        group.getStudents().add(new Student(resultSet.getString("student_name"),
            resultSet.getString("surname")));
      } while (resultSet.next());

      return group;
    }
  }
}