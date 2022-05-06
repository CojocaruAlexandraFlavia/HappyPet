package com.example.happypet.model.view_model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.happypet.data.repository.TokenRepository;
import com.example.happypet.data.repository.UserRepository;
import com.example.happypet.model.Location;
import com.example.happypet.model.Token;

import java.util.List;

import javax.inject.Inject;

public class TokenViewModel extends AndroidViewModel {
    private final TokenRepository repository;

    @Inject
    public TokenViewModel(@NonNull Application application) {
        super(application);
        repository = new TokenRepository(application);
    }

    public List<Token> getAll(){
        return repository.getAll();
    }
    public void insertToken(Token token){
        repository.insertToken(token);
    }

    public Token findByTokenValue(String tokenValue){
        return repository.findByTokenValue(tokenValue);
    }
    public List<Token> findByLocationId(Long locationId){
        return repository.findByLocationId(locationId);
    }


}
