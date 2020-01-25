package com.api_dev_fundamentals.APIDevFundamentals.repositories;

import java.io.Serializable;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentityGenerator;

import com.api_dev_fundamentals.APIDevFundamentals.models.User;

public class CustomUserIdGeneration extends IdentityGenerator {

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object obj) throws HibernateException {
        if (obj == null) {
            throw new HibernateException(new NullPointerException());
        }

        Integer userId = ((User) obj).getId();
        if (userId == null) {
            return super.generate(session, obj);
        }
        return userId;
    }

}

