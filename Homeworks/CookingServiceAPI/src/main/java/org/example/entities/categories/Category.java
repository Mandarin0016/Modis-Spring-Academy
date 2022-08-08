package org.example.entities.categories;

import org.example.common.InvalidEntityDataException;

public interface Category {
    void setId();
    void setModified();
    void setName(String name) throws InvalidEntityDataException;
    void setDescription(String description) throws InvalidEntityDataException;
    void setTags(String[] tags);
    String[] getTags();
    Long getId();
    String getName();
    String getDescription();
    String getCreated();
    String getModified();
}
