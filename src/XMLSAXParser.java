
import java.io.IOException;
import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import org.xml.sax.helpers.DefaultHandler;

public class XMLSAXParser extends DefaultHandler {

    List myBooks;
    HashMap<String, Integer> genres;
    HashMap<String, Integer> people;
    HashMap<String, Integer> booktitle;
    HashMap<String, Integer> publishers;
    private String tempVal;
    //to maintain context
    private document tempDoc;
    private Connection connection;

    public XMLSAXParser() {
        genres = new HashMap<String, Integer>();
        people = new HashMap<String, Integer>();
        booktitle = new HashMap<String, Integer>();
        publishers = new HashMap<String, Integer>();

        String password = "testpass";
        String username = "testuser";
        String server = "localhost";
        try {
            connection = DriverManager.getConnection("jdbc:mysql://" + server + ":3306/bookdb", username, password);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void main(String[] args) {
        XMLSAXParser spe = new XMLSAXParser();
        spe.runExample();
    }

    public void runExample() {
        parseDocument();
    }

    private void parseDocument() {

        //get a factory
        SAXParserFactory spf = SAXParserFactory.newInstance();
        try {

            //get a new instance of parser
            SAXParser sp = spf.newSAXParser();

            //parse the file and also register this class for call backs
            sp.parse("final-data.xml", this);//SMALL
//            sp.parse("dblp-data.xml", this);//LARGE

        } catch (SAXException se) {
            System.out.println(se.getMessage());
        } catch (ParserConfigurationException pce) {
            System.out.println(pce.getMessage());
        } catch (IOException ie) {
            System.out.println(ie.getMessage());
        }
    }

    //Event Handlers
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        //reset
        tempVal = "";
        if (isGenreElement(qName)) {
            getGenreID(qName);//TODO make book object
        }
    }

    public void characters(char[] ch, int start, int length) throws SAXException {
        String value = new String(ch, start, length);
        tempVal += (value.trim().isEmpty()?"":" "+value.trim());
    }

    public void endElement(String uri, String localName, String qName) throws SAXException {

        if (qName.equalsIgnoreCase("Employee")) {
            //add it to the list
        } else if (qName.equalsIgnoreCase("Author") || qName.equalsIgnoreCase("Editor")) {
            getPersonID(tempVal);
        } else if (qName.equalsIgnoreCase("Booktitle")) {
            getBooktitleID(tempVal);
        } else if (qName.equalsIgnoreCase("Publisher")) {
            getPublisherID(tempVal);
        }

    }

    private boolean isGenreElement(String qName) {
        if (qName.equalsIgnoreCase("article")
                || qName.equalsIgnoreCase("inproceedings")
                || qName.equalsIgnoreCase("proceedings")
                || qName.equalsIgnoreCase("book")
                || qName.equalsIgnoreCase("incollection")
                || qName.equalsIgnoreCase("phdthesis")
                || qName.equalsIgnoreCase("mastersthesis")
                || qName.equalsIgnoreCase("www")) {
            return true;
        }
        return false;
    }

    private Integer getGenreID(String genreName) {
        Integer ret = 0;
        if (genres.containsKey(genreName)) {
            return genres.get(genreName);
        }
        try {
            Statement st = connection.createStatement();
            ResultSet genreID = st.executeQuery("SELECT * FROM tbl_genres WHERE genre_name = '" + cleanSQL(genreName) + "'");
            if (genreID.next()) {
                int id = genreID.getInt("id");
                genres.put(genreName, id);
                return id;
            } else {
                st = connection.createStatement();
                st.executeUpdate("INSERT INTO tbl_genres (genre_name) VALUE ('" + cleanSQL(genreName) + "')");
                return getGenreID(genreName);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return ret;
    }

    private Integer getPersonID(String personName) {
        Integer ret = 0;
        if (people.containsKey(personName)) {
            return people.get(personName);
        }
        try {
            Statement st = connection.createStatement();
            ResultSet personID = st.executeQuery("SELECT * FROM tbl_people WHERE name = '" + cleanSQL(personName) + "'");
            if (personID.next()) {
                int id = personID.getInt("id");
                people.put(personName, id);
                return id;
            } else {
                st = connection.createStatement();
                st.executeUpdate("INSERT INTO tbl_people (name) VALUE ('" + cleanSQL(personName) + "')");
                return getPersonID(personName);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return ret;
    }

    private Integer getBooktitleID(String booktitleName) {
        Integer ret = 0;
        if (booktitle.containsKey(booktitleName)) {
            return booktitle.get(booktitleName);
        }
        try {
            Statement st = connection.createStatement();
            ResultSet booktitleID = st.executeQuery("SELECT * FROM tbl_booktitle WHERE title = '" + cleanSQL(booktitleName) + "'");
            if (booktitleID.next()) {
                int id = booktitleID.getInt("id");
                booktitle.put(booktitleName, id);
                return id;
            } else {
                st = connection.createStatement();
                st.executeUpdate("INSERT INTO tbl_booktitle (title) VALUE ('" + cleanSQL(booktitleName) + "')");
                return getBooktitleID(booktitleName);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return ret;
    }

    private Integer getPublisherID(String publisherName) {
        Integer ret = 0;
        if (publishers.containsKey(publisherName)) {
            return publishers.get(publisherName);
        }
        try {
            Statement st = connection.createStatement();
            ResultSet publisherID = st.executeQuery("SELECT * FROM tbl_publisher WHERE publisher_name = '" + cleanSQL(publisherName) + "'");
            if (publisherID.next()) {
                int id = publisherID.getInt("id");
                publishers.put(publisherName, id);
                return id;
            } else {
                st = connection.createStatement();
                st.executeUpdate("INSERT INTO tbl_publisher (publisher_name) VALUE ('" + cleanSQL(publisherName) + "')");
                return getBooktitleID(publisherName);
            }
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
