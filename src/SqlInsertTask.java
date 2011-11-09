
import java.sql.*;
import java.util.concurrent.*;

public class SqlInsertTask implements Callable {

    private String SQLQ;
    private Connection conn;
    private XMLSAXParser parent;

    public SqlInsertTask() {
    }

    public SqlInsertTask(Connection connection, String SQLQuery) {
        this.SQLQ = SQLQuery;
        this.conn = connection;
    }

    SqlInsertTask(XMLSAXParser aThis, Connection connection, String addDoc) {
        this(connection, addDoc);
        parent = aThis;
    }

    public Object call() {
        int rtn = 0;
        try {
            //Sync to parent to prevent adding doc before getting the last one's ID
            synchronized (parent) {
                Statement st = conn.createStatement();
                st.executeUpdate(SQLQ);
//                Thread.currentThread().sleep(200);//DEBUG
                st = conn.createStatement();
                ResultSet lastIDQ = st.executeQuery("SELECT LAST_INSERT_ID()");
                lastIDQ.next();
                rtn = lastIDQ.getInt(1);
//                System.out.println("IT:" + rtn + " " + lastIDQ.getInt(1) );//DEBUG
                st.close();
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return rtn;
    }
}
