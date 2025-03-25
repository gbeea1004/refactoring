package gbeea.refactoring.domain;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class TheaterCompany {

    public String statement(Invoice invoice, List<Play> plays) {
        return renderPlainText(StatementData.of(invoice, plays));
    }

    private String renderPlainText(StatementData data) {
        String result = "청구 내역 (고객명: " + data.getCustomer() + ")\n";
        for (Performance performance : data.getPerformances()) {
            result += " " + data.getPlay(performance).getName() + ": " + usd(data.getAmount(performance)) + " (" + performance.getAudience() + "석)\n";
        }
        result += "총액: " + usd(data.totalAmount()) + "\n";
        result += "적립 포인트: " + data.totalVolumeCredits() + "점\n";
        return result;
    }

    private String usd(int aNumber) {
        return NumberFormat.getCurrencyInstance(Locale.US)
                .format(aNumber / 100);
    }
}
