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
import java.time.Period;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GroupDaoImpl implements GroupDao {

    private static String START = "start";
    private static String FINISH = "finish";
    private static String GROUP_ID = "groupId";
    private static String GROUP_NAME = "name";
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
    public GroupDaoImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public List<Group> getGroups(Date start, Date finish) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue(START, start);
        mapSqlParameterSource.addValue(FINISH, finish);
        return namedParameterJdbcTemplate.query(getGroupsSql, mapSqlParameterSource, new GroupMapper());
    }

    @Override
    public Group getGroupById(Integer groupId) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource(GROUP_ID, groupId);
        return namedParameterJdbcTemplate.queryForObject(getGroupByIdSql, parameterSource, new GroupWithStudentMapper());
    }

    @Override
    public Integer addGroup(Group group) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue(GROUP_NAME, group.getName());
        mapSqlParameterSource.addValue(CREATE_DATE, group.getCreateDate());
        mapSqlParameterSource.addValue(FINISH_DATE, group.getFinishDate());

        namedParameterJdbcTemplate.update(addGroupSql, mapSqlParameterSource, keyHolder);
        return keyHolder.getKey().intValue();

    }

    @Override
    public Integer updateGroup(Group group) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue(GROUP_ID, group.getGroupId());
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

    @Override
    public Float getAverageAgeOfStudents() {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();

    }

     class GroupMapper implements RowMapper<Group> {
        @Override
        public Group mapRow(ResultSet resultSet, int i) throws SQLException {

            Group group = new Group(resultSet.getInt("groupId"),
                    resultSet.getString("name"),
                    resultSet.getDate("createDate"),
                    resultSet.getDate("finishDate"),
                    new ArrayList<>().size());

            while (resultSet.next()){
                group.getStudents().add(new Student());
            }
            return group;
        }
    }

    class GroupWithStudentMapper implements RowMapper<Group> {
        @Override
        public Group mapRow(ResultSet resultSet, int i) throws SQLException {

            Group group = new Group(resultSet.getInt("groupId"),
                    resultSet.getString("name"),
                    resultSet.getDate("createDate"),
                    resultSet.getDate("finishDate"),
                    new ArrayList<>());

            while (resultSet.next()){
                group.getStudents().add(new Student(resultSet.getInt("studentId"),
                        resultSet.getString("name"),
                        resultSet.getString("surname")));
            }
            return group;
        }
    }
}