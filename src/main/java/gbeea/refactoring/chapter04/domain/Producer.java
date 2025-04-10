package gbeea.refactoring.chapter04.domain;

public class Producer {

    private Province province;
    private String name;
    private int cost;
    private int production;

    public Producer(String name, int cost, int production) {
        this.name = name;
        this.cost = cost;
        this.production = production;
    }

    public void setProvince(Province province) {
        this.province = province;
    }

    public void setProduction(String amountStr) {
        production = Integer.parseInt(amountStr);
        province.addProduction(production);
    }

    public int getCost() {
        return cost;
    }

    public int getProduction() {
        return production;
    }
}
