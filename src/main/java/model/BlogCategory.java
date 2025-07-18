package model;

public class BlogCategory {
    private int id;
    private String name;
    private boolean status;
    private int blogCount;

    public BlogCategory() {
    }
    public BlogCategory(int id, String name, boolean status) {
        this.id = id;
        this.name = name;
        this.status = status;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }


    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getBlogCount() {
        return blogCount;
    }

    public void setBlogCount(int blogCount) {
        this.blogCount = blogCount;
    }
}
