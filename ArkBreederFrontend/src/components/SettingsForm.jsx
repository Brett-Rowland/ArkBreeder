import SettingsInput from "./SettingsInput"
import StatRow from "./StatsSettingRow"


export default function SettingsForm({settings, updateSettings}){

    const stats = ["Health", "Stamina", "Oxygen", "Food", "Weight", "Melee"]


    const singlePlayerChange = () =>{
        updateSettings({...settings, ["singlePlayer"]:!settings["singlePlayer"]})
    }


    return(
        <>
            {/* Main Container 3 Rows */}
            <div className="flex flex-col">
                
                {/* Div for Setting Chooser */}
                <div className="flex">

                    <select name="servers">
                        <option selected disabled value={-1}>Choose Server</option>

                    </select>

                </div>

                {/* Settings Div */}
                
                <div className="flex flex-row">

                    {/* Holds Server Settings */}
                    <div className="flex flex-col">
                        <h2>Server Settings</h2>

                        <SettingsInput  settingName={"eggHatch"} settings={settings} defaultValue={settings?.eggHatch} header={"Egg Hatch Speed"} settingsChange={updateSettings}/>
                        <SettingsInput  settingName={"mating"} settings={settings} defaultValue={settings?.mating} header={"Mating Interval"} settingsChange={updateSettings}/>
                        <SettingsInput  settingName={"maturation"} settings={settings} defaultValue={settings?.maturation} header={"Maturation Rate"} settingsChange={updateSettings}/>
                        <SettingsInput  settingName={"imprint"} settings={settings} defaultValue={settings?.imprint} header={"Imprint Stat Scale"} settingsChange={updateSettings}/>
                    
                        <div className="flex flex-row">
                            <h3>Single Player</h3>
                            <input type="checkbox" checked={settings?.singlePlayer} onChange={singlePlayerChange}/>
                        </div>

                    </div>

                    {/* Holds Stat Settings */}
                    <div>
                        <h1>Stat Settings</h1>
                        
                        <table>
                            <thead>
                                <tr>
                                    <th></th>
                                    <th>Wild Scale</th>
                                    <th>Stat Additive</th>
                                    <th>Stat Affinity</th>
                                </tr>
                            </thead>
                            <tbody>

                                {/* Health */}
                                <StatRow statType={"health"} settings={settings} stat={settings?.stats?.health} updateSettings={updateSettings}/>

                                {/* Stamina */}
                                <StatRow statType={"stamina"} settings={settings} stat={settings?.stats?.stamina} updateSettings={updateSettings}/>

                                {/* Oxygen*/}
                                <StatRow statType={"oxygen"} settings={settings} stat={settings?.stats?.oxygen} updateSettings={updateSettings}/>

                                {/* Food */}
                                <StatRow statType={"food"} settings={settings}  stat={settings?.stats?.food} updateSettings={updateSettings}/>

                                {/* Weight */}
                                <StatRow statType={"weight"} settings={settings} stat={settings?.stats?.weight} updateSettings={updateSettings}/>

                                {/* Melee */}
                                <StatRow statType={"melee"} settings={settings} stat={settings?.stats?.melee} updateSettings={updateSettings}/>
                            </tbody>
                        </table>
                    </div>
                </div>

                {/* Buttons Div */}
                <div className="flex flex-row">
                    <div><button>Create Server</button></div>
                    <div><button>Reset</button></div>
                </div>

            </div>
        </>
    )


}