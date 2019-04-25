package id.xaxxis.inventory.service.inventory;

import id.xaxxis.inventory.dao.inventory.InventoryDao;
import id.xaxxis.inventory.entity.inventory.Inventory;
import id.xaxxis.inventory.entity.master.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryServiceImpl implements InventoryService {

    private final InventoryDao inventoryDao;

    @Autowired
    public InventoryServiceImpl(InventoryDao inventoryDao) {
        this.inventoryDao = inventoryDao;
    }

    @Override
    public Inventory saveInv(Inventory inventory) {
        return inventoryDao.save(inventory);
    }

    @Override
    public List<Inventory> findAll() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String locationId = user.getMasterLocation().getLocationId();
        if(locationId.equals("ff8080816985d94101698633a8ad0000")) {
            return inventoryDao.findAll();
        }
        return inventoryDao.findAllByMasterLocation_LocationId(locationId);
    }

    @Override
    public List<Inventory> findAllByMasterLocation(String locationId) {
        return inventoryDao.findAllByMasterLocation_LocationId(locationId);
    }

    @Override
    public List<Inventory> findAllBylocationAndItem(String locationId, String itemId) {
        return inventoryDao.findAllByMasterLocation_LocationIdAndAndMasterItem_ItemId(locationId, itemId);
    }

    @Override
    public List<Inventory> findAllByItemCategoryAndLocation(String categoryId) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String locationId = user.getMasterLocation().getLocationId();
        return inventoryDao.findAllByMasterItem_CategoryItem_CategoryIdAndMasterLocation_LocationId(categoryId,locationId);
    }

    @Override
    public Inventory findByInventoryId(String itemId, String outletId) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String locaitonId = user.getMasterLocation().getLocationId();
        return inventoryDao.findByInventoryId_ItemIdAndInventoryId_LocationIdAndInventoryId_OutletId(itemId,locaitonId,outletId);
    }


}
