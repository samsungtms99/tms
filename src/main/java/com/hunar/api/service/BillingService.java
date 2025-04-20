package com.hunar.api.service;

import com.hunar.api.bean.BilingBean;
import com.hunar.api.bean.BillingInvoice;
import com.hunar.api.exceptionHandling.util.FmkException;

import java.util.List;

public interface BillingService {
    BilingBean createBillForOrder(BilingBean billingBean) throws FmkException;

    BilingBean updateBillForOrder(BilingBean billingBean) throws FmkException;

    List<BilingBean> getListOfAllBills();

    String deleteBillsById(int idBill);

    BillingInvoice generateBillingInvoice(int idBill) throws FmkException;
}
