

class walletDTO {
    constructor(iban, balance, assets, totalWorth, freeBalance)
    {
        this.iban = iban;
        this.balance = balance;
        this.assets = assets;
        this.totalWorth = totalWorth;
        this.freeBalance = freeBalance;
    }
}
let assetPrice = 0.0;
let token = localStorage.getItem(JWT_KEY);


getWallet();

/**fetches wallet and shows assets owned by client including buttons to trade with said assets*/
function getWallet(){
    fetch(`${rootURL}wallet`, {
        method: "GET",
        headers: acceptHeadersWithToken(token),
    })
        .then(response => response.json())
        .then(json => {
            console.log(json);
            let walletDTO = json;
            document.getElementById("iban").innerHTML = walletDTO.iban;
            document.getElementById("totalWorth").innerHTML = walletDTO.totalWorth.toFixed(2) + " €";
            document.getElementById("balance").innerHTML = walletDTO.balance.toFixed(2) + " €";
            document.getElementById("freeBalance").innerHTML = walletDTO.freeBalance.toFixed(2) + " €";
            for (let assetEntry of Object.entries(walletDTO.assets)){
                if(assetEntry[1] > 0) {
                    let assetDiv = document.createElement('div');
                    assetDiv.className = 'asset';
                    getPrice(assetEntry[0].substr(0,assetEntry[0].indexOf(' ')));
                    setTimeout(() => {
                        assetDiv.innerHTML = assetEntry[0] + " : " + assetEntry[1] + "<br> Value: " + (assetPrice * assetEntry[1]) + " €";
                    }, 100);
                    let orderButton = document.createElement('button');
                    orderButton.addEventListener("click", function(){
                        orderSelectedAsset(assetEntry[0]);});
                    orderButton.className = "smallButton";
                    orderButton.innerHTML = "Trade";
                    document.getElementById('assetContainer').appendChild(assetDiv);
                    document.getElementById('assetContainer').appendChild(orderButton);
                }
            }
        })
        .catch((error) => { console.error('Error', error) });
}

/**sets the currently selected asset variable to the asset the button belongs to and goes to order page*/
function orderSelectedAsset(assetText){
    let assetCode = assetText.substr(0,assetText.indexOf(' '));
    let assetName = assetText.split('(').pop().split(')')[0];
    asset = {
        name: assetName,
        code: assetCode,
        currentPrice: '0'
    }
    let assetObject = new Asset(asset);
    localStorage.setItem(CURRENT_ASSET_KEY, JSON.stringify(assetObject));

    window.location.href = "PlaceOrder.html";
}

/**fetches the latest price of the asset*/
function getPrice(code) {
    fetch(`${rootURL}getcurrentprice`, {
        method: "POST",
        headers: acceptHeadersWithToken(token),
        body: code
    })
        .then(async response => {
            if (response.ok) {
               response.json().then((price) => { assetPrice = price});
            }else {
                console.log("token expired");
                return 0;
            }
        });
}

