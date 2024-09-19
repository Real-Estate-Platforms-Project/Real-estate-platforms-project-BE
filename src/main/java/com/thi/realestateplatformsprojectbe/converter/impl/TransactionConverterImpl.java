package com.thi.realestateplatformsprojectbe.converter.impl;

import com.thi.realestateplatformsprojectbe.converter.ITransactionConverter;
import com.thi.realestateplatformsprojectbe.dto.request.TransactionRequest;
import com.thi.realestateplatformsprojectbe.models.Buyer;
import com.thi.realestateplatformsprojectbe.models.Employee;
import com.thi.realestateplatformsprojectbe.models.RealEstate;
import com.thi.realestateplatformsprojectbe.models.Seller;
import com.thi.realestateplatformsprojectbe.models.Transaction;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TransactionConverterImpl implements ITransactionConverter {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionConverterImpl.class);

    @Override
    public Transaction dtoToEntity(TransactionRequest transactionRequest) {
        LOGGER.debug("TransactionConverterImpl -> dtoToEntity");
        Transaction transaction = new Transaction();
        BeanUtils.copyProperties(transactionRequest, transaction);
        Employee employee = Employee.builder().id(transactionRequest.getEmployee()).build();
        RealEstate realEstate = RealEstate.builder().id(transactionRequest.getRealEstate()).build();
        Buyer buyer = Buyer.builder().id(transactionRequest.getBuyer()).build();
        Seller seller = Seller.builder().id(transactionRequest.getSeller()).build();
        transaction.setEmployee(employee);
        transaction.setRealEstate(realEstate);
        transaction.setBuyer(buyer);
        transaction.setSeller(seller);
        return transaction;
    }
}
