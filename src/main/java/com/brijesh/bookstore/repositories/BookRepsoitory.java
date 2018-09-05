package com.brijesh.bookstore.repositories;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Properties;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.annotation.PostConstruct;
import javax.management.RuntimeErrorException;

import org.springframework.stereotype.Repository;

import com.brijesh.bookstore.constants.BookstoreConstants;
import com.brijesh.bookstore.models.Book;

/**
 * @author brijesh
 *         <p>
 *         Represents a repository to work on book entity. Repository can be a
 *         DB or another REST service to fetch books in context of bookstore.
 *         Thing to notice here is for demonstration purpose I am not connecting
 *         this application to a DB and making a private shared list of books to
 *         operate upon.
 *         </p>
 */
@Repository
public class BookRepsoitory implements BasicCrud<Book> {

	/**
	 * Making a global books list because Iam going to mock book database. So as
	 * soon as the spring application boots up this datastructure will take care of
	 * books for us.
	 */
	List<Book> lstGlobalBooks = new CopyOnWriteArrayList<Book>();

	@PostConstruct
	public void init() {
		final Properties properties = new Properties();
		InputStream fileInputStream = null;
		try {
			fileInputStream = new FileInputStream(new File("src/main/resources/book.properties"));
			properties.load(fileInputStream);
			long isbnCounter = 1l;
			for (final Entry<Object, Object> entry : properties.entrySet()) {
				final String[] arrKey = entry.getKey().toString().split(",");
				final Book tempBook = new Book();
				tempBook.setName(arrKey[BookstoreConstants.INDEX_ZERO]);
				tempBook.setAuthor(arrKey[BookstoreConstants.INDEX_ONE]);
				tempBook.setNumberOfBooks(Integer.parseInt(entry.getValue().toString()));
				tempBook.setIsbn(++isbnCounter + BookstoreConstants.PRIMARY_KEY_CONSTANT);
				lstGlobalBooks.add(tempBook);
			}
		} catch (final IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public List<Book> getAll() {
		return lstGlobalBooks;
	}

	@Override
	public Book search(final Long searchParam) {
		final Book bookFound = lstGlobalBooks.stream().filter(book -> book.getIsbn().equals(searchParam)).findFirst()
				.get();
		if (bookFound != null) {
			return bookFound;
		}
		return null;
	}

	@Override
	public Book create(final Book incommingItem) {
		// First lets search if the same isbn is not already present
		if (lstGlobalBooks.stream().filter(book -> book.getIsbn().equals(incommingItem.getIsbn())).findFirst()
				.isPresent()) {
			// Throw exception
			throw new RuntimeErrorException(new Error("IsbnId already present."));
		} else {
			lstGlobalBooks.add(incommingItem);
			return incommingItem;
		}
	}

	@Override
	public Book update(final Long updateEntityId, final Book incommingItem) {
		final Optional<Book> bookToUpdate = lstGlobalBooks.stream()
				.filter(book -> book.getIsbn().equals(updateEntityId)).findFirst();
		if (!bookToUpdate.isPresent()) {
			// Throw exception
			throw new RuntimeErrorException(new Error("Entity to update is not present."));
		}
		lstGlobalBooks.removeIf(book -> book.getIsbn().equals(updateEntityId));
		lstGlobalBooks.add(incommingItem);
		return incommingItem;
	}

	@Override
	public void delete(final Long deleteEntityId) {
		final Optional<Book> bookToDelete = lstGlobalBooks.stream()
				.filter(book -> book.getIsbn().equals(deleteEntityId)).findFirst();
		if (!bookToDelete.isPresent()) {
			// Throw Exception
			throw new RuntimeErrorException(new Error("Entity to delete not found."));
		}
		lstGlobalBooks.removeIf(book -> book.getIsbn().equals(deleteEntityId));
	}

}
