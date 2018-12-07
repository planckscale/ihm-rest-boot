package com.home.ihm.demo.repository;

import com.home.ihm.demo.domain.Advertiser;
import org.springframework.data.jpa.repository.JpaRepository;

// https://www.baeldung.com/spring-data-repositories
public interface AdvertiserRepository extends JpaRepository<Advertiser, Long> { // inherits PagingAndSortingRepository

}
