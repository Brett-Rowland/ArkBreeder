import { useState } from "react"
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


/**
 * Tomorrow Work on Settings Form Components
 * 
 * Settings Form onChange working
 * 
 * 
 */




    return(
        <>
            {/* Main Div To Hold Each Item */}
            <div>
                
                {/* Settings Form */}
                <SettingsForm/>


                {/* Dino Live Update Display */}
                <DinoDisplay/>
            
            </div>
        </>
    )
}