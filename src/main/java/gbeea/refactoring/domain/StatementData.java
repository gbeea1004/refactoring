package gbeea.refactoring.domain;

import java.util.ArrayList;
import java.util.List;

public class StatementData {

    private final String customer;
    private final List<Performance> performances;
    private final List<Play> plays;
    private final int totalAmount;
    private final int totalVolumeCredits;

    public static StatementData of(Invoice invoice, List<Play> plays) {
        return new StatementData(invoice, plays);
    }

    private StatementData(Invoice invoice, List<Play> plays) {
        this.customer = invoice.getCustomer();
        this.performances = new ArrayList<>(invoice.getPerformances());
        this.plays = new ArrayList<>(plays);
        this.totalAmount = totalAmount();
        this.totalVolumeCredits = totalVolumeCredits();
    }

    private int totalVolumeCredits() {
        return performances.stream()
                .mapToInt(this::volumeCreditsFor)
                .sum();
    }

    private int volumeCreditsFor(Performance aPerformance) {
        return PerformanceCalculator.of(aPerformance, playFor(aPerformance)).getVolumeCredits();
    }

    public Play playFor(Performance aPerformance) {
        return plays.stream()
                .filter(p -> aPerformance.getPlayId().equals(p.getId()))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    private int totalAmount() {
        return performances.stream()
                .mapToInt(this::amountFor)
                .sum();
    }

    public int amountFor(Performance aPerformance) {
        return PerformanceCalculator.of(aPerformance, playFor(aPerformance)).getAmount();
    }

    public String getCustomer() {
        return customer;
    }

    public List<Performance> getPerformances() {
        return performances;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public int getTotalVolumeCredits() {
        return totalVolumeCredits;
    }
}
