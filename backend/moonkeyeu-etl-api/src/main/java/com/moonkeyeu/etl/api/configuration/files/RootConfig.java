package com.moonkeyeu.etl.api.configuration.files;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class RootConfig {
    @Value("${application.clean.root.data.folder}")
    private String cleanRootFolder;
    @Value("${application.raw.root.data.folder}")
    private String rawRootFolder;
    @Value("${application.clean.root.images.folder}")
    private String imagesRootFolder;
}
