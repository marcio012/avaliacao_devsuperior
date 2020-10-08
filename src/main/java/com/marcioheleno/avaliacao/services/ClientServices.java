package com.marcioheleno.avaliacao.services;

import com.marcioheleno.avaliacao.dto.ClientDto;
import com.marcioheleno.avaliacao.entity.Client;
import com.marcioheleno.avaliacao.repositories.ClientRepository;
import com.marcioheleno.avaliacao.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.ResourceAccessException;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class ClientServices {

    @Autowired
    private ClientRepository clientRepository;

    @Transactional(readOnly = true)
    public Page<ClientDto> findAllPaded(PageRequest pageRequest) {
        Page<Client> clientsPage = clientRepository.findAll(pageRequest);
        return clientsPage.map(ClientDto::new);
    }

    @Transactional(readOnly = true)
    public ClientDto findById(Long id) {
        Optional<Client> optionalClient = clientRepository.findById(id);
        Client clientEntity = optionalClient.orElseThrow(() -> new ResourceNotFoundException("Entity not Found"));
        return new ClientDto(clientEntity);
    }

    public ClientDto insert(ClientDto clientDto) {
        Client clientEntity = new Client();
        convertClientToClientDto(clientDto, clientEntity);
        clientEntity = clientRepository.save(clientEntity);
        return new ClientDto(clientEntity);
    }



    private void convertClientToClientDto(ClientDto clientDto, Client clientEntity) {
        clientEntity.setName(clientDto.getName());
        clientEntity.setCpf(clientDto.getCpf());
        clientEntity.setIncome(clientDto.getIncome());
        clientEntity.setBirthDate(clientDto.getBirthDate());
        clientEntity.setChildren(clientDto.getChildren());
    }

    @Transactional
    public ClientDto update(Long id, ClientDto clientDto) {
        try {
            Client clientEntity = clientRepository.getOne(id);
            convertClientToClientDto(clientDto, clientEntity);
            clientEntity = clientRepository.save(clientEntity);
            return new ClientDto(clientEntity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Id not found " + id);
        }
    }
}
