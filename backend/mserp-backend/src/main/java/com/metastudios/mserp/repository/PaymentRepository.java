package com.metastudios.mserp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.metastudios.mserp.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByInvoiceId(Long invoiceId);

    List<Payment> findByStatus(String status);

    List<Payment> findByPaymentMethod(String paymentMethod);
}
