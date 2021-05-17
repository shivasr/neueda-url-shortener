package com.app.neueda.repository;

import com.app.neueda.model.URLMappingRecord;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShortURLRepository extends CrudRepository<URLMappingRecord, Long> {
}
