package com.guli.edu.entity.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class CourseQuery implements Serializable {

    private String title;

    private String teacherId;

    private String subjectParentId;

    private String subjectId;

}
