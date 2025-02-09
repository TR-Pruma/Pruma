package com.br.pruma.servicos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class MySQLConnection {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void testConnection() {
        try {
            String sql = "SELECT 1";
            jdbcTemplate.execute(sql);
            System.out.println("Conexão estabelecida com sucesso!");
        } catch (Exception e) {
            System.err.println("Erro ao conectar ao banco de dados!");
            e.printStackTrace();
        }
    }
}

