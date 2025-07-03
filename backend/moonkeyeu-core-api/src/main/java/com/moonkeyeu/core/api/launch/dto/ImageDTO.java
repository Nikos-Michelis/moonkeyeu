package com.moonkeyeu.core.api.launch.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonPropertyOrder({"id", "name", "image_url", "thumbnail_url", "credit"})
public class ImageDTO implements DTOEntity {
    @JsonProperty("id")
    private Long imageId;
    @JsonProperty("name")
    private String imageName;
    @JsonProperty("image_url")
    private String imageUrl;
    @JsonProperty("thumbnail_url")
    private String thumbnailUrl;
    @JsonProperty("credit")
    private String credit;

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        ImageDTO imageDTO = (ImageDTO) object;
        return Objects.equals(imageId, imageDTO.imageId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(imageId);
    }
}
