package com.fno.community.exception;

public enum CustomizeErrorCode implements ICustomizeErrorCode {
    QUESTION_NOT_FOUND(2001,"您找的问题不在了，要不要换个问题试试？"),
    TARGET_PARAM_NOT_FOUND(2002,"未选中任何问题或评论进行回复"),
    NO_LOGIN(2003,"无法进行操作，请登录后重试"),
    SYS_ERROR(2004,"服务冒烟了，要不然您稍后再试试~"),
    TYPE_PARAM_WRONG(2005,"评论内容错误或不存在"),
    COMMENT_NOT_FOUND(2006,"回复的内容不存在"),
    COMMENT_IS_EMPTY(2007,"回复的内容不能为空")
    ;
    private String message;
    private Integer code;

    CustomizeErrorCode(Integer code ,String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Integer getCode() {
        return code;
    }
}
