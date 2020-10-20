package grabber;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class SqlRuParse {

    private static String url = "https://www.sql.ru/forum/job-offers";
    private static final int pages = 5;

    public static void main(String[] args) throws Exception {
        for (int i = 1; i <= pages; i++) {
            Document doc = Jsoup.connect(SqlRuParse.pages(url, i)).get();
            Elements row = doc.select(".postslisttopic");
            Elements date = doc.select(".altCol");
            int index = 1;
            for (Element td : row) {
                Element href = td.child(0);
                System.out.println(href.attr("href"));
                System.out.println(href.text());
                System.out.println(date.get(index).text());
                index += 2;
                System.out.println(System.lineSeparator());
            }
        }
    }

    private static String pages(String url, int i) {
        return url + "/" + i;
    }
}
