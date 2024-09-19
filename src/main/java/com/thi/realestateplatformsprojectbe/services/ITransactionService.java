package com.thi.realestateplatformsprojectbe.services;

import com.thi.realestateplatformsprojectbe.dto.request.TransactionRequest;
import com.thi.realestateplatformsprojectbe.dto.response.ResponsePage;
import com.thi.realestateplatformsprojectbe.dto.response.TransactionResponse;
import com.thi.realestateplatformsprojectbe.models.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ITransactionService {

    Page<TransactionResponse> findByCode(String keyword, Pageable pageable);

    Page<TransactionResponse> findAll(Pageable pageable);

    Optional<Transaction> findById(Long id);

    void deleteById(Long id);

    ResponsePage save(TransactionRequest transactionRequest);

    ResponsePage update(TransactionRequest transactionRequest);

    Page<TransactionResponse> searchTransaction(String keyword, Pageable pageable);
}
