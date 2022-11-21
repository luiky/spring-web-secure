package com.mdai.web.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mdai.web.model.Direccion;

@Repository
public interface DireccionRepository extends CrudRepository<Direccion, Long> {

}
