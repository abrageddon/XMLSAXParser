
import java.sql.*;
import java.util.Random;
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
            //TODO figure out if 2 adds can happen before one getLastID() is run
            //This could result in a mistaken docID being returned.
//            synchronized (parent) {
//            Random rand = new Random();
//            Integer rInt = rand.nextInt();

//            System.out.println("S:" + rInt );
                Statement st = conn.createStatement();
                st.executeUpdate(SQLQ);
//                Thread.sleep(1000);//DEBUG
                rtn = getLastID();

//            System.out.println("E:" + rInt );
//            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
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
