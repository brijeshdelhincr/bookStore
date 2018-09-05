package com.brijesh.bookstore.repositories;

import java.util.List;

public interface BasicCrud<T> {

	List<T> getAll();

	T search(final Long searchParam);

	T create(final T incommingItem);

	T update(final Long updateEntityId, final T incommingItem);

	void delete(final Long deleteEntityId);
}
