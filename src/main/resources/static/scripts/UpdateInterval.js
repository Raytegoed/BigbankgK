"use strict"
let initialTimeOut;
let updateInterval;
let token = localStorage.getItem(JWT_KEY)
const getUpdateInterval = async (token) => {
    return await fetch(`${rootURL}updateInterval`,
        {
            method: 'GET',
            headers: acceptHeadersWithToken(token)
        }).then(async promise => {
        if (promise.ok) {
           return promise.json()
        } else if (promise.status > 400) {
            console.log("Couldn't retrieve updateInterval")
        }
    }).then(json => json)
        .catch(error => console.log("Somethin went wrong: " + error))
}

function retrieveUpdateInterInformation(json) {
    console.log(json)
    initialTimeOut = JSON.parse(json.initialTimeOut);
    updateInterval = JSON.parse(json.updateInterval);
    console.log(initialTimeOut)
    console.log(updateInterval)
}
async function setUpdateInterval(token){
    const json = await getUpdateInterval(token);
    retrieveUpdateInterInformation(json)
}

setUpdateInterval(token);
