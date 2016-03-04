package www;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import db.DBConnection;

import javax.print.attribute.standard.PagesPerMinute;

public class jpcproject {
    private static Connection conn;
    private  Node[] top1000paper = new Node[1000];

    public static void main(String args[]) throws SQLException {
        int paperCount = 0;
        int confCount = 0;
        int authorCount = 0;
//        int citationCount = 0;
//        int au_Conf_PaCount = 0;
        int index = 0;
        int allCount = 0;
        ResultSet result = null;
        conn = DBConnection.getConn();

        // 获取paper对象
        PreparedStatement selectPaper = conn.prepareStatement("select * from paper");
        result = selectPaper.executeQuery();
        while (result.next()) {
            paperCount++;
        }
        Paper[] paper = new Paper[paperCount];
        result = selectPaper.executeQuery();
        for (int i = 0; result.next(); i++) {
            paper[i] = new Paper(result.getInt("paper_id"),result.getString("title"), result.getInt("year"), result.getString("conference"),
                    result.getString("paper_key"), result.getString("pages"), result.getString("conf_key"));
        }

//        // 获取会议对象
//        PreparedStatement selectConf = conn.prepareStatement("select * from conference");
//        result = selectConf.executeQuery();
//        while (result.next()) {
//            confCount++;
//        }
//        Conference[] conf = new Conference[confCount];
//        result = selectConf.executeQuery();
//        for (int i = 0; result.next(); i++) {
//            conf[i] = new Conference(result.getInt("conf_id"),result.getString("conf_key"), result.getString("name"), result.getString("detail"),
//                    result.getInt("year"), result.getString("isbn"), result.getString("publisher"));
//        }
//
//        // 获取作者信息
//        PreparedStatement selectAuthor = conn.prepareStatement("select * from author");
//        result = selectAuthor.executeQuery();
//        while (result.next()) {
//            authorCount++;
//        }
//        Author[] author = new Author[authorCount];
//        result = selectAuthor.executeQuery();
//        for (int i = 0; result.next(); i++) {
//            author[i] = new Author(result.getString("author"));
//        }

//        //获取论文-论文二元组 3.1
//        PreparedStatement selectCitation = conn.prepareStatement("select * from citation");
//        int[][] citation = new int[paperCount][paperCount];
//        int[][] citation_2step = new int[paperCount][paperCount];
//        int[][] citation_all = new int[paperCount][paperCount];
//        for (int i = 0; i < paperCount; i++) {
//            for (int j = 0; j < paperCount; j++) {
//                citation[i][j] = 0;
//                citation_2step[i][j]=0;
//                citation_all[i][j]=0;
//            }
//        }
//        result = selectCitation.executeQuery();
//        while (result.next()) {
//            citation[result.getInt("paper_cite_id") - 1][result.getInt("paper_cited_id") - 1]++;
//            citation[result.getInt("paper_cited_id") - 1][result.getInt("paper_cite_id") - 1]++;
//            //System.out.println(citation[result.getInt("paper_cited_id")-1][result.getInt("paper_cite_id")-1]);
//        }
//        for (int i = 0; i < paperCount; i++) {
//            for (int j = 0; j < paperCount; j++) {
//                int temp = 0;
//                for (int k = 0; k < paperCount; k++) {
//                    temp += citation_2step[i][k] * citation_2step[j][k];
//                }
//                if (i != j)
//                    citation_2step[i][j] = temp;
//            }
//        }
//        for (int i = 0; i < paperCount; i++) {
//            for (int j = 0; j < paperCount; j++) {
//                citation_all[i][j] = citation[i][j] * 3 + citation_2step[i][j];
//                System.out.println(citation_all[i][j]);
//            }
//        }

//        // 作者-论文关系创建 3.1
//        PreparedStatement selectAu_paper = conn.prepareStatement("select * from author_paper");
//        int[][] author_paper = new int[authorCount][paperCount];
//        for (int i = 0; i < authorCount; i++) {
//            for (int j = 0; j < paperCount; j++) {
//                author_paper[i][j] = 0;
//            }
//        }
//        result = selectAu_paper.executeQuery();
//        while (result.next()) {
//            author_paper[result.getInt("author_id") - 1][result.getInt("paper_id") - 1]++;
//        }
//        int[][] coauthor = new int[authorCount][authorCount];
//        for (int i = 0; i < authorCount; i++) {  //i表示数组coauthor的每一行
//            for (int j = 0; j < authorCount; j++) {  //j表示数组coauthor的每一列
//                int temp = 0;
//                for (int k = 0; k < paperCount; k++) {//k表示数组a的列号和数组b的行号
//                    temp += author_paper[i][k] * author_paper[j][k];
//                }
//                coauthor[i][j] = temp;
//            }
//        }

//        //获取作者-会议-论文三元组
//        PreparedStatement selectAu_Conf_Pa = conn.prepareStatement("select paper.paper_key,conf_key,author from " +
//                "author_paper,paper where author_paper.paper_key=paper.paper_key and conf_key is not null;");
//        result = selectAu_Conf_Pa.executeQuery();
//        while (result.next()) {
//            au_Conf_PaCount++;
//        }
//        System.out.print((long)authorCount*confCount*paperCount);
//        boolean[][][] au_Conf_Pa = new boolean[authorCount][confCount][paperCount];
//        result = selectAu_Conf_Pa.executeQuery();
//        for (int i = 0; i < authorCount; i++) {
//            for (int j = 0; j < confCount; j++) {
//                for (int k = 0; k < paperCount; k++) {
//                    au_Conf_Pa[i][j][k] = false;
//                }
//            }
//        }
//        for (int m = 0; result.next(); m++) {
//            for (int i = 0; i < authorCount; i++) {
//                for (int j = 0; j < confCount; j++) {
//                    for (int k = 0; k < paperCount; k++) {
//                        if (author[i].getKey().equals(result.getString("author")) && conf[i].getKey()
//                                .equals(result.getString("conf_key")) && paper[i].getKey().equals(result.getString("paper_key")))
//                            au_Conf_Pa[i][j][k] = true;
//                    }
//                }
//            }
//        }
//        for (int i = 0; i < authorCount; i++) {
//            for (int j = 0; j < confCount; j++) {
//                for (int k = 0; k < paperCount; k++) {
//                    System.out.print(au_Conf_Pa[i][j][k]);
//                }
//            }
//        }

        // 创建node
        //allCount = paperCount + authorCount + confCount;
        Node[] node = new Node[paperCount];
        for (int i = 0; i < paperCount; i++) {
            node[index] = new Node(index, paper[i]);
            index++;
        }
//        for (int i = 0; i < confCount; i++) {
//            node[index] = new Node(index, conf[i]);
//            index++;
//        }
//        for (int i = 0; i < authorCount; i++) {
//            node[index] = new Node(index, author[i]);
//            index++;
//        }
//
//        // 作者之间合作关系建立
//        PreparedStatement selectCoauthor = conn.prepareStatement("select * from coauthor");
//        result = selectCoauthor.executeQuery();
//        while (result.next()) {
//            for (int i = paperCount + confCount; i < allCount; i++) {
//                if (result.getString("author1").equals(node[i].getKey())) {
//                    for (int j = paperCount + confCount; j < allCount; j++) {
//                        if (result.getString("author2").equals(node[j].getKey())) {
//                            node[i].outgoing.add(node[j]);
//                            node[j].outgoing.add(node[i]);
//                            break;
//                        }
//                    }
//                    break;
//                }
//
//            }
//        }
//
        // 论文之间索引关系建立
        PreparedStatement selectCitation = conn.prepareStatement("select * from citation");
        result = selectCitation.executeQuery();
        while (result.next()) {
            node[result.getInt("paper_cite_id") - 1].outgoing.add(node[result.getInt("paper_cited_id") - 1]);
            node[result.getInt("paper_cited_id") - 1].outgoing.add(node[result.getInt("paper_cite_id") - 1]);
        }
        jpcproject search = new jpcproject();
        System.out.println(node.length);
        search.widthSearch(node[0], node);

        for(int i=0;i<1000;i++){

        }
    }

