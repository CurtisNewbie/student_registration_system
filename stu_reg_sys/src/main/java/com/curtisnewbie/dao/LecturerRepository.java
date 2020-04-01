package com.curtisnewbie.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.curtisnewbie.model.Lecturer;
import com.curtisnewbie.util.LoggerProducer;

public class LecturerRepository implements LecturerDao {

    private final String SELECT_ALL = "SELECT * FROM lecturer";
    private final String SELECT_BY_ID = "SELECT * FROM lecturer WHERE id = ?";
    private final String DELETE_BY_ID = "DELETE FROM lecturer WHERE id = ?";
    private final String UPDATE_FIRSTNAME = "UPDATE lecturer SET firstname = ? WHERE id = ?";
    private final String UPDATE_LASTNAME = "UPDATE lecturer SET lastname = ? WHERE id = ?";
    private final String UPDATE_POSITION = "UPDATE lecturer SET position = ? WHERE id = ?";

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
        logger.info(String.format("Delete id: '%d'", id));
        try {
            var stmt = conn.prepareStatement(DELETE_BY_ID);
            stmt.setInt(1, id);
            stmt.executeUpdate();
            return true;
        } catch (Exception e) {
            logger.severe(e.getMessage());
        }
        return false;
    }

    @Override
    public Lecturer findById(int id) {
        logger.info(String.format("Find id: '%d'", id));
        try {
            var stmt = conn.prepareStatement(SELECT_BY_ID);
            stmt.setInt(1, id);
            var set = stmt.executeQuery();
            Lecturer lec = null;
            if (set.next())
                lec = new Lecturer(set.getInt(1), set.getString(2), set.getString(3), set.getString(4));
            return lec;
        } catch (Exception e) {
            logger.severe(e.getMessage());
        }
        return null;
    }

    @Override
    public boolean update(Lecturer lect) {
        logger.info(String.format("Update lecturer to: '%s'", lect.toString()));
        try {
            conn.setAutoCommit(false);
            Lecturer prev;
            if ((prev = findById(lect.getId())) != null) {
                boolean succeeded = true;
                if (!prev.getPosition().equals(lect.getPosition()))
                    if (!updatePosition(lect.getId(), lect.getPosition()))
                        succeeded = false;

                if (succeeded && !prev.getFirstname().equals(lect.getFirstname()))
                    if (!updateFirstname(lect.getId(), lect.getFirstname()))
                        succeeded = false;

                if (succeeded && !prev.getLastname().equals(lect.getLastname()))
                    if (!updateLastname(lect.getId(), lect.getLastname()))
                        succeeded = false;

                if (succeeded) {
                    conn.commit();
                    return true;
                } else {
                    conn.rollback();
                }
            }
        } catch (Exception e) {
            logger.severe(e.getMessage());
            try {
                conn.rollback();
            } catch (SQLException e1) {
                logger.severe(e1.getMessage());
            }
        }
        return false;
    }

    @Override
    public boolean create(Lecturer obj) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean updateFirstname(int id, String fname) {
        logger.info(String.format("Update id: '%d', firstname updated to '%s'", id, fname));
        try {
            var stmt = conn.prepareStatement(UPDATE_FIRSTNAME);
            stmt.setString(1, fname);
            stmt.setInt(2, id);
            stmt.executeUpdate();
            return true;
        } catch (Exception e) {
            logger.severe(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean updateLastname(int id, String lname) {
        logger.info(String.format("Update id: '%d', lastname updated to '%s'", id, lname));
        try {
            var stmt = conn.prepareStatement(UPDATE_LASTNAME);
            stmt.setString(1, lname);
            stmt.setInt(2, id);
            stmt.executeUpdate();
            return true;
        } catch (Exception e) {
            logger.severe(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean updatePosition(int id, String pos) {
        logger.info(String.format("Update id: '%d', lastname updated to '%s'", id, pos));
        try {
            var stmt = conn.prepareStatement(UPDATE_POSITION);
            stmt.setString(1, pos);
            stmt.setInt(2, id);
            stmt.executeUpdate();
            return true;
        } catch (Exception e) {
            logger.severe(e.getMessage());
        }
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