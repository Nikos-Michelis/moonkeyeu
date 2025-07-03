package com.moonkeyeu.core.api.launch.services;

import com.moonkeyeu.core.api.launch.dto.CrawlerDTO;
import com.moonkeyeu.core.api.launch.model.SocialMediaCrawler;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
@Service
public class CrawlerService {
    public ResponseEntity<Object> handlePreview(String userAgent, Object id, String type, CrawlerDTO crawlerDTO) {
        if (!isCrawler(userAgent)) {
            String clientAppUrl = "https://www.moonkeyeu.com/" + type.toLowerCase() + "/" + id;
            HttpHeaders headers = new HttpHeaders();
            headers.add("Location", clientAppUrl);
            return new ResponseEntity<>(headers, HttpStatus.FOUND);
        }

        String jsonLdScript = """
            <script type="application/ld+json">
                {
                    "@context": "https://schema.org",
                    "@type": "WebPage",
                    "url": "https://www.moonkeyeu.com/%s/%s",
                    "name": "MoonkeyEU",
                    "description": "%s",
                    "image": "%s",
                    "datePublished": "%s",
                    "dateModified": "%s",
                    "inLanguage": "en-US"
                }
            </script>
        """.formatted(
                type,
                id,
                crawlerDTO.getDescription(),
                crawlerDTO.getImage(),
                crawlerDTO.getDatePublished(),
                crawlerDTO.getDateModified()
        );

        String html = """
                <!DOCTYPE html>
                <html lang="en">
                <head>
                    <meta charset="UTF-8">
                    <title>%s</title>
                    <meta property="og:type" content="article" />
                    <meta property="og:title" content="%s" />
                    <meta property="og:description" content="%s" />
                    <meta property="og:image" content="%s" />
                    <meta property="og:image:alt" content="%s" />
                    <meta property="og:url" content="https://www.moonkeyeu.com/%s/%s" />
                    <meta property="og:site_name" content="MoonkeyEU" />
                    <meta property="og:locale" content="en_US" />
                    <meta name="twitter:card" content="summary_large_image" />
                    <meta name="twitter:title" content="%s" />
                    <meta name="twitter:description" content="%s" />
                    <meta name="twitter:image" content="%s" />
                    <meta name="twitter:image:alt" content="%s" />
                    <meta name="author" content="MoonkeyEU" />
                    <link rel="canonical" href="https://www.moonkeyeu.com/%s/%s" />
                    %s
                </head>
                <body></body>
                </html>
                """.formatted(
                        crawlerDTO.getTitle(),
                        crawlerDTO.getTitle(),
                        crawlerDTO.getDescription(),
                        crawlerDTO.getImage(),
                        crawlerDTO.getTitle(),
                        type,
                        id,
                        crawlerDTO.getTitle(),
                        crawlerDTO.getDescription(),
                        crawlerDTO.getImage(),
                        crawlerDTO.getTitle(),
                        type,
                        id,
                        jsonLdScript
        );

        return ResponseEntity.ok()
                .contentType(MediaType.TEXT_HTML)
                .body(html);
    }

    public ResponseEntity<Object> getDefaultPreview(String userAgent, String type, CrawlerDTO crawlerDTO) {
       if (!isCrawler(userAgent)) {
            String clientAppUrl = "https://www.moonkeyeu.com/" + type.toLowerCase();
            HttpHeaders headers = new HttpHeaders();
            headers.add("Location", clientAppUrl);
            return new ResponseEntity<>(headers, HttpStatus.FOUND);
       }

       String jsonLdScript = """
            <script type="application/ld+json">
            {
                "@context": "https://schema.org",
                "@type": "WebPage",
                "url": "https://www.moonkeyeu.com/%s",
                "name": "MoonkeyEU",
                "description": "%s",
                "image": "%s",
                "datePublished": "%s",
                "dateModified": "%s",
                "inLanguage": "en-US"
            }
        </script>
       """.formatted(
                (type != null && !type.isEmpty()) ? type.toLowerCase() : "",
                crawlerDTO.getDescription(),
                crawlerDTO.getImage(),
                crawlerDTO.getDatePublished(),
                crawlerDTO.getDateModified()
       );

       String html = """
                <!DOCTYPE html>
                <html lang="en">
                <head>
                    <meta charset="UTF-8">
                    <title>%s</title>
                    <meta property="og:type" content="website" />
                    <meta property="og:title" content="%s" />
                    <meta property="og:description" content="%s" />
                    <meta property="og:image" content="%s" />
                    <meta property="og:image:alt" content="%s" />
                    <meta property="og:url" content="https://www.moonkeyeu.com/%s" />
                    <meta property="og:site_name" content="MoonkeyEU" />
                    <meta property="og:locale" content="en_US" />
                    <meta name="twitter:card" content="summary_large_image" />
                    <meta name="twitter:title" content="%s" />
                    <meta name="twitter:description" content="%s" />
                    <meta name="twitter:image" content="%s" />
                    <meta name="twitter:image:alt" content="%s" />
                    <meta name="author" content="MoonkeyEU" />
                    <link rel="canonical" href="https://www.moonkeyeu.com/%s" />
                    %s
                </head>
                <body></body>
                </html>
                """.formatted(
                crawlerDTO.getTitle(),
                crawlerDTO.getTitle(),
                crawlerDTO.getDescription(),
                crawlerDTO.getImage(),
                crawlerDTO.getTitle(),
                (type != null && !type.isEmpty()) ? type.toLowerCase() : "",
                crawlerDTO.getTitle(),
                crawlerDTO.getDescription(),
                crawlerDTO.getImage(),
                crawlerDTO.getTitle(),
                (type != null && !type.isEmpty()) ? type.toLowerCase() : "",
                jsonLdScript

       );

        return ResponseEntity.ok()
                .contentType(MediaType.TEXT_HTML)
                .body(html);
    }

    private boolean isCrawler(String userAgent) {
        if (userAgent == null) return false;

        String agent = userAgent.toLowerCase();
        for (SocialMediaCrawler crawler : SocialMediaCrawler.values()) {
            if (agent.contains(crawler.getIdentifier())) {
                return true;
            }
        }
        return false;
    }

}
