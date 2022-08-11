package org.example.repositories.interfaces;

public interface RepositoryFactory<E> {
    Repository<E> createRepository();

}
