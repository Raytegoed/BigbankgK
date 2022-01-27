package com.example.project_bigbangk.service;

import com.example.project_bigbangk.model.Asset;
import com.example.project_bigbangk.model.Client;
import com.example.project_bigbangk.model.Orders.Transaction;
import com.example.project_bigbangk.model.Wallet;
import com.example.project_bigbangk.repository.RootRepository;
import com.example.project_bigbangk.service.Security.AuthenticateService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

/**
 * Hier wordt de WalletService gestest.
 *
 * @Author Kelly Speelman - de Jonge
 */

class WalletServiceTest {
    private WalletService walletService;

    private final RootRepository rootRepository = Mockito.mock(RootRepository.class);
    private final AuthenticateService authenticateService = Mockito.mock(AuthenticateService.class);
    private final Client mockClient = Mockito.mock(Client.class);
    private final Wallet mockWallet = Mockito.mock(Wallet.class);
    private final Wallet mockWallet2 = Mockito.mock(Wallet.class);
    private final Asset mockAssetCardano = Mockito.mock(Asset.class);
    private final Asset mockAssetDai = Mockito.mock(Asset.class);
    private final Asset mockAssetLitecoin = Mockito.mock(Asset.class);
    private final Transaction transaction1 = Mockito.mock(Transaction.class);
    private final Transaction transaction2 = Mockito.mock(Transaction.class);
    private final Transaction transaction3 = Mockito.mock(Transaction.class);

    @BeforeEach
    void setUp(){
        this.walletService = new WalletService(authenticateService, rootRepository);

        Mockito.when(mockWallet.getIban()).thenReturn("NL17 BGBK 7265515");
        Mockito.when(mockWallet.getBalance()).thenReturn(1000.0);

        Mockito.when(mockAssetCardano.getName()).thenReturn("Cardano");
        Mockito.when(mockAssetCardano.getCode()).thenReturn("ADA");
        Mockito.when(mockAssetCardano.getCurrentPrice()).thenReturn(2.0);
        Mockito.when(mockAssetDai.getName()).thenReturn("Dai");
        Mockito.when(mockAssetDai.getCode()).thenReturn("DAI");
        Mockito.when(mockAssetDai.getCurrentPrice()).thenReturn(2.0);
        Mockito.when(mockAssetLitecoin.getName()).thenReturn("Litecoin");
        Mockito.when(mockAssetLitecoin.getCode()).thenReturn("LTC");
        Mockito.when(mockAssetLitecoin.getCurrentPrice()).thenReturn(2.0);
        Map<Asset, Double> assetMap = new HashMap<>();
        assetMap.put(mockAssetCardano, 0.0);
        assetMap.put(mockAssetDai, 1.0);
        assetMap.put(mockAssetLitecoin, 8.0);

        Mockito.when(mockWallet.getAssets()).thenReturn(assetMap);
        //Asset asset = new Asset("DAI", "Dai");

        Mockito.when(transaction1.getAsset()).thenReturn(mockAssetDai);
        Mockito.when(transaction1.getPriceExcludingFee()).thenReturn(480.0);
        Mockito.when(transaction1.getAssetAmount()).thenReturn(4.0);
        Mockito.when(transaction1.getFee()).thenReturn(2.0);
        Mockito.when(transaction1.getDate()).thenReturn(LocalDateTime.now().minusDays(3));
        Mockito.when(transaction1.getBuyerWallet()).thenReturn(mockWallet);

        Mockito.when(transaction2.getAsset()).thenReturn(mockAssetDai);
        Mockito.when(transaction2.getPriceExcludingFee()).thenReturn(600.0);
        Mockito.when(transaction2.getAssetAmount()).thenReturn(2.0);
        Mockito.when(transaction2.getFee()).thenReturn(4.0);
        Mockito.when(transaction2.getDate()).thenReturn(LocalDateTime.now().minusDays(5).minusHours(7));
        Mockito.when(transaction2.getBuyerWallet()).thenReturn(mockWallet);

        Mockito.when(transaction3.getAsset()).thenReturn(mockAssetDai);
        Mockito.when(transaction3.getPriceExcludingFee()).thenReturn(600.0);
        Mockito.when(transaction3.getAssetAmount()).thenReturn(3.0);
        Mockito.when(transaction3.getFee()).thenReturn(4.5);
        Mockito.when(transaction3.getDate()).thenReturn(LocalDateTime.now().minusDays(8).minusHours(7));
        Mockito.when(transaction3.getBuyerWallet()).thenReturn(mockWallet2);
        Mockito.when(transaction3.getSellerWallet()).thenReturn(mockWallet);

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction1);
        transactions.add(transaction3);
        transactions.add(transaction2);


        Mockito.when(mockWallet.getTransaction()).thenReturn(transactions);

        Mockito.when(mockClient.getWallet()).thenReturn(mockWallet);

        Mockito.when(authenticateService.getClientFromToken("token")).thenReturn(mockClient);
    }

    @Test
    void getWalletClient() {
        Wallet klantWallet = walletService.getWallet("token");
        assertThat(klantWallet).isEqualTo(mockWallet);
    }

    @Test
    void calculateInformationPie(){
        walletService.getWallet("token");
        walletService.calculateInformationPie();
    }

    @Test
    void calculateInformationLine(){
        /*walletService.getWallet("token");
        walletService.calculateInformationLine(LocalDateTime.now().minusDays(9));*/
    }

    @Test
    void calculateInformationBar(){
       /* walletService.getWallet("token");
        walletService.calculateInformationBar();*/
    }
}