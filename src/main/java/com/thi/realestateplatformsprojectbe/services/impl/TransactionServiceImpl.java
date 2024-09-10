package com.thi.realestateplatformsprojectbe.services.impl;

import com.thi.realestateplatformsprojectbe.converter.ITransactionConverter;
import com.thi.realestateplatformsprojectbe.dto.request.TransactionRequest;
import com.thi.realestateplatformsprojectbe.dto.response.ResponsePage;
import com.thi.realestateplatformsprojectbe.dto.response.TransactionResponse;
import com.thi.realestateplatformsprojectbe.models.Transaction;
import com.thi.realestateplatformsprojectbe.repositories.ITransactionRepository;
import com.thi.realestateplatformsprojectbe.services.ITransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.Optional;


@Service
public class TransactionServiceImpl implements ITransactionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionServiceImpl.class);

    @Autowired
    private ITransactionConverter transactionConverter;

    @Autowired
    private ITransactionRepository transactionRepository;


    @Override
    public Page<TransactionResponse> findByCode(String keyword, Pageable pageable) {
        Page<Transaction> transactionPage = transactionRepository.findByCode(keyword, pageable);
        return transactionPage.map(transaction -> TransactionResponse.builder()
                .id(transaction.getId())
                .code(transaction.getCode())
                .realEstateId(transaction.getId())
                .employeeId(transaction.getId())
                .buyerId(transaction.getId())
                .SellerId(transaction.getId())
                .amount(transaction.getAmount())
                .createAt(transaction.getCreateAt())
                .commissionFee(transaction.getCommissionFee())
                .description(transaction.getDescription())
                .status(transaction.getStatus())
                .isDeleted(transaction.getIsDeleted())
                .build());
    }

    @Override
    public Page<TransactionResponse> findAll(Pageable pageable) {
        Page<Transaction> transactionPage = transactionRepository.findAll(pageable);
        return transactionPage.map(transaction -> TransactionResponse.builder()
                .id(transaction.getId())
                .code(transaction.getCode())
                .realEstateId(transaction.getRealEstate() != null ? transaction.getRealEstate().getId() : null)
                .employeeId(transaction.getEmployee() != null ? transaction.getEmployee().getId() : null)
                .buyerId(transaction.getBuyer() != null ? transaction.getBuyer().getId() : null)
                .SellerId(transaction.getSeller() != null ? transaction.getSeller().getId() : null)
                .amount(transaction.getAmount())
                .createAt(transaction.getCreateAt())
                .commissionFee(transaction.getCommissionFee())
                .description(transaction.getDescription())
                .status(transaction.getStatus())
                .isDeleted(transaction.getIsDeleted())
                .build());
    }


    @Override
    public Optional<Transaction> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public ResponsePage save(TransactionRequest transactionRequest) {
        return null;
    }

    @Override
    public Page<Transaction> searchTransaction(String keyword, Pageable pageable) {
        return null;
    }
}
