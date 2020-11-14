package grabber;

import java.util.List;
import java.util.function.Predicate;

public interface Store {

    void saveAll(List<Post> posts);

    void save(Post post);

    List<Post> getAll();

    Post findByID(String id);
}
