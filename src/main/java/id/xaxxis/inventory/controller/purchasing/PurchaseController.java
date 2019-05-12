package id.xaxxis.inventory.controller.purchasing;

import id.xaxxis.inventory.dto.purchasing.PurchaseRequestCart;
import id.xaxxis.inventory.entity.inventory.Inventory;
import id.xaxxis.inventory.entity.master.suplier.Suplier;
import id.xaxxis.inventory.entity.purchasing.order.PurchaseOrder;
import id.xaxxis.inventory.entity.purchasing.request.PurchaseRequest;
import id.xaxxis.inventory.entity.purchasing.request.PurchaseRequestItem;
import id.xaxxis.inventory.enums.RequestStatus;
import id.xaxxis.inventory.service.inventory.InventoryService;
import id.xaxxis.inventory.service.master.item.MasterItemService;
import id.xaxxis.inventory.service.master.location.MasterLocationService;
import id.xaxxis.inventory.service.master.suplier.SuplierService;
import id.xaxxis.inventory.service.purchasing.order.PurchaseOrderService;
import id.xaxxis.inventory.service.purchasing.request.PurchaseRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
@RequestMapping("/app/purchasing")
public class PurchaseController {

    private final SuplierService suplierService;
    private final MasterLocationService masterLocationService;
    private final MasterItemService masterItemService;
    private final PurchaseRequestService purchaseRequestService;
    private final InventoryService inventoryService;
    private final PurchaseOrderService purchaseOrderService;

    @Autowired
    public PurchaseController(SuplierService suplierService,
                              MasterLocationService masterLocationService,
                              MasterItemService masterItemService,
                              PurchaseRequestService purchaseRequestService,
                              InventoryService inventoryService,
                              PurchaseOrderService purchaseOrderService) {
        this.suplierService = suplierService;
        this.masterLocationService = masterLocationService;
        this.masterItemService = masterItemService;
        this.purchaseRequestService = purchaseRequestService;
        this.inventoryService = inventoryService;
        this.purchaseOrderService = purchaseOrderService;
    }

    @RequestMapping(value = "/suplier", method = RequestMethod.GET)
    public String suplier(Model model) {
        model.addAttribute("purchaseOpen", "open");
        model.addAttribute("suplierActive", "active");
        model.addAttribute("suplierList", suplierService.findAll());
        model.addAttribute("typeList", suplierService.findAllSuplierType());
        model.addAttribute("locationList", masterLocationService.findAllLocation());
        return "purchasing/suplier";
    }

    @RequestMapping(value = "/suplier/create", method = RequestMethod.POST)
    public String suplierCreate(@ModelAttribute("suplier")Suplier suplier) {
        suplier.setActive(true);
        suplierService.createSuplier(suplier);
        return "redirect:/app/purchasing/suplier";
    }

    @RequestMapping(value = "/suplier/update", method = RequestMethod.POST)
    public String suplierUpdate(@ModelAttribute("suplierId") String suplierId, @ModelAttribute("suplier") Suplier newSuplier) {
        Suplier suplier = suplierService.findBySuplierId(suplierId);
        suplier.setSuplierName(newSuplier.getSuplierName());
        suplier.setAddress(newSuplier.getAddress());
        suplier.setSuplierPhone(newSuplier.getSuplierPhone());
        suplier.setSuplierPic(newSuplier.getSuplierPic());
        suplier.setSuplierType(newSuplier.getSuplierType());
        suplier.setActive(newSuplier.isActive());
        suplier.setMasterLocation(newSuplier.getMasterLocation());
        suplierService.saveSuplier(suplier);
        return "redirect:/app/purchasing/suplier";
    }

    @RequestMapping(value = "/request", method = RequestMethod.GET)
    public ModelAndView purchaseRequest() {
        ModelAndView mv = new ModelAndView("purchasing/purchase-request");
        mv.addObject("purchaseOpen", "open");
        mv.addObject("requestActive", "active");
        mv.addObject("itemList", masterItemService.findAll());
        mv.addObject("itemCart", purchaseRequestService.getItemInCart());
        return mv;
    }

    @Secured("ROLE_SO")
    @RequestMapping(value = "/request/list", method = RequestMethod.GET)
    public String prList(Model model) {
        model.addAttribute("locations", masterLocationService.findAll());
        model.addAttribute("status", RequestStatus.values());
        model.addAttribute("tertunda", purchaseRequestService.countByStatus(RequestStatus.TERTUNDA));
        model.addAttribute("selesai", purchaseRequestService.countByStatus(RequestStatus.SELESAI));
        model.addAttribute("proses", purchaseRequestService.countByStatus(RequestStatus.DIPROSES));
        model.addAttribute("batal", purchaseRequestService.countByStatus(RequestStatus.DIBATALKAN));
        model.addAttribute("revisi", purchaseRequestService.countByStatus(RequestStatus.REVISI));
        return "purchasing/request-list";
    }