    //广度优先搜索实现
    void widthSearch(Node start, Node[] paper) {
        // 记录所有访问过的元素
        Set<Node> visited = new HashSet<Node>();
        // 用队列存放所有依次要访问元素
        Queue<Node> q = new LinkedList<Node>();
        // 把当前的元素加入到队列尾
        q.offer(start);
        int top1000Count = 0;
        while (!q.isEmpty()) {
            Node cur = q.poll();
            // 被访问过了，就不访问，防止死循环
            if (!visited.contains(cur) && visited.size() < 1000) {
                visited.add(cur);
                top1000paper[top1000Count] = paper[cur.getIndex()];
                top1000Count++;
                for (int i = 0; i < cur.outgoing.size(); i++) {
                    // 把它的下一层，加入到队列中
                    if (visited.size() < 1000)
                        q.offer(cur.outgoing.get(i));
                }
            }
        }
    }
//
//        // 作者-论文关系创建
//        PreparedStatement selectAu_paper = conn.prepareStatement("select * from author_paper");
//        result = selectAu_paper.executeQuery();
//        while (result.next()) {
//            for (int i = paperCount + confCount; i < allCount; i++) {
//                if (result.getString("author").equals(node[i].getKey())) {
//                    for (int j = 0; j < paperCount; j++) {
//                        if (result.getString("paper_key").equals(node[j].getKey())) {
//                            node[i].outgoing.add(node[j]);
//                            node[j].outgoing.add(node[i]);
//                            break;
//                        }
//                    }
//                    break;
//                }
//            }
//        }
//
//        // 作者-会议之间关系建立
//        PreparedStatement selectAu_conf = conn.prepareStatement("select * from author_conf");
//        result = selectAu_conf.executeQuery();
//        while (result.next()) {
//            for (int i = paperCount + confCount; i < allCount; i++) {
//                if (result.getString("author").equals(node[i].getKey())) {
//                    for (int j = paperCount; j < paperCount + confCount; j++) {
//                        if (result.getString("conf_key").equals(node[j].getKey())) {
//                            node[i].outgoing.add(node[j]);
//                            node[j].outgoing.add(node[i]);
//                            break;
//                        }
//                    }
//                    break;
//                }
//            }
//        }
//
//        // 论文-会议关系建立
//        PreparedStatement selectPa_conf = conn.prepareStatement("select paper_key,conf_key from paper");
//        result = selectPa_conf.executeQuery();
//        while (result.next()) {
//            for (int i = 0; i < paperCount; i++) {
//                if (result.getString("paper_key").equals(node[i].getKey())) {
//                    for (int j = paperCount; j < paperCount + confCount; j++) {
//                        if (result.getString("conf_key").equals(node[j].getKey())) {
//                            node[i].outgoing.add(node[j]);
//                            node[j].outgoing.add(node[i]);
//                            break;
//                        }
//                    }
//                    break;
//                }
//            }
//        }

// // 获取paper_key信息和合作关系
// int pCount = 0;
// PreparedStatement selectP = conn.prepareStatement("select paper_key
// from author_paper group by paper_key");
// result = selectP.executeQuery();
// while (result.next()) {
// pCount++;
// }
// String[][] au_p = new String[pCount][27];
// for (int i = 0; i < pCount; i++) {
// for (int j = 0; j < 27; j++) {
// au_p[i][j] = null;
// }
// }
// result = selectP.executeQuery();
// for (int i = 0; result.next(); i++) {
// au_p[i][0] = result.getString("paper_key");
// }
// for (int i = 0; i < pCount; i++) {
// PreparedStatement selectAu = conn.prepareStatement("select * from
// author_paper where paper_key = ?");
// selectAu.setString(1, au_p[i][0]);
// result = selectAu.executeQuery();
// int j = 1;
// while (result.next()) {
// au_p[i][j] = result.getString("author");
// j++;
// }
// }
// int[] notNull = new int[pCount];
// for (int i = 0; i < pCount; i++) {
// notNull[i] = 0;
// for (int j = 1; j < 27; j++) {
// if (au_p[i][j] != null) {
// notNull[i]++;
// } else
// break;
// }
// }
// String[][] coauthor = new String[pCount * 10][2];
// for (int i = 0; i < pCount; i++) {
// for (int j = 1; j < notNull[i]; j++) {
// for (int k = 1; k < j; k++) {
// coauthor[i][0] = au_p[i][j];
// coauthor[i][1] = au_p[i][k];
// }
// }
// }
// conn.setAutoCommit(false);
// try {
// PreparedStatement coauthor_exe = conn.prepareStatement
// ("insert into coauthor(author1,author2) values (?,?)");
// for (int i = 0; i < pCount; i++) {
// coauthor_exe.setString(1, coauthor[i][0]);
// coauthor_exe.setString(2, coauthor[i][1]);
// coauthor_exe.addBatch();
// }
// coauthor_exe.executeBatch();
// conn.commit();
// coauthor_exe.close();
// conn.close();
// } catch (SQLException e) {
// e.printStackTrace();
// }

// 构造本对象
//GraphTraverse search = new GraphTraverse();
// search.creatIndex(allCount, node);
// System.out.println(paperCount+" "+confCount+" "+authorCount);
// 广度遍历
// System.out.println("广度遍历如下：");
// search.widthSearch(node[0]);
// System.out.println(allCount);

// 筛选出1999年发表的论文，作者，会议不加限制
//        ArrayList<Node> paperList = new ArrayList<Node>();
//        ArrayList<Node> confList = new ArrayList<Node>();
//        ArrayList<Node> authorList = new ArrayList<Node>();
//        for (int i = 0; i < paperCount; i++) {
//            Paper paper_1999 = (Paper) node[i].getObject();
//            if (paper_1999.getYear() == 1999) {
//                paperList.add(node[i]);
//            }
//        }
//        for (int i = paperCount; i < paperCount + confCount; i++) {
//
//            confList.add(node[i]);
//        }
//        for (int i = paperCount + confCount; i < allCount; i++) {
//            authorList.add(node[i]);
//        }
//    }

