package com.moonkeyeu.core.api.launch.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class NasaApodDTO implements DTOEntity {
    private String copyright;
    private LocalDate date;
    private String explanation;
    private String hdurl;
    private String media_type;
    private String title;
    private String url;
}
