package com.mateuszmedon.ticketViewer.service;

import com.mateuszmedon.ticketViewer.entity.Ticket;
import com.mateuszmedon.ticketViewer.exception.NotFoundExceptionId;
import com.mateuszmedon.ticketViewer.repository.TicketRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;



class TicketsViewerServiceTest {


    @InjectMocks
    TicketsViewerService ticketsViewerService;

    @Mock
    TicketRepository ticketRepository;

    Ticket ticket;
    Ticket ticket1;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        ticket = new Ticket();
        ticket.setId(1L);
        ticket.setUrl("www.zendesk.com/api/tickets.json");
        ticket.setSubject("Sample ticket: Meet the ticket");
        ticket.setStatus("open");
        ticket.setDescription("Hello, Something dramatic happened and I could really use your help.");

        ticket1 = new Ticket();

        ticketRepository.save(ticket);

    }

    @Test
    final void test_findTicketById() {

        when(ticketRepository.findById(anyLong())).thenReturn(Optional.of(ticket));

        Ticket ticket1 = ticketsViewerService.findTicketById(1L);

        assertNotNull(ticket1);
        assertEquals("Sample ticket: Meet the ticket", ticket1.getSubject());
        assertEquals(1L, ticket1.getId());
    }

    @Test
    final void test_findTicketById_NotFoundExceptionId() {

        when(ticketRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundExceptionId.class,
                () -> { ticketsViewerService.findTicketById(1L); }
        );
    }

    @Test
    final void test_saveOrUpdate(){

        when(ticketRepository.save(any(Ticket.class))).thenReturn(ticket);

        verify(ticketRepository).save(ticket);
    }

    @Test
    final void test_findAll(){

        Pageable pageableRequest = PageRequest.of(1, 1);
        List<Ticket> tasks = new ArrayList<>();
        Page<Ticket> pagedTasks = new PageImpl(tasks);
        Mockito.when(ticketRepository.findAll(pageableRequest)).thenReturn(pagedTasks);

        List<Ticket> ticketList = pagedTasks.getContent();
        assertNotNull(ticketList);
        assertTrue(ticketList.size() == 0);
    }
}