package com.anna.dao;

import com.anna.model.Group;
import com.anna.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class StudentsDaoImpl implements StudentsDao {

    private static String MIN_STUDENT = "minStudent";
    private static String MAX_STUDENT = "maxStudent";
    private static String GROUP_ID = "groupId";


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
    public StudentsDaoImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate){
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }


    @Override
    public List<Group> getGroups(Integer minStudent, Integer maxStudent) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue(MIN_STUDENT, "minStudent");
        parameterSource.addValue(MAX_STUDENT,"maxStudent");

        return namedParameterJdbcTemplate.query(getGroupsSql, parameterSource, new GroupMapperWithStudents());
    }

    @Override
    public Group getGroupById(Integer groupId) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource(GROUP_ID, "groupId");
        return namedParameterJdbcTemplate.queryForObject(getGroupByIdSql, parameterSource, new GroupMapper());
    }

    @Override
    public Integer addGroup(Group group) {
        return null;
    }

    @Override
    public Integer updateGroup(Group group) {
        return null;
    }

    @Override
    public Integer deleteGroup(Integer id) {
        return null;
    }

    @Override
    public List<Student> getStudents(Integer minAge, Integer maxAge) {
        return null;
    }

    @Override
    public Student getStudentById(Integer studentId) {
        return null;
    }

    @Override
    public Integer addStudent(Student student) {
        return null;
    }

    @Override
    public Integer updateStudent(Student student) {
        return null;
    }

    @Override
    public Integer deleteStudent(Integer id) {
        return null;
    }

    private class GroupMapperWithStudents implements RowMapper<Group> {

        @Override
        public Group mapRow(ResultSet resultSet, int i) throws SQLException {
            return new Group(resultSet.getInt("groupId"),
                    resultSet.getString("name"),
                    resultSet.getInt("numberOfStudents"));
        }
    }
}
