package org.example.repositories.interfaces;

import java.util.Collection;

/**
 * Public interface for managing lifecycle of Repository objects
 */

public interface Repository<T> {
    T getById(Long id);

    Collection<T> getAll();

    void add(T unit);

    boolean remove(T unit);
}
