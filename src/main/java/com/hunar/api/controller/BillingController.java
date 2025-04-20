package com.hunar.api.controller;

import com.hunar.api.bean.BilingBean;
import com.hunar.api.bean.BillingInvoice;
import com.hunar.api.constant.Constants;
import com.hunar.api.exceptionHandling.util.FmkException;
import com.hunar.api.service.BillingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = Constants.CROSS_ORIGIN)
@RequestMapping("/billing")
public class BillingController {

    @Autowired
    BillingService billingService;

    @PostMapping(value = "/createBillForOrder")
    public BilingBean createBillForOrder (@RequestBody BilingBean billingBean) throws FmkException {
        return  billingService.createBillForOrder(billingBean);
    }

    @PutMapping(value = "/updateBillForOrder")
    public BilingBean updateBillForOrder (@RequestBody BilingBean billingBean) throws FmkException {
        return  billingService.updateBillForOrder(billingBean);
    }

    @GetMapping(value = "/getListOfAllBills")
    public List<BilingBean> getListOfAllBills(){
        return billingService.getListOfAllBills();
    }

    @DeleteMapping(value = "/deleteBillsById/{idBill}")
    public String deleteBillsById(@PathVariable("idBill") int idBill){
        return billingService.deleteBillsById(idBill);
    }

    @PostMapping(value = "/generateBillingInvoice/{idBill}")
    public BillingInvoice generateBillingInvoice(@PathVariable("idBill") int idBill) throws FmkException {
        return  billingService.generateBillingInvoice(idBill);
    }



}
