package sn.oneclic.bank.banksn.model;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import sn.oneclic.bank.banksn.exceptions.AccountManagerException;
import sn.oneclic.bank.banksn.exceptions.BankException;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Slf4j
@Getter
public class Bank {

    private static int id;
    private static Logger logger = Logger.getLogger("Bank");
    private String name;
    private List<Agency> agencyList = new ArrayList<>();
    private List<Account> accountList = new ArrayList<>();
    private List<Manager> managerList = new ArrayList<>();
    private List<Customer> customerList = new ArrayList<>();

    public Bank(String name) throws BankException {
        if (name == null)
            throw new BankException();
        id++;
        this.name = name;
    }

    public void affectAccountToManager(Account account, Manager manager) throws AccountManagerException {
        if (!manager.getAccountList().contains(account)) {
            manager.getAccountList().add(account);
            account.setManager(manager);
        } else
            throw new AccountManagerException();
    }


    public String displayCustomerInformationByAccountNumber(String accountNumber) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Account account : accountList) {
            if (account.getAccountNumber().equals(accountNumber)) {
                stringBuilder.append(" Customer name  => ");
                stringBuilder.append(account.getCustomer().getName());
                stringBuilder.append('\n');

                stringBuilder.append(" Customer phone  => ");
                stringBuilder.append(account.getCustomer().getPhone());
                stringBuilder.append('\n');

                stringBuilder.append(" Customer address  => ");
                stringBuilder.append(account.getCustomer().getAddress());
                stringBuilder.append('\n');

                stringBuilder.append(" Customer Manager  => ");
                stringBuilder.append(account.getManager().getName());
                stringBuilder.append(" | ");
                stringBuilder.append(account.getManager().getPhone());
                stringBuilder.append('\n');
            }
        }
        return stringBuilder.toString();
    }


}