    // 建立shared_index
//    public void creatIndex(int allCount, Node[] node) {
//        int[][] shared_index = new int[allCount][9];
//        for (int i = 0; i < allCount; i++) {
//            for (int j = 0; j < 9; j++) {
//                shared_index[i][j] = 0;
//            }
//        }
//        for (int i = 0; i < allCount; i++) {
//            ArrayList<Node> paper = new ArrayList<Node>();
//            ArrayList<Node> conf = new ArrayList<Node>();
//            ArrayList<Node> author = new ArrayList<Node>();
//            for (int j = 0; j < node[i].outgoing.size(); j++) {
//                if (node[i].outgoing.get(j).getType().equals("Paper")) {
//                    paper.add(node[i].outgoing.get(j));
//                } else if (node[i].outgoing.get(j).getType().equals("Conference")) {
//                    conf.add(node[i].outgoing.get(j));
//                } else {
//                    author.add(node[i].outgoing.get(j));
//                }
//            }
//            ArrayList<Node> paper_p = new ArrayList<Node>();
//            ArrayList<Node> paper_c = new ArrayList<Node>();
//            ArrayList<Node> paper_a = new ArrayList<Node>();
//            ArrayList<Node> conf_p = new ArrayList<Node>();
//            ArrayList<Node> conf_c = new ArrayList<Node>();
//            ArrayList<Node> conf_a = new ArrayList<Node>();
//            ArrayList<Node> author_p = new ArrayList<Node>();
//            ArrayList<Node> author_c = new ArrayList<Node>();
//            ArrayList<Node> author_a = new ArrayList<Node>();
//            for (int j = 0; j < paper.size(); j++) {
//                for (int k = 0; k < paper.get(j).outgoing.size(); k++) {
//                    if (paper.get(j).outgoing.get(k).getType().equals("Paper")
//                            && !paper_p.contains(paper.get(j).outgoing.get(k))) {
//                        paper_p.add(paper.get(j).outgoing.get(k));
//                    } else if (paper.get(j).outgoing.get(k).getType().equals("Conference")
//                            && !paper_c.contains(paper.get(j).outgoing.get(k))) {
//                        paper_c.add(paper.get(j).outgoing.get(k));
//                    } else if (paper.get(j).outgoing.get(k).getType().equals("Author")
//                            && !paper_a.contains(paper.get(j).outgoing.get(k))) {
//                        paper_a.add(paper.get(j).outgoing.get(k));
//                    }
//                }
//            }
//            for (int j = 0; j < conf.size(); j++) {
//                for (int k = 0; k < conf.get(j).outgoing.size(); k++) {
//                    if (conf.get(j).outgoing.get(k).getType().equals("Paper")
//                            && !conf_p.contains(conf.get(j).outgoing.get(k))) {
//                        conf_p.add(conf.get(j).outgoing.get(k));
//                    } else if (conf.get(j).outgoing.get(k).getType().equals("Conference")
//                            && !conf_c.contains(conf.get(j).outgoing.get(k))) {
//                        paper_c.add(conf.get(j).outgoing.get(k));
//                    } else if (conf.get(j).outgoing.get(k).getType().equals("Author")
//                            && !conf_a.contains(conf.get(j).outgoing.get(k))) {
//                        conf_a.add(conf.get(j).outgoing.get(k));
//                    }
//                }
//            }
//            for (int j = 0; j < author.size(); j++) {
//                for (int k = 0; k < author.get(j).outgoing.size(); k++) {
//                    if (author.get(j).outgoing.get(k).getType().equals("Paper")
//                            && !author_p.contains(author.get(j).outgoing.get(k))) {
//                        author_p.add(author.get(j).outgoing.get(k));
//                    } else if (author.get(j).outgoing.get(k).getType().equals("Conference")
//                            && !author_c.contains(author.get(j).outgoing.get(k))) {
//                        author_c.add(author.get(j).outgoing.get(k));
//                    } else if (author.get(j).outgoing.get(k).getType().equals("Author")
//                            && !author_a.contains(author.get(j).outgoing.get(k))) {
//                        author_a.add(author.get(j).outgoing.get(k));
//                    }
//                }
//            }
//
//            for (int j = 0; j < paper.size(); j++) {
//                // System.out.print(paper.get(j).getKey()+" ");
//                if (paper_p.contains(paper.get(j))) {
//                    shared_index[i][0]++;
//                }
//                if (conf_p.contains(paper.get(j))) {
//                    shared_index[i][3]++;
//                }
//                if (author_p.contains(paper.get(j))) {
//                    shared_index[i][6]++;
//                }
//            }
//            for (int j = 0; j < conf.size(); j++) {
//                // System.out.print(conf.get(j).getKey()+" ");
//                if (paper_c.contains(conf.get(j))) {
//                    shared_index[i][1]++;
//                }
//                if (conf_c.contains(conf.get(j))) {
//                    shared_index[i][4]++;
//                }
//                if (author_c.contains(conf.get(j))) {
//                    shared_index[i][7]++;
//                }
//            }
//            for (int j = 0; j < author.size(); j++) {
//                // System.out.print(author.get(j).getKey()+" ");
//                if (paper_a.contains(author.get(j))) {
//                    shared_index[i][2]++;
//                }
//                if (conf_a.contains(author.get(j))) {
//                    shared_index[i][5]++;
//                }
//                if (author_a.contains(author.get(j))) {
//                    shared_index[i][8]++;
//                }
//            }
//        }
//        // for (int i = 0; i < allCount; i++) {
//        // for (int j = 0; j < 9; j++) {
//        // System.out.print(shared_index[i][j] + " ");
//        // }
//        // System.out.println();
//        // }
//
//        try {
//            conn.setAutoCommit(false);
//            PreparedStatement creatIndex_exe = conn
//                    .prepareStatement("insert into shared_index values (?,?,?,?,?,?,?,?,?,?,?)");
//            for (int i = 0; i < allCount; i++) {
//                creatIndex_exe.setInt(1, i + 1);
//                creatIndex_exe.setInt(2, shared_index[i][0]);
//                creatIndex_exe.setInt(3, shared_index[i][1]);
//                creatIndex_exe.setInt(4, shared_index[i][2]);
//                creatIndex_exe.setInt(5, shared_index[i][3]);
//                creatIndex_exe.setInt(6, shared_index[i][4]);
//                creatIndex_exe.setInt(7, shared_index[i][5]);
//                creatIndex_exe.setInt(8, shared_index[i][6]);
//                creatIndex_exe.setInt(9, shared_index[i][7]);
//                creatIndex_exe.setInt(10, shared_index[i][8]);
//                creatIndex_exe.setString(11, node[i].getKey());
//                creatIndex_exe.addBatch();
//            }
//            creatIndex_exe.executeBatch();
//            conn.commit();
//            creatIndex_exe.close();
//            conn.close();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    int[][] userIndex(int allCount) {
//        int[][] shared_index = new int[allCount][9];
//        for (int i = 0; i < allCount; i++) {
//            for (int j = 0; j < 9; j++) {
//                shared_index[i][j] = 0;
//            }
//        }
////		PreparedStatement selectAuthor = conn.prepareStatement("select * from author_paper group by author");
////		result = selectAuthor.executeQuery();
////		Author[] author = new Author[authorCount];
////		result = selectAuthor.executeQuery();
////		for (int i = 0; result.next(); i++) {
////			author[i] = new Author(result.getString("author"));
////		}
//        return shared_index;
//    }
//
}


