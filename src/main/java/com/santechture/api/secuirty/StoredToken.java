package com.santechture.api.secuirty;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class StoredToken {

    private static ConcurrentHashMap<String, String> usersTokens = new ConcurrentHashMap<>();

    public void putToken(String key, String value) {
        usersTokens.put(key, value);
    }

    public void removeTokenByKey(String key) {;
        usersTokens.remove(key);
    }

    public String getTokenByUserName(String key) {
        return usersTokens.get(key);
    }

    public void removeTokenByValue(String jwt) {
        for(Map.Entry<String, String> entry : usersTokens.entrySet()){
            if(entry.getValue().equals(jwt)) {
                usersTokens.remove(entry.getKey());
            }
        }
    }

    public boolean checkIfTokenExists(String token) {
        return usersTokens.values().stream().anyMatch(e -> e.equals(token));
    }
}