package grabber;


public class SqlRuParse {
    public static void main(String[] args) throws Exception {
        JoinerPost joinerPost = new JoinerPost();
        joinerPost.start();
        for (Post post : joinerPost.getPosts()) {
            System.out.println(post);
        }
    }
}
