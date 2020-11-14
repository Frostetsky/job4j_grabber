package grabber;

import org.junit.Test;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.time.LocalDateTime;
import java.util.Properties;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class GrabberTest {

    public Connection init() {
        try (InputStream in = PsqlStore.class.getClassLoader().getResourceAsStream("app.properties")) {
            Properties config = new Properties();
            config.load(in);
            Class.forName(config.getProperty("jdbc.driver"));
            return DriverManager.getConnection(
                    config.getProperty("url"),
                    config.getProperty("login"),
                    config.getProperty("password")

            );
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @Test
    public void createItem() throws Exception {
        try (PsqlStore store = new PsqlStore(ConnectionRollback.create(this.init()))) {
            Post post = new Post("1", "job", "link", "description", LocalDateTime.now());
            store.save(post);
            Post out = store.findByID(post.getId());
            assertEquals(post.getTextLink(), out.getTextLink());
            assertEquals(post.getLink(), out.getLink());
            assertEquals(post.getDescription(), out.getDescription());
            assertNotNull(out.getDescription());
            assertThat(post.getId(), is(out.getId()));
        }
    }

    @Test
    public void getAll() throws Exception {
        try (PsqlStore store = new PsqlStore(ConnectionRollback.create(this.init()))) {
            Post post1 = new Post("1", "job1", "link", "description", LocalDateTime.now());
            Post post2 = new Post("2", "job2", "link", "description", LocalDateTime.now());
            Post post3 = new Post("3", "job3", "link", "description", LocalDateTime.now());
            store.save(post1);
            store.save(post2);
            store.save(post3);
            assertThat(store.getAll().size(), is(3));
        }
    }

    @Test
    public void findByID() throws Exception {
        try (PsqlStore store = new PsqlStore(ConnectionRollback.create(this.init()))) {
            Post post = new Post("3", "job3", "link", "description", LocalDateTime.now());
            store.save(post);
            Post out = store.findByID(post.getId());
            assertEquals(post.getTextLink(), out.getTextLink());
            assertEquals(post.getLink(), out.getLink());
            assertEquals(post.getDescription(), out.getDescription());
            assertNotNull(out.getDescription());
            assertThat(post.getId(), is(out.getId()));
        }
    }

}