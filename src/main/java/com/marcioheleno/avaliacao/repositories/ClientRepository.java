package com.marcioheleno.avaliacao.repositories;

import com.marcioheleno.avaliacao.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {}
