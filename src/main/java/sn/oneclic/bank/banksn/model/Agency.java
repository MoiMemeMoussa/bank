package sn.oneclic.bank.banksn.model;

import java.util.List;
import java.util.Objects;

public class Agency {

    private static int idAgency;
    private String name;
    private String address;
    private List<Customer> listCustomer;
    private Bank bank;

    public Agency() {

    }

    public Agency(String name, String address, Bank bank) {
        verifyData(name, address, bank);
        this.name = name;
        this.address = address;

    }

    private void verifyData(String name, String address, Bank bank) {
        if (bank == null)
            throw new NullPointerException(" an agency must be  linked to a bank !!!! ");
        if (name == null)
            throw new NullPointerException(" an agency must have a name !!! ");
        if (address == null)
            throw new NullPointerException(" an agency must have an address !!! ");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Agency agency = (Agency) o;
        return name.equals(agency.name) &&
                address.equals(agency.address) &&
                Objects.equals(listCustomer, agency.listCustomer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, address);
    }

    @Override
    public String toString() {
        return "Agency{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", listCustomer=" + listCustomer +
                '}';
    }
}
