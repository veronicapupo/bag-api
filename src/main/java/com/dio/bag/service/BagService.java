package com.dio.bag.service;

import com.dio.bag.enumeration.dto.ItemDto;
import com.dio.bag.model.Bag;
import com.dio.bag.model.Item;

public interface BagService {
    Item incluirItemNaSacola(ItemDto itemDto);
    Bag verSacola(Long id);
    Bag fecharSacola(Long id, int formOfPayment );
}
