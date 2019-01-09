package com.anna.model;

import com.anna.model.json.View;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SaveGroup {

    @JsonView({View.Student.class, View.GroupWithStudents.class})
    @Min(value = 1, message = "Id should not be less than 0")
    @NotNull
    private int groupId;

    @JsonView({View.Student.class, View.GroupWithStudents.class})
    @Size(min = 2, max = 15)
    @NotNull
    private String name;

    @JsonView(View.GroupWithStudents.class)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull
    private Date createDate;

    @JsonView(View.GroupWithStudents.class)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull
    private Date finishDate;

    public SaveGroup() {

    }

    public SaveGroup(int groupId, String name, Date createDate, Date finishDate) {
        this.groupId = groupId;
        this.name = name;
        this.createDate = createDate;
        this.finishDate = finishDate;
    }

    public SaveGroup(String name, Date createDate, Date finishDate) {
        this.name = name;
        this.createDate = createDate;
        this.finishDate = finishDate;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
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
