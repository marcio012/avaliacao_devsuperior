package com.marcioheleno.avaliacao.services;

import com.marcioheleno.avaliacao.dto.ClientDto;
import com.marcioheleno.avaliacao.entity.Client;
import com.marcioheleno.avaliacao.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClientServices {

    @Autowired
    private ClientRepository clientRepository;

    @Transactional(readOnly = true)
    public Page<ClientDto> findAllPaded(PageRequest pageRequest) {
        Page<Client> clientsPage = clientRepository.findAll(pageRequest);
        return clientsPage.map(ClientDto::new);
    }
}
