package jpabook.jpashop.domain.entity;


import jpabook.jpashop.domain.entity.item.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.*;

@Entity
@Getter
@Setter
public class Category {

    @Id
    @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    private String name;

    @ManyToMany // N:N 관계
    // N:N 관계에서는 JoinColumn이 아닌 JoinTable을 사용해 연관관계를 매핑한다.
    @JoinTable(name = "category_item",
    joinColumns = @JoinColumn(name = "category_id"),
    inverseJoinColumns = @JoinColumn(name = "item_id"))
    private List<Item> items = new ArrayList<>();


    /*
     셀프로 자신의 객체를 참조하는 방법
    */
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent")
    private List<Category> child = new ArrayList<>();


    // 연관관계 편의 메서드//
    public void addChildCategory(Category child){
        this.child.add(child);
        child.setParent(this);
    }

}
