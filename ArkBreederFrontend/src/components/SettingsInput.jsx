export default function SettingsInput({header, settingName, defaultValue, settingsChange, settings}){


    // Function to change these properties
    const updateSettings =  (newValue,setting) => {
        settingsChange({...settings, [setting]:newValue})
    }




    return(
        <>
           <div className="flex flex-row">
                <h3>{header}</h3>
                <input type="number" name={settingName} step={.1} value={defaultValue} onChange={(e) => updateSettings(e.target.value,settingName)}/>
            </div>
        
        </>
    )
}