package com.gbadegesin.urlgen.repository;


import com.gbadegesin.urlgen.model.Url;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UrlGenRepository extends JpaRepository<Url, Long> {
}
