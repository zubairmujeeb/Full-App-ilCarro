package com.car.rentservice.repositories.search;

import com.sun.istack.Nullable;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import javax.persistence.criteria.Predicate;

public interface Specification<T> extends Serializable {

    @Nullable
    Predicate toPredicate(Root<T> root, CriteriaQuery<?> query,
                          CriteriaBuilder criteriaBuilder);
}
