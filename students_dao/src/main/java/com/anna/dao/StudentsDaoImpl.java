package com.anna.dao;

import static com.anna.builder.BuilderMapSqlParameterSource.buildMapSqlParameterSource;

import com.anna.model.Group;
import com.anna.model.SaveStudent;
import com.anna.model.Student;
import java.sql.ResultSet;
import java.sql.SQLException;
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
public class StudentsDaoImpl implements StudentsDao {

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
  public List<Student> getStudents(Integer page, Integer size,
      String minBirthDate, String maxBirthDate) {
    MapSqlParameterSource mapSqlParameterSource = buildMapSqlParameterSource(page, size,
        minBirthDate, maxBirthDate);

    return namedParameterJdbcTemplate.query(getStudentsSql, mapSqlParameterSource,
        new StudentWithGroupMapper());
  }

  @Override
  public Student getStudentById(Integer studentId) {
    MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource(STUDENT_ID, studentId);
    return namedParameterJdbcTemplate
        .queryForObject(getStudentByIdSql, mapSqlParameterSource, new StudentWithGroupMapper());
  }

  @Override
  public Integer addStudent(SaveStudent student) {
    KeyHolder keyHolder = new GeneratedKeyHolder();
    MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
    mapSqlParameterSource.addValue(STUDENT_NAME, student.getName());
    build(student, mapSqlParameterSource);

    namedParameterJdbcTemplate
        .update(addStudentSql, mapSqlParameterSource, keyHolder, new String[]{"student_id"});
    return keyHolder.getKey().intValue();
  }

  private void build(SaveStudent student, MapSqlParameterSource mapSqlParameterSource) {
    mapSqlParameterSource.addValue(SURNAME, student.getSurname());
    mapSqlParameterSource.addValue(Birth_Date, student.getBirthDate());
    mapSqlParameterSource.addValue(GROUP_ID, student.getGroup().getGroupId());
  }

  @Override
  public Integer updateStudent(Integer studentId, SaveStudent student) {
    MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
    mapSqlParameterSource.addValue(STUDENT_ID, studentId);
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
