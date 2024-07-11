package ru.iteratia.titanic.request;

public record PaginationRequest(int page, int size, String sortField, String sortDirection) {
}
