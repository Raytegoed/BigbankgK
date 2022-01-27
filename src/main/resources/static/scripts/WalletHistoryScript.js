/**
 *
 * @Author Kelly Speelman - de Jonge
 */

google.charts.load('current', {'packages':['corechart']});
google.charts.setOnLoadCallback(drawCharts);
let token = localStorage.getItem(JWT_KEY);

async function drawCharts() {
    //let token = "hoi@hotmail.nl"; // test token voor het testen van de back met de frond end.
    let valutaData =
        await getWalletHistorie(token);
    valutaData = valutaData.toString();
    console.log(valutaData);
    /*let valutaData =
        '{"Bitcoin":0.0,"Uniswap":0.0,"Polkadot":0.0,"Litecoin":0.0,"Euro":10000.0,"Dai":0.0,"Cardano":0.0,"Avalanche":0.0,"Tether":0.0,"Terra":0.0}';
*/
    let data2 =
        '{"linechart": [' +
        '{"dateTime":"2022-01-01T14:53:41","Euro":20, "All crypto":200},' +
        '{"dateTime":"2022-01-01T14:53:41","Euro":20, "All crypto":210},' +
        '{"dateTime":"2022-01-01T14:53:41","Euro":100, "All crypto":100}' +
        '],"barchart": [' +
        '{"Valuta":"Euro","old":20,"nieuw":20},' +
        '{"Valuta":"Cardano","old":200,"nieuw":210},' +
        '{"Valuta":"Cardano3","old":200,"nieuw":210},' +
        '{"Valuta":"Cardano4","old":200,"nieuw":210},' +
        '{"Valuta":"Cardano5","old":200,"nieuw":210},' +
        '{"Valuta":"Cardano6","old":200,"nieuw":210},' +
        '{"Valuta":"Cardano7","old":200,"nieuw":210},' +
        '{"Valuta":"Cardano8","old":200,"nieuw":210},' +
        '{"Valuta":"Cardano9","old":200,"nieuw":210},' +
        '{"Valuta":"Cardano10","old":200,"nieuw":210},' +
        '{"Valuta":"Cardano11","old":200,"nieuw":210},' +
        '{"Valuta":"Cardano12","old":200,"nieuw":210},' +
        '{"Valuta":"Cardano13","old":200,"nieuw":210},' +
        '{"Valuta":"Cardano14","old":200,"nieuw":210},' +
        '{"Valuta":"Cardano15","old":200,"nieuw":210},' +
        '{"Valuta":"Cardano16","old":200,"nieuw":210},' +
        '{"Valuta":"Cardano17","old":200,"nieuw":210},' +
        '{"Valuta":"Cardano18","old":200,"nieuw":210},' +
        '{"Valuta":"Cardano19","old":200,"nieuw":210},' +
        '{"Valuta":"Litecoin","old":20,"nieuw":30}]}';

    document.getElementById("test").innerHTML = valutaData;
    console.log(valutaData);
    console.log(valutaData[0]);
    let obj = JSON.parse(valutaData);
    let obj2 = JSON.parse(data2);

    const dataPie = google.visualization.arrayToDataTable(getInfromationPieChart(obj));
    const dataLine = google.visualization.arrayToDataTable(getInfromationLineChart(obj2["linechart"]));
    const dataBar = google.visualization.arrayToDataTable(getInfromationBarChart(obj2["barchart"]));

    const options = {
        is3D: true,
        pieSliceTextStyle: {
            color: 'black',
        },
        sliceVisibilityThreshold: 0.08,
        backgroundColor: 'none',
    };

    const piechart = new google.visualization.PieChart(document.getElementById('piechart'));
    const linechart = new google.visualization.LineChart(document.getElementById('linechart'));
    const barchart = new google.visualization.BarChart(document.getElementById('barchart'));

    piechart.draw(dataPie, options);
    linechart.draw(dataLine, options);
    barchart.draw(dataBar, options);
}

const getWalletHistorie = async (token) => {
    return await fetch(`${rootURL}walletHistory`,
        {
            method: 'GET',
            headers: acceptHeadersWithToken(token),
        }).then(promise => {
        if (promise.ok) {
            return promise.json();
        } else if(promise.status===400){
            console.log("Couldn't retrieve pricehistory from the server")
            window.location.href = loginPageURL
        }else if(promise.status===401){
            window.location.href = loginPageURL
        }
    }).then(json => json)
        .catch(error=>console.log("Somethin went wrong: " + error))
}

function getInfromationPieChart(obj) {
    let dataArray = [['Valuta', 'Price']];
    for (const x in obj) {
console.log(x + " " + obj[x])
            dataArray.push([x, obj[x]]);

    }
    return dataArray;
}

function getInfromationLineChart(obj) {
    let dataArray = [['Date', 'Euro', 'All Crypto', 'Total']];
    for (const x in obj) {
        //document.getElementById("test").innerHTML = x;
        let totaal = parseInt(obj[x]["Euro"]) + parseInt(obj[x]["All crypto"]);
        dataArray.push([obj[x]["dateTime"], obj[x]["Euro"], obj[x]["All crypto"], totaal]);
    }
    return dataArray;
}

function getInfromationBarChart(myobj) {
    let dataArray = [['Valuta', 'Purchase price', 'Current price']];
    for (const x in myobj) {
        dataArray.push([myobj[x]["Valuta"], myobj[x]["old"], myobj[x]["nieuw"]])
    }
    return dataArray;
}
