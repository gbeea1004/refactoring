package gbeea.refactoring.chapter01.domain;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class TheaterCompanyTest {

    @Test
    void statementTest() {
        // given
        TheaterCompany company = new TheaterCompany();
        Invoice invoice = new Invoice("BigCo", List.of(
                new Performance("hamlet", 55),
                new Performance("as-like", 35),
                new Performance("othello", 40)));
        List<Play> plays = List.of(
                new Play("hamlet", "Hamlet", "tragedy"),
                new Play("as-like", "As You Like It", "comedy"),
                new Play("othello", "Othello", "tragedy"));

        // when
        String result = company.statement(invoice, plays);

        // then
        assertThat(result).isEqualTo("청구 내역 (고객명: BigCo)\n" +
                " Hamlet: $650.00 (55석)\n" +
                " As You Like It: $580.00 (35석)\n" +
                " Othello: $500.00 (40석)\n" +
                "총액: $1,730.00\n" +
                "적립 포인트: 47점\n");
    }

    @Test
    void htmlStatementTest() {
        // given
        TheaterCompany company = new TheaterCompany();
        Invoice invoice = new Invoice("BigCo", List.of(
                new Performance("hamlet", 55),
                new Performance("as-like", 35),
                new Performance("othello", 40)));
        List<Play> plays = List.of(
                new Play("hamlet", "Hamlet", "tragedy"),
                new Play("as-like", "As You Like It", "comedy"),
                new Play("othello", "Othello", "tragedy"));

        // when
        String result = company.htmlStatement(invoice, plays);

        // then
        assertThat(result).isEqualTo("<h1>청구 내역 (고객명: BigCo)</h1>\n" +
                "<table>\n" +
                "  <tr>\n" +
                "    <th>연극</th>\n" +
                "    <th>죄석 수</th>\n" +
                "    <th>금액</th>\n" +
                "  </tr>\n" +
                "  <tr>\n" +
                "    <td>Hamlet</td>\n" +
                "    <td>(55석)</td>\n" +
                "    <td>$650.00</td>\n" +
                "  </tr>\n" +
                "  <tr>\n" +
                "    <td>As You Like It</td>\n" +
                "    <td>(35석)</td>\n" +
                "    <td>$580.00</td>\n" +
                "  </tr>\n" +
                "  <tr>\n" +
                "    <td>Othello</td>\n" +
                "    <td>(40석)</td>\n" +
                "    <td>$500.00</td>\n" +
                "  </tr>\n" +
                "</table>\n" +
                "<p>총액: <em>$1,730.00</em></p>\n" +
                "<p>적립 포인트: <em>47</em>점</p>\n");
    }
}