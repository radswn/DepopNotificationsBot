package pl.winiecki.domain;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CronjobHandler {

    private final WebManager manager;

    public CronjobHandler(final WebManager manager) {
        this.manager = manager;
    }

    @GetMapping("/")
    public ResponseEntity<String> processPing(){
        manager.reloadItems();
        return ResponseEntity.ok().body("Git majonez");
    }

}
