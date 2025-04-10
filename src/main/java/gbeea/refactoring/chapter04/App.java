package gbeea.refactoring.chapter04;

import gbeea.refactoring.chapter04.domain.Producer;
import gbeea.refactoring.chapter04.domain.Province;

import java.util.List;

public class App {

    public static void main(String[] args) {
        Province province = new Province(
                "Asia",
                List.of(
                        new Producer("Byzantium", 10, 9),
                        new Producer("Attalia", 12, 10),
                        new Producer("Sinope", 10, 6)
                ),
                "30",
                "20"
                );
        province.getProducers().forEach(producer -> producer.setProvince(province));

        System.out.println("부족분 = " + province.shortfall());
    }
}
