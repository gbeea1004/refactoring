package gbeea.refactoring.chapter01.domain;

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
            result += " " + data.playFor(performance).getName() + ": " + usd(data.amountFor(performance)) + " (" + performance.getAudience() + "석)\n";
        }
        result += "총액: " + usd(data.getTotalAmount()) + "\n";
        result += "적립 포인트: " + data.getTotalVolumeCredits() + "점\n";
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
            result += "  <tr>\n    <td>" + data.playFor(performance).getName() + "</td>\n    <td>(" + performance.getAudience() + "석)</td>\n";
            result += "    <td>" + usd(data.amountFor(performance)) + "</td>\n  </tr>\n";
        }
        result += "</table>\n";
        result += "<p>총액: <em>" + usd(data.getTotalAmount()) + "</em></p>\n";
        result += "<p>적립 포인트: <em>" + data.getTotalVolumeCredits() + "</em>점</p>\n";
        return result;
    }

    private String usd(int aNumber) {
        return NumberFormat.getCurrencyInstance(Locale.US)
                .format(aNumber / 100);
    }
}
