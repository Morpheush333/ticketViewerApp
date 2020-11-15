package com.mateuszmedon.ticketViewer.repository;


import com.mateuszmedon.ticketViewer.entity.Ticket;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TicketRepository extends PagingAndSortingRepository<Ticket, Long> {
}
