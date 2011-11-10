
import java.util.ArrayList;
import java.util.concurrent.Future;

class document {
    private Future publisher_idFuture;
    private Future booktitle_idFuture;
    private Future editor_idFuture;
    private Future genre_idFuture;
    private ArrayList<Future> authorsIDsFuture;

    document(Integer genreID) {
        this();
        this.genre_id = genreID;
    }

    document(Future genreID) {
        this();
        this.genre_idFuture = genreID;
    }

    public ArrayList<Integer> getAuthorsIDs() {
        return authorsIDs;
    }

    public void addAuthorsIDs(Integer peopleID) {
        authorsIDs.add(peopleID);
    }
    private String title;
    private Integer start_page;
    private Integer end_page;
    private Integer year;
    private Integer volume;
    private Integer number;
    private String url;
    private String ee;
    private String cdrom;
    private String cite;
    private String crossref;
    private String isbn;
    private String series;
    private ArrayList<Integer> authorsIDs;
    private Integer editor_id;
    private Integer booktitle_id;
    private Integer genre_id;
    private Integer publisher_id;

    public document() {
        authorsIDs = new ArrayList<Integer>();
        authorsIDsFuture = new ArrayList<Future>();
    }
 
    String getColAndVal() {
        String val = "";
        String col = "";

        //Finalize Future Values
        try {
            if (genre_idFuture != null){
                setGenre_id((Integer) genre_idFuture.get());
            }
            if (editor_idFuture != null){
                setEditor_id((Integer) editor_idFuture.get());
            }
            if (publisher_idFuture != null){
                setPublisher_id((Integer) publisher_idFuture.get());
            }
            if (booktitle_idFuture != null){
                setBooktitle_id((Integer) booktitle_idFuture.get());
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        //Build insert values
        if (title != null) {
            col += "title,";
            val += "'" + cleanSQL(title) + "',";
        }
        if (start_page != null) {
            col += "start_page,";
            val += "'" + start_page + "',";
        }
        if (end_page != null) {
            col += "end_page,";
            val += "'" + end_page + "',";
        }
        if (year != null) {
            col += "year,";
            val += "'" + year + "',";
        }
        if (volume != null) {
            col += "volume,";
            val += "'" + volume + "',";
        }
        if (number != null) {
            col += "number,";
            val += "'" + number + "',";
        }
        if (url != null) {
            col += "url,";
            val += "'" + cleanSQL(url) + "',";
        }
        if (ee != null) {
            col += "ee,";
            val += "'" + cleanSQL(ee) + "',";
        }
        if (cdrom != null) {
            col += "cdrom,";
            val += "'" + cleanSQL(cdrom) + "',";
        }
        if (cite != null) {
            col += "cite,";
            val += "'" + cleanSQL(cite) + "',";
        }
        if (crossref != null) {
            col += "crossref,";
            val += "'" + cleanSQL(crossref) + "',";
        }
        if (isbn != null) {
            col += "isbn,";
            val += "'" + cleanSQL(isbn) + "',";
        }
        if (series != null) {
            col += "series,";
            val += "'" + series + "',";
        }
        if (genre_id != null) {
            col += "genre_id,";
            val += "'" + genre_id + "',";
        }
        if (editor_id != null) {
            col += "editor_id,";
            val += "'" + editor_id + "',";
        }
        if (booktitle_id != null) {
            col += "booktitle_id,";
            val += "'" + booktitle_id + "',";
        }
        if (publisher_id != null) {
            col += "publisher_id,";
            val += "'" + publisher_id + "',";
        }

        if (!val.isEmpty() && !col.isEmpty() ) {
            return "(" + col.substring(0, col.length() - 1) + ") VALUES (" + val.substring(0, val.length() - 1) + ")";
        }
        return "";
    }

    public Integer getBooktitle_id() {
        return booktitle_id;
    }

    public void setBooktitle_id(Integer booktitle_id) {
        if (this.booktitle_id == null) {
            this.booktitle_id = booktitle_id;
        } else {
            System.out.println("Multiple booktitle_id: " + booktitle_id);
        }
    }

    public String getCdrom() {
        return cdrom;
    }

    public void setCdrom(String cdrom) {
        this.cdrom = cdrom;
    }

    public String getCite() {
        return cite;
    }

    public void setCite(String cite) {
        if (this.cite == null) {
            this.cite = cite;
        } else {
            System.out.println("Multiple cite: " + cite);
        }
    }

    public String getCrossref() {
        return crossref;
    }

    public void setCrossref(String crossref) {
        if (this.crossref == null) {
            this.crossref = crossref;
        } else {
            System.out.println("Multiple crossref: " + crossref);
        }
    }

    public Integer getEditor_id() {
        return editor_id;
    }

    public void setEditor_id(Integer editor_id) {
        if (this.editor_id == null) {
            this.editor_id = editor_id;
        } else {
            System.out.println("Multiple editor_id: " + editor_id);
        }
    }

    public String getEe() {
        return ee;
    }

    public void setEe(String ee) {
        if (this.ee == null) {
            this.ee = ee;
        } else {
            System.out.println("Multiple ee: " + ee);
        }
    }

    public Integer getEnd_page() {
        return end_page;
    }

    public void setPages(String pages) {
        String page[] = pages.split("-");
        if (this.start_page == null ){
            if (page.length > 0 && page[0] != null && !page[0].isEmpty()){
                this.start_page = Integer.parseInt(page[0]);
            }
        } else if (page.length > 0 && page[0] != null && !page[0].isEmpty()) {
            System.out.println("Multiple start_page: " + page[0]);
        }
        if (this.end_page == null ){
            if (page.length > 1 && page[1] != null && !page[1].isEmpty()){
                this.end_page = Integer.parseInt(page[1]);
            }
        } else if (page.length > 1 && page[1] != null && !page[1].isEmpty()) {
            System.out.println("Multiple end_page: " + page[1]);
        }
    }

    public Integer getGenre_id() {
        return genre_id;
    }

    public void setGenre_id(Integer genre_id) {
        if (this.genre_id == null) {
            this.genre_id = genre_id;
        } else {
            System.out.println("Multiple genre_id: " + genre_id);
        }
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        if (this.isbn == null) {
            this.isbn = isbn;
        } else {
            System.out.println("Multiple isbn: " + isbn);
        }
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        if (this.number == null) {
            this.number = number;
        } else {
            System.out.println("Multiple number: " + number);
        }
    }

    public Integer getPublisher_id() {
        return publisher_id;
    }

    public void setPublisher_id(Integer publisher_id) {
        if (this.publisher_id == null) {
            this.publisher_id = publisher_id;
        } else {
            System.out.println("Multiple publisher_id: " + publisher_id);
        }
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        if (this.series == null) {
            this.series = series;
        } else {
            System.out.println("Multiple series: " + series);
        }
    }

    public Integer getStart_page() {
        return start_page;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if (this.title == null) {
            this.title = title;
        } else {
            System.out.println("Multiple title: " + title);
        }
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        if(this.url == null){
            this.url = url;
        } else {
            System.out.println("Multiple url: " + url);
        }
    }

    public Integer getVolume() {
        return volume;
    }

    public void setVolume(Integer volume) {
        if(this.volume == null){
            this.volume = volume;
        } else {
            System.out.println("Multiple volume: " + volume);
        }
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        if(this.year == null){
        this.year = year;
        } else {
            System.out.println("Multiple year: " + year);
        }
    }

    public static String cleanSQL(String arg) {
        String rtn = arg.replace("\\", "\\\\");
        return rtn.replace("'", "''");
    }

    Future setPublisher_idFuture(Future publisher_idFuture) {
        if (this.publisher_idFuture == null) {
            this.publisher_idFuture = publisher_idFuture;
            return null;
        }
        return publisher_idFuture;
    }

    Future setBooktitle_idFuture(Future booktitle_idFuture) {
        if (this.booktitle_idFuture == null) {
            this.booktitle_idFuture = booktitle_idFuture;
            return null;
        }
        return booktitle_idFuture;
    }

    Future setEditor_idFuture(Future editor_idFuture) {
        if (this.editor_idFuture == null) {
            this.editor_idFuture = editor_idFuture;
            return null;
        }
        return editor_idFuture;
    }

    void addAuthorsIDsFuture(Future peopleID) {
        authorsIDsFuture.add(peopleID);
    }

    ArrayList<Future> getAuthorsIDsFuture() {
        return authorsIDsFuture;
    }
}
