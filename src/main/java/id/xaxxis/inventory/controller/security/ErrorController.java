package id.xaxxis.inventory.controller.security;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.annotation.security.PermitAll;

@Controller
public class ErrorController {
    @RequestMapping("/404")
    @PermitAll
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String notFound() {
        return "/error/404";
    }

    @RequestMapping("/403")
    @PermitAll
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String forbidden() {
        return "/error/403";
    }

    @RequestMapping("/500")
    @PermitAll
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String internalServerError() {
        return "/error/500";
    }

    @RequestMapping( value = "Access_Denied", method = RequestMethod.GET)
    public String accessDeniedPage(Model model) {
        model.addAttribute("userss", getPrincipal());

        return "error/accessDenied";
    }

    private String getPrincipal(){
        String userName = null;
        String fullName = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(principal instanceof UserDetails) {
            userName =((UserDetails)principal).getUsername();
        }else{
            userName = principal.toString();
        }
        return userName;
    }

}
