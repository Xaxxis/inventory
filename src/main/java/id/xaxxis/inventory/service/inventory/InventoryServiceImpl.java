package id.xaxxis.inventory.service.inventory;

import id.xaxxis.inventory.dao.inventory.InventoryDao;
import id.xaxxis.inventory.entity.inventory.Inventory;
import id.xaxxis.inventory.entity.master.user.User;
import id.xaxxis.inventory.service.spesification.inventory.InventorySpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        return null;
    }

    @Override
    public Optional<Inventory> findByInvId(String invId) {
        return inventoryDao.findById(invId);
    }

    @Override
    public DataTablesOutput<Inventory> findAllApi(DataTablesInput input) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String locationId = user.getMasterLocation().getLocationId();
        if(locationId.equals("ff8080816985d94101698633a8ad0000")){
            return inventoryDao.findAll(input);
        }
        InventorySpecification specification = new InventorySpecification();
        return inventoryDao.findAll(input, specification);
    }


}
