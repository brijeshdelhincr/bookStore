package com.brijesh.bookstore.service;

import com.brijesh.bookstore.models.BookIssued;

public interface BookIssueService extends BaseService<BookIssued> {

	BookIssued issueBookById(final BookIssued bookIssued);

	BookIssued returnIssuedBook(Long isbnId);

}
