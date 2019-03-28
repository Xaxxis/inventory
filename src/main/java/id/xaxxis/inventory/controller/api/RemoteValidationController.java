package id.xaxxis.inventory.controller.api;

import id.xaxxis.inventory.entity.master.item.MasterItem;
import id.xaxxis.inventory.entity.master.user.User;
import id.xaxxis.inventory.service.master.item.MasterItemService;
import id.xaxxis.inventory.service.master.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RemoteValidationController {

    private final UserService userService;

    private final MasterItemService masterItemService;

    @Autowired
    public RemoteValidationController(UserService userService, MasterItemService masterItemService) {
        this.userService = userService;
        this.masterItemService = masterItemService;
    }

    @RequestMapping(value = "/app/user/emailCheck", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity checkEmail(@RequestParam("email") String email){
        User user = userService.findByEmail(email);
        if(user == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }else
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/app/user/usernameCheck", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity checkUsername(@RequestParam("username") String username) {
        User user = userService.findByUsername(username);
        if(user == null ){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }else
            return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/app/inv/item/barcodeCheck", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity checkBarcode(@RequestParam("itemBarcode") String itemBarcode) {
        MasterItem masterItem = masterItemService.findByItemBarcode(itemBarcode);
        if(masterItem == null){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

}
