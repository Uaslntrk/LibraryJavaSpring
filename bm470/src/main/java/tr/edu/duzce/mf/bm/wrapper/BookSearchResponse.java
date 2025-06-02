package tr.edu.duzce.mf.bm.wrapper;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import tr.edu.duzce.mf.bm.entity.BookAPI;

import java.util.List;

public class BookSearchResponse {

    @Getter
    @Setter
    @JsonProperty("docs")
    private BookAPI[] books;

    public int getNumFound() {
        return books.length;
    }

}
