package com.anna.model;

import java.util.Date;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

public class SaveGroup {

  @Size(min = 1, max = 15)
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

  /**
   * Constructor for class SaveGroup.
   *
   * @param name this is group name
   * @param createDate this is the date the group was created
   * @param finishDate this is the graduation date
   */
  public SaveGroup(String name, Date createDate, Date finishDate) {
    this.name = name;
    this.createDate = createDate;
    this.finishDate = finishDate;
  }

  public SaveGroup(Date createDate, Date finishDate) {
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
