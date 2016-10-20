package com.jfonzuer.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * Created by pgm on 17/10/16.
 */
@Entity
public class Visit implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private LocalDate visitedDate;

    @OneToOne
    private User visitor;

    @OneToOne
    private User visited;

    @Column(columnDefinition="tinyint(1) default 0", nullable = false)
    private Boolean isSeenByVisited;

    public Visit() {
    }

    public Visit(LocalDate visitedDate, User visitor, User visited, Boolean isSeenByVisited) {
        this.visitedDate = visitedDate;
        this.visitor = visitor;
        this.visited = visited;
        this.isSeenByVisited = isSeenByVisited;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getVisitedDate() {
        return visitedDate;
    }

    public void setVisitedDate(LocalDate visitedDate) {
        this.visitedDate = visitedDate;
    }

    public User getVisitor() {
        return visitor;
    }

    public void setVisitor(User visitor) {
        this.visitor = visitor;
    }

    public User getVisited() {
        return visited;
    }

    public void setVisited(User visited) {
        this.visited = visited;
    }

    public Boolean getSeenByVisited() {
        return isSeenByVisited;
    }

    public void setSeenByVisited(Boolean seenByVisited) {
        isSeenByVisited = seenByVisited;
    }


    public static class VisitBuilder {
        private LocalDate visitedDate;
        private User visitor;
        private User visited;
        private Boolean isSeenByVisited;

        public VisitBuilder setVisitedDate(LocalDate visitedDate) {
            this.visitedDate = visitedDate;
            return this;
        }

        public VisitBuilder setVisitor(User visitor) {
            this.visitor = visitor;
            return this;
        }

        public VisitBuilder setVisited(User visited) {
            this.visited = visited;
            return this;
        }

        public VisitBuilder setIsSeenByVisited(Boolean isSeenByVisited) {
            this.isSeenByVisited = isSeenByVisited;
            return this;
        }

        public Visit createVisit() {
            return new Visit(visitedDate, visitor, visited, isSeenByVisited);
        }
    }
}
