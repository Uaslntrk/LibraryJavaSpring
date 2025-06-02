package tr.edu.duzce.mf.bm.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

public class BookAPI {

    @Getter
    @Setter
    @JsonProperty("title")
    private String title;

    @Getter @Setter
    @JsonProperty("author_name")
    private String[] authors;

    @Getter @Setter
    @JsonProperty("first_publish_year")
    private Integer firstPublishYear;

    @Getter @Setter
    @JsonProperty("cover_i")
    private Integer coverId;

    @Getter @Setter
    @JsonProperty("number_of_pages")
    private Long number_of_pages;

    @Getter @Setter
    @JsonProperty("isbn")
    private String[] isbns;

    @Getter @Setter
    @JsonProperty("numFound")
    private int numFound;

    // Kapak URL'si için özel getter
    @JsonIgnore
    public String getCoverImageUrl() {
        if (coverId != null) {
            return "https://covers.openlibrary.org/b/id/" + coverId + "-L.jpg";
        } else if (isbns != null && isbns.length > 0) {
            return "https://covers.openlibrary.org/b/isbn/" + isbns[0] + "-L.jpg";
        }
        return "https://via.placeholder.com/150x200?text=No+Cover";
    }
}
