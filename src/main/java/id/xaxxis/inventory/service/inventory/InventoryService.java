package id.xaxxis.inventory.service.inventory;

import id.xaxxis.inventory.entity.inventory.Inventory;

import java.util.List;

public interface InventoryService {

    Inventory saveInv(Inventory inventory);
    List<Inventory> findAll();
    List<Inventory> findAllByMasterLocation(String locationId);
    List<Inventory> findAllBylocationAndItem(String locationId, String itemId);
    List<Inventory> findAllByItemCategoryAndLocation(String categoryId);

    Inventory findByInventoryId(String itemId, String outletId);
}
