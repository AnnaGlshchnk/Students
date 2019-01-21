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

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class StudentsDaoImpl implements StudentsDao {

    private static String MIN_BIRTH_DATE = "minBirthDate";
    private static String MAX_BIRTH_DATE = "maxBirthDate";
    private static String STUDENT_ID = "studentId";
    private static String STUDENT_NAME = "studentName";
    private static String SURNAME = "surname";
    private static String Birth_Date = "birthDate";
    private static String GROUP_ID = "groupId";

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
    public List<Student> getStudents(String minBirthDate, String maxBirthDate) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue(MIN_BIRTH_DATE, minBirthDate);
        mapSqlParameterSource.addValue(MAX_BIRTH_DATE, maxBirthDate);

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
        build(student, mapSqlParameterSource);

        namedParameterJdbcTemplate.update(addStudentSql, mapSqlParameterSource, keyHolder);
        return keyHolder.getKey().intValue();
    }

    private void build(Student student, MapSqlParameterSource mapSqlParameterSource) {
        mapSqlParameterSource.addValue(SURNAME, student.getSurname());
        mapSqlParameterSource.addValue(Birth_Date, student.getBirthDate());
        mapSqlParameterSource.addValue(GROUP_ID, student.getGroup().getGroupId());
    }

    @Override
    public Integer updateStudent(Student student) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue(STUDENT_ID, student.getStudentId());
        mapSqlParameterSource.addValue(STUDENT_NAME, student.getName());
        build(student, mapSqlParameterSource);

        return namedParameterJdbcTemplate.update(updateStudentSql, mapSqlParameterSource);
    }

    @Override
    public Integer deleteStudent(Integer id) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue(STUDENT_ID, id);
        return namedParameterJdbcTemplate.update(deleteStudentSql, mapSqlParameterSource);

    }


    private class StudentWithGroupMapper implements RowMapper<Student> {

        @Override
        public Student mapRow(ResultSet resultSet, int i) throws SQLException {
            return new Student(resultSet.getInt("student_id"),
                    resultSet.getString("student_name"),
                    resultSet.getString("surname"),
                    resultSet.getDate("birth_date"),
                    new Group(resultSet.getInt("student_groupId")));
        }
    }
}
