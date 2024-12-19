package com.naresh.UrlShortner;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping
@CrossOrigin(origins = "*")
public class UrlController {
    private final UrlService urlService;
    public UrlController(UrlService urlService){
        this.urlService=urlService;
    }

    @GetMapping
    public String shortenUrl(@RequestParam String url){
        return urlService.shortenUrl(url);
    }
    @GetMapping("/{shortUrl}")
    public void redirectToOriginalUrl(HttpServletResponse res, @PathVariable String shortUrl ) throws IOException {

        res.sendRedirect(urlService.getOriginalUrl(shortUrl));
    }
}
