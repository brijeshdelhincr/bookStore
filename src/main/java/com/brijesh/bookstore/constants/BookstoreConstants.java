package com.brijesh.bookstore.constants;

public final class BookstoreConstants {

	public static final String BOOK_ENDPOINT = "book";
	public static final String ENDPOINT_SEPARATOR = "/";
	public static final String APPLICATION_PRODUCER_TYPE = "application/json";
	public static final String BY_ISBNID = ENDPOINT_SEPARATOR + "{isbnId}";
	public static final String BOOK_ISSUE = BOOK_ENDPOINT + ENDPOINT_SEPARATOR + "issue";
	
	public static final int INDEX_ONE = 1;
	public static final int INDEX_ZERO = 0;
	public static final long PRIMARY_KEY_CONSTANT = 235616l;
}
