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
            int thisAmount = amountFor(plays, performance);

            // 포인트 적립
            volumeCredits += Math.max(performance.getAudience() - 30, 0);
            // 추가 포인트 적립
            if ("comedy".equals(playFor(plays, performance).getType())) {
                volumeCredits += performance.getAudience() / 5;
            }

            result += " " + playFor(plays, performance).getName() + ": " + numberFormat.format(thisAmount / 100) + " (" + performance.getAudience() + "석)\n";
            totalAmount += thisAmount;
        }
        result += "총액: " + numberFormat.format(totalAmount / 100) + "\n";
        result += "적립 포인트: " + volumeCredits + "점\n";
        return result;
    }

    private Play playFor(List<Play> plays, Performance aPerformance) {
        return plays.stream()
                .filter(p -> aPerformance.getPlayId().equals(p.getId()))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    private int amountFor(List<Play> plays, Performance aPerformance) {
        int result;
        switch (playFor(plays, aPerformance).getType()) {
            case "tragedy":
                result = 40000;
                if (aPerformance.getAudience() > 30) {
                    result += 1000 * (aPerformance.getAudience() - 30);
                }
                break;
            case "comedy":
                result = 30000;
                if (aPerformance.getAudience() > 20) {
                    result += 10000 + 500 * (aPerformance.getAudience() - 20);
                }
                result += 300 * aPerformance.getAudience();
                break;
            default:
                throw new IllegalArgumentException("알 수 없는 장르: " + playFor(plays, aPerformance).getName());
        }
        return result;
    }
}
