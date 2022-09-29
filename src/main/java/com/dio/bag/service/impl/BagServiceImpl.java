package com.dio.bag.service.impl;

import com.dio.bag.enumeration.FormOfPayment;
import com.dio.bag.model.Bag;
import com.dio.bag.model.Item;
import com.dio.bag.model.Restaurant;
import com.dio.bag.repository.BagRepository;
import com.dio.bag.repository.ItemRepository;
import com.dio.bag.repository.ProductRepository;
import com.dio.bag.service.BagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BagServiceImpl implements BagService {

    private final BagRepository bagRepository;
    private final ProductRepository productRepository;
    private final ItemRepository itemRepository;

    @Override
    public Item incluirItemNaSacola(ItemDto itemDto) {
        Bag bag = verSacola(itemDto.getBagId());

        if (bag.isFechada()) {
            throw new RuntimeException("Esta sacola está fechada.");
        }

        Item itemParaSerInserido = Item.builder()
                .quantity(itemDto.getQuantity())
                .bag(bag)
                .product(productRepository.findById(itemDto.getProductId()).orElseThrow(
                        () -> {
                            throw new RuntimeException("Esse produto não existe!");
                        }
                ))
                .build();

        List<Item> itensDaScola = bag.getItens();
        if (itensDaScola.isEmpty()) {
            itensDaScola.add(itemParaSerInserido);
        } else {
            Restaurant restauranteAtual = itensDaScola.get(0).getProduct().getRestaurant();
            Restaurant restauranteDoItemParaAdicionar = itemParaSerInserido.getProduct().getRestaurant();
            if (restauranteAtual.equals(restauranteDoItemParaAdicionar)) {
                itensDaScola.add(itemParaSerInserido);
            } else {
                throw new RuntimeException("Não é possível adicionar produtos de restaurantes diferentes. Feche a sacola ou esvazie.");
            }
        }

        List<Double> valorDosItens = new ArrayList<>();
        for (Item itemDaSacola: itensDaScola) {
            double valorTotalItem =
                    itemDaSacola.getProduct().getUnitaryValue() * itemDaSacola.getQuantity();
            valorDosItens.add(valorTotalItem);
        }

        double valorTotalSacola = valorDosItens.stream()
                .mapToDouble(valorTotalDeCadaItem -> valorTotalDeCadaItem)
                .sum();

        bag.setAmount(valorTotalSacola);
        bagRepository.save(bag);
        return itemParaSerInserido;
    }

    @Override
    public Bag verSacola(Long id) {
        return bagRepository.findById(id).orElseThrow(
                () -> {
                    throw new RuntimeException("Essa sacola não existe!");
                }
        );
    }

    @Override
    public Bag fecharSacola(Long id, int numeroformaPagamento) {
        Bag bag = verSacola(id);
        if (bag.getItens().isEmpty()) {
            throw new RuntimeException("Inclua ítens na sacola!");
        }
    /*if (numeroformaPagamento == 0) {
      sacola.setFormaPagamento(FormaPagamento.DINHEIRO);
    } else {
      sacola.setFormaPagamento(FormaPagamento.MAQUINETA);
    }*/
        FormOfPayment formOfPayment =
                numeroformaPagamento == 0 ? FormOfPayment.money : FormOfPayment.card_machine;
        bag.setFormOfPayment(formOfPayment);
        bag.setFechada(true);
        return bagRepository.save(bag);
    }
}
