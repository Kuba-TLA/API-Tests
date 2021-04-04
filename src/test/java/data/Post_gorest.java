package data;

public class Post_gorest {
    private int code;
    Object meta;
    Post_data data;

    public Post_gorest(int code, Object meta, Post_data data) {
        this.code = code;
        this.meta = meta;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getMeta() {
        return meta;
    }

    public void setMeta(Object meta) {
        this.meta = meta;
    }

    public Post_data getData() {
        return data;
    }

    public void setData(Post_data data) {
        this.data = data;
    }
}
