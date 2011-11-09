
import java.sql.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class SQLTask implements Callable {

    private String SQLQ;
    private Connection conn;

    public SQLTask() {
    }

    public SQLTask(Connection conn, String SQLQuery) {
        this.SQLQ = SQLQuery;
        this.conn = conn;
    }
    public SQLTask(Connection conn, String SQLQuery, Future docID, Integer authID) throws InterruptedException, ExecutionException {
        this(conn, SQLQuery + " ('" + docID.get() + "','" + authID + "')" );
    }

    public Object call() {
        int rtn = 0;
        try {
            Statement st = conn.createStatement();
            rtn = st.executeUpdate(SQLQ);
        } catch (SQLException e) {
            System.err.println (e.getMessage());
        }
        return rtn;
    }
}
