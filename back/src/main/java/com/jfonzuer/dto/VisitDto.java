package com.jfonzuer.dto;

/**
 * Created by pgm on 17/10/16.
 */
public class VisitDto {
    private Long id;
    private String visitedDate;
    private UserDto visitor;
    private UserDto visited;
    private boolean isSeenByVisited;

    public VisitDto() {
    }

    public VisitDto(Long id, String visitedDate, UserDto visitor, UserDto visited, boolean isSeenByVisited) {
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

    public UserDto getVisitor() {
        return visitor;
    }

    public void setVisitor(UserDto visitor) {
        this.visitor = visitor;
    }

    public UserDto getVisited() {
        return visited;
    }

    public void setVisited(UserDto visited) {
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
        private UserDto visitor;
        private UserDto visited;
        private boolean isSeenByVisited;

        public VisitDtoBuilder setId(Long id) {
            this.id = id;
            return this;
        }

        public VisitDtoBuilder setVisitedDate(String visitedDate) {
            this.visitedDate = visitedDate;
            return this;
        }

        public VisitDtoBuilder setVisitor(UserDto visitor) {
            this.visitor = visitor;
            return this;
        }

        public VisitDtoBuilder setVisited(UserDto visited) {
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
