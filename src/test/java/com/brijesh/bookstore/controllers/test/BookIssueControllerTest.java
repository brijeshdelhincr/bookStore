package com.brijesh.bookstore.controllers.test;

import java.util.Arrays;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.brijesh.bookstore.controllers.BookIssueController;
import com.brijesh.bookstore.models.BookIssued;
import com.brijesh.bookstore.service.BookIssueService;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import mockit.integration.junit4.JMockit;

@RunWith(JMockit.class)
public class BookIssueControllerTest {

	@Tested
	private BookIssueController bookIssueController;

	@Injectable
	private BookIssueService bookIssueService;

	@Test
	public void testissueABookFailFirst() {
		final BookIssued bookIssued = new BookIssued();
		bookIssued.setIsbnId(123L);
		bookIssued.setDaysToReturn(3);
		new Expectations() {
			{
				bookIssueService.issueBookById(bookIssued);
				result = null;
			}
		};
		final ResponseEntity<BookIssued> issueABook = this.bookIssueController.issueABook(bookIssued);
		Assertions.assertThat(issueABook.getBody()).isNull();
		Assertions.assertThat(issueABook.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Test
	public void testissueABookSuccess() {
		final BookIssued bookIssued = new BookIssued();
		bookIssued.setIsbnId(123L);
		bookIssued.setDaysToReturn(3);
		new Expectations() {
			{
				bookIssueService.issueBookById(bookIssued);
				result = bookIssued;
			}
		};
		final ResponseEntity<BookIssued> issueABook = this.bookIssueController.issueABook(bookIssued);
		Assertions.assertThat(issueABook.getBody()).isNotNull();
		Assertions.assertThat(issueABook.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@Test
	public void testGetAllIssuedBooksFailFirst() {
		new Expectations() {
			{
				bookIssueService.getAll();
				result = Arrays.asList();
			}
		};
		final ResponseEntity<List<BookIssued>> allIssuedBooks = this.bookIssueController.getAllIssuedBooks();
		Assertions.assertThat(allIssuedBooks.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
		Assertions.assertThat(allIssuedBooks.getBody()).isNull();
	}

	@Test
	public void testGetAllIssuedBooksSuccess() {
		final BookIssued bookIssued = new BookIssued();
		bookIssued.setIsbnId(123L);
		bookIssued.setDaysToReturn(3);
		new Expectations() {
			{
				bookIssueService.getAll();
				result = Arrays.asList(bookIssued);
			}
		};
		final ResponseEntity<List<BookIssued>> allIssuedBooks = this.bookIssueController.getAllIssuedBooks();
		Assertions.assertThat(allIssuedBooks.getStatusCode()).isEqualTo(HttpStatus.OK);
		Assertions.assertThat(allIssuedBooks.getBody()).isNotNull();
	}

	@Test
	public void testIsBookIssuedFailFirst() {
		final Long isbnId = 123L;
		new Expectations() {
			{
				bookIssueService.get(anyLong);
				result = null;
			}
		};
		final ResponseEntity<Boolean> bookIssued = this.bookIssueController.isBookIssued(isbnId);
		Assertions.assertThat(bookIssued.getStatusCode()).isEqualTo(HttpStatus.OK);
		Assertions.assertThat(bookIssued.getBody()).isFalse();
	}

	@Test
	public void testIsBookIssuedSuccess() {
		final Long isbnId = 123L;
		final BookIssued bookIssued = new BookIssued();
		bookIssued.setIsbnId(123L);
		bookIssued.setDaysToReturn(3);
		new Expectations() {
			{
				bookIssueService.get(anyLong);
				result = bookIssued;
			}
		};
		final ResponseEntity<Boolean> bookIssuedResult = this.bookIssueController.isBookIssued(isbnId);
		Assertions.assertThat(bookIssuedResult.getStatusCode()).isEqualTo(HttpStatus.OK);
		Assertions.assertThat(bookIssuedResult.getBody()).isTrue();
	}

	@Test
	public void testReturnBookFailFirst() {
		final Long isbnId = 123L;
		final BookIssued bookIssued = new BookIssued();
		bookIssued.setIsbnId(123L);
		bookIssued.setDaysToReturn(3);
		bookIssued.setIsReturned(Boolean.FALSE);
		new Expectations() {
			{
				bookIssueService.returnIssuedBook(anyLong);
				result = bookIssued;
			}
		};
		final ResponseEntity<BookIssued> returnBook = this.bookIssueController.returnBook(isbnId);
		Assertions.assertThat(returnBook.getStatusCode()).isEqualTo(HttpStatus.OK);
		Assertions.assertThat(returnBook.getBody().isReturned()).isFalse();
	}

	@Test
	public void testReturnBookSuccess() {
		final Long isbnId = 123L;
		final BookIssued bookIssued = new BookIssued();
		bookIssued.setIsbnId(123L);
		bookIssued.setDaysToReturn(3);
		bookIssued.setIsReturned(Boolean.TRUE);
		new Expectations() {
			{
				bookIssueService.returnIssuedBook(anyLong);
				result = bookIssued;
			}
		};
		final ResponseEntity<BookIssued> returnBook = this.bookIssueController.returnBook(isbnId);
		Assertions.assertThat(returnBook.getStatusCode()).isEqualTo(HttpStatus.OK);
		Assertions.assertThat(returnBook.getBody().isReturned()).isTrue();
	}
}
