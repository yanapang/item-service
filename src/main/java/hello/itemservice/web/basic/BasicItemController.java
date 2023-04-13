package hello.itemservice.web.basic;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor
public class BasicItemController {

    private final ItemRepository itemRepository;

    //@Autowired //생성자가 하나만 있을 경우 @Autowired 생략 가능
//    public BasicItemController(ItemRepository itemRepository) {
//        this.itemRepository = itemRepository;
//    }
    //@RequiredArgsConstructor 쓸 경우, 생성자 생략 가능.

    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "basic/items"; //화면 위치.
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/item";
    }

    @GetMapping("/add")
    public String addForm() {
        return "basic/addForm";
    }

//    @PostMapping("/add")
    public String addItemV1(@RequestParam String itemName,
                       @RequestParam int price,
                       @RequestParam Integer quantity,
                       Model model) {

        Item item = new Item();
        item.setItemName(itemName);
        item.setPrice(price);
        item.setQuantity(quantity);
        //생성자 통해 바로 만들어도 ok.

        itemRepository.save(item);

        model.addAttribute("item", item); //저장후 바로 model에 담아 반환.

        return "basic/item"; //목록으로이동.
    }


    //ModelAttribute 사용 버전.
//    @PostMapping("/add")
    public String addItemV2(@ModelAttribute("item") Item item, Model model) {
        //@ModelAttribute("item") : 여기서 "item"은 model.addAttribute에 담기는 값의 이름.
        //@ModelAttritube 가 자동으로 객체 생성해 요청파라미터의 값을 set방식으로 담아준다.

        itemRepository.save(item);
//        model.addAttribute("item", item); //자동 생성되므로 없어도 동일하게 작동. 생략가능.

        return "basic/item"; //목록으로이동.
    }

//    @PostMapping("/add")
    public String addItemV3(@ModelAttribute Item item, Model model) {
        //@ModelAttribute에 name 미지정시 item이 자동으로 name이 된다.
        itemRepository.save(item);
//        model.addAttribute("item", item);

        return "basic/item"; //목록으로이동.
    }

    //가장 생략된 버전. @ModelAttribute 생략.
    @PostMapping("/add")
    public String addItemV4(Item item) {
        itemRepository.save(item);
        return "basic/item"; //목록으로이동.
    }

    /**
     * Test용 데이터 추가
     */
    @PostConstruct
    public void init() {
        itemRepository.save(new Item("itemA", 1000, 3));
        itemRepository.save(new Item("itemB", 300, 8));
    }
}
