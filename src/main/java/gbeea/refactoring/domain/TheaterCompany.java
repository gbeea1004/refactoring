package gbeea.refactoring.domain;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class TheaterCompany {

    private static final NumberFormat numberFormat = NumberFormat.getCurrencyInstance(Locale.US);

    public String statement(Invoice invoice, List<Play> plays) {
        int totalAmount = 0;
        int volumeCredits = 0;
        String result = "청구 내역 (고객명: " + invoice.getCustomer() + ")\n";

        for (Performance performance : invoice.getPerformances()) {
            Play play = plays.stream()
                    .filter(p -> performance.getPlayId().equals(p.getId()))
                    .findFirst()
                    .orElseThrow(IllegalArgumentException::new);
            int thisAmount = amountFor(performance, play);

            // 포인트 적립
            volumeCredits += Math.max(performance.getAudience() - 30, 0);
            // 추가 포인트 적립
            if ("comedy".equals(play.getType())) {
                volumeCredits += performance.getAudience() / 5;
            }

            result += " " + play.getName() + ": " + numberFormat.format(thisAmount / 100) + " (" + performance.getAudience() + "석)\n";
            totalAmount += thisAmount;
        }
        result += "총액: " + numberFormat.format(totalAmount / 100) + "\n";
        result += "적립 포인트: " + volumeCredits + "점\n";
        return result;
    }

    private int amountFor(Performance performance, Play play) {
        int thisAmount;
        switch (play.getType()) {
            case "tragedy":
                thisAmount = 40000;
                if (performance.getAudience() > 30) {
                    thisAmount += 1000 * (performance.getAudience() - 30);
                }
                break;
            case "comedy":
                thisAmount = 30000;
                if (performance.getAudience() > 20) {
                    thisAmount += 10000 + 500 * (performance.getAudience() - 20);
                }
                thisAmount += 300 * performance.getAudience();
                break;
            default:
                throw new IllegalArgumentException("알 수 없는 장르: " + play.getName());
        }
        return thisAmount;
    }
}
