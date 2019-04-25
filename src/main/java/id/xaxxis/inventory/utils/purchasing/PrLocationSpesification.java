package id.xaxxis.inventory.utils.purchasing;

import id.xaxxis.inventory.entity.master.user.User;
import id.xaxxis.inventory.entity.purchasing.pr.PurchaseRequest;
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
        return criteriaBuilder.equal(root.get("masterLocation"),  user.getMasterLocation());
    }
}
