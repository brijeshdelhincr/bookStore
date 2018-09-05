package com.brijesh.bookstore.controllers.test;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.brijesh.bookstore.controllers.BookController;
import com.brijesh.bookstore.models.Book;
import com.brijesh.bookstore.service.BookService;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import mockit.integration.junit4.JMockit;

@RunWith(JMockit.class)
public class BookControllerTest {

	@Tested
	private BookController bookController;

	@Injectable
	private BookService bookService;

	@Test
	public void testGetAllFailFirst() {
		new Expectations() {
			{
				bookService.getAll();
				result = Arrays.asList();
			}
		};
		final ResponseEntity<List<Book>> allBooks = this.bookController.getAllBooks();
		assertEquals(HttpStatus.NO_CONTENT, allBooks.getStatusCode());
		Assertions.assertThat(allBooks.getBody()).isNull();
	}

	@Test
	public void testGetAllSuccess() {
		final Book testBook = new Book();
		new Expectations() {
			{
				bookService.getAll();
				result = Arrays.asList(testBook);
			}
		};
		final ResponseEntity<List<Book>> allBooks = this.bookController.getAllBooks();
		assertEquals(HttpStatus.OK, allBooks.getStatusCode());
		Assertions.assertThat(allBooks.getBody()).hasSize(1);
	}

	@Test
	public void testGetBookFailFirst() {
		final Long bookSearchParam = 123L;
		new Expectations() {
			{
				bookService.get(bookSearchParam);
				result = null;
			}
		};
		final ResponseEntity<Book> book = this.bookController.getBook(bookSearchParam);
		Assertions.assertThat(book.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
		Assertions.assertThat(book.getBody()).isNull();
	}

	@Test
	public void testGetBookSuccess() {
		final Book testBook = new Book();
		final Long bookSearchParam = 123L;
		new Expectations() {
			{
				bookService.get(bookSearchParam);
				result = Arrays.asList(testBook);
			}
		};
		final ResponseEntity<Book> book = this.bookController.getBook(bookSearchParam);
		Assertions.assertThat(book.getStatusCode()).isEqualTo(HttpStatus.OK);
		Assertions.assertThat(book.getBody()).isNotNull();
	}

	@Test
	public void testCreateBookFailFirst() {
		final Book testBook = new Book();
		new Expectations() {
			{
				bookService.save(testBook);
				result = null;
			}
		};
		final ResponseEntity<Book> createBook = this.bookController.createBook(testBook);
		Assertions.assertThat(createBook.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
		Assertions.assertThat(createBook.getBody()).isNull();
	}

	@Test
	public void testCreateBookSuccess() {
		final Book testBook = new Book();
		new Expectations() {
			{
				bookService.save(testBook);
				result = testBook;
			}
		};
		final ResponseEntity<Book> createBook = this.bookController.createBook(testBook);
		Assertions.assertThat(createBook.getStatusCode()).isEqualTo(HttpStatus.OK);
		Assertions.assertThat(createBook.getBody()).isNotNull();
	}

	@Test
	public void testUpdateBookDetailFailFirst() {
		final Book testBook = new Book();
		final Long isbnId = 123L;
		new Expectations() {
			{
				bookService.update(anyLong, testBook);
				result = null;
			}
		};
		final ResponseEntity<Book> updateBookDetail = this.bookController.updateBookDetail(isbnId, testBook);
		Assertions.assertThat(updateBookDetail.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
		Assertions.assertThat(updateBookDetail.getBody()).isNull();
	}

	@Test
	public void testUpdateBookDetailSuccess() {
		final Book testBook = new Book();
		final Long isbnId = 123L;
		new Expectations() {
			{
				bookService.update(anyLong, testBook);
				result = testBook;
			}
		};
		final ResponseEntity<Book> updateBookDetail = this.bookController.updateBookDetail(isbnId, testBook);
		Assertions.assertThat(updateBookDetail.getStatusCode()).isEqualTo(HttpStatus.OK);
		Assertions.assertThat(updateBookDetail.getBody()).isNotNull();
	}

	@Test(expected = RuntimeException.class)
	public void testDeleteBookFailFirst() {
		final Long isbnId = 123L;
		new Expectations() {
			{
				bookService.delete(anyLong);
				result = new RuntimeException();
			}
		};
		final ResponseEntity<Object> deleteBook = this.bookController.deleteBook(isbnId);
		Assertions.assertThat(deleteBook.getStatusCode()).isNotEqualTo(HttpStatus.OK);
	}

	@Test
	public void testDeleteBookSuccess() {
		final Long isbnId = 123L;
		new Expectations() {
			{
				bookService.delete(anyLong);
			}
		};
		final ResponseEntity<Object> deleteBook = this.bookController.deleteBook(isbnId);
		Assertions.assertThat(deleteBook.getStatusCode()).isEqualTo(HttpStatus.OK);
	}
}
