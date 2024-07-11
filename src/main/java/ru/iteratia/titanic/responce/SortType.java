package ru.iteratia.titanic.responce;

public record SortType(int page, int size, String sortField, String sortDirection) {
}
