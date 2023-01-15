package jpabook.jpashop.domain.entity.item;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("M") // 엔티티 저장시 구분 컬럼에 입력할 값을 지정
@Getter
@Setter
public class Movie extends Item {

    private String director;
    private String actor;

}
