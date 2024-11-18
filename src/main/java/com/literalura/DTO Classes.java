package com.literalura.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GutendexResponse {
    private int count;
    private String next;
    private String previous;
    private List<BookDto> results;
}

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BookDto {
    private long id;
    private String title;
    private List<AuthorDto> authors;
    private List<String> languages;
    @JsonProperty("download_count")
    private int downloadCount;
}

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthorDto {
    private String name;
    @JsonProperty("birth_year")
    private Integer birthYear;
    @JsonProperty("death_year")
    private Integer deathYear;
}
