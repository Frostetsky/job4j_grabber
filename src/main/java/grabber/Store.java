package grabber;

import java.util.List;
import java.util.function.Predicate;

public interface Store {

    void loading(Parse parse, String url, int pages);

    void save(Post post);

    List<Post> getAll();

    Post findByID(String id);
}
