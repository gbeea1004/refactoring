package gbeea.refactoring.domain;

public class TragedyCalculator extends PerformanceCalculator {

    protected TragedyCalculator(Performance aPerformance, Play aPlay) {
        super(aPerformance, aPlay);
    }

    @Override
    public int getAmount() {
        int result = 40000;
        int audience = getPerformance().getAudience();

        if (audience > 30) {
            result += 1000 * (audience - 30);
        }

        return result;
    }
}