    @RequestMapping(value = "/request/getPR", method = RequestMethod.GET)
    public String getPR(@ModelAttribute("number") String prNumber, Model model) {
        PurchaseRequest purchaseRequest = purchaseRequestService.findByPrNumber(prNumber);
        //List<PurchaseRequestItem> purchaseRequestItemList = purchaseRequestService.findAllItemReq(purchaseRequest.getPurchaseReqId());
        model.addAttribute("pr", purchaseRequest);
        model.addAttribute("itemList", purchaseRequest.getRequestItemList());
        return "purchasing/request-detail";
    }

    @GetMapping(value = "/request/addItem/")
    public String addItem(@RequestParam("itemId") List<String> itemId,
                                @RequestParam("quantity") List<Integer> quantity,
                                @RequestParam("itemRemarks") List<String> itemRemarks) {
        for(int i=0; i < itemId.size(); i++) {
            Optional<Inventory> inventory = inventoryService.findByInvId(Stream.of(itemId.get(i)).collect(Collectors.joining()));
            PurchaseRequestCart purchaseCart = new PurchaseRequestCart();
            purchaseCart.setInventory(inventory.get());
            purchaseCart.setQtyReq(quantity.get(i));
            purchaseCart.setQtyRev(0);
            purchaseCart.setItemRemarks(Stream.of(itemRemarks.get(i)).collect(Collectors.joining()));
            purchaseRequestService.addItem(purchaseCart, itemId.get(i));
        }
        return "redirect:/app/purchasing/request";
    }

    @GetMapping(value = "/request/removeItem/{itemId}")
    public ModelAndView removeItem(@PathVariable("itemId") String itemId) {
        purchaseRequestService.removeItem(itemId);
        return purchaseRequest();
    }

    @RequestMapping(value = "/request/submit", method = RequestMethod.POST)
    public String createPr(PurchaseRequest purchaseRequest, PurchaseRequestItem purchaseRequestItem, Model model,
                           @RequestParam("itemId") List<String> itemId,
                           @RequestParam("quantity") List<Integer> quantity,
                           @RequestParam("itemRemarks") List<String> itemRemarks) {
        for(int i=0; i < itemId.size(); i++) {
            Optional<Inventory> inventory = inventoryService.findByInvId(Stream.of(itemId.get(i)).collect(Collectors.joining()));
            PurchaseRequestCart purchaseCart = new PurchaseRequestCart();
            purchaseCart.setInventory(inventory.get());
            purchaseCart.setQtyReq(quantity.get(i));
            purchaseCart.setQtyRev(0);
            purchaseCart.setItemRemarks(Stream.of(itemRemarks.get(i)).collect(Collectors.joining()));
            purchaseRequestService.updateItem(purchaseCart, itemId.get(i));
        }
        model.addAttribute("purchaseRequest", purchaseRequest);
        purchaseRequestService.createPurchaseRequest(purchaseRequest, purchaseRequestItem);
        return "redirect:/app/purchasing/request";
    }

    @RequestMapping(value = "/so/remove/{itemReqId}/{purchaseReqId}", method = RequestMethod.GET)
    public String deleteItemReq(@PathVariable("itemReqId") String itemReqId,
                                @PathVariable("purchaseReqId") String purchaseReqId){
        purchaseRequestService.deleteItemReq(itemReqId);
        return "redirect:/app/purchasing/so/revPR?getPR="+purchaseReqId;
    }

    @RequestMapping(value = "/order/list", method = RequestMethod.GET)
    public ModelAndView poList(){
        ModelAndView mv = new ModelAndView("purchasing/po-list");
        mv.addObject("locations",masterLocationService.findAllLocation());
        mv.addObject("status", RequestStatus.values());
        mv.addObject("poListActive", "active");
        mv.addObject("purchaseOpen", "open");
        return mv;
    }

    @RequestMapping(value = "/order/getPo", method = RequestMethod.GET)
    public ModelAndView getPo(@RequestParam("poId") String poId){
        ModelAndView mv = new ModelAndView("purchasing/po-detail");
        Optional<PurchaseOrder> po = purchaseOrderService.findById(poId);
        mv.addObject("po", po.get());
        mv.addObject("itemList", po.get().getPurchaseOrderDetails());
        return mv;
    }
}
