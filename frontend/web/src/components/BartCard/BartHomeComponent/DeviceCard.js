import MainDisplayCard from "../MainDisplayCard";
import {HomeComponent} from "../../../index";


export default function DeviceCard(props){
    return(
        <MainDisplayCard type={HomeComponent.DEVICE} deviceID/>
    );
}