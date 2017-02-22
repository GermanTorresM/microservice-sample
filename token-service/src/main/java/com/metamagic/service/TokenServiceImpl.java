package com.metamagic.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
public class TokenServiceImpl implements TokenService{

	@Override
	public String generateToken(String data) {
		return UUID.randomUUID().toString()+" | "+data;
	}
	
	@Override
	public void updateTokenLastAccessTime(String tokenId) {
		// TODO Auto-generated method stub
		
	}
}
