package www;

/**
 * Created by www on 2016/3/5.
 */
class Conference {
    private int conf_id;
    private String conf_key;
    private String name;
    private String detail;
    private int year;
    private String isbn;
    private String publisher;

    public Conference(int conf_id, String conf_key, String name, String detail, int year, String isbn, String publisher) {
        this.conf_id = conf_id;
        this.conf_key = conf_key;
        this.name = name;
        this.detail = detail;
        this.year = year;
        this.isbn = isbn;
        this.publisher = publisher;
    }

    public void showConference() {
        System.out.println(" conf_key: " + conf_key + " name: " + name + " detail: " + detail + " year: " + year
                + " isbn: " + isbn + " publisher: " + publisher);
    }

    public String toString() {
        return "conference";
    }

    public String getKey() {
        return conf_key;
    }

    public int getId() {
        return conf_id;
    }
}