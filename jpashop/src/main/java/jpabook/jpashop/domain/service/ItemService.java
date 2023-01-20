package jpabook.jpashop.domain.service;

import jpabook.jpashop.domain.entity.item.Book;
import jpabook.jpashop.domain.entity.item.Item;
import jpabook.jpashop.domain.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    public List<Item> findItems() {
        return itemRepository.findAll();
    }

    @Transactional
    // itemRepository에 있는 영속 상태 DB를 찾아왔다.
    public void updateItem(Long itemId, String name, int price, int stockQuantity){
        Item findItem = itemRepository.findOne(itemId);
        findItem.setName(name);
        findItem.setPrice(price);
        findItem.setStockQuantity(stockQuantity);

/*
        @Transactional에 의해 JPA에서 Flush를 날려 영속성 Entity에서 변경이 된 점을 찾아 업데이트 쿼리를 자동으로 날린다.
        itemRepository.save(findItem);
        em.merge()
*/

    }

    public Item findOne(Long itemId) {
        return itemRepository.findOne(itemId);
    }


}
