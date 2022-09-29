package com.dio.bag.controller;

import com.dio.bag.enumeration.dto.ItemDto;
import com.dio.bag.model.Bag;
import com.dio.bag.model.Item;
import com.dio.bag.service.BagService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

//@Api(value="/ifood-devweek/bag")
@RequestMapping(value = "/ifood-devweek/sacolas")
@RestController
@RequiredArgsConstructor
public class BagController {

    private BagService bagService;

    @PostMapping
    public Item incluirItemNaSacola(@RequestBody ItemDto itemDto) {
        return bagService.incluirItemNaSacola(itemDto);
    }

    @GetMapping("/{id}")
    public Bag verSacola(@PathVariable("id") Long id) {
        return bagService.verSacola(id);
    }

    @PatchMapping("/fecharSacola/{sacolaId}")
    public Bag fecharSacola(@PathVariable("bagId") Long bagId,
                            @RequestParam("formOfPayment") int formOfPaymen) {
        return bagService.fecharSacola(bagId, formOfPaymen);
    }
}
