package id.xaxxis.inventory.service.spesification.purchasing;

import id.xaxxis.inventory.entity.purchasing.order.PurchaseOrder;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class POSpesification implements Specification<PurchaseOrder> {
    @Override
    public Predicate toPredicate(Root<PurchaseOrder> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        return null;
    }
}
