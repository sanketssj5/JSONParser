package com.opensource.tokens;

import com.opensource.exceptions.InvalidTokenException;

import java.util.ArrayList;
import java.util.List;

public class TokenGenerator {


    public static List<Token> generateTokens(String input) throws InvalidTokenException {
        List<Token> tokens = new ArrayList<>();
        int i = 0;
        while(i < input.length()) {
            if(Character.isWhitespace(input.charAt(i))) {
                i++;
                continue;
            }
            if (input.charAt(i) == '{') {
                tokens.add(new Token(TokenType.OP_BRACE, String.valueOf(input.charAt(i))));
                i++;
            } else if (input.charAt(i) == '}') {
                tokens.add(new Token(TokenType.CL_BRACE, String.valueOf(input.charAt(i))));
                i++;
            }  else if (input.charAt(i) == '[') {
                tokens.add(new Token(TokenType.OP_SQUARE, String.valueOf(input.charAt(i))));
                i++;
            } else if (input.charAt(i) == ']') {
                tokens.add(new Token(TokenType.CL_SQUARE, String.valueOf(input.charAt(i))));
                i++;
            } else if (input.charAt(i) == ':') {
                tokens.add(new Token(TokenType.COLON, String.valueOf(input.charAt(i))));
                i++;
            } else if (input.charAt(i) == ',') {
                tokens.add(new Token(TokenType.COMMA, String.valueOf(input.charAt(i))));
                i++;
            } else if(input.charAt(i) == '"') {
                StringBuilder sb = new StringBuilder();
                i++;
                while(input.charAt(i) != '"') sb.append(input.charAt(i++));
                tokens.add(new Token(TokenType.STRING, sb.toString()));
                i++;
            } else if(Character.isDigit(input.charAt(i))) {
                if(input.charAt(i) == '0') invalidTokenException(input, i);
                StringBuilder sb = new StringBuilder();
                while(Character.isDigit(input.charAt(i))) sb.append(input.charAt(i++));
                tokens.add(new Token(TokenType.INTEGER, sb.toString()));
            } else if( i + 4 <= input.length() && "true".equals(input.substring(i, i + 4))) {
                tokens.add(new Token(TokenType.BOOLEAN, "true"));
                i+=4;
            }  else if( i + 5 <= input.length() && "false".equals(input.substring(i, i + 5))) {
                tokens.add(new Token(TokenType.BOOLEAN, "false"));
                i+=5;
            }else if( i + 4 <= input.length() && "null".equals(input.substring(i, i + 4))) {
                tokens.add(new Token(TokenType.NULL, "null"));
                i+=4;
            }   else {
                invalidTokenException(input, i);
            }
        }

        return tokens;
    }

    private static void invalidTokenException(String input, int i) throws InvalidTokenException {
        throw new InvalidTokenException("Not a valid token: " + input.charAt(i) + " index: " + i);
    }
}
