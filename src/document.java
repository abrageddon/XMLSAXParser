
import java.util.ArrayList;

class document {

	private Integer id;
	private String title;

    public Integer getBooktitleID() {
        return booktitleID;
    }

    public void setBooktitleID(Integer booktitleID) {
        this.booktitleID = booktitleID;
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
        this.cite = cite;
    }

    public String getCrossref() {
        return crossref;
    }

    public void setCrossref(String crossref) {
        this.crossref = crossref;
    }

    public String getEe() {
        return ee;
    }

    public void setEe(String ee) {
        this.ee = ee;
    }

    public Integer getEnd_page() {
        return end_page;
    }

    public void setEnd_page(Integer end_page) {
        this.end_page = end_page;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public ArrayList<Integer> getPeopleIDs() {
        return peopleIDs;
    }

    public void setPeopleIDs(ArrayList<Integer> peopleIDs) {
        this.peopleIDs = peopleIDs;
    }

    public Integer getPublisherID() {
        return publisherID;
    }

    public void setPublisherID(Integer publisherID) {
        this.publisherID = publisherID;
    }

    public int getSeries() {
        return series;
    }

    public void setSeries(int series) {
        this.series = series;
    }

    public Integer getStart_page() {
        return start_page;
    }

    public void setStart_page(Integer start_page) {
        this.start_page = start_page;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

	private Integer start_page;
	private Integer end_page;
        
	private int year;
	private int volume;
	private int number;

	private String url;
	private String ee;
	private String cdrom;
	private String cite;
	private String crossref;
	private String isbn;
	private int series;

	private ArrayList<Integer> peopleIDs;
	private Integer booktitleID;
	private Integer publisherID;


	public document(){

	}

	public document(String mdate, String key) {

	}

}
