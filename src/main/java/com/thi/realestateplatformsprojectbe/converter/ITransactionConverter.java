package com.thi.realestateplatformsprojectbe.converter;

import com.thi.realestateplatformsprojectbe.dto.request.TransactionRequest;
import com.thi.realestateplatformsprojectbe.models.Transaction;

public interface ITransactionConverter {

    Transaction dtoToEntity (TransactionRequest transactionRequest);
}
