package com.anna.dao;

import com.anna.model.Group;
import com.anna.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GroupDaoImpl implements GroupDao {

    private static String MIN_STUDENT = "minStudent";
    private static String MAX_STUDENT = "maxStudent";
    private static String GROUP_ID = "groupId";
    private static String GROUP_NAME = "name";
    private static String NUMBER_OF_STUDENT = "numberOfStudent";

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
    public GroupDaoImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public List<Group> getGroups(Date start, Date finish) {
        return null;
    }

    @Override
    public Group getGroupById(Integer groupId) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource(GROUP_ID, groupId);
        return namedParameterJdbcTemplate.queryForObject(getGroupByIdSql, parameterSource, new GroupMapper());
    }

    @Override
    public Integer addGroup(Group group) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue(GROUP_NAME, group.getName());

        namedParameterJdbcTemplate.update(addGroupSql, mapSqlParameterSource, keyHolder);
        return keyHolder.getKey().intValue();

    }

    @Override
    public Integer updateGroup(Group group) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue(GROUP_ID, group.getGroupId());
        mapSqlParameterSource.addValue(GROUP_NAME, group.getName());

        return namedParameterJdbcTemplate.update(updateGroupSql, mapSqlParameterSource);
    }

    @Override
    public Integer deleteGroup(Integer id) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue(GROUP_ID, id);
        return namedParameterJdbcTemplate.update(deleteGroupSql, mapSqlParameterSource);
    }

    @Override
    public Float getAverageAgeOfStudents() {
        return null;
    }

    private class GroupMapperWithStudents implements RowMapper<Group> {

        @Override
        public Group mapRow(ResultSet resultSet, int i) throws SQLException {
            return new Group(resultSet.getInt("group_id"),
                    resultSet.getString("name"),
                    resultSet.getInt("numberOfStudents"));
        }
    }

     class GroupMapper implements RowMapper<Group> {
        @Override
        public Group mapRow(ResultSet resultSet, int i) throws SQLException {
            Group group = new Group(resultSet.getInt("group_id"),
                    resultSet.getString("name"),
                    new ArrayList<>());

            if (resultSet.getObject("name", String.class) == null) {
                return group;
            }

            while (!(resultSet.next())) {
                group.getStudents().add(new Student(resultSet.getString("name"),
                        resultSet.getString("surname")));
            }
            return group;
        }
    }
}
