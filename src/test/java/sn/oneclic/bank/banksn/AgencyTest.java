package sn.oneclic.bank.banksn;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sn.oneclic.bank.banksn.business.BankBusiness;
import sn.oneclic.bank.banksn.exceptions.AgencyException;
import sn.oneclic.bank.banksn.exceptions.BankException;
import sn.oneclic.bank.banksn.model.Agency;
import sn.oneclic.bank.banksn.model.Bank;


class AgencyTest {

    private Agency firstAgency = null;
    private Agency secondAgency = null;
    private Bank bank = null;
    private BankBusiness bankService = new BankBusiness();

    @BeforeEach
    void setup() {
        try {
            bank = new Bank("SGBS");
            firstAgency = new Agency("Dakar", "336332353", bank);
            secondAgency = new Agency("Ouakam", "336748474", bank);

        } catch (AgencyException | BankException agencyException) {
            agencyException.printStackTrace();
        }
    }

    @Test
    void createAgency() {
        System.out.println("bank = " + bank);
        bank.getAgencyList().add(firstAgency);
        bank.getAgencyList().add(secondAgency);
        Assertions.assertEquals(2, bank.getAgencyList().size());
    }

    @Test
    void test_exception_when_creating_agency_with_null_address() {
        Assertions.assertThrows(AgencyException.class, () -> new Agency(null, "774115141", bank));
    }
}
