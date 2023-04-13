package hello.itemservice.domain.item;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ItemRepository {

    private static final Map<Long, Item> store = new HashMap<>();//static //실무에선 ConcurrentHashMap.
    private static long sequence = 0L; //static singleton설정해주는것. Spring은 singleton 으로 생성해주지만, 설정을 바꾸는 경우를 대비.
    //AtomicLong등 실무에선 다른것 사용. 멀티 쓰레드 환경 고려해야함.

    public Item save(Item item) {
        item.setId(++sequence);
        store.put(item.getId(), item);
        return item;
    }

    public Item findById(Long id) {
        return store.get(id);
    }

    public List<Item> findAll(){
        return new ArrayList<>(store.values());
    }

    public void update(Long itemId, Item updateParam) {
        Item findItem = findById(itemId);
        findItem.setItemName(updateParam.getItemName());
        findItem.setPrice(updateParam.getPrice());
        findItem.setQuantity(updateParam.getQuantity());
        //실무에선 update때 update를 위한 dto를 생성해 사용하는게 권장.
    }

    public void clearStore(){
        store.clear();
    }

}
