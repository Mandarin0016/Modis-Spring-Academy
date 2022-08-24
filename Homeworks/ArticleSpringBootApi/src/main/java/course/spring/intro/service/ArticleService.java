package course.spring.intro.service;

import course.spring.intro.entity.Article;
import course.spring.intro.exception.NonexistentEntityException;

import java.util.List;

/**
 * ArticleService interface
 */
public interface ArticleService {
    /**
     * @return list of all {@link Article} class
     */
    List<Article> getAllArticles();

    /**
     * @param id the ID of Article to be found
     * @return The Article with give ID, if such exists
     * @throws NonexistentEntityException when the Article with given ID does not exist
     */
    Article getArticleById(Long id) throws NonexistentEntityException;

    Article create(Article article);

    Article update(Article article) throws NonexistentEntityException;

    Article deleteArticleById(Long id) throws NonexistentEntityException;

    String getArticlesCount();
}
