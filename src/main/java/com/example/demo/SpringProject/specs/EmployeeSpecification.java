package com.example.demo.SpringProject.specs;


import com.example.demo.SpringProject.model.EmployeeEntity;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Path;
import java.util.Arrays;
import java.util.List;

public class EmployeeSpecification {

    public static Specification<EmployeeEntity> likeOperation(
            final String column, final String keyword) {
        if (keyword == null || column == null) {
            return null;
        }
        // SELECT ... FROM Customer c WHERE c.name LIKE %name%;
        return (root, query, criteriaBuilder) -> {
            List<String> sts = Arrays.asList(column.split("\\."));
            Path path = root;
            for (int i = 0; i < sts.size() - 1; i++) path = path.get(sts.get(i));
            return criteriaBuilder.like(path.get(sts.get(sts.size() - 1)).as(String.class), String.format("%%%s%%", keyword));
        };
    }

    public static Specification<EmployeeEntity> ContainsOperation(
            final String column, final String keyword) {
        if (keyword == null || column == null) {
            return null;
        }
        return (root, query, criteriaBuilder) -> {
            List<String> sts = Arrays.asList(column.split("\\."));
            Path path = root;
            for (int i = 0; i < sts.size() - 1; i++) path = path.get(sts.get(i));
            return criteriaBuilder.like(path.get(sts.get(sts.size() - 1)).as(String.class), String.format("%%%s%%", keyword));
        };
    }

    public static Specification<EmployeeEntity> equalsOperation(
            final String column, final String keyword) {
        if (keyword == null || column == null) {
            return null;
        }
        // SELECT ... FROM Customer c WHERE c.name equal %name%;
        return (root, query, criteriaBuilder) -> {
            List<String> sts = Arrays.asList(column.split("\\."));
            Path path = root;
            for (int i = 0; i < sts.size() - 1; i++) path = path.get(sts.get(i));
            return criteriaBuilder.equal(path.get(sts.get(sts.size() - 1)).as(String.class), String.format("%%%s%%", keyword));
        };
    }
}