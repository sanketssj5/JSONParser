package com.opensource;

import com.opensource.exceptions.InvalidTokenException;
import com.opensource.parser.Parser;
import com.opensource.tokens.Token;
import com.opensource.tokens.TokenGenerator;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class AppStarter {
    /*
    TODO:
    1. create a parse object
    2. add validation for numbers and string tokens
    3. accept input from a file.
    4. format test cases.
     */
    public static void main(String[] args) throws IOException {
        Path filePath = Paths.get("/Users/sanketbodele/IdeaProjects/JSONParser/src/test/resources/test.json");
        //failed -> 1, 15, 17, 18, 25, 26, 27, 28, pass1
        String content = Files.readString(filePath);

        System.out.println("Input: \n"+ content);

        List<Token> tokens;
        try {
            tokens = TokenGenerator.generateTokens(content);
        } catch (InvalidTokenException ex) {
            System.out.println(ex.getMessage());
            System.out.println(false);
            return;
        }
        System.out.println("=========");
        System.out.println("Tokens:\n" + tokens);

        Parser parser = new Parser(tokens);
        Boolean isValid = parser.parse();
        System.out.println("=========");
        System.out.println("isValid:\n" + isValid);



    }
}
