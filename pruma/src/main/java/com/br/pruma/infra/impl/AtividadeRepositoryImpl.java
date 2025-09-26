package com.br.pruma.infra.impl;

import com.br.pruma.core.domain.Atividade;
import com.br.pruma.core.repository.AtividadeRepository;

import java.util.List;
import java.util.Optional;

import com.br.pruma.core.domain.Atividade;
import com.br.pruma.core.repository.AtividadeRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Repository
@Transactional
public class AtividadeRepositoryImpl implements AtividadeRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public <S extends Atividade> List<S> saveAll(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public List<Atividade> findAll() {
        return em.createQuery("SELECT a FROM Atividade a", Atividade.class)
                .getResultList();
    }

    @Override
    public List<Atividade> findAllById(Iterable<Integer> integers) {
        return List.of();
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public Optional<Atividade> findById(Integer id) {
        return Optional.ofNullable(em.find(Atividade.class, id));
    }

    @Override
    public boolean existsById(Integer integer) {
        return false;
    }

    @Override
    public <S extends Atividade> S save(S entidade) {
        if (entidade.getId() == null) {
            em.persist(entidade);
            return entidade;
        } else {
            return em.merge(entidade);
        }
    }

    @Override
    public void deleteById(Integer id) {
        findById(id).ifPresent(em::remove);
    }

    @Override
    public void delete(Atividade entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Integer> integers) {

    }

    @Override
    public void deleteAll(Iterable<? extends Atividade> entities) {

    }

    @Override
    public void deleteAll() {

    }


    @Override
    public void flush() {

    }

    @Override
    public <S extends Atividade> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends Atividade> List<S> saveAllAndFlush(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public void deleteAllInBatch(Iterable<Atividade> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Integer> integers) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Atividade getOne(Integer integer) {
        return null;
    }

    @Override
    public Atividade getById(Integer integer) {
        return null;
    }

    @Override
    public Atividade getReferenceById(Integer integer) {
        return null;
    }

    @Override
    public <S extends Atividade> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Atividade> List<S> findAll(Example<S> example) {
        return List.of();
    }

    @Override
    public <S extends Atividade> List<S> findAll(Example<S> example, Sort sort) {
        return List.of();
    }

    @Override
    public <S extends Atividade> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Atividade> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Atividade> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Atividade, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public List<Atividade> findAll(Sort sort) {
        return List.of();
    }

    @Override
    public Page<Atividade> findAll(Pageable pageable) {
        return null;
    }
}