class Node {
    List<Node> outgoing;
    private int index;
    Object object;

    public Node(int index, Object object) {
        outgoing = new ArrayList<Node>();
        this.index = index;
        this.object = object;
    }

    public String getType() {
        if (object.toString().equals("paper")) {
            return "Paper";
        } else if (object.toString().equals("author")) {
            return "Author";
        } else {
            return "Conference";
        }
    }

    public String getKey() {
        if (object.toString().equals("paper")) {
            Paper paper = (Paper) object;
            return paper.getKey();
        } else if (object.toString().equals("author")) {
            Author author = (Author) object;
            return author.getKey();
        } else {
            Conference conf = (Conference) object;
            return conf.getKey();
        }
    }

    public int getId() {
        if (object.toString().equals("paper")) {
            Paper paper = (Paper) object;
            return paper.getId();
        } else {
            return -1;
        }
    }

    public int getIndex() {
        return index;
    }

    public Object getObject() {
        return object;
    }
}

class Paper {
    private int paper_id;
    private String paper_key;
    private String title;
    private int year;
    private String conference;
    private String pages;
    private int conf_id;
    private String conf_key;

    public Paper(int paper_id,String title, int year, String conference, String paper_key, String pages, String conf_key) {
        this.paper_id=paper_id;
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

    public int getYear() {
        return year;
    }
}

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

