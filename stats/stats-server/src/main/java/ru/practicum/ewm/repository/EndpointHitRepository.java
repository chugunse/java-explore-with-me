package ru.practicum.ewm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.practicum.ewm.model.EndpointHit;
import ru.practicum.ewm.model.ViewStat;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EndpointHitRepository extends JpaRepository<EndpointHit, Long> {

    @Query("SELECT new ru.practicum.ewm.model.ViewStat(h.app, h.uri, COUNT(h.ip)) " +
            "FROM EndpointHit AS h " +
            "WHERE (h.timestamp >= :start) " +
            "AND (h.timestamp <= :end) " +
            "AND ((:uris) IS NULL OR h.uri IN (:uris))" +
            "GROUP BY h.app, h.uri " +
            "ORDER BY COUNT(h.ip) DESC")
    List<ViewStat> findViewStats(
            @Param("uris") List<String> uris,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end);

    @Query("SELECT new ru.practicum.ewm.model.ViewStat(h.app, h.uri, COUNT(DISTINCT h.ip)) " +
            "FROM EndpointHit AS h " +
            "WHERE (h.timestamp >= :start) " +
            "AND (h.timestamp <= :end) " +
            "AND ((:uris) IS NULL OR h.uri IN (:uris))" +
            "GROUP BY h.app, h.uri " +
            "ORDER BY COUNT(h.ip) DESC")
    List<ViewStat> findViewStatsUniqueIp(
            @Param("uris") List<String> uris,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end);

}
