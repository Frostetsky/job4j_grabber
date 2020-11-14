package grabber;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;

public class SqlRuParse implements Parse {

    @Override
    public List<Post> list(String link, int pages) {
        List<Post> posts = new ArrayList<>();
        try {
            for (int i = 1; i <= pages; i++) {
                Document doc = Jsoup.connect(SqlRuParse.pages(link, i)).get();
                Elements row = doc.select(".postslisttopic");
                for (Element td : row) {
                    Element href = td.child(0);
                    String linkAttr = href.attr("href");
                    String textLink = href.text();
                    Post post = detail(linkAttr, textLink);
                    if (post.getTextLink().toLowerCase().contains("java ")) {
                        post.setId(String.valueOf(randomID()));
                        posts.add(post);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return posts;
    }

    @Override
    public Post detail(String urlPost, String urlInfo) {
        Post post = new Post();
        try {
            Document doc = Jsoup.connect(urlPost).get();
            Elements linkDescription = doc.select(".msgTable");
            Elements timesCreate = doc.select(".msgFooter");
            for (Element element : linkDescription) {
                post.setLink(urlPost);
                post.setTextLink(urlInfo);
                post.setDescription(element.getElementsByClass("msgBody").get(1).text());
                post.setCreateDate(new ParseDate(timesCreate.get(0).text().split(" \\[")[0]).parseMethod());
                break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return post;
    }

    private static String pages(String url, int i) {
        return url + "/" + i;
    }

    private int randomID() {
        String number = String.valueOf(System.currentTimeMillis());
        String randomNumber = number.substring(4);
        return Integer.parseInt(randomNumber);
    }
}
