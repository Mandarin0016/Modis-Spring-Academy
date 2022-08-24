package course.spring.intro.web;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/hello")
public class HelloRestController {

    @GetMapping("/{name}")
    public String sayHelloNamePath(@PathVariable String name){
        return String.format("<h1>Hello %s, from Spring REST Service, using @PathVariable!!!</h1>", name);
    }

    @GetMapping
    public String sayHello(@RequestParam(value = "name", required = false) String name){
        return String.format("<h1>Hello %s, from Spring REST Service!!!</h1>", name != null ? name : "Anonymous");
    }
}
