import Paho from "paho-mqtt";
import {BROKER_URL_REGEX} from "../../index";

export default class MqttService {
    _client;
    _isInitialized = false;

    _brokerURL;
    _userID;

    constructor(brokerURL, userID) {
        this._brokerURL = brokerURL;
        this._userID = userID;
        this.initClient();
        this._client.onConnectionLost = this.onConnectionLost;
        this._client.onMessageArrived = this.onMessageArrived;

        this._client.connect({onSuccess: this.onConnect});
    }

    initClient = () => {
        try {
            let regex = new RegExp(BROKER_URL_REGEX);
            let group = this._brokerURL.match(regex);
            this._brokerURL = group[2];
            this._client = new Paho.Client(this._brokerURL, Number(8000), this._userID);
        } catch (e) {
            console.log(e);
            this._isInitialized = false;
        }
    };

    onConnect() {
        this._isInitialized = true;
        console.log("onConnect");
        this._client.subscribe("World");
        let message = new Paho.Message("Hello");
        message.destinationName = "World";
        this._client.send(message);
    }

    onConnectionLost(responseObject) {
        if (responseObject.errorCode !== 0) {
            console.log("onConnectionLost:" + responseObject.errorMessage);
        }
    }

    onMessageArrived(message) {
        console.log("onMessageArrived:" + message.payloadString);
    }

    get client() {
        return this._client;
    }

    get isInitialized() {
        return this._isInitialized;
    }

    get hostname() {
        return this._brokerURL;
    }

    get userID() {
        return this._userID;
    }
}