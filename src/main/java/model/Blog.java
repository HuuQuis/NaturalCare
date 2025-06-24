package model;

import java.sql.Timestamp;

public class Blog {
    private int blogId;
    private String blogTitle;
    private String blogDescription;
    private Timestamp datePublished;
    private BlogCategory blogCategory;

    // Constructors
    public Blog() {
    }

    public Blog(int blogId, String blogTitle, String blogDescription, Timestamp datePublished, BlogCategory blogCategory) {
        this.blogId = blogId;
        this.blogTitle = blogTitle;
        this.blogDescription = blogDescription;
        this.datePublished = datePublished;
        this.blogCategory = blogCategory;
    }

    // Getters and Setters
    public int getBlogId() {
        return blogId;
    }

    public void setBlogId(int blogId) {
        this.blogId = blogId;
    }

    public String getBlogTitle() {
        return blogTitle;
    }

    public void setBlogTitle(String blogTitle) {
        this.blogTitle = blogTitle;
    }

    public String getBlogDescription() {
        return blogDescription;
    }

    public void setBlogDescription(String blogDescription) {
        this.blogDescription = blogDescription;
    }

    public Timestamp getDatePublished() {
        return datePublished;
    }

    public void setDatePublished(Timestamp datePublished) {
        this.datePublished = datePublished;
    }

    public BlogCategory getBlogCategory() {
        return blogCategory;
    }

    public void setBlogCategory(BlogCategory blogCategory) {
        this.blogCategory = blogCategory;
    }
}
