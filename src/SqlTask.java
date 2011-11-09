
import java.sql.*;
import java.util.concurrent.*;

public class SqlTask implements Callable {

    private String SQLQ;
    private Connection conn;
    private Future FdocID;
    private Future FAuthor;

    public SqlTask(Connection connection, String SQLQuery) {
        this.SQLQ = SQLQuery;
        this.conn = connection;
    }

    public SqlTask(Connection connection, String SQLQuery, Future FDocID, Future FAuthor) {
        this(connection, SQLQuery);
        this.FdocID = FDocID;
        this.FAuthor = FAuthor;
    }

    public Object call() {
        int rtn = 0;
        try {
            Statement st = conn.createStatement();
            if (FdocID != null && FAuthor != null){
                rtn = st.executeUpdate(SQLQ + " ('" + FdocID.get() + "','" + FAuthor.get() + "')");
            }else{
                rtn = st.executeUpdate(SQLQ);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return rtn;
    }
}
