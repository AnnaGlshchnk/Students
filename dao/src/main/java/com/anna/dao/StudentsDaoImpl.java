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
import org.springframework.stereotype.Repository;

import java.security.Key;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class StudentsDaoImpl implements StudentsDao {

    private static String MIN_STUDENT = "minStudent";
    private static String MAX_STUDENT = "maxStudent";
    private static String MIN_AGE = "minAge";
    private static String MAX_AGE = "maxAge";
    private static String GROUP_ID = "groupId";
    private static String STUDENT_ID = "studentId";
    private static String GROUP_NAME = "name";
    private static String STUDENT_NAME = "name";
    private static String SURNAME = "surname";
    private static String AGE = "age";
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
    @Value("${StudentsDaoSql.getStudents}")
    private String getStudentsSql;
    @Value("${StudentsDaoSql.getStudentById}")
    private String getStudentByIdSql;
    @Value("${StudentsDaoSql.addStudent}")
    private String addStudentSql;
    @Value("${StudentsDaoSql.updateStudent}")
    private String updateStudentSql;
    @Value("${StudentsDaoSql.deleteStudent}")
    private String deleteStudentSql;


    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public StudentsDaoImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }


    @Override
    public List<Group> getGroups(Integer minStudent, Integer maxStudent) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue(MIN_STUDENT, minStudent);
        parameterSource.addValue(MAX_STUDENT, maxStudent);

        return namedParameterJdbcTemplate.query(getGroupsSql, parameterSource, new GroupMapperWithStudents());
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
        mapSqlParameterSource.addValue(NUMBER_OF_STUDENT, group.getNumberOfStudents());

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
    public List<Student> getStudents(Integer minAge, Integer maxAge) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue(MIN_AGE, minAge);
        mapSqlParameterSource.addValue(MAX_AGE, maxAge);

        return namedParameterJdbcTemplate.query(getStudentsSql, mapSqlParameterSource, new StudentWithGroupMapper());
    }

    @Override
    public Student getStudentById(Integer studentId) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource(STUDENT_ID, studentId);
        return namedParameterJdbcTemplate.queryForObject(getStudentByIdSql, mapSqlParameterSource, new StudentWithGroupMapper());
    }

    @Override
    public Integer addStudent(Student student) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue(STUDENT_NAME, student.getName());
        mapSqlParameterSource.addValue(SURNAME, student.getSurname());
        mapSqlParameterSource.addValue(AGE, student.getAge());

        namedParameterJdbcTemplate.update(addStudentSql, mapSqlParameterSource, keyHolder);
        return keyHolder.getKey().intValue();
    }

    @Override
    public Integer updateStudent(Student student) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue(STUDENT_ID, student.getGroupId());
        mapSqlParameterSource.addValue(STUDENT_NAME, student.getName());
        mapSqlParameterSource.addValue(SURNAME, student.getName());
        mapSqlParameterSource.addValue(AGE, student.getAge());

        return namedParameterJdbcTemplate.update(updateStudentSql, mapSqlParameterSource);
    }

    @Override
    public Integer deleteStudent(Integer id) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue(STUDENT_ID, id);
        return namedParameterJdbcTemplate.update(deleteStudentSql, mapSqlParameterSource);

    }


    private class GroupMapperWithStudents implements RowMapper<Group> {

        @Override
        public Group mapRow(ResultSet resultSet, int i) throws SQLException {
            return new Group(resultSet.getInt("group_id"),
                    resultSet.getString("name"),
                    resultSet.getInt("numberOfStudents"));
        }
    }

    private class GroupMapper implements RowMapper<Group> {
        @Override
        public Group mapRow(ResultSet resultSet, int i) throws SQLException {
            Group group = new Group(resultSet.getInt("group_id"),
                    resultSet.getString("name"),
                    new ArrayList<>());

            if (resultSet.getObject("student_id", Integer.class) == null) {
                return group;
            }

            while (!(resultSet.next())) {
                group.getStudents().add(new Student(resultSet.getInt("student_Id"),
                        resultSet.getString("name"),
                        resultSet.getString("surname"),
                        resultSet.getInt("age")));
            }
            return group;
        }
    }

    private class StudentWithGroupMapper implements RowMapper<Student> {

        @Override
        public Student mapRow(ResultSet resultSet, int i) throws SQLException {
            Student student = new Student(resultSet.getInt("student_id"),
                    resultSet.getString("name"),
                    resultSet.getString("surname"),
                    resultSet.getInt("age"),
                    new Group(resultSet.getString("name")));
            return student;
        }
    }
}
