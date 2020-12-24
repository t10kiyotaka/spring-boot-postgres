package com.example.demo.dao;

import com.example.demo.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository("postgres")
public class PersonDataAccessService implements PersonDao {
    final private JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDataAccessService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int insertPerson(UUID id, Person person) {
        final String sql = "insert into person (id, name) values (?, ?)";
        return jdbcTemplate.update(sql, id, person.getName());
    }

    @Override
    public List<Person> selectAllPeople() {
        final String sql = "select id, name from person";
        return jdbcTemplate.query(sql, (resultSet, i) -> {
            UUID id = UUID.fromString(resultSet.getString("id"));
            String name = resultSet.getString("name");
            return new Person(id, name);
        });
    }

    @Override
    public Optional<Person> selectPersonById(UUID id) {
        final String sql = "select id, name from person where id = ?";
        Person person = jdbcTemplate.queryForObject(sql, new Object[]{id}, (resultSet, i) -> {
            UUID personId = UUID.fromString(resultSet.getString("id"));
            String name = resultSet.getString("name");
            return new Person(personId, name);
        });
        return Optional.ofNullable(person);
    }

    @Override
    public int updatePersonById(UUID id, Person person) {
        final String sql = "update person set name = ? where id = ?";
        return jdbcTemplate.update(sql, person.getName(), id);
    }

    @Override
    public int deletePersonById(UUID id) {
        final String sql = "delete from person where id = ?";
        return jdbcTemplate.update(sql, id);
    }
}
