package com.coderscampus.assignment13.repository;

import com.coderscampus.assignment13.domain.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
List<Address> findAddressByUserId(Long userId);

}
