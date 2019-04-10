package id.xaxxis.inventory.service.purchasing.pr;

import id.xaxxis.inventory.dao.purchasing.PurchaseRequestDao;
import id.xaxxis.inventory.dao.purchasing.PurchaseRequestItemDao;
import id.xaxxis.inventory.entity.master.item.MasterItem;
import id.xaxxis.inventory.entity.master.location.MasterLocation;
import id.xaxxis.inventory.entity.master.user.User;
import id.xaxxis.inventory.entity.purchasing.PurchaseRequest;
import id.xaxxis.inventory.entity.purchasing.PurchaseRequestItem;
import id.xaxxis.inventory.enums.RequestStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class PurchaseRequestServiceImpl implements PurchaseRequestService {

    private final PurchaseRequestDao purchaseRequestDao;
    private final PurchaseRequestItemDao purchaseRequestItemDao;

    @Autowired
    public PurchaseRequestServiceImpl(PurchaseRequestDao purchaseRequestDao,
                                      PurchaseRequestItemDao purchaseRequestItemDao){
        this.purchaseRequestDao = purchaseRequestDao;
        this.purchaseRequestItemDao = purchaseRequestItemDao;
    }

    private Map<MasterItem, Integer> masterItems = new HashMap<>();

    @Override
    public void addItem(MasterItem masterItem) {
        if(masterItems.containsKey(masterItem)) {
            masterItems.replace(masterItem, masterItems.get(masterItem) + 1);
        } else {
            masterItems.put(masterItem, 1);
        }
    }

    @Override
    public void removeItem(MasterItem masterItem) {
        if(masterItems.containsKey(masterItem)) {
            if(masterItems.get(masterItem) > 1)
                masterItems.replace(masterItem, masterItems.get(masterItem) - 1);
            else if(masterItems.get(masterItem) == 1) {
                masterItems.remove(masterItem);
            }
        }
    }

    @Override
    public void updateItem(MasterItem masterItem, Integer qty) {
        if(masterItems.containsKey(masterItem)){
            masterItems.replace(masterItem, masterItems.get(masterItem) + qty);
        } else {
            masterItems.put(masterItem, 1);
        }
    }

    @Override
    public Map<MasterItem, Integer> getItemInCart() {
        return Collections.unmodifiableMap(masterItems);
    }

    @Override
    public void createPurchaseRequest(PurchaseRequest purchaseRequest, PurchaseRequestItem purchaseRequestItem) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User)authentication.getPrincipal();
        MasterLocation masterLocation = user.getMasterLocation();

        purchaseRequest.setUser(user);
        purchaseRequest.setMasterLocation(masterLocation);
        purchaseRequest.setRequestStatus(RequestStatus.TERTUNDA);
        purchaseRequest.setPurchaseRequestNumber(generatePrNumber());
        purchaseRequestDao.save(purchaseRequest);

        for(Map.Entry<MasterItem, Integer> entry : masterItems.entrySet()) {
            final String uuid = UUID.randomUUID().toString().replace("-", "");
            purchaseRequestItem.setRequestItemId(uuid);
            purchaseRequestItem.setMasterItems(entry.getKey());
            purchaseRequestItem.setQuantity(entry.getValue());
            purchaseRequestItem.setItemRemarks(purchaseRequestItem.getItemRemarks());
            purchaseRequestItem.setPurchaseRequest(purchaseRequest);
            purchaseRequestItemDao.save(purchaseRequestItem);
        }
        masterItems.clear();
    }

    private final static String prefix = "PR/";

    @Override
    public String generatePrNumber() {
        Long rs = purchaseRequestDao.count();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User)authentication.getPrincipal();
        String location = user.getMasterLocation().getLocationName();
        if (rs != null) {
            Date date = Calendar.getInstance().getTime();
            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            String strDate = dateFormat.format(date);
            int ids = rs.intValue() + 101;
            String generatedId = new Integer(ids).toString();
            return prefix + strDate.replace("-", "") + "/" + user.getFirstName().toUpperCase() + "/" + location.replace(" ", "").toUpperCase()+ "/" +generatedId ;
        }
        return null ;
    }
}
