package gbeea.refactoring.chapter01.domain;

public class ComedyCalculator extends PerformanceCalculator {

    protected ComedyCalculator(Performance aPerformance, Play aPlay) {
        super(aPerformance, aPlay);
    }

    @Override
    public int getAmount() {
        int result = 30000;
        int audience = getPerformance().getAudience();

        if (audience > 20) {
            result += 10000 + 500 * (audience - 20);
        }
        result += 300 * audience;

        return result;
    }

    @Override
    public int getVolumeCredits() {
        return super.getVolumeCredits() + getPerformance().getAudience() / 5;
    }
}
