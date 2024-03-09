/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package common;

import java.util.List;

public class Pagination<T> {

    private List<T> allItems;
    private int totalItems;
    private int itemsPerPage;
    private int totalPages;
    private int currentPage;

    public Pagination(List<T> allItems, int itemsPerPage, int currentPage) {
        this.allItems = allItems;
        this.totalItems = allItems.size();
        this.itemsPerPage = itemsPerPage;
        this.totalPages = calculateTotalPages();
        this.currentPage = validateCurrentPage(currentPage);
    }

    private int calculateTotalPages() {
        return (int) Math.ceil((double) totalItems / itemsPerPage);
    }

    private int validateCurrentPage(int currentPage) {
        return Math.max(1, Math.min(currentPage, totalPages));
    }

    public List<T> getItemsOnPage() {
        int startItem = (currentPage - 1) * itemsPerPage;
        int lastItem = Math.min(startItem + itemsPerPage, totalItems);

        return allItems.subList(startItem, lastItem);
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int getTotalPages() {
        return totalPages;
    }
}
