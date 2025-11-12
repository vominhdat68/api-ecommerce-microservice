package com.ecommerce.productservice.domain.model;

import lombok.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    private Long id;
    private String name;
    private String slug;
    private Category parent;
    private int level;
    private String imageUrl;
    private boolean active;
    private List<Category> children;
    private List<Product> products;

    public void addChildCategory(Category child) {
        child.setParent(this);
        child.setLevel(this.level + 1);
        this.children.add(child);
    }
}