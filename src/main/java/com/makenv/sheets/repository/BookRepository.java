package com.makenv.sheets.repository;

import org.zkoss.zss.api.model.Book;

import java.io.IOException;
import java.util.Map;

/**
 * Created by alei on 2015/1/27.
 */
public interface BookRepository {

    BookInfo getTemplate();
    BookInfo getBookInfo();

    Book load(BookInfo info) throws IOException;

    boolean save(Book book) throws IOException;

    boolean delete(BookInfo info) throws IOException;
}
