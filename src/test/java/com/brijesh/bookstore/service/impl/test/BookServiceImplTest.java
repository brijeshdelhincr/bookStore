package com.brijesh.bookstore.service.impl.test;

import java.util.Arrays;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.brijesh.bookstore.models.Book;
import com.brijesh.bookstore.repositories.BookRepsoitory;
import com.brijesh.bookstore.service.impl.BookServiceImpl;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import mockit.integration.junit4.JMockit;

@RunWith(JMockit.class)
public class BookServiceImplTest {

	@Tested
	private BookServiceImpl bookServiceImpl;

	@Injectable
	private BookRepsoitory bookRepository;

	@Test
	public void testGetAllFailFirst() {
		new Expectations() {
			{
				bookRepository.getAll();
				result = null;
			}
		};
		final List<Book> all = this.bookServiceImpl.getAll();
		Assertions.assertThat(all).isNull();
	}

	@Test
	public void testGetAllSuccess() {
		final Book testBook = new Book();
		new Expectations() {
			{
				bookRepository.getAll();
				result = Arrays.asList(testBook);
			}
		};
		final List<Book> all = this.bookServiceImpl.getAll();
		Assertions.assertThat(all).isNotNull();
		Assertions.assertThat(all).hasSize(1);
	}

	@Test
	public void testGetFailFirst() {
		new Expectations() {
			{
				bookRepository.search(anyLong);
				result = null;
			}
		};
		final Book book = this.bookServiceImpl.get(123L);
		Assertions.assertThat(book).isNull();
	}

	@Test
	public void testGetSuccess() {
		final Book testBook = new Book();
		new Expectations() {
			{
				bookRepository.search(anyLong);
				result = testBook;
			}
		};
		final Book book = this.bookServiceImpl.get(123L);
		Assertions.assertThat(book).isNotNull();
	}

	@Test
	public void testSaveFailFirst() {
		final Book testBook = new Book();
		new Expectations() {
			{
				bookRepository.create(testBook);
				result = null;
			}
		};
		final Book book = this.bookServiceImpl.save(testBook);
		Assertions.assertThat(book).isNull();
	}

	@Test
	public void testSaveSuccess() {
		final Book testBook = new Book();
		new Expectations() {
			{
				bookRepository.create(testBook);
				result = testBook;
			}
		};
		final Book book = this.bookServiceImpl.save(testBook);
		Assertions.assertThat(book).isNotNull();
	}

	@Test
	public void testUpdateFailTest() {
		final Book testBook = new Book();
		new Expectations() {
			{
				bookRepository.update(anyLong, testBook);
				result = null;
			}
		};
		final Book update = this.bookServiceImpl.update(123L, testBook);
		Assertions.assertThat(update).isNull();
	}

	@Test
	public void testUpdateSuccessTest() {
		final Book testBook = new Book();
		new Expectations() {
			{
				bookRepository.update(anyLong, testBook);
				result = testBook;
			}
		};
		final Book update = this.bookServiceImpl.update(123L, testBook);
		Assertions.assertThat(update).isNotNull();
	}
}
