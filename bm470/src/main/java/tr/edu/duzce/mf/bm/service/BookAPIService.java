package tr.edu.duzce.mf.bm.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tr.edu.duzce.mf.bm.entity.BookAPI;
import tr.edu.duzce.mf.bm.wrapper.BookSearchResponse;

import java.util.Map;

@Service
public class BookAPIService {

    private static final Logger logger = LoggerFactory.getLogger(BookAPIService.class);

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    private static final String OPEN_LIBRARY_API = "https://openlibrary.org/search.json?q=";
    private static final String OPEN_LIBRARY_API_TITLE = "https://openlibrary.org/search.json?title=";

    public BookAPIService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public BookSearchResponse searchBooks(String query, int apiPage) {
        String apiUrl = OPEN_LIBRARY_API_TITLE + query.replace(" ", "+") + "&page=" + apiPage;
        return restTemplate.getForObject(apiUrl, BookSearchResponse.class);
    }

    public BookSearchResponse searchBooksByTitle(String title) {
        String apiUrl = OPEN_LIBRARY_API_TITLE + title.replace(" ","+");
        return restTemplate.getForObject(apiUrl, BookSearchResponse.class);
    }


}
