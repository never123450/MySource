package com.xwy.ArrayList;

/**
 * 自定义list接口 
 */
public interface ExtList<E> {
	public void add(E e);

	public int getSize();

	public E get(int index);
}
