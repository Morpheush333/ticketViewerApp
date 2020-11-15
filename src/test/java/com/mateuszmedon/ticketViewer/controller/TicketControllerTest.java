package com.mateuszmedon.ticketViewer.controller;

import com.mateuszmedon.ticketViewer.entity.Ticket;
import com.mateuszmedon.ticketViewer.service.TicketsViewerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;


class TicketControllerTest {

    @InjectMocks
    TicketController ticketController;

    @Mock
    TicketsViewerService ticketsViewerService;

    Model model;
    Ticket ticket;

    final Long TICKET_ID = 1L;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        model = new ExtendedModelMap();
        model.addAttribute("msg");

        ticket = new Ticket();
        ticket.setId(TICKET_ID);
        ticket.setUrl("www.zendesk.com/api/tickets.json");
        ticket.setSubject("I need help");
        ticket.setStatus("incident");
        ticket.setDescription("Thanks in advance");
    }

    @Test
    void test_getTicketById() {
        when(ticketsViewerService.findTicketById(anyLong())).thenReturn(ticket);

        assertNotNull(model);
        assertEquals("select-one/view", ticketController.getTicketById(TICKET_ID,model));
        assertEquals("I need help", ticket.getSubject());
    }

    @Test
    void test_getAllTickets(){

        List<Ticket> tasks = new ArrayList<>();
        Page<Ticket> pagedTasks = new PageImpl(tasks);

        when(ticketsViewerService.findAll(any(Pageable.class))).thenReturn(pagedTasks);

        List<Ticket> ticketList = pagedTasks.getContent();
        assertNotNull(ticketList);
        assertTrue(ticketList.size() == 0);

    }

}