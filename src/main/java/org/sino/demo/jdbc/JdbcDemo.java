package org.sino.demo.jdbc;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class JdbcDemo {
    public void updateTradeOrder(TradeOrderData tradeOrderData) {
        DataSource dataSource = null;
        Connection connection = null;
        Statement statement = null;
        try {
            dataSource = (DataSource) (new InitialContext()).lookup("jdbc/MasterDS");
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            String sql = "update trade_order ...";
            statement.executeUpdate(sql);
            connection.commit();
        } catch (NamingException|SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);
        } finally {
            try {
                statement.close();
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

class TradeOrderData{

}