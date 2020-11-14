package grabber;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class PsqlStore implements Store, AutoCloseable {

    private Connection cn;
    private final String path = "./src/main/resources/rabbit.properties";

    public PsqlStore(Properties cfg) {
        try {
            FileInputStream in = new FileInputStream(this.path);
            cfg.load(in);
            Class.forName(cfg.getProperty("jdbc.driver"));
            cn = DriverManager.getConnection(
                    cfg.getProperty("url"),
                    cfg.getProperty("login"),
                    cfg.getProperty("password"));
        } catch (IOException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void loading(Parse parse, String url, int pages) {
        List<Post> posts = parse.list(url, pages);
        for (Post post : posts) {
            save(post);
        }
    }

    @Override
    public void save(Post post) {
        try {
            PreparedStatement ps = cn.prepareStatement(
                    "INSERT INTO post(id, name, description, link, created_date) VALUES (?, ?, ?, ?, ?)");
            ps.setInt(1, Integer.parseInt(post.getId()));
            ps.setString(2, post.getTextLink());
            ps.setString(3, post.getDescription());
            ps.setString(4, post.getLink());
            ps.setDate(5, java.sql.Date.valueOf(post.getCreateDate().toLocalDate()));
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Post> getAll() {
        List<Post> posts = new ArrayList<>();
        try {
            Statement st = cn.createStatement();
            ResultSet set = st.executeQuery("SELECT * FROM post");
            while (set.next()) {
                Post post = new Post();
                post.setId(set.getString("id"));
                post.setTextLink(set.getString("name"));
                post.setDescription(set.getString("description"));
                post.setLink(set.getString("link"));
                post.setCreateDate(set.getTimestamp("created_date").toLocalDateTime());
                posts.add(post);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return posts;
    }

    @Override
    public Post findByID(String id) {
        Post post = null;
        try {
            PreparedStatement ps = cn.prepareStatement("SELECT * FROM post WHERE ID = ?");
            ps.setInt(1, Integer.parseInt(id));
            ResultSet set = ps.executeQuery();
            while (set.next()) {
                post = new Post();
                post.setId(id);
                post.setTextLink(set.getString("name"));
                post.setDescription(set.getString("description"));
                post.setLink(set.getString("link"));
                post.setCreateDate(set.getTimestamp("created_date").toLocalDateTime());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return post;
    }

    @Override
    public void close() throws Exception {
        if (cn != null) {
            cn.close();
        }
    }
}
