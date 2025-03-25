package gbeea.refactoring.domain;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class TheaterCompany {

    public String statement(Invoice invoice, List<Play> plays) {
        StatementData statementData = new StatementData(invoice, plays);

        return renderPlainText(statementData, plays);
    }

    private String renderPlainText(StatementData data, List<Play> plays) {
        String result = "청구 내역 (고객명: " + data.getCustomer() + ")\n";
        for (Performance performance : data.getPerformances()) {
            result += " " + data.getPlay(performance).getName() + ": " + usd(data.getAmount(performance)) + " (" + performance.getAudience() + "석)\n";
        }
        result += "총액: " + usd(totalAmount(data)) + "\n";
        result += "적립 포인트: " + totalVolumeCredits(data, plays) + "점\n";
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

    private String usd(int aNumber) {
        return NumberFormat.getCurrencyInstance(Locale.US)
                .format(aNumber / 100);
    }

    private int totalAmount(StatementData data) {
        int result = 0;
        for (Performance performance : data.getPerformances()) {
            result += data.getAmount(performance);
        }
        return result;
    }

    private int totalVolumeCredits(StatementData data, List<Play> plays) {
        int result = 0;
        for (Performance performance : data.getPerformances()) {
            result += volumeCreditsFor(plays, performance);
        }
        return result;
    }

    private int volumeCreditsFor(List<Play> plays, Performance aPerformance) {
        int result = Math.max(aPerformance.getAudience() - 30, 0);
        if ("comedy".equals(playFor(plays, aPerformance).getType())) {
            result += aPerformance.getAudience() / 5;
        }
        return result;
    }
}
