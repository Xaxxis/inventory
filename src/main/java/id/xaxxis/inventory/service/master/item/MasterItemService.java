package id.xaxxis.inventory.service.master.item;

import id.xaxxis.inventory.entity.master.item.CategoryItem;
import id.xaxxis.inventory.entity.master.item.MasterItem;
import id.xaxxis.inventory.entity.master.item.UnitItem;

import java.util.List;
import java.util.Optional;

public interface MasterItemService {
    MasterItem findByItemBarcode(String itemBarcode);
    Optional<MasterItem> findByItemId(String itemId);
    List<MasterItem> findAll();
    List<MasterItem> findAllByItemCategory(String categoryName);
    MasterItem saveItem(MasterItem masterItem);

    CategoryItem saveCategory(CategoryItem categoryItem);
    CategoryItem findByCategoryName(String categoryName);
    CategoryItem findBYCategoryId(String categoryId);
    List<CategoryItem> findAllCategory();

    UnitItem saveUnit(UnitItem unitItem);
    UnitItem findByUnitId(String unitId);
    UnitItem findByUnitName(String unitName);
    List<UnitItem> findAllUnit();


}
