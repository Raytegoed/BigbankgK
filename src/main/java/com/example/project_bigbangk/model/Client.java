package com.example.project_bigbangk.model;

/**
 * Client object created by Vanessa Philips.
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.Objects;

public class Client {

    private final Logger logger = LoggerFactory.getLogger(Client.class);

    private String email;
    private String firstName;
    private String insertion;
    private String lastName;
    private LocalDate dateOfBirth;
    private String bsn;
    private String passWord;
    private Address address;
    private Wallet wallet;

    public Client(String email, String firstName, String insertion, String lastName, LocalDate dateOfBirth,
                  String bsn, String passWord, Address address, Wallet wallet) {
        this.email = email;
        this.firstName = firstName;
        this.insertion = insertion;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.bsn = bsn;
        this.passWord = passWord;
        this.address = address;
        this.wallet = wallet;
    }

    public Client(String email, String firstName, String insertion, String lastName, LocalDate dateOfBirth,
                  String bsn, String passWord) {
        this.email = email;
        this.firstName = firstName;
        this.insertion = insertion;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.bsn = bsn;
        this.passWord = passWord;
    }

    public Client() {
        this("", "", "", "", null, "", "", null, null);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getInsertion() {
        return insertion;
    }

    public void setInsertion(String insertion) {
        this.insertion = insertion;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getBsn() {
        return bsn;
    }

    public void setBsn(String bsn) {
        this.bsn = bsn;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    //NOTE: alleen comparison van email om gebruikers te vergelijken.
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return Objects.equals(email, client.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }
}