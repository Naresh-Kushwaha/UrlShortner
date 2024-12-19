package com.naresh.UrlShortner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class UrlService {

    private final RedisTemplate<String,String> redisTemplate;
    private final AtomicInteger counter=new AtomicInteger(0);
    private static final int EXPIRATIOIN_DAY=7;

    public UrlService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
    public String shortenUrl(String originalUrl){
        int id=counter.incrementAndGet();
        String shortUrl= ""+originalUrl.charAt(0)
        +originalUrl.charAt(originalUrl.length()-1)
                +originalUrl.charAt(originalUrl.length()/2);

        redisTemplate.opsForValue().set(shortUrl,originalUrl,EXPIRATIOIN_DAY, TimeUnit.DAYS);
        return "http://localhost:8080/"+shortUrl;
    }
    public String getOriginalUrl(String shortUrl){
        System.out.println(redisTemplate.opsForValue().get(shortUrl));
        return redisTemplate.opsForValue().get(shortUrl);
    }
}
