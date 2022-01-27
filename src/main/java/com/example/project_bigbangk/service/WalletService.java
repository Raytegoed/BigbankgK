package com.example.project_bigbangk.service;

import com.example.project_bigbangk.BigBangkApplicatie;
import com.example.project_bigbangk.model.*;
import com.example.project_bigbangk.model.DTO.GraphicsWalletDTO;
import com.example.project_bigbangk.model.Orders.Transaction;
import com.example.project_bigbangk.repository.RootRepository;
import com.example.project_bigbangk.service.Security.AuthenticateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

/**
 * @Author Kelly Speelman - de Jonge
 */

@Service
public class WalletService {
    private final AuthenticateService authenticateService;
    private final RootRepository rootRepository;
    private Client client;

    @Autowired
    public WalletService (AuthenticateService authenticateService, RootRepository rootRepository) {
        super();
        this.authenticateService = authenticateService;
        this.rootRepository = rootRepository;
    }

    public Wallet getWalletClient(String token){
        this.client = authenticateService.getClientFromToken(token);
        return client.getWallet();
    }

    public Map<String, Double> calculateInformationPie(){
        double balance = client.getWallet().getBalance();
        Map<Asset, Double> assets = client.getWallet().getAssets();
        Map<String, Double> assetsValues = new HashMap<>();
        assetsValues.put("Euro", balance);

        for (Asset entry:assets.keySet()){
            double assetValue = entry.getCurrentPrice()*assets.get(entry);
            assetsValues.put(entry.getName(), assetValue);
        }
        System.out.println(assetsValues);
        return assetsValues;
    }
/*
    public void calculateInformationLine(LocalDateTime dateTime){
        List<Map<String, String>> lijst = new ArrayList<>();
        double balance = client.getWallet().getBalance();
        Map<Asset, Double> assets = client.getWallet().getAssets();
        LocalDateTime dateTimeNow = LocalDateTime.now();

        Map<String, String> assetsValues = new HashMap<>();
        assetsValues.put("dateTime", dateTimeNow.toString());
        assetsValues.put("Euro", String.valueOf(balance));
        double assetValue = 0;
        for (Asset entry:assets.keySet()){
            assetValue = entry.getCurrentPrice()*assets.get(entry); // price van datum berekening (uit database?)
        }
        assetsValues.put("All crypto", String.valueOf(assetValue));

        lijst.add(assetsValues);
        calculateHistory(lijst);
        System.out.println(lijst);
    }

    public void calculateHistory(List<Map<String, String>> lijst){
        double balance = client.getWallet().getBalance();
        Map<Asset, Double> assets = client.getWallet().getAssets();
        double assetBalance = Double.parseDouble(lijst.get(lijst.size() - 1).get("All crypto"));
        LocalDateTime dateTime = LocalDateTime.parse(lijst.get(lijst.size() - 1).get("dateTime"));
        List<PriceHistory> priceHistories = rootRepository.getAllPriceHistories(dateTime);

        for (Transaction transaction : client.getWallet().getTransaction()) {
            if (transaction.getBuyerWallet().equals(client.getWallet())){
                balance += transaction.getPriceExcludingFee() + transaction.getFee();
                double assetValue = 0;
                for (Asset entry:assets.keySet()){
                    int index = priceHistories.indexOf(entry);
                    int indexDate = priceHistories.get(index).getPriceDates().indexOf(transaction.getDate());
                    double price = priceHistories.get(index).getPriceDates().get(indexDate).getPrice();
                    assetValue += price*assets.get(entry);
                }
            }
        }

        Map<String, String> assetsValues = new HashMap<>();
        assetsValues.put("dateTime", dateTime.toString());
        assetsValues.put("Euro", String.valueOf(balance));

        //assetsValues.put("All crypto", String.valueOf(assetValue));

        lijst.add(assetsValues);
    }

    public void calculateInformationBar(){ // inkoop prijs van een asset berekenen.
        Map<Asset, Double> assets = client.getWallet().getAssets();
        Map<String, Double> assetsValues = new HashMap<>();

        for (Asset entry:assets.keySet()){
            double assetAantal = assets.get(entry);
            if (assetAantal == 0){
                assetsValues.put(entry.getName(), 0.0);
            } else {
                double assetPice = 0;
                for (Transaction t : client.getWallet().getTransaction()) {
                    if(t.getAsset().equals(entry)) {
                        if(t.getBuyerWallet().equals(client.getWallet())){
                            assetPice += (t.getPriceExcludingFee()/t.getAssetAmount()) ;
                        } else {
                            assetPice -= (t.getPriceExcludingFee()/t.getAssetAmount()) ;
                            *//*if (assetPice <= 0 ){
                                assetPice *= -1;
                            }*//*
                        }
                        assetsValues.put(entry.getName(), assetPice);
                        System.out.println(t.getDate() + " "+ t.getAssetAmount() + " "+ t.getPriceExcludingFee());
                        System.out.println(assetPice);
                    }
                }
            }
            //client.getWallet().getTransaction().get(client.getWallet().getTransaction().size() - 1).getAsset();
            //assetsValues.put(entry.getName(), asssetValue);
        }
        System.out.println(assetsValues);
    }*/
}
