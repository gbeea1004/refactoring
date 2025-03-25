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

    public String htmlStatement(Invoice invoice, List<Play> plays) {
        return renderHtml(StatementData.of(invoice, plays));
    }

    private String renderHtml(StatementData data) {
        String result = "<h1>청구 내역 (고객명: " + data.getCustomer() + ")</h1>\n";
        result += "<table>\n";
        result += "  <tr>\n    <th>연극</th>\n    <th>죄석 수</th>\n    <th>금액</th>\n  </tr>\n";
        for (Performance performance : data.getPerformances()) {
            result += "  <tr>\n    <td>" + data.getPlay(performance).getName() + "</td>\n    <td>(" + performance.getAudience() + "석)</td>\n";
            result += "    <td>" + usd(data.getAmount(performance)) + "</td>\n  </tr>\n";
        }
        result += "</table>\n";
        result += "<p>총액: <em>" + usd(data.totalAmount()) + "</em></p>\n";
        result += "<p>적립 포인트: <em>" + data.totalVolumeCredits() + "</em>점</p>\n";
        return result;
    }

    private String usd(int aNumber) {
        return NumberFormat.getCurrencyInstance(Locale.US)
                .format(aNumber / 100);
    }
}
