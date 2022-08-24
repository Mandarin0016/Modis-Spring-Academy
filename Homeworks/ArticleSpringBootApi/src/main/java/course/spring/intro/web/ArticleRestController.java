package course.spring.intro.web;

import course.spring.intro.entity.Article;
import course.spring.intro.exception.InvalidEntityDataException;
import course.spring.intro.service.ArticleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/articles")
public class ArticleRestController {
  //  @Autowired
    private final ArticleService articleService;

    public ArticleRestController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping
    public List<Article> getAllArticles() {
        return articleService.getAllArticles();
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<Article> addNewArticle(@Valid @RequestBody Article article, Errors errors) {
        handleErrors(errors);
        return ResponseEntity.created(
                ServletUriComponentsBuilder.fromCurrentRequest().pathSegment("{id}")
                        .buildAndExpand(articleService.create(article).getId()).toUri()).body(articleService.create(article));
    }

    @GetMapping("/{id:\\d+}")
    public Article getAllArticleByID(@PathVariable("id") Long id) {
        return articleService.getArticleById(id);
    }

    @PutMapping("/{id}")
    public Article update(@RequestBody Article article,@Valid @PathVariable("id") Long id, Errors errors){
        handleErrors(errors);
        if (!id.equals(article.getId())){
            throw new InvalidEntityDataException(String.format("ID in URL='%d' is different from ID in message body = '%d'", id, article.getId()));
        }
        return articleService.update(article);
    }

    @DeleteMapping("/{id}")
    public Article deleteArticleById(@PathVariable("id") Long id) {
        return articleService.deleteArticleById(id);
    }

    @GetMapping(value = "/count", produces = MediaType.TEXT_PLAIN_VALUE)
    public String count() {
        return articleService.getArticlesCount();
    }

    private void handleErrors(Errors errors) {
        if (errors.hasErrors()){
            List<String> errorMessages = errors.getFieldErrors()
                    .stream()
                    .map(err ->
                            String.format("%s for: '%s' = '%s'", err.getDefaultMessage(), err.getField(), err.getRejectedValue())
                    ).toList();

            throw new InvalidEntityDataException("Invalid post data: ", errorMessages);
        }
    }

}
