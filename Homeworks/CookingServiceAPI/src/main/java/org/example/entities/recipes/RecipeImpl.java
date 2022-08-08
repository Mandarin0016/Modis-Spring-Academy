package org.example.entities.recipes;

import org.example.common.ExceptionMessages;
import org.example.common.InvalidEntityDataException;
import org.example.entities.categories.Category;
import org.example.entities.users.User;

import java.net.URL;
import java.sql.Timestamp;
import java.util.Arrays;

public class RecipeImpl implements Recipe {
    private static long counter = 1000;
    private Long id;
    private Category category;
    private String title;
    private User author;
    private String shortDescription;
    private int cookingTime;
    private String usedProducts;
    private URL pictureURL;
    private String description;
    private String[] tags;
    private final String created;
    private String modified;

    public RecipeImpl(Category category, String title, User author, String shortDescription, int cookingTime, String usedProducts, String pictureURL, String[] tags) throws InvalidEntityDataException {
        setId();
        setCategory(category);
        setTitle(title);
        setAuthor(author);
        setShortDescription(shortDescription);
        setCookingTime(cookingTime);
        setUsedProducts(usedProducts);
        setPictureURL(pictureURL);
        setTags(tags);
        this.description = "This recipe has no description.";
        this.created = new Timestamp(System.currentTimeMillis()).toString().split("\\.")[0];
        setModified();
    }

    public RecipeImpl(Category category, String title, String description, User author, String shortDescription, int cookingTime, String usedProducts, String pictureURL, String[] tags) throws InvalidEntityDataException {
        this(category, title, author, shortDescription, cookingTime, usedProducts, pictureURL, tags);
        setDescription(description);
    }

    public Long getId() {
        return this.id;
    }

    public void setId() {
        this.id = counter++;
    }

    public Category getCategory() {
        return this.category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) throws InvalidEntityDataException {
        if (title.length() < 2 || title.length() > 120 || title.trim().isEmpty()) {
            throw new InvalidEntityDataException(ExceptionMessages.RECIPE_TITLE_DOES_NOT_MATCH_REQUIREMENTS);
        }
        this.title = title;
    }

    public User getAuthor() {
        return this.author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getShortDescription() {
        return this.shortDescription;
    }

    public void setShortDescription(String shortDescription) throws InvalidEntityDataException {
        if (shortDescription.length() < 2 || shortDescription.length() > 250 || shortDescription.trim().isEmpty()) {
            throw new InvalidEntityDataException(ExceptionMessages.RECIPE_SHORT_DESCRIPTION_DOES_NOT_MATCH_REQUIREMENTS);
        }
        this.shortDescription = shortDescription;
    }

    public int getCookingTime() {
        return this.cookingTime;
    }

    public void setCookingTime(int cookingTime) {
        this.cookingTime = cookingTime;
    }

    public String getUsedProducts() {
        return this.usedProducts;
    }

    public void setUsedProducts(String usedProducts) throws InvalidEntityDataException {
        if (usedProducts.length() < 20 || usedProducts.length() > 500 || usedProducts.trim().isEmpty()) {
            throw new InvalidEntityDataException(ExceptionMessages.RECIPE_USED_PRODUCTS_DOES_NOT_MATCH_REQUIREMENTS);
        }
        this.usedProducts = usedProducts;
    }

    public String getPictureURL() {
        return this.pictureURL.toString();
    }

    public void setPictureURL(String pictureURL) throws InvalidEntityDataException {
        try {
            URL url = new URL(pictureURL);
            this.pictureURL = url;
        } catch (Exception e) {
            throw new InvalidEntityDataException(ExceptionMessages.INVALID_URL);
        }
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) throws InvalidEntityDataException {
        if (description.length() < 150 || description.length() > 2500 || description.trim().isEmpty()) {
            throw new InvalidEntityDataException(ExceptionMessages.RECIPE_DESCRIPTION_DOES_NOT_MATCH_REQUIREMENTS);
        }
        this.description = description;
    }

    public String[] getTags() {
        return this.tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public String getCreated() {
        return this.created;
    }

    public String getModified() {
        return this.modified;
    }

    public void setModified() {
        this.modified = new Timestamp(System.currentTimeMillis()).toString().split("\\.")[0];
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Recipe found - {");
        sb.append("ID=").append(id);
        sb.append(", category ID=").append(category.getId());
        sb.append(", title='").append(title).append('\'');
        sb.append(", author username=").append(author.getUserName());
        sb.append(", shortDescription='").append(shortDescription).append('\'');
        sb.append(", cookingTime=").append(cookingTime);
        sb.append(", usedProducts='").append(usedProducts).append('\'');
        sb.append(", pictureURL=").append(pictureURL);
        sb.append(", description='").append(description).append('\'');
        sb.append(", tags=").append(Arrays.toString(tags));
        sb.append(", created='").append(created).append('\'');
        sb.append(", modified='").append(modified).append('\'');
        sb.append('}');
        sb.append(System.lineSeparator());
        return sb.toString().trim();
    }
}
