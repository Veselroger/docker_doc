package simpleapp;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest")
public class MyRestController {
    @GetMapping("/{requestId}")
    public String getUser(@PathVariable Long requestId) {
        return "Request " + requestId;
    }
}