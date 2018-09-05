package com.brijesh.bookstore.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.brijesh.bookstore.models.Book;
import com.brijesh.bookstore.repositories.BookRepsoitory;
import com.brijesh.bookstore.service.BookService;

/**
 * @author brijesh
 *         <p>
 *         Service class to implement specific business logic on books.Although
 *         no such business logic required. Still emulating the services
 *         architecture.
 *         </p>
 */
@Service
public class BookServiceImpl implements BookService {

	Logger logger = LoggerFactory.getLogger(BookServiceImpl.class);

	@Resource
	private BookRepsoitory bookRepository;

	@Override
	public List<Book> getAll() {
		logger.info("Fetching all books from repository");
		return bookRepository.getAll();
	}

	@Override
	public Book get(final Long searchParam) {
		logger.info("Searching a book from repository based on isbnId");
		return bookRepository.search(searchParam);
	}

	@Override
	public Book save(final Book incommingItem) {
		logger.info("Saving a book in repository");
		return bookRepository.create(incommingItem);
	}

	@Override
	public Book update(final Long isbnId, final Book incommingItem) {
		logger.info("Updating a book in repository based on a particular isbn id.");
		return bookRepository.update(isbnId, incommingItem);
	}

	@Override
	public void delete(final long deleteEntityId) {
		logger.info("Delete a book from repository based on an isbn id");
		bookRepository.delete(deleteEntityId);
	}

}
