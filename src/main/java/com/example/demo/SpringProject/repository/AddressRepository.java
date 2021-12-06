package com.example.demo.SpringProject.repository;

import com.example.demo.SpringProject.model.AddressEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends CrudRepository<AddressEntity,Integer> {

}