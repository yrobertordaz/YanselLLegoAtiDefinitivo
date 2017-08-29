package com.llegoati.llegoati.infrastructure.models;

/**
 * Created by Yansel on 1/2/2017.
 */

public class Comment {
    private String Id;
    private String TextComment;

    public Comment(String id, String textComment) {
        Id = id;
        TextComment = textComment;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getTextComment() {
        return TextComment;
    }

    public void setTextComment(String textComment) {
        TextComment = textComment;
    }
}
