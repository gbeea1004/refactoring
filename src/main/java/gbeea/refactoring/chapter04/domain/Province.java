package gbeea.refactoring.chapter04.domain;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Province {

    private String name;
    private List<Producer> producers;
    private int totalProduction;
    private int demand;
    private int price;

    public Province(String name, List<Producer> producers, String demand, String price) {
        this.name = name;
        this.producers = producers;
        this.producers.forEach(producer -> addProduction(producer.getProduction()));
        this.demand = Integer.parseInt(demand);
        this.price = Integer.parseInt(price);
    }

    public void addProducer(Producer producer) {
        producers.add(producer);
    }

    public void addProduction(int production) {
        totalProduction += production;
    }

    public int shortfall() {
        return demand - totalProduction;
    }

    public int profit() {
        return demandValue() - demandCost();
    }

    private int demandValue() {
        return satisfiedDemand() * price;
    }

    private int satisfiedDemand() {
        return Math.min(totalProduction, demand);
    }

    private int demandCost() {
        AtomicInteger remainingDemand = new AtomicInteger(demand);
        AtomicInteger result = new AtomicInteger();
        producers.sort(Comparator.comparingInt(Producer::getCost));
        producers.forEach(producer -> {
            int contribution = Math.min(remainingDemand.get(), producer.getProduction());
            remainingDemand.addAndGet(-contribution);
            result.addAndGet(contribution * producer.getCost());
        });
        return result.get();
    }

    public String getName() {
        return name;
    }

    public List<Producer> getProducers() {
        return producers;
    }

    public int getTotalProduction() {
        return totalProduction;
    }

    public int getDemand() {
        return demand;
    }

    public int getPrice() {
        return price;
    }
}
