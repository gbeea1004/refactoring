package gbeea.refactoring.domain;

import java.util.ArrayList;
import java.util.List;

public class StatementData {

    private final String customer;
    private final List<Performance> performances;
    private final List<Play> plays;

    public static StatementData of(Invoice invoice, List<Play> plays) {
        return new StatementData(invoice, plays);
    }

    private StatementData(Invoice invoice, List<Play> plays) {
        this.customer = invoice.getCustomer();
        this.performances = new ArrayList<>(invoice.getPerformances());
        this.plays = new ArrayList<>(plays);
    }

    public Play getPlay(Performance aPerformance) {
        return plays.stream()
                .filter(p -> aPerformance.getPlayId().equals(p.getId()))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    public int getAmount(Performance aPerformance) {
        int result;
        switch (getPlay(aPerformance).getType()) {
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
                throw new IllegalArgumentException("알 수 없는 장르: " + getPlay(aPerformance).getName());
        }
        return result;
    }

    public int totalVolumeCredits() {
        return performances.stream()
                .mapToInt(this::volumeCreditsFor)
                .sum();
    }

    private int volumeCreditsFor(Performance aPerformance) {
        int result = Math.max(aPerformance.getAudience() - 30, 0);
        if ("comedy".equals(getPlay(aPerformance).getType())) {
            result += aPerformance.getAudience() / 5;
        }
        return result;
    }

    public int totalAmount() {
        return performances.stream()
                .mapToInt(this::getAmount)
                .sum();
    }

    public String getCustomer() {
        return customer;
    }

    public List<Performance> getPerformances() {
        return performances;
    }
}
