
import java.sql.*;
import java.util.HashMap;
import java.util.concurrent.Callable;

public class SqlGetIDTask implements Callable {

    private Connection connection;
    private HashMap<String, Integer> map;
    private String name;
    private String table;
    private String column;

    public SqlGetIDTask(Connection connection, HashMap<String, Integer> map, String table, String column, String name) {
        this.connection = connection;
        this.map = map;
        this.name = cleanSQL(name.replaceAll("\n", ""));
        this.table = table;
        this.column = column;
    }

    public Object call() {
        Integer ret = 0;

        try {
            if (XMLSAXParser.useHashMap) {
                if (map.containsKey(name)) {
                    return map.get(name);
                }
            } else {
                Statement st = connection.createStatement();
                ResultSet rs = st.executeQuery("SELECT * FROM " + table + " WHERE " + column + " = '" + name + "'");
                if (rs.next()) {
                    int id = rs.getInt("id");
                    st.close();
                    return id;
                }

            }

            Statement st = connection.createStatement();
            int id = 0;

            synchronized (map) {
                synchronized (connection) {

                    if (XMLSAXParser.useHashMap) {
                        if (map.containsKey(name)) {
                            return map.get(name);
                        }
                    } else {
                        ResultSet rs = st.executeQuery("SELECT * FROM " + table + " WHERE " + column + " = '" + name + "'");
                        if (rs.next()) {
                            id = rs.getInt("id");
                            st.close();
                            return id;
                        }

                    }

                    //Sync all uses of getLastID()

                    //Made column unique so just throw it in there
                    st.executeUpdate("INSERT IGNORE INTO " + table + " (" + column + ") VALUE ('" + name + "')");

                    st = connection.createStatement();
                    ResultSet lastIDQ = st.executeQuery("SELECT LAST_INSERT_ID()");
                    lastIDQ.next();
                    id = lastIDQ.getInt(1);


                    if (XMLSAXParser.useHashMap) {
                        map.put(name, id);
                    }
                }
            }

            st.close();
            return id;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }


        return ret;

    }

    public static String cleanSQL(String arg) {
        String rtn = arg.replace("\\", "\\\\");
        return rtn.replace("'", "''");
    }
}
