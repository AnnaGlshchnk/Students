package com.anna.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

public class SaveGroup {

    @Size(min = 2, max = 15)
    @NotNull
    private String name;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull
    private Date createDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull
    private Date finishDate;

    public SaveGroup() {

    }

    public SaveGroup(String name, Date createDate, Date finishDate) {
        this.name = name;
        this.createDate = createDate;
        this.finishDate = finishDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }
}
