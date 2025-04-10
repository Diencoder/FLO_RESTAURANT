package com.example.florestaurant.model;

import jakarta.persistence.*;

@Entity
@Table(name = "tbl_category")  // Tên bảng trong cơ sở dữ liệu
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;  // Tên danh mục (tương đương với "name" trong bảng của bạn)

    @Column(name = "image_name")
    private String imageName;  // Tên ảnh của danh mục

    @Column(name = "featured")
    private String featured;  // Xác định danh mục nổi bật (có thể là 'yes' hoặc 'no')

    @Column(name = "active")
    private String active;  // Xác định trạng thái (có sẵn hay không)

    // Getter và Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getFeatured() {
        return featured;
    }

    public void setFeatured(String featured) {
        this.featured = featured;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", imageName='" + imageName + '\'' +
                ", featured='" + featured + '\'' +
                ", active='" + active + '\'' +
                '}';
    }
}
