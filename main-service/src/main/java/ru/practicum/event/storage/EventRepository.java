package ru.practicum.event.storage;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.practicum.event.model.Event;
import ru.practicum.event.model.EventState;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findAllByInitiatorId(Long userId, Pageable pageable);

    Optional<Event> findByIdAndStateIs(Long id, EventState published);

    List<Event> findAllByCategoryId(Long catId);

    @Query("select e from Event e " +
            "where ((:text is null or lower(e.annotation) like lower(concat('%', :text, '%'))) " +
            "or (:text is null or lower(e.description) like lower(concat('%', :text, '%')))) " +
            "and (:categories is null or e.category.id in :categories) " +
            "and (:paid is null or e.paid = :paid) " +
            "and (e.eventDate between " +
            "to_timestamp(:rangeStart, 'yyyy-mm-dd hh24:mi:ss') and to_timestamp(:rangeEnd, 'yyyy-mm-dd hh24:mi:ss') " +
            "or e.eventDate > current_timestamp) " +
            "order by lower(:sort)")
    List<Event> findAllByPublic(@Param("text") String text,
                                @Param("categories") List<Long> categories,
                                @Param("paid") Boolean paid,
                                @Param("rangeStart") String rangeStart,
                                @Param("rangeEnd") String rangeEnd,
                                @Param("sort") String sort,
                                Pageable pageable);

    @Query("SELECT e FROM Event e " +
            "WHERE (e.initiator.id IN (:users) OR :users = NULL) " +
            "AND (e.state IN (:states) OR :states = NULL) " +
            "AND (e.category.id IN (:categories) OR :categories = NULL) " +
            "AND (e.eventDate>=:rangeStart OR CAST(:rangeStart AS date) = NULL) " +
            "AND (e.eventDate<=:rangeEnd OR CAST(:rangeEnd AS date) = NULL)")
    List<Event> findAllByAdmin(@Param("users") List<Long> users,
                               @Param("states") List<EventState> states,
                               @Param("categories") List<Long> categories,
                               @Param("rangeStart") LocalDateTime rangeStart,
                               @Param("rangeEnd") LocalDateTime rangeEnd,
                               Pageable page);
}
