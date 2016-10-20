package com.jfonzuer.dto;

import com.jfonzuer.security.JwtUser;

/**
 * Created by pgm on 17/10/16.
 */
public class VisitDto {
    private Long id;
    private String visitedDate;
    private JwtUser visitor;
    private JwtUser visited;
    private boolean isSeenByVisited;

    public VisitDto() {
    }

    public VisitDto(Long id, String visitedDate, JwtUser visitor, JwtUser visited, boolean isSeenByVisited) {
        this.id = id;
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

    public String getVisitedDate() {
        return visitedDate;
    }

    public void setVisitedDate(String visitedDate) {
        this.visitedDate = visitedDate;
    }

    public JwtUser getVisitor() {
        return visitor;
    }

    public void setVisitor(JwtUser visitor) {
        this.visitor = visitor;
    }

    public JwtUser getVisited() {
        return visited;
    }

    public void setVisited(JwtUser visited) {
        this.visited = visited;
    }

    public boolean isSeenByVisited() {
        return isSeenByVisited;
    }

    public void setSeenByVisited(boolean seenByVisited) {
        isSeenByVisited = seenByVisited;
    }

    public static class VisitDtoBuilder {
        private Long id;
        private String visitedDate;
        private JwtUser visitor;
        private JwtUser visited;
        private boolean isSeenByVisited;

        public VisitDtoBuilder setId(Long id) {
            this.id = id;
            return this;
        }

        public VisitDtoBuilder setVisitedDate(String visitedDate) {
            this.visitedDate = visitedDate;
            return this;
        }

        public VisitDtoBuilder setVisitor(JwtUser visitor) {
            this.visitor = visitor;
            return this;
        }

        public VisitDtoBuilder setVisited(JwtUser visited) {
            this.visited = visited;
            return this;
        }

        public VisitDtoBuilder setIsSeenByVisited(boolean isSeenByVisited) {
            this.isSeenByVisited = isSeenByVisited;
            return this;
        }

        public VisitDto createVisitDto() {
            return new VisitDto(id, visitedDate, visitor, visited, isSeenByVisited);
        }
    }
}
