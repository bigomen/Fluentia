package br.com.fluentia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface BaseRepository<E> extends JpaRepository<E, Long> {

	public Optional<E> findById(Long id);

	public E getById(Long id);

	public <S extends E> S save(S entity);

	public List<E> findAll();

}
