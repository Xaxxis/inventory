package id.xaxxis.inventory.controller.purchasing;

import id.xaxxis.inventory.entity.purchasing.request.PurchaseRequest;
import id.xaxxis.inventory.entity.purchasing.request.PurchaseRequestItem;
import id.xaxxis.inventory.service.purchasing.order.PurchaseOrderService;
import id.xaxxis.inventory.service.purchasing.request.PurchaseRequestService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/app/so")
public class StockOpnameController {

    private final PurchaseRequestService purchaseRequestService;
    private final PurchaseOrderService purchaseOrderService;

    public StockOpnameController(PurchaseRequestService purchaseRequestService,
                                 PurchaseOrderService purchaseOrderService){
        this.purchaseRequestService = purchaseRequestService;
        this.purchaseOrderService = purchaseOrderService;
    }

    @Secured("ROLE_SO")
    @RequestMapping(value = "/revPR", method = RequestMethod.GET)
    public String revisionPR(@RequestParam("getPR") String prId, Model model){
        PurchaseRequest purchaseRequest = purchaseRequestService.findByPrId(prId);
        System.out.println(purchaseRequestService.checkReStatus(purchaseRequest.getRequestStatus()));
        if(purchaseRequestService.checkReStatus(purchaseRequest.getRequestStatus())){
            model.addAttribute("pr", purchaseRequest);
            model.addAttribute("itemList", purchaseRequest.getRequestItemList());
            return "purchasing/pr-revision";
        }
        return "redirect:/app/purchasing/request/getPR?number="+purchaseRequest.getPurchaseRequestNumber();
    }

    @Secured("ROLE_SO")
    @RequestMapping(value = "/prItem/remove/{itemReqId}/{purchaseReqId}", method = RequestMethod.GET)
    public String deleteItemReq(@PathVariable("itemReqId") String itemReqId,
                                @PathVariable("purchaseReqId") String purchaseReqId){
        purchaseRequestService.deleteItemReq(itemReqId);
        return "redirect:/app/so/revPR?getPR="+purchaseReqId;
    }

    @Secured("ROLE_SO")
    @RequestMapping(value = "/order/submit", method = RequestMethod.GET)
    public String createPo(@RequestParam("id") String id,
                           @RequestParam("itemId") List<String> itemId,
                           @RequestParam("revQty") List<Integer> revQty,
                           @RequestParam("revRemarks") List<String> revRemarks,
                           @RequestParam("action") String action,
                           @RequestParam(required = false, value = "submit") String submit) {
        PurchaseRequest purchaseRequest = purchaseRequestService.findByPrId(id);
        if (action.equalsIgnoreCase("Save")) {
            purchaseRequestService.savePr(purchaseRequest);
            for (int i = 0; i < itemId.size(); i++) {
                PurchaseRequestItem purchaseRequestItem = purchaseRequestService.findByitemReqId(itemId.get(i));
                purchaseRequestItem.setQtyRev(revQty.get(i));
                purchaseRequestItem.setItemRemarks(revRemarks.get(i));
                purchaseRequestService.savePrItem(purchaseRequestItem);
            }
        } else if (action.equalsIgnoreCase("Submit")) {
            purchaseOrderService.createPO(id);
            return "redirect:/app/purchasing/request/list";
        }
        return "redirect:/app/purchasing/request/list";
    }
}
