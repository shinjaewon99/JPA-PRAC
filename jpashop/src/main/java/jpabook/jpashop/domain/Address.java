package jpabook.jpashop.domain;


import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable // 가독성을 높이기 위해 "주소"의 하위느낌이나는 필드를 모아주었다.
@Getter
public class Address {
    private String city;
    private String street;
    private String zipcode;

    // public하면 변동이 될수있음으로 protected까지는 허용
    protected Address() {
    }

    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
