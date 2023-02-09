package jpabook.jpashop.controller;

import jpabook.jpashop.domain.entity.item.Book;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookForm {

    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    private String author;
    private String isbn;


/*     최대한 set 호출을 Controller에서 하지않고, static 클래스를 만들어서 해줘야 좋다. Order 엔티티 참고
public static Book getBook(String author, String isbn, String name, int price, int quantity) {
        Book book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(quantity);
        book.setAuthor(author);
        book.setIsbn(isbn);
        return book;
    }
    */
}
