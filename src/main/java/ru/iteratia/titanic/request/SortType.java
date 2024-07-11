package ru.iteratia.titanic.request;

public record SortType(int page, int size, String sortField, String sortDirection) {
}
