package com.thi.realestateplatformsprojectbe.repositories;

import com.thi.realestateplatformsprojectbe.models.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ITransactionRepository extends PagingAndSortingRepository<Transaction, Long> {

    Page<Transaction> findByCode(String code, Pageable pageable);

    Page<Transaction> findAll(Pageable pageable);

    Optional<Transaction> findById(Long id);

    Transaction save(Transaction transaction);

    void  deleteById(Long id);
}
