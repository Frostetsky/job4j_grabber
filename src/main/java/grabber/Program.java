package grabber;

import java.util.Properties;

public class Program {

    private static String URL = "https://www.sql.ru/forum/job-offers";

    private static final int PAGES = 3;

    private static final Parse parse = new SqlRuParse();

    public static void main(String[] args) {
        Store store = new PsqlStore(new Properties());
        store.loading(parse, URL, PAGES);
        // TODO - testing
    }
}
