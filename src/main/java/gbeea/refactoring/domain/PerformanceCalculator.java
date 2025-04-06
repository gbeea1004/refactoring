package gbeea.refactoring.domain;

public abstract class PerformanceCalculator {

    private final Performance performance;
    private final Play play;

    public static PerformanceCalculator of(Performance aPerformance, Play aPlay) {
        return switch (aPlay.getType()) {
            case "tragedy" -> new TragedyCalculator(aPerformance, aPlay);
            case "comedy" -> new ComedyCalculator(aPerformance, aPlay);
            default -> throw new IllegalArgumentException("알 수 없는 장르: " + aPlay.getName());
        };
    }

    protected PerformanceCalculator(Performance aPerformance, Play aPlay) {
        this.performance = aPerformance;
        this.play = aPlay;
    }

    public int getVolumeCredits() {
        return Math.max(performance.getAudience() - 30, 0);
    }

    public Performance getPerformance() {
        return performance;
    }

    public Play getPlay() {
        return play;
    }

    public abstract int getAmount();
}
