package org.example.entities.recipes;

import org.example.common.InvalidEntityDataException;
import org.example.entities.categories.Category;
import org.example.entities.users.User;

public interface Recipe {
    Long getId();
    Category getCategory();
    String getTitle();
    User getAuthor();
    String getShortDescription();
    int getCookingTime();
    String getUsedProducts();
    String getPictureURL();
    String getDescription();
    String getCreated();
    String getModified();
    String[] getTags();
    void setId();
    void setCategory(Category category);
    void setTitle(String title) throws InvalidEntityDataException;
    void setAuthor(User author);
    void setShortDescription(String shortDescription) throws InvalidEntityDataException;
    void setCookingTime(int cookingTime);
    void setUsedProducts(String usedProducts) throws InvalidEntityDataException;
    void setPictureURL(String pictureURL) throws InvalidEntityDataException;
    void setDescription(String description) throws InvalidEntityDataException;
    void setTags(String[] tags);
    void setModified();
}
