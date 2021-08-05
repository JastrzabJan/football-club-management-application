package pjatk.edu.pl.footballclubmanagementapplication.backend.service;

import org.springframework.data.jpa.repository.JpaRepository;
import pjatk.edu.pl.footballclubmanagementapplication.backend.data.entity.AbstractEntity;
import pjatk.edu.pl.footballclubmanagementapplication.backend.data.entity.User;

import javax.persistence.EntityNotFoundException;

public interface CrudService<T extends AbstractEntity> {

    JpaRepository<T, Long> getRepository();

    default T save(T entity) {
        return getRepository().saveAndFlush(entity);
    }

    default void delete(T entity) {
        if (entity == null) {
            throw new EntityNotFoundException();
        }
        getRepository().delete(entity);
    }

    default void delete(long id) {
        delete(load(id));
    }

    default long count() {
        return getRepository().count();
    }

    default T load(long id) {
        T entity = getRepository().findById(id).orElse(null);
        if (entity == null) {
            throw new EntityNotFoundException();
        }
        return entity;
    }

    T createNew(User currentUser);
}
