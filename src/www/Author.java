package www;

/**
 * Created by www on 2016/3/5.
 */
class Author {
    private String author;

    public Author(String author) {
        this.author = author;
    }

    public void showAuthor() {
        System.out.println("author: " + author);
    }

    public String toString() {
        return "author";
    }

    public String getKey() {
        return author;
    }
}