package com.mtm.dao.connection;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Created by Admin on 2/24/2019.
 */
public class MySqlDatabase implements Database {
    public Connection getConnection() throws Exception {
        Class.forName("com.mysql.jdbc.Driver");
        Connection conn = null;
        conn = DriverManager.getConnection("jdbc:mysql://35.200.166.234:3306/mtm","mtmwebuser", "mtm@123");
        return conn;
    }
}
