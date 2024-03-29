package com.example.demo.src.company;



import com.example.demo.config.BaseException;
import com.example.demo.src.company.model.*;
import com.example.demo.utils.JwtService;
import com.example.demo.utils.SHA256;
import com.fasterxml.jackson.databind.JsonSerializable.Base;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.demo.config.BaseResponseStatus.*;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.CountDownLatch;

// Service Create, Update, Delete 의 로직 처리
@Service
public class CompanyService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final CompanyDao companyDao;
    private final CompanyProvider companyProvider;
    private final JwtService jwtService;


    @Autowired
    public CompanyService(CompanyDao companyDao, CompanyProvider companyProvider, JwtService jwtService) {
        this.companyDao = companyDao;
        this.companyProvider = companyProvider;
        this.jwtService = jwtService;

    }


    public void createCompany(Company req) throws BaseException {
        Company company = new Company();
        company.setName(req.getName());
        company.setExplanation(req.getExplanation());
        company.setTotalEmployees(req.getTotalEmployees());
        int result = 0;
        try{
            result = companyDao.createCompany(company);
        } catch (Exception exception){
            logger.error("App - createCompany Service Error", exception);
			throw new BaseException(DATABASE_ERROR);
        }
        if (result == 0){
            throw new BaseException(CREATE_FAIL_COMPANY);
        }
    }
    
}
