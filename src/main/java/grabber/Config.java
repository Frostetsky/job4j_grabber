package grabber;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {

    Properties properties = new Properties();
    private String name;

    public Config(String name) {
        this.name = name;
        this.load();
    }

    public String get(String key) {
        return (String) properties.get(key);
    }

    private void load() {
        try (InputStream in = getClass().getClassLoader().getResourceAsStream(name)) {
            properties.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
