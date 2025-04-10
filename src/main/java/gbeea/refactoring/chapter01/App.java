package gbeea.refactoring.chapter01;

import gbeea.refactoring.chapter01.domain.Invoice;
import gbeea.refactoring.chapter01.domain.Performance;
import gbeea.refactoring.chapter01.domain.Play;
import gbeea.refactoring.chapter01.domain.TheaterCompany;

import java.util.List;

public class App {

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
