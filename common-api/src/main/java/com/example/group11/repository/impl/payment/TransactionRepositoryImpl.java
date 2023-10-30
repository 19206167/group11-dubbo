package com.example.group11.repository.impl.payment;

import com.example.group11.repository.custom.TransactionRepositoryCustom;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class TransactionRepositoryImpl implements TransactionRepositoryCustom {
    @PersistenceContext
    EntityManager entityManager;

}
