package com.hunar.api.service.impl;

import com.hunar.api.entity.CompanyEntity;
import com.hunar.api.repository.CompanyRepository;
import com.hunar.api.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    CompanyRepository companyRepository;

    @Override
    public CompanyEntity createCompany(CompanyEntity companyEntity) {
        if (companyEntity!=null){
           companyEntity= companyRepository.save(companyEntity);
           return companyEntity;
        }
        return null;
    }
}
