package com.thi.realestateplatformsprojectbe.services.impl;

import com.thi.realestateplatformsprojectbe.converter.ITransactionConverter;
import com.thi.realestateplatformsprojectbe.dto.request.TransactionRequest;
import com.thi.realestateplatformsprojectbe.dto.response.ResponsePage;
import com.thi.realestateplatformsprojectbe.dto.response.TransactionResponse;
import com.thi.realestateplatformsprojectbe.models.Buyer;
import com.thi.realestateplatformsprojectbe.models.Employee;
import com.thi.realestateplatformsprojectbe.models.RealEstate;
import com.thi.realestateplatformsprojectbe.models.Seller;
import com.thi.realestateplatformsprojectbe.models.Transaction;
import com.thi.realestateplatformsprojectbe.repositories.ITransactionRepository;
import com.thi.realestateplatformsprojectbe.services.ITransactionService;
import jakarta.persistence.EntityNotFoundException;
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

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private SellerService sellerService;

    @Autowired
    private BuyerService buyerService;

    @Autowired
    private RealEstateService realEstateService;


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
    public ResponsePage update(TransactionRequest transactionRequest) {
        LOGGER.info("TransactionService -> update invoked!!!");

        Optional<Transaction> transactionOptional = transactionRepository.findById(transactionRequest.getId());
        if (transactionOptional.isEmpty()) {
            return ResponsePage.builder()
                    .data(null)
                    .message("Giao dịch không tồn tại!")
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }

        Transaction transaction = transactionOptional.get();

        transaction.setCode(transactionRequest.getCode());
        transaction.setAmount(transactionRequest.getAmount());
        transaction.setCreateAt(transactionRequest.getCreateAt());
        transaction.setCommissionFee(transactionRequest.getCommissionFee());
        transaction.setDescription(transactionRequest.getDescription());
        transaction.setStatus(transactionRequest.getStatus());
        transaction.setIsDeleted(transactionRequest.getIsDeleted());

        Optional<Employee> employeeOptional = employeeService.getEmployeeById(transactionRequest.getEmployee());
        if (employeeOptional.isPresent()) {
            transaction.setEmployee(employeeOptional.get());
        } else {
            throw new EntityNotFoundException("Employee not found");
        }

        RealEstate realEstate = realEstateService.findById(transactionRequest.getRealEstate());
        if (realEstate == null) {
            throw new EntityNotFoundException("Real Estate not found");
        } else {
            transaction.setRealEstate(realEstate);
        }

        Buyer buyer = buyerService.getBuyerById(transactionRequest.getBuyer());
        if (buyer == null) {
            throw new EntityNotFoundException("Buyer not found");
        } else {
            transaction.setBuyer(buyer);
        }

        Seller seller = sellerService.getSellerById(transactionRequest.getSeller());
        if (seller == null) {
            throw new EntityNotFoundException("Seller not found");
        } else {
            transaction.setSeller(seller);
        }

        // Lưu transaction sau khi cập nhật
        try {
            transactionRepository.save(transaction);
            return ResponsePage.builder()
                    .data(null)
                    .message("Cập nhật giao dịch thành công")
                    .status(HttpStatus.OK)
                    .build();
        } catch (Exception e) {
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
