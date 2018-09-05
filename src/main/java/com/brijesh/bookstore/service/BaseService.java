package com.brijesh.bookstore.service;

import java.util.List;

public interface BaseService<T> {

	List<T> getAll();

	T get(Long searchParam);

	T save(T incommingItem);

	T update(Long updateEntityId, T incommingItem);

	void delete(long deleteEntityId);
}
