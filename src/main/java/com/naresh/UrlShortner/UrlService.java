package com.naresh.UrlShortner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class UrlService {

    private final RedisTemplate<String,String> redisTemplate;
    private final AtomicInteger counter=new AtomicInteger(0);
    private static final int EXPIRATIOIN_DAY=7;

    public  String generateHash(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString().substring(0,5);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error generating hash", e);
        }
    }

    public UrlService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
    public String shortenUrl(String originalUrl){
        int id=counter.incrementAndGet();
        String shortUrl=""+generateHash(originalUrl);
        redisTemplate.opsForValue().set(shortUrl,originalUrl,EXPIRATIOIN_DAY, TimeUnit.DAYS);
        return "http://localhost:8087/"+shortUrl;
    }
    public String getOriginalUrl(String shortUrl){
        System.out.println(redisTemplate.opsForValue().get(shortUrl));
        return redisTemplate.opsForValue().get(shortUrl);
    }
}
