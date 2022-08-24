package course.spring.intro.service.impl;

import course.spring.intro.dao.ArticleRepository;
import course.spring.intro.entity.Article;
import course.spring.intro.exception.InvalidEntityDataException;
import course.spring.intro.exception.NonexistentEntityException;
import course.spring.intro.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * ArticleService default implementation
 */
@Service
public class ArticleServiceImpl implements ArticleService {

    private ArticleRepository articleRepo;

    @Autowired
    public ArticleServiceImpl(ArticleRepository articleRepo) {
        this.articleRepo = articleRepo;
    }

    /**
     * @return list of all {@link Article} class
     */
    @Override
    public List<Article> getAllArticles() {
        return articleRepo.findAll();
    }

    /**
     * @param id the ID of Article to be found
     * @return The Article with give ID, if such exists
     * @throws NonexistentEntityException when the Article with given ID does not exist
     */
    @Override
    public Article getArticleById(Long id) throws NonexistentEntityException {
        return articleRepo.findById(id).orElseThrow(() -> new NonexistentEntityException(
                String.format("Post with ID='%s' does not exist.", id)
        ));
    }

    /**
     * @param article
     * @return
     */
    @Override
    public Article create(Article article) {
        article.setId(null);
        return articleRepo.save(article);
    }

    /**
     * @param article
     * @return
     */
    @Override
    public Article update(Article article) throws NonexistentEntityException, InvalidEntityDataException {
        Article old = getArticleById(article.getId());
        if (!old.getAuthor().equals(article.getAuthor())) {
            throw new InvalidEntityDataException("Post author can not be changed.");
        }
        article.setCreated(old.getCreated());
        article.setModified(LocalDateTime.now());
        return articleRepo.save(article);
    }

    /**
     * @param id
     * @return
     */
    @Override
    public Article deleteArticleById(Long id) throws NonexistentEntityException {
        Article old = getArticleById(id);
        articleRepo.deleteById(id);
        return old;
    }

    /**
     * @return
     */
    @Override
    public String getArticlesCount() {
        return String.valueOf(articleRepo.count());
    }
}
