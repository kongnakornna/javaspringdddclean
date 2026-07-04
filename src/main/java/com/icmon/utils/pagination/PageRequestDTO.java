package com.icmon.utils.pagination;

import lombok.Getter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;


@Getter
public class PageRequestDTO<T extends Enum<T>> extends PageRequest {
    private int pageNumber;
    private int pageSize;
    private T sortField;
    private SortOrder sortOrder;

    private PageRequestDTO(int pageNumber, int pageSize, Sort sort) {
        super(pageNumber, pageSize, sort);
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
    }

    public static <T extends Enum<T>> Builder<T> builder() {
        return new Builder<>();
    }

    public enum SortOrder {
        ASC, DESC
    }

    public static final class Builder<T extends Enum<T>> {
        private int pageNumber = 0;
        private int pageSize = 20;
        private T sortField = null;
        private SortOrder sortOrder = null;

        public Builder<T> pageNumber(int pageNumber) {
            this.pageNumber = pageNumber;
            return this;
        }

        public Builder<T> pageSize(int pageSize) {
            this.pageSize = pageSize;
            return this;
        }

        public Builder<T> sortField(T sortField, SortOrder sortOrder) {
            this.sortField = sortField;
            this.sortOrder = sortOrder;
            return this;
        }

        private Sort createSortOrDefault(SortOrder sortOrder, T sortField) {
            SortOrder finalOrder = (sortOrder == null) ? SortOrder.DESC : sortOrder;

            String finalField = (sortField == null) ? "" : sortField.name();

            return (finalOrder == SortOrder.ASC)
                    ? Sort.by(Sort.Order.asc(finalField))
                    : Sort.by(Sort.Order.desc(finalField));
        }

        public PageRequestDTO<T> build() {
            PageRequestDTO<T> dto = new PageRequestDTO<>(this.pageNumber, this.pageSize, this.createSortOrDefault(this.sortOrder, this.sortField));
            dto.pageNumber = this.pageNumber;
            dto.pageSize = this.pageSize;
            dto.sortField = this.sortField;
            dto.sortOrder = this.sortOrder;
            return dto;
        }
    }
}
