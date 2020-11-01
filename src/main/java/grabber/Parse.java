package grabber;

import java.util.List;

public interface Parse {
    List<Post> list(String link, int pages);

    Post detail(String link, String urlInfo);
}
