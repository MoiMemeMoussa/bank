package sn.oneclic.bank.banksn.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Bank {
    private int id;
    private String name;
    private List<Agency> listAgency = new ArrayList<>();

    public Bank() {

    }

    public Bank(int id, String name) {
        if (name == null)
            throw new NullPointerException(" bank must have a name !!! ");
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bank bank = (Bank) o;
        return Objects.equals(name, bank.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "Bank{" +
                "id = '" + id + '\'' +
                "name='" + name + '\'' +
                '}';
    }

    public List<Agency> getListAgency() {
        return listAgency;
    }

    public void addAgency(Agency agency) {
        if (agency == null)
            throw new NullPointerException(" agency cannot be null !! ");
        this.listAgency.add(agency);
    }
}
