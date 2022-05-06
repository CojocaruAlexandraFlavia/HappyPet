package com.example.happypet.data.repository;

import android.app.Application;

import com.example.happypet.data.dao.AnimalDao;
import com.example.happypet.data.dao.ClientDao;
import com.example.happypet.data.dao.TokenDao;
import com.example.happypet.model.Animal;
import com.example.happypet.model.Location;
import com.example.happypet.model.Token;
import com.example.happypet.util.RoomDatabaseImpl;

import java.util.List;

import javax.inject.Inject;

public class TokenRepository {
    private final TokenDao tokenDao;
    @Inject
    public TokenRepository(Application application)
    {
        RoomDatabaseImpl db = RoomDatabaseImpl.getDatabase(application);
        tokenDao = db.tokenDao();

    }

    public List<Token> getAll(){
        return tokenDao.getAll();
    }
    public void insertToken(Token token){
        tokenDao.insertToken(token);
    }

    public Token findByTokenValue(String tokenValue){
        return tokenDao.findByTokenValue(tokenValue);
    }
    public List<Token> findByLocationId(Long id ){
        return tokenDao.findByLocationId(id);
    }
}
