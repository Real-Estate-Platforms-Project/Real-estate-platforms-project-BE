package com.thi.realestateplatformsprojectbe.controllers.admin;

import com.thi.realestateplatformsprojectbe.dto.response.TransactionResponse;
import com.thi.realestateplatformsprojectbe.services.impl.TransactionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/transactions")
@CrossOrigin(origins = "*")

public class HomeTransactionController {

    @Autowired
    private TransactionServiceImpl transactionService;


    private static final int DEFAULT_PAGE = 0;

    public static final int PAGE_SIZE = 2;


    @GetMapping
    public ResponseEntity<Page<TransactionResponse>> homeTransaction(@RequestParam(defaultValue = "", required = false) String search,
                                                                     @PageableDefault(page = DEFAULT_PAGE, size = PAGE_SIZE) Pageable pageable) {
        Page<TransactionResponse> transactionPage;
        if (!search.isEmpty()) {
            transactionPage = transactionService.findByCode(search, pageable);
        } else {
            transactionPage = transactionService.findAll(pageable);
        }

        if (transactionPage.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        }

        return new ResponseEntity<>(transactionPage, HttpStatus.OK);
    }
}
