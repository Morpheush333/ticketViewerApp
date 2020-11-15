package com.mateuszmedon.ticketViewer.controller;


import com.mateuszmedon.ticketViewer.entity.Ticket;
import com.mateuszmedon.ticketViewer.service.TicketsViewerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.PostConstruct;


@Controller
@RequestMapping("/ticketViewer")
public class TicketController {

    @Autowired
    private TicketsViewerService ticketsViewerService;

    @PostConstruct
    public void runData() {
        ticketsViewerService.runData();
    }


//  http://localhost:8080/ticketViewer/tickets
    @GetMapping("/tickets")
    public String getAllTickets(@PageableDefault(size = 25) Pageable pageable,
                                Model model) {


        Page<Ticket> page = ticketsViewerService.findAll(pageable);
        model.addAttribute("page", page);
        return "tickets-page";
    }

//  http://localhost:8080/ticketViewer/ticket/1
    @GetMapping("ticket/{id}")
    public String getTicketById(@PathVariable("id") Long id, Model model){
        model.addAttribute("item", ticketsViewerService.findTicketById(id));
        return "select-one/view";
    }
}
