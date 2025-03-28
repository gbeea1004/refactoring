package gbeea.refactoring;

import gbeea.refactoring.domain.Invoice;
import gbeea.refactoring.domain.Performance;
import gbeea.refactoring.domain.Play;
import gbeea.refactoring.domain.TheaterCompany;

import java.util.List;

public class RefactoringApplication {

    public static void main(String[] args) {
        TheaterCompany company = new TheaterCompany();
        Invoice invoice = new Invoice("BigCo", List.of(
                new Performance("hamlet", 55),
                new Performance("as-like", 35),
                new Performance("othello", 40)));
        List<Play> plays = List.of(
                new Play("hamlet", "Hamlet", "tragedy"),
                new Play("as-like", "As You Like It", "comedy"),
                new Play("othello", "Othello", "tragedy"));

        String result = company.statement(invoice, plays);

        System.out.println(result);
    }
}
