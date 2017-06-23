package com.ccd.models;

public class InterpretationCode {
private String name;
private Code code;

/**
 * {@inheritDoc}
 */
@Override
public String toString() {
    return "InterpretationCode [name=" + name + ", code=" + code + "]";
}

/**
 * @return the name
 */
public String getName() {
    return name;
}

/**
 * @param name the name to set
 */
public void setName(String name) {
    this.name = name;
}

/**
 * @return the code
 */
public Code getCode() {
    return code;
}

/**
 * @param code the code to set
 */
public void setCode(Code code) {
    this.code = code;
}
}
