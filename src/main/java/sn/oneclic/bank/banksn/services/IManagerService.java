package sn.oneclic.bank.banksn.services;

import sn.oneclic.bank.banksn.model.Bank;
import sn.oneclic.bank.banksn.model.Manager;

public interface IManagerService {


    Manager create(Bank bank, Manager manager);


}
