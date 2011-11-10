
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
        this.name = name;
        this.table = table;
        this.column = column;
    }

    public Object call() {
        Integer ret = 0;

        if (map.containsKey(name)) {
            return map.get(name);
        }

        try {

            Statement st = connection.createStatement();
            int id = 0;

            ResultSet results = st.executeQuery("SELECT * FROM " + table + " WHERE " + column + " = '" + cleanSQL(name) + "'");
            if (results.next()) {
                System.out.println(name);
                id = results.getInt("id");
                st.close();
                map.put(name, id);
                return id;
            }
            st = connection.createStatement();

            synchronized (map) {
                    results = st.executeQuery("SELECT * FROM " + table + " WHERE " + column + " = '" + cleanSQL(name) + "'");
                    if (results.next()) {
                        id = results.getInt("id");
                        st.close();
                        map.put(name, id);
                        return id;
                    }
                    st = connection.createStatement();

                //Sync all uses of getLastID()
                synchronized (connection) {
                    st.executeUpdate("INSERT INTO " + table + " (" + column + ") VALUE ('" + cleanSQL(name) + "')");
                    st = connection.createStatement();
                    ResultSet lastIDQ = st.executeQuery("SELECT LAST_INSERT_ID()");
                    lastIDQ.next();
                    id = lastIDQ.getInt(1);
                }
                st.close();

                map.put(name, id);
            }
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
