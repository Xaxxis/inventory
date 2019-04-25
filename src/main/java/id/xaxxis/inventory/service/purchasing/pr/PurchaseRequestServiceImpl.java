package id.xaxxis.inventory.service.purchasing.pr;

import id.xaxxis.inventory.dao.purchasing.PurchaseRequestDao;
import id.xaxxis.inventory.dao.purchasing.PurchaseRequestItemDao;
import id.xaxxis.inventory.dto.purchasing.PurchaseRequestCart;
import id.xaxxis.inventory.entity.master.location.MasterLocation;
import id.xaxxis.inventory.entity.master.user.User;
import id.xaxxis.inventory.entity.purchasing.pr.PurchaseRequest;
import id.xaxxis.inventory.entity.purchasing.pr.PurchaseRequestItem;
import id.xaxxis.inventory.enums.RequestStatus;
import id.xaxxis.inventory.utils.purchasing.PrLocationSpesification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
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

    private Map<String, PurchaseRequestCart> prCarts = new HashMap<>();

    @Override
    public long countByStatus(RequestStatus status) {
        return purchaseRequestDao.countByRequestStatus(status);
    }

    @Override
    public void addItem(PurchaseRequestCart prCart, String itemId) {
        prCarts.put(itemId, prCart);
    }

    @Override
    public void removeItem(String itemId) {
        prCarts.remove(itemId);
    }

    @Override
    public void updateItem(PurchaseRequestCart prCart, String itemId) {
        PurchaseRequestCart pc = prCarts.get(itemId);
        pc.setItemRemarks(prCart.getItemRemarks());
        pc.setQuantity(prCart.getQuantity());
        prCarts.put(itemId, pc);

    }

    @Override
    public Map<String, PurchaseRequestCart> getItemInCart() {
       return Collections.unmodifiableMap(prCarts);
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

        for(Map.Entry<String, PurchaseRequestCart> entry : prCarts.entrySet()) {
            final String uuid = UUID.randomUUID().toString().replace("-", "");
            purchaseRequestItem.setRequestItemId(uuid);
            purchaseRequestItem.setMasterItems(entry.getValue().getMasterItem());
            purchaseRequestItem.setQuantity(entry.getValue().getQuantity());
            purchaseRequestItem.setItemRemarks(entry.getValue().getItemRemarks());
            purchaseRequestItem.setPurchaseRequest(purchaseRequest);
            purchaseRequestItemDao.save(purchaseRequestItem);
        }
        prCarts.clear();
    }

    private final static String prefix = "PR";

    @Override
    public String generatePrNumber() {
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String newDate = dateFormat.format(date);
        Long rs = purchaseRequestDao.countByUseDate(newDate);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User)authentication.getPrincipal();
        String location = user.getMasterLocation().getLocationName();
        if (rs != null) {
            DateFormat formats = new SimpleDateFormat("dd-MM-yy");
            String strDate = formats.format(date);
            int ids = rs.intValue() + 101;
            String generatedId = new Integer(ids).toString();
            return prefix + strDate.replace("-", "") +"."+ generatedId +date.getSeconds();
        }
        return null ;
    }

    @Override
    public DataTablesOutput<PurchaseRequest> findAll(DataTablesInput input) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        String locationId = user.getMasterLocation().getLocationId();
        if (!locationId.equals("ff8080816985d94101698633a8ad0000")) {
            PrLocationSpesification spec = new PrLocationSpesification();
            return purchaseRequestDao.findAll(input,spec);
        }
        return purchaseRequestDao.findAll(input);
    }


    @Override
    public Page<PurchaseRequest> findAllByCreatedDate(String date, Pageable pageable) {
        return purchaseRequestDao.findAllByCreatedDate(date, pageable);
    }

    @Override
    public PurchaseRequest findByPrNumber(String prNumber) {
        return purchaseRequestDao.findByPurchaseRequestNumber(prNumber);
    }

    @Override
    public PurchaseRequest findByPrId(String prId) {
        return purchaseRequestDao.findByPurchaseReqId(prId);
    }

    @Override
    public List<PurchaseRequestItem> findAllItemReq(String id) {
        PurchaseRequest purchaseRequest = purchaseRequestDao.findByPurchaseReqId(id);
        List<PurchaseRequestItem> purchaseRequestItemList = purchaseRequest.getRequestItemList();
        return purchaseRequestItemList;
    }
}
