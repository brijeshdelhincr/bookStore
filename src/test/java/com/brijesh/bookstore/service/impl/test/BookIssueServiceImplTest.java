package com.brijesh.bookstore.service.impl.test;

import java.util.Arrays;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.brijesh.bookstore.models.Book;
import com.brijesh.bookstore.models.BookIssued;
import com.brijesh.bookstore.repositories.BookIssueRepository;
import com.brijesh.bookstore.service.BookService;
import com.brijesh.bookstore.service.impl.BookIssueServiceImpl;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import mockit.integration.junit4.JMockit;

@RunWith(JMockit.class)
public class BookIssueServiceImplTest {

	@Tested
	private BookIssueServiceImpl bookIssueService;

	@Injectable
	private BookService bookService;

	@Injectable
	private BookIssueRepository bookIssueRepository;

	@Test
	public void testIssueBookByIdWhenBookNotPresent() {
		final BookIssued bookIssued = new BookIssued();
		bookIssued.setIsbnId(123L);
		new Expectations() {
			{
				bookService.getAll();
				result = Arrays.asList();
			}
		};
		final BookIssued issueBookById = this.bookIssueService.issueBookById(bookIssued);
		Assertions.assertThat(issueBookById).isNull();
	}

	@Test
	public void testIssueBookByIdWhenBookPresent() {
		final BookIssued bookIssued = new BookIssued();
		final Book bookToIssue = new Book();
		bookToIssue.setIsbn(123L);
		bookToIssue.setNumberOfBooks(2);
		bookIssued.setIsbnId(123L);
		new Expectations() {
			{
				bookService.getAll();
				result = Arrays.asList(bookToIssue);

				bookIssueService.save(bookIssued);
				result = bookIssued;

				bookService.update(anyLong, bookToIssue);
				result = bookToIssue;
			}
		};
		final BookIssued issueBookById = this.bookIssueService.issueBookById(bookIssued);
		Assertions.assertThat(issueBookById).isNotNull();
		// Number of books to decrease
		Assertions.assertThat(bookToIssue.getNumberOfBooks()).isEqualTo(1);
	}

	@Test
	public void testReturnIssuedBookFailFirstTest() {
		new Expectations() {
			{
				bookIssueService.get(123L);
				result = null;
			}
		};
		final BookIssued returnIssuedBook = this.bookIssueService.returnIssuedBook(123L);
		Assertions.assertThat(returnIssuedBook).isNull();
	}

	@Test
	public void testReturnIssuedBookSuccessTest() {
		final BookIssued bookIssued = new BookIssued();
		final Book bookToReturn = new Book();
		bookToReturn.setIsbn(123L);
		bookToReturn.setNumberOfBooks(2);
		bookIssued.setIsbnId(123L);
		new Expectations() {
			{
				bookIssueService.get(anyLong);
				result = bookIssued;

				bookIssueService.delete(anyLong);

				bookService.get(anyLong);
				result = bookToReturn;

				bookService.update(anyLong, bookToReturn);
				result = bookToReturn;
			}
		};
		final BookIssued returnIssuedBook = this.bookIssueService.returnIssuedBook(123L);
		Assertions.assertThat(returnIssuedBook).isNotNull();
		Assertions.assertThat(bookToReturn.getNumberOfBooks()).isEqualTo(3);
	}

	@Test
	public void getAllFailFirst() {
		new Expectations() {
			{
				bookIssueRepository.getAll();
				result = Arrays.asList();
			}
		};
		final List<BookIssued> all = this.bookIssueService.getAll();
		Assertions.assertThat(all).isEmpty();
	}

	@Test
	public void getAllSucces() {
		final BookIssued bookIssued = new BookIssued();
		new Expectations() {
			{
				bookIssueRepository.getAll();
				result = Arrays.asList(bookIssued);
			}
		};
		final List<BookIssued> all = this.bookIssueService.getAll();
		Assertions.assertThat(all).isNotEmpty();
	}

	@Test
	public void getFailFirst() {
		final Long isbnId = 123L;
		new Expectations() {
			{
				bookIssueRepository.search(anyLong);
				result = null;
			}
		};
		final BookIssued bookIssued = this.bookIssueService.get(isbnId);
		Assertions.assertThat(bookIssued).isNull();
	}

	@Test
	public void getSuccess() {
		final Long isbnId = 123L;
		final BookIssued bookIssued = new BookIssued();
		new Expectations() {
			{
				bookIssueRepository.search(anyLong);
				result = Arrays.asList(bookIssued);
			}
		};
		final BookIssued bookIssuedRet = this.bookIssueService.get(isbnId);
		Assertions.assertThat(bookIssuedRet).isNotNull();
	}
}
