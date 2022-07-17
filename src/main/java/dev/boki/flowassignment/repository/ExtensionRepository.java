package dev.boki.flowassignment.repository;

import dev.boki.flowassignment.entity.ExtensionEntity;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface ExtensionRepository<T extends ExtensionEntity> extends JpaRepository<T, Long> {

    @Override
    Optional<T> findById(Long id);

    Optional<T> findEntityByExtension(String extension);

    @Override
    List<T> findAll();

    default Class<T> getEntityClass() {
        return (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass())
            .getActualTypeArguments()[0];
    }
}