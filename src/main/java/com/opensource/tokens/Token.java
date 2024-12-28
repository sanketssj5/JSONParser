package com.opensource.tokens;

public class Token {

    TokenType tokenType;
    String value;

    public Token(TokenType tokenType, String value) {
        this.tokenType = tokenType;
        this.value = value;
    }

    public TokenType getTokenType() {
        return tokenType;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return tokenType + " " + value;
    }
}
