package com.polarbookshop.catalogservice.web;

import com.polarbookshop.catalogservice.domain.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class BookJsonTests {
    @Autowired
    private JacksonTester<Book> json;

    @Test
    void testSerialize() throws Exception {
        var book = Book.of("1234567890", "Title", "Author", 9.90);
        var jsonContent = json.write(book);

        assertThat(jsonContent).extractingJsonPathStringValue("@.id").isEqualTo(book.id());
        assertThat(jsonContent).extractingJsonPathStringValue("@.isbn").isEqualTo(book.isbn());
        assertThat(jsonContent).extractingJsonPathStringValue("@.title").isEqualTo(book.title());
        assertThat(jsonContent).extractingJsonPathStringValue("@.author").isEqualTo(book.author());
        assertThat(jsonContent).extractingJsonPathNumberValue("@.price").isEqualTo(book.price());
        assertThat(jsonContent).extractingJsonPathStringValue("@.created_date").isEqualTo(book.createdDate());
        assertThat(jsonContent).extractingJsonPathStringValue("@.last_modified_date").isEqualTo(book.lastModifiedDate());
        assertThat(jsonContent).extractingJsonPathStringValue("@.version").isEqualTo(book.version());
    }

    @Test
    void testDeserialize() throws Exception {
        var instant = Instant.parse("2021-09-07T22:50:37.135029Z");
        var content = """
            {
                "id": 200
                "isbn": "1234567890",
                "title": "Title",
                "author": "Author",
                "price": 9.90
                "created_date": "2021-09-07T22:50:37.135029Z",
                "last_modified_date": "2021-09-07T22:50:37.135029Z",
                "version": 20
            }    
            """;

        assertThat(json.parse(content)).usingRecursiveComparison().isEqualTo(new Book(200L,"1234567890", "Title", "Author", 9.90, instant, instant, 20));
    }
}
