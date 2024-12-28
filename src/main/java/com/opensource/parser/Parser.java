package com.opensource.parser;

import com.opensource.tokens.Token;
import com.opensource.tokens.TokenType;

import java.util.List;

public class Parser {
    List<Token> tokens;
    int index = 0;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
    }

    /*
       Grammer:
       S -> V | empty
       V -> int | boolean | string | {O} | [L]
       O -> empty | A
       A -> string:V | string:V, A
       L -> D | empty
       D -> V | V,D
     */

    // S from grammer is this method
    public boolean parse() {
        if (tokens == null || tokens.isEmpty()) {
            return false;
        }
        return V() && index == tokens.size();
    }

    private boolean V() {
        switch (getCurrentTokenType()) {
            case INTEGER, BOOLEAN, STRING, NULL: {
                index++;
                return true;
            }
            case OP_BRACE: {
                index++;
                //O() will move the index to the first character which it is not able to pass
                if (index < tokens.size() && O() && index < tokens.size() && getCurrentTokenType().equals(TokenType.CL_BRACE)) {
                    index++;
                    return true;
                }
                return false;
            }
            case OP_SQUARE: {
                index++;
                if (index < tokens.size() && L() && index < tokens.size() && getCurrentTokenType().equals(TokenType.CL_SQUARE)) {
                    index++;
                    return true;
                }
                return false;
            }
            default: return false;
        }
    }

    private boolean O() {
        if (getCurrentTokenType().equals(TokenType.CL_BRACE)) return true;
        return A();
    }
    private boolean A() {
        switch (getCurrentTokenType()) {
            case STRING: {
                index++;
                if(index == tokens.size()) return false;
                if (getCurrentTokenType() != TokenType.COLON) return false;
                index++;
                if(index == tokens.size()) return false;
                if(!V()) return false;
                if(index == tokens.size()) return true;
                if (getCurrentTokenType() != TokenType.COMMA) return true;
                index++;
                if(index == tokens.size()) return false;
                return A();
            }
            default: return false;
        }
    }

    private boolean L() {
        if (getCurrentTokenType().equals(TokenType.CL_SQUARE)) return true;
        return D();
    }

    private boolean D() {
        if(!V()) return false;
        if (index == tokens.size()) return true;
        if (!getCurrentTokenType().equals(TokenType.COMMA)) return true;
        index++;
        if (index == tokens.size()) return false;
        return D();
    }

    private TokenType getCurrentTokenType() {
        return tokens.get(index).getTokenType();
    }
}
