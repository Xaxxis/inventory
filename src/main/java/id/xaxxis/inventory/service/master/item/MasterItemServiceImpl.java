package id.xaxxis.inventory.service.master.item;

import id.xaxxis.inventory.dao.master.item.CategoryItemDao;
import id.xaxxis.inventory.dao.master.item.MasterItemDao;
import id.xaxxis.inventory.dao.master.item.UnitItemDao;
import id.xaxxis.inventory.entity.master.item.CategoryItem;
import id.xaxxis.inventory.entity.master.item.MasterItem;
import id.xaxxis.inventory.entity.master.item.UnitItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MasterItemServiceImpl implements MasterItemService {

    private final MasterItemDao masterItemDao;
    private final UnitItemDao unitItemDao;
    private final CategoryItemDao categoryItemDao;

    @Autowired
    public MasterItemServiceImpl(MasterItemDao masterItemDao, UnitItemDao unitItemDao, CategoryItemDao categoryItemDao) {
        this.masterItemDao = masterItemDao;
        this.unitItemDao = unitItemDao;
        this.categoryItemDao = categoryItemDao;
    }

    @Override
    public MasterItem findByItemBarcode(String itemBarcode) {
        return masterItemDao.findByItemBarcode(itemBarcode);
    }

    @Override
    public MasterItem findByItemId(String itemId) {
        return masterItemDao.findByItemId(itemId);
    }

    @Override
    public List<MasterItem> findAll() {
        return masterItemDao.findAll();
    }

    @Override
    public List<MasterItem> findAllByItemCategory(String categoryName) {
        return masterItemDao.findAllByCategoryItem_CategoryName(categoryName);
    }

    @Override
    public MasterItem saveItem(MasterItem masterItem) {
        return masterItemDao.save(masterItem);
    }

    @Override
    public CategoryItem saveCategory(CategoryItem categoryItem) {
        return categoryItemDao.save(categoryItem);
    }

    @Override
    public CategoryItem findByCategoryName(String categoryName) {
        return categoryItemDao.findByCategoryName(categoryName);
    }

    @Override
    public CategoryItem findBYCategoryId(String categoryId) {
        return categoryItemDao.findByCategoryId(categoryId);
    }

    @Override
    public List<CategoryItem> findAllCategory() {
        return categoryItemDao.findAll();
    }

    @Override
    public UnitItem saveUnit(UnitItem unitItem) {
        return unitItemDao.save(unitItem);
    }

    @Override
    public UnitItem findByUnitId(String unitId) {
        return unitItemDao.findByUnitId(unitId);
    }

    @Override
    public UnitItem findByUnitName(String unitName) {
        return unitItemDao.findByUnitName(unitName);
    }

    @Override
    public List<UnitItem> findAllUnit() {
        return unitItemDao.findAll();
    }


}
