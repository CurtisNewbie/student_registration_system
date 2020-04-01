package com.curtisnewbie.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.curtisnewbie.model.Lecturer;
import com.curtisnewbie.util.LoggerProducer;

public class LecturerRepository implements LecturerDao {

    private final String SELECT_ALL = "SELECT * FROM lecturer";

    private final Connection conn = new DBManager().getConnection();
    private final Logger logger = LoggerProducer.getLogger(this);

    @Override
    public List<Lecturer> getAll() {
        logger.info("Get all lecturer");
        List<Lecturer> list = new ArrayList<>();
        try {
            var stmt = conn.createStatement();
            ResultSet set = stmt.executeQuery(SELECT_ALL);
            while (set.next()) {
                var lec = new Lecturer(set.getInt(1), set.getString(2), set.getString(3), set.getString(4));
                list.add(lec);
            }
        } catch (Exception e) {
            logger.severe(e.getMessage());
        }
        return list;
    }

    @Override
    public boolean deleteById(int id) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Lecturer findById(int id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean update(Lecturer obj) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean create(Lecturer obj) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean updateFirstname(int id, String fname) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean updateLastname(int id, String lname) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean updatePosition(int id, String pos) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public List<Lecturer> findByFirstname(String fname) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Lecturer> findByLastname(String lname) {
        // TODO Auto-generated method stub
        return null;
    }

}