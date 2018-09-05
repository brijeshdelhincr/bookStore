package com.brijesh.bookstore.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.brijesh.bookstore.constants.BookstoreConstants;
import com.brijesh.bookstore.models.BookIssued;
import com.brijesh.bookstore.service.BookIssueService;

@RestController
@RequestMapping(BookstoreConstants.BOOK_ISSUE)
public class BookIssueController {

	Logger logger = LoggerFactory.getLogger(BookIssueController.class);

	@Autowired
	private BookIssueService bookIssueService;

	/**
	 * <p>
	 * Issues a book and updates the count in inventory.
	 * </p>
	 *
	 * @return
	 */
	@RequestMapping(value = BookstoreConstants.ENDPOINT_SEPARATOR, method = RequestMethod.POST, produces = BookstoreConstants.APPLICATION_PRODUCER_TYPE)
	public ResponseEntity<BookIssued> issueABook(@RequestBody final BookIssued bookIssued) {
		logger.info("REST request received to issue a book.");
		final BookIssued issueBookById = bookIssueService.issueBookById(bookIssued);
		if (issueBookById != null) {
			return new ResponseEntity<BookIssued>(issueBookById, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/**
	 * <p>
	 * Fetches all books issued from memory.
	 * </p>
	 *
	 * @return
	 */
	@RequestMapping(value = BookstoreConstants.ENDPOINT_SEPARATOR, method = RequestMethod.GET, produces = BookstoreConstants.APPLICATION_PRODUCER_TYPE)
	public ResponseEntity<List<BookIssued>> getAllIssuedBooks() {
		logger.info("REST request received to fetch all issued books.");
		final List<BookIssued> lstAllBooksIssued = bookIssueService.getAll();
		if (CollectionUtils.isEmpty(lstAllBooksIssued)) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<BookIssued>>(lstAllBooksIssued, HttpStatus.OK);
	}

	/**
	 * <p>
	 * Returns an instance of a book after searching based on an isbn id.
	 * </p>
	 *
	 * @param isbnId
	 * @return
	 */
	@RequestMapping(value = BookstoreConstants.BY_ISBNID, method = RequestMethod.GET, produces = BookstoreConstants.APPLICATION_PRODUCER_TYPE)
	public ResponseEntity<Boolean> isBookIssued(@PathVariable final Long isbnId) {
		logger.info("REST request received to find if a book is issued by: {0}", isbnId);
		final BookIssued bookIssued = bookIssueService.get(isbnId);
		if (bookIssued != null) {
			return new ResponseEntity<Boolean>(Boolean.TRUE, HttpStatus.OK);
		}
		return new ResponseEntity<Boolean>(Boolean.FALSE, HttpStatus.OK);
	}

	@RequestMapping(value = BookstoreConstants.BY_ISBNID, method = RequestMethod.PUT, produces = BookstoreConstants.APPLICATION_PRODUCER_TYPE)
	public ResponseEntity<BookIssued> returnBook(@PathVariable final Long isbnId) {
		logger.info("REST request received to return a book issued : {0}", isbnId);
		return new ResponseEntity<BookIssued>(bookIssueService.returnIssuedBook(isbnId), HttpStatus.OK);
	}
}
