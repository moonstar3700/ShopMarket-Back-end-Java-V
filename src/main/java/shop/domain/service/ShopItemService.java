package shop.domain.service;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import shop.domain.model.ShopItem;

import java.util.List;

/** Lombok use */

@Service @NoArgsConstructor
public class ShopItemService {
    @Autowired
    private ShopItemRepository shopItemRepository;

    public List<ShopItem> getAllShopItems() {return shopItemRepository.findAll();}

    public ShopItem addShopItem(ShopItem shopItem){
        if (shopItemRepository.existsByName(shopItem.getName())){
            throw new ServiceException("save", "shopItem.name.exists");
        }
        return shopItemRepository.save(shopItem);
    }

    public  boolean getUserById(long id){return shopItemRepository.existsById(id);}

    public ShopItem updateShopItem(ShopItem shopItem, long id){
        if (shopItemRepository.existsByNameAndExcludeId(shopItem.getName(), shopItem.getId())){
            throw new ServiceException("update", "shopitem.name.exists");
        }
        shopItem.setId(id);
        return shopItemRepository.save(shopItem);
    }

    public ShopItem deleteById(long id) {
        ShopItem shopItem = shopItemRepository.findById(id).orElseThrow(() -> new ServiceException("delete", "shopItem.not.exists"));
        shopItemRepository.delete(shopItem);
        return shopItem;
    }
}
