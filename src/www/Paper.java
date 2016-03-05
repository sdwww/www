package www;

import java.util.ArrayList;
import java.util.List;

public class Paper {
    List<Paper> outgoing;
    private int paper_id;
    private String paper_key;
    private String title;
    private int year;
    private String conference;
    private String pages;
    private int conf_id;
    private String conf_key;

    public Paper(int paper_id, String title, int year, String conference, String paper_key, String pages, String conf_key) {
        outgoing = new ArrayList<Paper>();
        this.paper_id = paper_id;
        this.title = title;
        this.year = year;
        this.conference = conference;
        this.paper_key = paper_key;
        this.pages = pages;
        this.conf_key = conf_key;
    }

    public void showPaper() {
        System.out.println("title: " + title + "year" + year + " conference: " + conference + " paper_key: " + paper_key
                + "pages:" + pages + "con_key" + conf_key);
    }

    public String toString() {
        return "paper";
    }

    public String getKey() {
        return paper_key;
    }

    public int getId() {
        return paper_id;
    }

    public String getTitle() {
        return title;
    }

    public int getYear() {
        return year;
    }

    public String getConference() {
        return conference;
    }

    public String getPages() {
        return pages;
    }

    public String getConf_key() {
        return conf_key;
    }
}