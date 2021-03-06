package id.xaxxis.inventory.service.spesification.purchasing;

import id.xaxxis.inventory.entity.master.user.User;
import id.xaxxis.inventory.entity.purchasing.request.PurchaseRequest;
import id.xaxxis.inventory.enums.RequestStatus;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@Component
public class PrLocationSpesification implements Specification<PurchaseRequest> {

    @Override
    public Predicate toPredicate(Root<PurchaseRequest> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        if(user.getRoles().toString().contains("PURCHASING")){
            return criteriaBuilder.equal(root.get("requestStatus"), RequestStatus.valueOf("DIPROSES"));
        }else if(user.getRoles().toString().contains("SO")){
            return criteriaBuilder.equal(root.get("requestStatus"), RequestStatus.valueOf(""));
        }
        return criteriaBuilder.equal(root.get("outlet").get("masterLocation") ,  user.getOutlet().getMasterLocation());
    }
}
