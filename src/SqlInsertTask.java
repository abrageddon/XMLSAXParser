
import java.sql.*;
import java.util.concurrent.*;

public class SqlInsertTask implements Callable  {

    private String SQLQ;
    private Connection conn;

    public SqlInsertTask() {
    }

    public SqlInsertTask(Connection conn, String SQLQuery) {
        this.SQLQ = SQLQuery;
        this.conn = conn;
    }

    public Object call() {
        int rtn = 0;
        try {
            Statement st = conn.createStatement();
            st.executeUpdate(SQLQ);
            rtn = getLastID();
        } catch (SQLException e) {
            System.err.println (e.getMessage());
        }
        return rtn;
    }

        private Integer getLastID() throws SQLException {
        Statement st = conn.createStatement();
        ResultSet lastIDQ = st.executeQuery("SELECT LAST_INSERT_ID()");
        if (lastIDQ.next()) {
            int id = lastIDQ.getInt(1);
            st.close();
            return id;
        } else {
            return null;
        }
    }
}
