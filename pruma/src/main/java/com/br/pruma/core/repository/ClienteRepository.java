package com.br.pruma.core.repository;

import com.br.pruma.core.domain.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, String> {

    @Query("SELECT c FROM Cliente c LEFT JOIN FETCH c.endereco WHERE c.cpf = :cpf")
    Optional<Cliente> findByCpfWithEndereco(@Param("cpf") String cpf);

    boolean existsByEmailAndAtivoTrueAndCpfNot(String email, String cpf);

    @Query("SELECT c FROM Cliente c LEFT JOIN FETCH c.endereco WHERE c.email = :email AND c.ativo = true")
    Optional<Cliente> findByEmailAndAtivoTrue(@Param("email") String email);

    @Query("SELECT c FROM Cliente c WHERE LOWER(c.nome) LIKE LOWER(CONCAT('%', :nome, '%')) AND c.ativo = true")
    List<Cliente> findByNomeContainingIgnoreCaseAndAtivoTrue(@Param("nome") String nome);

    @Modifying
    @Query("UPDATE Cliente c SET c.ativo = false WHERE c.cpf = :cpf")
    void softDelete(@Param("cpf") String cpf);
}
