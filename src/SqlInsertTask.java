
import java.sql.*;
import java.util.concurrent.*;

public class SqlInsertTask implements Callable {

    private String SQLQ;
    private Connection conn;
    private XMLSAXParser parent;
    private document doc;

    SqlInsertTask(XMLSAXParser aThis, Connection connection, String SQLQuery) {
        this.SQLQ = SQLQuery;
        this.conn = connection;
        parent = aThis;
    }

    SqlInsertTask(XMLSAXParser aThis, Connection connection, document tempDoc) {
        this(aThis, connection, "INSERT INTO tbl_dblp_document ");
        this.doc = tempDoc;
    }

    public Object call() {
        int rtn = 0;
        try {
            if (doc != null){
                SQLQ += doc.getColAndVal();
            }
            //Sync to parent to prevent adding doc before getting the last one's ID
            Statement st = conn.createStatement();
            synchronized (parent) {
                st.executeUpdate(SQLQ);
                st = conn.createStatement();
                ResultSet lastIDQ = st.executeQuery("SELECT LAST_INSERT_ID()");
                lastIDQ.next();
                rtn = lastIDQ.getInt(1);
                st.close();
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return rtn;
    }
}
