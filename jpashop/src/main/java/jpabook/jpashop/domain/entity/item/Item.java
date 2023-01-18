package jpabook.jpashop.domain.entity.item;


import jpabook.jpashop.domain.entity.Category;
import jpabook.jpashop.domain.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
/*
  상속관계 매핑을 해야함으로 @Inheritance 조인 전략을 해줘야된다.
*/
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype") // 구분 컬럼을 지정, default 값이 dtype이다.
@Getter
@Setter
// 추상클래스로 하는 이유 ? : 구현체를 가지고 할것이기에
public abstract class Item {

    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();


    //== 비즈니스 로직 ==//
    /*
    stock 증가
    */
    public void addStock(int quantity){
        this.stockQuantity += quantity;
    }

    /*
    stock 감소
    */
    public void removeStock(int quantity){
        int restStock = this.stockQuantity - quantity;

        // 남아있는 재고가 0이하면 안됨
        if(restStock < 0 ){
            throw new NotEnoughStockException("need more stock");
        }
        this.stockQuantity = restStock;
    }


}
