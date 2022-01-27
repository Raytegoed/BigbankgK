package com.example.project_bigbangk.model.DTO;

import com.example.project_bigbangk.model.Wallet;

public class GraphicsWalletDTO {
    public String dateTime;
    public String Valuta;
    public String Price;
    public String Euro;
    public String AllCrypto;
    public String Old;
    public String nieuw;

    public void GraphicsWalletDTOLine(String dateTime, String euro, String allCrypto) {
        this.dateTime = dateTime;
        Euro = euro;
        AllCrypto = allCrypto;
    }

    public void GraphicsWalletDTOPie(String valuta, String price) {
        Valuta = valuta;
        Price = price;
    }

    public void GraphicsWalletDTOBar(String valuta, String old, String nieuw) {
        Valuta = valuta;
        Old = old;
        this.nieuw = nieuw;
    }
}
