import { useEffect, useState } from "react"
import SettingsForm from "../components/SettingsForm"
import DinoDisplay from "../components/DinoSettingsDisplay"


/**
 * Settings Page for making new settings to your specific server
 * 
 * 
 * Manual Input the stats that are needed for everything
 * 
 * Future Feature Breakdown the stats on specific example from pre tame to post tame
 * To build custom server settings on people that dont have the opportunity to figure it out
 * 
 * @returns 
 */

export default function Settings(){
    
    const [settings, updateSettings] = useState({
        "eggHatch":1,
        "mating":1,
        "maturation":1,
        "imprint":1,
        "singlePlayer":true,
        "stats":{
            "health":[1,0.14,0.44],
            "stamina":[1,1,1],
            "food":[1,1,1],
            "oxygen":[1,1,1],
            "weight":[1,1,1],
            "melee":[1,0.14,0.44],
        }
    });


    const [personalSettings, setPersonalSettings] = useState({});
/**
 * Tomorrow Work on Settings Form Components
 * 
 * Settings Form onChange working
 * 
 * 
 */


    useEffect(()=>{
        var beginningToken = sessionStorage.getItem("token");
        const token = beginningToken === null ? 6423385 : beginningToken;
        // Go and grab the settings available. On change of the settings is when everything is switched on over
        try{
            const res = fetch(`http://localhost:8787/servers/settings/${token}`, {method:"GET"})

            if (res.status !== 200){
                throw new Error("Failure to get personal settings")
            }
            const newSettings = res.json();

            setPersonalSettings(newSettings)
        }
        catch(error){
            console.error(error)
        }
    },[])

    return(
        <>
            {/* Main Div To Hold Each Item */}
            <div>
                
                {/* Settings Form */}
                <SettingsForm settings={settings} updateSettings={updateSettings} />


                {/* Dino Live Update Display */}
                <DinoDisplay/>
            
            </div>
        </>
    )
}