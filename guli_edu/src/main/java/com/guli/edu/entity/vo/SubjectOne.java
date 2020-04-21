package com.guli.edu.entity.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SubjectOne {

    private String id;

    private String title;

    private List<SubjectTwo> children = new ArrayList<>();

}
