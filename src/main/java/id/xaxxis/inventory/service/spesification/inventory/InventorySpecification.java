package id.xaxxis.inventory.service.spesification.inventory;

import id.xaxxis.inventory.entity.inventory.Inventory;
import id.xaxxis.inventory.entity.master.user.User;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class InventorySpecification implements Specification<Inventory> {

    @Override
    public Predicate toPredicate(Root<Inventory> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        if(user.getRoles().toString().contains("HO")){
           return criteriaBuilder.and(criteriaBuilder.equal(root.get("masterLocation"), user.getOutlet().getMasterLocation()));
        }
        return criteriaBuilder.and(criteriaBuilder.equal(root.get("outlet"), user.getOutlet()));
    }
}
