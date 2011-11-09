
import java.sql.*;
import java.util.HashMap;
import java.util.concurrent.Callable;

public class SqlGetIDTask implements Callable {

    private Connection connection;
    private XMLSAXParser parent;
    private HashMap<String, Integer> map;
    private String name;
    private String table;
    private String column;

    public SqlGetIDTask(XMLSAXParser aThis, Connection connection, HashMap<String, Integer> map, String table, String column, String name) {
        this.connection = connection;
        this.parent = aThis;
        this.map = map;
        this.name = name;
        this.table = table;
        this.column = column;
    }

    public Object call() {
        Integer ret = 0;

        synchronized (parent) {
            if (map.containsKey(name)) {
                return map.get(name);
            }
            try {
                Statement st = connection.createStatement();
                ResultSet results = st.executeQuery("SELECT * FROM " + table + " WHERE " + column + " = '" + cleanSQL(name) + "'");
                if (results.next()) {
                    int id = results.getInt("id");
                    st.close();
                    map.put(name, id);
                    return id;
                } else {
                    int id;
                    st = connection.createStatement();
                    //Sync all uses of getLastID()
                    st.executeUpdate("INSERT INTO " + table + " (" + column + ") VALUE ('" + cleanSQL(name) + "')");
                    id = getLastID();
                    st.close();
                    map.put(name, id);
                    return id;
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }

        }
        return ret;

    }

    public static String cleanSQL(String arg) {
        String rtn = arg.replace("\\", "\\\\");
        return rtn.replace("'", "''");
    }

    private Integer getLastID() throws SQLException {
        //MUST SYNC all uses of getLastID()
        Statement st = connection.createStatement();
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
