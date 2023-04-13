package hello.itemservice.domain.item;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class ItemRepositoryTest {

    ItemRepository itemRepository = new ItemRepository();

    @AfterEach
    void afterEach(){
        itemRepository.clearStore();
    }

    @Test
    void save() {
        //given
        Item item = new Item("itemA", 10000, 10);

        //when
        Item savedItem = itemRepository.save(item);

        //then
        Item retrievedItem = itemRepository.findById(item.getId());
        assertThat(retrievedItem).isEqualTo(savedItem);
    }

    @Test
    void findAll() {
        //given
        Item item1 = new Item("itemA", 10000, 10);
        Item item2 = new Item("itemB", 15000, 30);

        itemRepository.save(item1);
        itemRepository.save(item2);

        //when
        List<Item> result = itemRepository.findAll();

        //then
        assertThat(result.size()).isEqualTo(2);
    }

    @Test
    void updateItem() {
        //given
        Item item1 = new Item("itemA", 10000, 10);

        Item savedItem = itemRepository.save(item1);
        Long itemId = savedItem.getId();

        //when
        Item updateParam = new Item("item2", 20000, 30);
        itemRepository.update(itemId, updateParam);

        //then
        Item retrievedItem = itemRepository.findById(itemId);

        assertThat(retrievedItem.getItemName()).isEqualTo(updateParam.getItemName());
        assertThat(retrievedItem.getPrice()).isEqualTo(updateParam.getPrice());
        assertThat(retrievedItem.getQuantity()).isEqualTo(updateParam.getQuantity());

    }
}