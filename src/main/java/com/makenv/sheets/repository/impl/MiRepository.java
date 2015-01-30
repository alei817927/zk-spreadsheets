package com.makenv.sheets.repository.impl;

import com.makenv.sheets.repository.BookInfo;
import com.makenv.sheets.repository.BookRepository;
import com.makenv.sheets.util.FileUtil;
import com.makenv.sheets.util.UiUtil;
import org.zkoss.zss.api.Exporters;
import org.zkoss.zss.api.Importers;
import org.zkoss.zss.api.model.Book;

import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by alei on 2015/1/27.
 */
public class MiRepository implements BookRepository {
    private static Map<String, BookInfo> templateBooks;
    private final static String userId = "alei";
    private final static String bookId = "1";

    public MiRepository() {
    }

    @Override
    public BookInfo getTemplate() {
        if (templateBooks == null) {
            templateBooks = getTemplates();
        }
        return templateBooks.get(bookId);
    }

    private Map<String, BookInfo> getTemplates() {
        Map<String, BookInfo> books = new HashMap<>();
        File templateFile = new File(FileUtil.getBookTemplatePath());
        for (File f : templateFile.listFiles(new FileFilter() {
            public boolean accept(File file) {
                if (file.isFile() && !file.isHidden()) {
                    String ext = FileUtil.getNameExtension(file.getName()).toLowerCase();
                    if ("xls".equals(ext) || "xlsx".equals(ext)) {
                        return true;
                    }
                }
                return false;
            }
        })) {
            String[] _fns = f.getName().split("-");
            if (_fns.length < 2) {
                continue;
            }
            books.put(_fns[0], new MiBookInfo(f, f.getName(), new Date(f.lastModified())));
        }
        return books;
    }

    @Override
    public BookInfo getBookInfo() {
        File bookFile = new File(FileUtil.getBookUserFilePath(userId, bookId));
        if (!bookFile.exists()) {
            return null;
        }
        return new MiBookInfo(bookFile, bookFile.getName(), new Date(bookFile.lastModified()));
    }

    @Override
    public Book load(BookInfo info) throws IOException {
        Book book = Importers.getImporter().imports(((MiBookInfo) info).getFile(), info.getName());
        return book;
    }

    @Override
    public boolean save(Book book) throws IOException {
        if (UiUtil.isRepositoryReadonly()) {
            return false;
        }
        FileOutputStream fos = null;
        try {
            FileUtil.checkAndCreateDir(userId);
            File f = new File(FileUtil.getBookUserFilePath(userId, bookId));
            //write to temp file first to avoid write error damage original file
            File temp = File.createTempFile("temp", f.getName());
            fos = new FileOutputStream(temp);
            //ZSS-680: ZSS app always save to xlsx format
            String type = "excel";
            switch (book.getType()) {
                case XLS:
                    type = "xls";
                    break;
                case XLSX:
                    //fall down
                default:
                    type = "xlsx";
                    break;
            }
            Exporters.getExporter(type).export(book, fos);

            fos.close();
            fos = null;

            FileUtil.copy(temp, f);
            temp.delete();

        } finally {
            if (fos != null)
                fos.close();
        }
        return true;
    }

    @Override
    public boolean delete(BookInfo info) throws IOException {
        if (UiUtil.isRepositoryReadonly()) {
            return false;
        }
        File f = ((MiBookInfo) info).getFile();
        return FileUtil.delete(f);
    }
}
