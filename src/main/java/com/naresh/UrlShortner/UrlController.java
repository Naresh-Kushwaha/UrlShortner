package com.naresh.UrlShortner;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;

@RestController
@CrossOrigin(origins = "*")
public class UrlController {
    private final UrlService urlService;
    public UrlController(UrlService urlService){
        this.urlService=urlService;
    }

    @PostMapping("/shorten")
    public String shortenUrl(@RequestBody String url){
        return urlService.shortenUrl(url);
    }
    @GetMapping("/{shortUrl}")
    public void redirectToOriginalUrl(@PathVariable String shortUrl,HttpServletResponse response ) throws IOException {
      String s=urlService.getOriginalUrl(shortUrl);
      s=s.substring(1,s.length()-1);
        response.sendRedirect(s);

    }

}
