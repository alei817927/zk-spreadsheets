package com.makenv.sheets.repository.impl;

import com.makenv.sheets.repository.BookInfo;

import java.io.File;
import java.util.Date;

/**
 * Created by alei on 2015/1/27.
 */
public class MiBookInfo implements BookInfo {
    private String name;
    private Date lastModify;
    private File file;
    private String id;

    public MiBookInfo() {
    }
    public MiBookInfo(File file, String name, Date lastModify) {
        this.file = file;
        this.name = name;
        this.lastModify = lastModify;
    }

    public String getId() {
        return id;
    }

    public void setLastModify(Date lastModify) {

        this.lastModify = lastModify;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getLastModify() {
        return new Date(file.lastModified());
    }

    public File getFile() {
        return file;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((file == null) ? 0 : file.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        MiBookInfo other = (MiBookInfo) obj;
        if (file == null) {
            if (other.file != null)
                return false;
        } else if (!file.equals(other.file))
            return false;
        return true;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{name:").append(name).append(", file:" + file).append("}");
        return sb.toString();
    }
}
