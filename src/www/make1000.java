package www;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import db.DBConnection;

public class make1000 {
    private static Connection conn;

    public static void main(String args[]) throws SQLException {
        int paperCount = 0;
        int confCount = 0;
        int authorCount = 0;
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
            paper[i] = new Paper(result.getInt("paper_id"), result.getString("title"), result.getInt("year"), result.getString("conference"),
                    result.getString("paper_key"), result.getString("pages"), result.getString("conf_key"));
        }

        // 获取会议对象
        PreparedStatement selectConf = conn.prepareStatement("select * from conference");
        result = selectConf.executeQuery();
        while (result.next()) {
            confCount++;
        }
        Conference[] conf = new Conference[confCount];
        result = selectConf.executeQuery();
        for (int i = 0; result.next(); i++) {
            conf[i] = new Conference(result.getInt("conf_id"), result.getString("conf_key"), result.getString("name"), result.getString("detail"),
                    result.getInt("year"), result.getString("isbn"), result.getString("publisher"));
        }

        // 获取作者信息
        PreparedStatement selectAuthor = conn.prepareStatement("select * from author");
        result = selectAuthor.executeQuery();
        while (result.next()) {
            authorCount++;
        }
        Author[] author = new Author[authorCount];
        result = selectAuthor.executeQuery();
        for (int i = 0; result.next(); i++) {
            author[i] = new Author(result.getString("author"));
        }

        //获取论文-论文二元组 3.1
        PreparedStatement selectCitation = conn.prepareStatement("select * from citation");
        int[][] citation = new int[paperCount][paperCount];
        int[][] citation_2step = new int[paperCount][paperCount];
        int[][] citation_all = new int[paperCount][paperCount];
        for (int i = 0; i < paperCount; i++) {
            for (int j = 0; j < paperCount; j++) {
                citation[i][j] = 0;
                citation_2step[i][j] = 0;
                citation_all[i][j] = 0;
            }
        }
        result = selectCitation.executeQuery();
        while (result.next()) {
            citation[result.getInt("paper_cite_id") - 1][result.getInt("paper_cited_id") - 1]++;
            citation[result.getInt("paper_cited_id") - 1][result.getInt("paper_cite_id") - 1]++;
            //System.out.println(citation[result.getInt("paper_cited_id")-1][result.getInt("paper_cite_id")-1]);
        }
        for (int i = 0; i < paperCount; i++) {
            for (int j = 0; j < paperCount; j++) {
                int temp = 0;
                for (int k = 0; k < paperCount; k++) {
                    temp += citation_2step[i][k] * citation_2step[j][k];
                }
                if (i != j)
                    citation_2step[i][j] = temp;
            }
        }
        for (int i = 0; i < paperCount; i++) {
            for (int j = 0; j < paperCount; j++) {
                citation_all[i][j] = citation[i][j] * 3 + citation_2step[i][j];
            }
        }

        // 作者-论文关系创建 3.1
        PreparedStatement selectAu_paper = conn.prepareStatement("select * from author_paper");
        int[][] author_paper = new int[authorCount][paperCount];
        for (int i = 0; i < authorCount; i++) {
            for (int j = 0; j < paperCount; j++) {
                author_paper[i][j] = 0;
            }
        }
        result = selectAu_paper.executeQuery();
        while (result.next()) {
            author_paper[result.getInt("author_id") - 1][result.getInt("paper_id") - 1]++;
        }
        int[][] coauthor = new int[authorCount][authorCount];
        for (int i = 0; i < authorCount; i++) {  //i表示数组coauthor的每一行
            for (int j = 0; j < authorCount; j++) {  //j表示数组coauthor的每一列
                int temp = 0;
                for (int k = 0; k < paperCount; k++) {//k表示数组a的列号和数组b的行号
                    temp += author_paper[i][k] * author_paper[j][k];
                }
                coauthor[i][j] = temp;
            }
        }

        //获取作者-会议-论文三元组
        PreparedStatement selectAu_Conf_Pa = conn.prepareStatement("select paper.paper_id,conf_id,author_id from " +
                "author_paper,paper,conference where author_paper.paper_key=paper.paper_key and paper.conf_key=conference.conf_key;");
        boolean[][][] au_Conf_Pa = new boolean[authorCount][confCount][paperCount];
        result = selectAu_Conf_Pa.executeQuery();
        for (int i = 0; i < authorCount; i++) {
            for (int j = 0; j < confCount; j++) {
                for (int k = 0; k < paperCount; k++) {
                    au_Conf_Pa[i][j][k] = false;
                }
            }
        }
        while (result.next()) {
            au_Conf_Pa[result.getInt("author_id") - 1][result.getInt("conf_id") - 1][result.getInt("paper_id") - 1] = true;
        }
    }
}