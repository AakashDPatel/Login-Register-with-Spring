package com.model;

import com.common.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.springframework.stereotype.Service;

@Service
public class UserDAOImpl implements UserDAO {

    @Override
    public boolean validateUser(User user) {
        Connection con = null;
        try {
            con = DBConnection.getDBConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from user where username='" + user.getUsername() + "'" + (user.getPassword() == null ? "" : " and password='" + user.getPassword() + "'"));
            if (rs.next()) {
                user.setId(rs.getInt(1));
                user.setName(rs.getString(4));
                user.setPhone(rs.getString(5));
                user.setEmail(rs.getString(6));
                return true;
            }
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException ex) {
                }
            }
        }
        return false;
    }

    @Override
    public int registerUser(User user) {
        Connection con = null;
        try {
            con = DBConnection.getDBConnection();
            if (usernameExisted(user.getUsername())) {
                return -1;
            } else {
                PreparedStatement pstmt = con.prepareStatement("insert into user (username, password, name, phone, email) values(?, ?, ?, ?, ?) ");
                int i = 1;
                pstmt.setString(i++, user.getUsername());
                pstmt.setString(i++, user.getPassword());
                pstmt.setString(i++, user.getName());
                pstmt.setString(i++, user.getPhone());
                pstmt.setString(i++, user.getEmail());
                pstmt.executeUpdate();
                return 1;
            }
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException ex) {
                }
            }
        }
        return 0;
    }

    @Override
    public boolean usernameExisted(String username) {
        Connection con = null;
        try {
            con = DBConnection.getDBConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from user where username = '" + username + "'");
            if (rs.next()) {
                return true;
            }
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException ex) {
                }
            }
        }
        return false;
    }

    @Override
    public boolean updateProfile(User newUser) {
        Connection con = null;
        try {
            con = DBConnection.getDBConnection();
            PreparedStatement pstmt = con.prepareStatement("update user set name=?, email=?, phone=? where id=?");
            int i = 1;
            pstmt.setString(i++, newUser.getName());
            pstmt.setString(i++, newUser.getEmail());
            pstmt.setString(i++, newUser.getPhone());
            pstmt.setInt(i++, newUser.getId());
            pstmt.executeUpdate();
            return true;
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException x) {
                }
            }
        }
        return false;
    }

    @Override
    public boolean updatePassword(int id, String newpass) {
        Connection con = null;
        try {
            con = DBConnection.getDBConnection();
            PreparedStatement pstmt = con.prepareStatement("update user set password=? where id=?");
            int i = 1;
            pstmt.setString(i++, newpass);
            pstmt.setInt(i++, id);
            pstmt.executeUpdate();
            return true;
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException x) {
                }
            }
        }
        return false;
    }
    
}
