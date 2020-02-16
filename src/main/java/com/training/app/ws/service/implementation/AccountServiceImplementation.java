package com.training.app.ws.service.implementation;

import java.util.Arrays;
import java.util.List;

import com.training.app.ws.io.entity.AccountEntity;
import com.training.app.ws.repository.AccountRepository;
import com.training.app.ws.service.AccountService;
import com.training.app.ws.shared.Utils;
import com.training.app.ws.shared.dto.AccountDto;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AccountServiceImplementation implements AccountService {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    Utils utils;

    @Override
    public AccountDto createAccount(AccountDto account) {

        // checks if is a full name
        String[] splitedName = account.getName().split("\\s+");
        List<String> splitedNameAsList = Arrays.asList(splitedName);
        if (splitedNameAsList.size() > 1) {

            AccountEntity accountEntity = new AccountEntity();
            BeanUtils.copyProperties(account, accountEntity);

            String publicUid = utils.generateUId(30);
            accountEntity.setUid(publicUid);

            AccountEntity storedAccountDetails = accountRepository.save(accountEntity);

            AccountDto returnValue = new AccountDto();
            BeanUtils.copyProperties(storedAccountDetails, returnValue);

            return returnValue;
        } throw new RuntimeException("name field : It must be a full name");
    }

    @Override
    public AccountDto getAccountByUId(String uId) {
        AccountDto returnValue = new AccountDto();
        AccountEntity accountEntity = accountRepository.findByUid(uId);

        if(accountEntity == null)
            throw new RuntimeException("not found by uid" + uId);

        BeanUtils.copyProperties(accountEntity, returnValue);

        return returnValue;
    }
}