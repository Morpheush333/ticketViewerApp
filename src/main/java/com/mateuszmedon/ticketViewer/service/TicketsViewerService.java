package com.mateuszmedon.ticketViewer.service;


import com.mateuszmedon.ticketViewer.entity.Ticket;
import com.mateuszmedon.ticketViewer.exception.NotFoundExceptionId;
import com.mateuszmedon.ticketViewer.repository.TicketRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class TicketsViewerService {

    @Value("${spring.datasource.username}")
    private String EMAIL;

    @Value("${spring.datasource.password}")
    private String PASSWORD;

    @Value("${spring.datasource.address}")
    private String URL;

    private static final String TICKET_NOT_FOUND = "Trip with id %s not found.";

    @Autowired
    private TicketRepository ticketRepository;

    public void saveOrUpdate(Ticket ticket) {
        ticketRepository.save(ticket);
    }

    public Page<Ticket> findAll(Pageable pageable) {
        return ticketRepository.findAll(pageable);
    }

    public Ticket findTicketById(Long id) {
        Optional<Ticket> trip = ticketRepository.findById(id);

        if (!trip.isPresent()) {
            throw new NotFoundExceptionId(
                    String.format(TICKET_NOT_FOUND, id));
        }

        return trip.get();
    }

    public void runData() {

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(EMAIL, PASSWORD);
        HttpEntity request = new HttpEntity(headers);
        ResponseEntity<String> response = restTemplate.exchange(
                URL,
                HttpMethod.GET,
                request,
                String.class
        );

        JSONObject obj = new JSONObject(response.getBody());
        JSONArray arr = obj.getJSONArray("tickets");
        for (int i = 0; i < arr.length(); i++) {
            Ticket ticket = new Ticket();

            String post_sub = arr.getJSONObject(i).getString("subject");
            ticket.setSubject(post_sub);

            String post_url = arr.getJSONObject(i).getString("url");
            ticket.setUrl(post_url);

            String post_status = arr.getJSONObject(i).getString("status");
            ticket.setStatus(post_status);

            String post_description = arr.getJSONObject(i).getString("description");
            ticket.setDescription(post_description);

            saveOrUpdate(ticket);
        }
    }
}