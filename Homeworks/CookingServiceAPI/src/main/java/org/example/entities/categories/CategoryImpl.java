package org.example.entities.categories;

import org.example.common.ExceptionMessages;
import org.example.common.InvalidEntityDataException;

import java.sql.Timestamp;
import java.util.Arrays;

public class CategoryImpl implements Category {
    private static long counter = 1000;
    private Long id;
    private String name;
    private String description;
    private String[] tags;
    private final String created;
    private String modified;


    public CategoryImpl(String name, String[] tags) throws InvalidEntityDataException {
        setId();
        setName(name);
        setTags(tags);
        this.description = "This category has no description.";
        this.created = new Timestamp(System.currentTimeMillis()).toString().split("\\.")[0];
        setModified();
    }

    public CategoryImpl(String name, String description, String[] tags) throws InvalidEntityDataException {
        this(name, tags);
        setDescription(description);
    }

    public void setId() {
        this.id = counter++;
    }

    public void setName(String name) throws InvalidEntityDataException {
        if (name.length() < 2 || name.length() > 120 || name.trim().isEmpty()) {
            throw new InvalidEntityDataException(ExceptionMessages.CATEGORY_NAME_DOES_NOT_MATCH_REQUIREMENTS);
        }
        this.name = name;
    }

    public void setDescription(String description) throws InvalidEntityDataException {
        if (description.length() < 10 || description.length() > 500 || description.trim().isEmpty()) {
            throw new InvalidEntityDataException(ExceptionMessages.CATEGORY_DESCRIPTION_DOES_NOT_MATCH_REQUIREMENTS);
        }
        this.description = description;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public void setModified() {
        this.modified = new Timestamp(System.currentTimeMillis()).toString().split("\\.")[0];
    }

    public Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public String[] getTags() {
        return this.tags;
    }

    public String getCreated() {
        return this.created;
    }

    public String getModified() {
        return this.modified;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Category found - ");
        sb.append("id =").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", tags=").append(Arrays.toString(tags));
        sb.append(", created='").append(created).append('\'');
        sb.append(", modified='").append(modified).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
