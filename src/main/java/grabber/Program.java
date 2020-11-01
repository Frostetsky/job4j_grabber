package grabber;

import java.util.List;

public class Program {

    private static String url = "https://www.sql.ru/forum/job-offers";
    private static final int pages = 1;

    public static void main(String[] args) {
        Parse parse = new SqlRuParse();
        List<Post> post = parse.list(url, pages);
        for (Post po : post) {
            System.out.println(po);
        }
    }
}
