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
    private static String STUDENT_NAME = "name";
    private static String SURNAME = "surname";
    private static String BirthDate = "birthDate";

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
    public List<Student> getStudents(Integer minBirthDate, Integer maxBirthDate) {
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
        mapSqlParameterSource.addValue(SURNAME, student.getSurname());
        mapSqlParameterSource.addValue(BirthDate, student.getBirthDate());

        namedParameterJdbcTemplate.update(addStudentSql, mapSqlParameterSource, keyHolder);
        return keyHolder.getKey().intValue();
    }

    @Override
    public Integer updateStudent(Student student) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue(STUDENT_ID, student.getGroupId());
        mapSqlParameterSource.addValue(STUDENT_NAME, student.getName());
        mapSqlParameterSource.addValue(SURNAME, student.getName());
        mapSqlParameterSource.addValue(BirthDate, student.getBirthDate());

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
            return new Student(resultSet.getInt("studentId"),
                    resultSet.getString("name"),
                    resultSet.getString("surname"),
                    resultSet.getDate("birthDate"),
                    new Group(resultSet.getString("name")));
        }
    }
}
