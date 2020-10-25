package grabber;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class JoinerPost {

    private static String url = "https://www.sql.ru/forum/job-offers";
    private static final int pages = 1;
    private static int StartID = 1;
    private final List<Post> posts = new ArrayList<>();


    public void start() throws Exception {
        for (int i = 1; i <= pages; i++) {
            Document doc = Jsoup.connect(JoinerPost.pages(url, i)).get();
            Elements row = doc.select(".postslisttopic");
            for (Element td : row) {
                Element href = td.child(0);
                String link = href.attr("href");
                String textlink = href.text();
                joinPostInformation(link, textlink);
            }
        }
    }

    private void joinPostInformation(String urlPost, String urlInfo) throws Exception {
        Post post = new Post();
        Document doc = Jsoup.connect(urlPost).get();
        Elements linkDescription = doc.select(".msgTable");
        Elements timesCreate = doc.select(".msgFooter");
        for (Element element : linkDescription) {
            post.setId(String.valueOf(StartID));;
            post.setLink(urlPost);
            post.setTextLink(urlInfo);
            post.setDescription(element.getElementsByClass("msgBody").get(1).text());
            post.setCreateDate(new ParseDate(timesCreate.get(0).text().split(" \\[")[0]).parseMethod());
            StartID++;
            this.posts.add(post);
            break;
        }
    }

    private static String pages(String url, int i) {
        return url + "/" + i;
    }

    public List<Post> getPosts() {
        return this.posts;
    }
}
