package com.brijesh.bookstore.repositories;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.stereotype.Repository;

import com.brijesh.bookstore.models.BookIssued;

@Repository
public class BookIssueRepository implements BasicCrud<BookIssued> {

	private final CopyOnWriteArrayList<BookIssued> lstBookIssuedGlobal = new CopyOnWriteArrayList<>();

	@Override
	public List<BookIssued> getAll() {
		return lstBookIssuedGlobal;
	}

	@Override
	public BookIssued search(final Long searchParam) {
		final Optional<BookIssued> bookIssued = lstBookIssuedGlobal.stream()
				.filter(book -> book.getIsbnId().equals(searchParam)).findFirst();
		if (bookIssued.isPresent()) {
			return bookIssued.get();
		}
		return null;
	}

	@Override
	public BookIssued create(final BookIssued incommingItem) {
		lstBookIssuedGlobal.add(incommingItem);
		return incommingItem;
	}

	@Override
	public BookIssued update(final Long updateEntityId, final BookIssued incommingItem) {
		return null;
	}

	@Override
	public void delete(final Long deleteEntityId) {
		lstBookIssuedGlobal.removeIf(book -> book.getIsbnId().equals(deleteEntityId));
	}

}
