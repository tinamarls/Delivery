package dto;


import lombok.Builder;
import lombok.Data;

import java.io.InputStream;

@Builder
@Data
public class FileDto {

    private String name;
    private InputStream inputStream;
    private Long size;
    private String contentType;
    private String storagePath;

}
