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
import org.springframework.http.HttpStatus;
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
                .realEstate(transaction.getBuyer().getCode())
                .employee(transaction.getEmployee().getName())
                .buyer(transaction.getBuyer().getName())
                .seller(transaction.getSeller().getName())
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
        Page<TransactionResponse> transactionPageResponse = transactionPage.map(transaction -> TransactionResponse.builder()
                .id(transaction.getId())
                .code(transaction.getCode())
                .realEstate(transaction.getRealEstate().getCode()) // Lấy mã bất động sản
                .employee(transaction.getEmployee().getCode()) // Lấy tên nhân viên
                .buyer(transaction.getBuyer().getName()) // Lấy tên bên mua
                .seller(transaction.getSeller().getName()) // Lấy tên bên bán
                .amount(transaction.getAmount())
                .createAt(transaction.getCreateAt())
                .commissionFee(transaction.getCommissionFee())
                .description(transaction.getDescription())
                .status(transaction.getStatus())
                .isDeleted(transaction.getIsDeleted())
                .build());

        return transactionPageResponse;
    }



    @Override
    public Optional<Transaction> findById(Long id) {
        return transactionRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        transactionRepository.deleteById(id);
    }

    @Override
    public ResponsePage save(TransactionRequest transactionRequest) {
        LOGGER.info("TransactionService -> save invoked!!!");

        Optional<Transaction> existingTransaction = transactionRepository.findByCode(transactionRequest.getCode());
        if (existingTransaction.isPresent()) {
            return ResponsePage.builder()
                    .data(null)
                    .message("Mã giao dịch đã tồn tại!")
                    .status(HttpStatus.BAD_REQUEST)
                    .build();
        }

        Transaction transaction = transactionConverter.dtoToEntity(transactionRequest);
        try {
            transactionRepository.save(transaction);
            return ResponsePage.builder()
                    .data(null)
                    .message("giao dịch đã được tạo thành công")
                    .status(HttpStatus.OK)
                    .build();
        }  catch (Exception e) {
            return ResponsePage.builder()
                    .data(null)
                    .message(e.getMessage())
                    .status(HttpStatus.BAD_REQUEST)
                    .build();
        }
    }

    @Override
    public Page<TransactionResponse> searchTransaction(String keyword, Pageable pageable) {
        Page<Transaction> transactionPage = transactionRepository.searchTransactionByCodeAndDescription(keyword, pageable);
        return transactionPage.map(transaction -> TransactionResponse.builder()
                .id(transaction.getId())
                .code(transaction.getCode())
                .realEstate(transaction.getRealEstate().getCode())
                .employee(transaction.getEmployee().getCode())
                .buyer(transaction.getBuyer().getName())
                .seller(transaction.getSeller().getName())
                .amount(transaction.getAmount())
                .createAt(transaction.getCreateAt())
                .commissionFee(transaction.getCommissionFee())
                .description(transaction.getDescription())
                .status(transaction.getStatus())
                .isDeleted(transaction.getIsDeleted())
                .build());
    }
}
