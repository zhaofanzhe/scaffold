package io.github.zhaofanzhe.scaffold.storage;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;


@RestController
@RequiredArgsConstructor
public class LocalStorageController {

    private final LocalStorageFactory localStorageFactory;

    @GetMapping("/static/{*path}")
    public ResponseEntity<?> statics(@PathVariable String path, HttpServletRequest request) throws IOException {

        final String url = request.getRequestURL().toString() + "?" + request.getQueryString();

        final LocalStorage storage = localStorageFactory.findUrl(url);

        if (storage == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("sign error or expire.");
        }

        final File file = storage.getFile();

        // 文件不存在
        if (!file.isFile()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("resource not fund.");
        }

        final FileSystemResource resource = new FileSystemResource(file);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        final MediaType contentType = MediaType.parseMediaType(Files.probeContentType(file.toPath()));

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(contentType)
                .body(resource);
    }

}
