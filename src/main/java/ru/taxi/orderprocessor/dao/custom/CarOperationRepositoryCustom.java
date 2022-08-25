package ru.taxi.orderprocessor.dao.custom;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.taxi.orderprocessor.dto.FindCarsDto;
import ru.taxi.orderprocessor.entity.CarEntity;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.String.format;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class CarOperationRepositoryCustom {

    private final EntityManager em;

    public List<CarEntity> find(FindCarsDto.ECriteria criteria, FindCarsDto.Sort sort) {

        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

        CriteriaQuery<CarEntity> query = criteriaBuilder.createQuery(CarEntity.class);

        Root<CarEntity> root=query.from(CarEntity.class);

        List<Predicate> predicates=new ArrayList<>();


        if (criteria != null) {
            Optional.ofNullable(criteria.getColor()).ifPresent(optional->predicates.add(criteriaBuilder.equal(root.get("color"),optional)));
            Optional.ofNullable(criteria.getCarClass()).ifPresent(optional->predicates.add(criteriaBuilder.equal(root.get("carClass"),optional)));

            Optional.ofNullable(criteria.getPriorityClass()).ifPresent(optional->predicates.add(criteriaBuilder.equal(root.get("priorityClass"),optional)));
            Optional.ofNullable(criteria.getIssuedAtGreater()).ifPresent(optional->predicates.add(criteriaBuilder.greaterThan(root.get("issuedAt"),optional)));
            Optional.ofNullable(criteria.getIssuedAtLower()).ifPresent(optional->predicates.add(criteriaBuilder.lessThan(root.get("issuedAt"),optional)));
        }


        Optional.ofNullable(sort).ifPresent(sortingStrategy->{
            validateSort(sortingStrategy);
            if (sortingStrategy.getSortOrder().equals(SortOrder.ASCENDING)) {
                query.orderBy(criteriaBuilder.asc(root.get(sortingStrategy.getSortBy())));
            }
            else if(sortingStrategy.getSortOrder().equals(SortOrder.DESCENDING)){
                query.orderBy(criteriaBuilder.desc(root.get(sortingStrategy.getSortBy())));
            }

        });
        query.where(predicates.toArray(new Predicate[0]));
        return em.createQuery(query).getResultList();


    }

    private void validateSort(FindCarsDto.Sort sortingStrategy){

        boolean anyMatch= Arrays.stream(CarEntity.class.getDeclaredFields()).anyMatch(field->field.getName().equals(sortingStrategy.getSortBy()));
        if(!anyMatch){
            throw new IllegalArgumentException(format("Field %s does not exist in entity!",sortingStrategy.getSortBy()));
        }

    }

}
