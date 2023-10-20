package ru.relex.minisocialnetwork.service;

import ru.relex.minisocialnetwork.model.dto.view.ViewListPage;

import java.util.List;

public interface PaginationService<T> {

    default ViewListPage<T> getViewListPage(double totalAmount, int pageSize, int pageNumber, List<T> viewDtoList) {
        final int totalPages = (int) Math.ceil(totalAmount / pageSize);
        return ViewListPage.<T>builder()
                .pageNumber(pageNumber)
                .pageSize(pageSize)
                .totalPages(totalPages)
                .totalCount((int) totalAmount)
                .viewDtoList(viewDtoList)
                .build();
    }

}