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
            <div className="flex flex-col justify-center gap-3">
                
                <div className="flex flex-row justify-between">
                    <div><h1 className="text-3xl tracking-wide">Server Settings</h1>
                    <p className="text-sm">Customize Personal Server Settings</p></div>
                    {/* Div for Setting Chooser */}
                    <div className="flex flex-col justify-center items-center">
                        <label className="text-md">Active Server</label>
                        <select name="servers" className="border rounded-md">
                            <option selected disabled value={-1}>Choose Server</option>
                        </select>
                    </div>
                </div>

                {/* Settings Div */}
                
                <div className="grid gap-3 lg:grid-cols-2">
                    <div className="border border-zinc-600 py-3 rounded-md">
                    {/* Holds Breeding Settings */}
                        <div className="flex flex-col px-4 gap-3">
                            <div>
                              <h2 className="text-lg font-semibold text-zinc-100">
                                Breeding Settings
                              </h2>
                              <p className="text-sm text-zinc-400">
                                Configure Egg Hatching, Mating Timers, Maturation Time, Imprint Quality
                              </p>
                            </div>

                            {/* <SettingsInput  settingName={"eggHatch"} settings={settings} defaultValue={settings?.eggHatch} header={"Egg Hatch Speed"} settingsChange={updateSettings}/> */}
                            {/* <SettingsInput  settingName={"mating"} settings={settings} defaultValue={settings?.mating} header={"Mating Interval"} settingsChange={updateSettings}/> */}
                            {/* <SettingsInput  settingName={"maturation"} settings={settings} defaultValue={settings?.maturation} header={"Maturation Rate"} settingsChange={updateSettings}/> */}
                            <SettingsInput  settingName={"imprint"} settings={settings} defaultValue={settings?.imprint} header={"Imprint Stat Scale"} settingsChange={updateSettings}/>

                            <div className="flex items-center justify-between gap-5 rounded-lg border border-zinc-800 px-4 py-3">
                                <div className="flex flex-col">
                                    <h3 className="text-sm font-medium text-zinc-200">Single Player</h3>
                                    <span className="text-xs text-zinc-400">
                                      Applies single-player multipliers
                                    </span>
                                </div>
                                    <button
                                        type="button"
                                        onClick={singlePlayerChange}
                                        role="switch"
                                        aria-checked={settings?.singlePlayer}
                                        className={[
                                          "relative inline-flex h-6 w-11 items-center rounded-full transition",
                                          settings?.singlePlayer ? "bg-emerald-500" : "bg-zinc-700"
                                        ].join(" ")}
                                      >
                                    <span
                                      className={[
                                        "inline-block h-5 w-5 transform rounded-full bg-white transition",
                                        settings?.singlePlayer ? "translate-x-5" : "translate-x-1"
                                      ].join(" ")}
                                    />
                              </button>                            
                            </div>
                        </div>
                    </div>

                    {/* Holds Stat Settings */}
                    <div className="flex flex-col gap-4 rounded-xl border border-zinc-800 bg-zinc-900/40 p-5 shadow-sm">

                      {/* Card Header */}
                      <div className="flex items-center justify-between">
                        <div>
                          <h2 className="text-lg font-semibold text-zinc-100">
                            Stat Settings
                          </h2>
                          <p className="text-sm text-zinc-400">
                            Configure wild scaling, additive, and affinity multipliers.
                          </p>
                        </div>
                      </div>

                      {/* Table Wrapper */}
                      <div className="overflow-hidden rounded-lg border border-zinc-800">
                        <table className="w-full text-sm">

                          {/* Table Header */}
                          <thead className="bg-zinc-950/60 text-zinc-300">
                            <tr>
                              <th className="px-4 py-3 text-left font-medium"></th>
                              <th className="px-4 py-3 text-center font-medium">
                                Wild Scale
                              </th>
                              <th className="px-4 py-3 text-center font-medium">
                                Stat Additive
                              </th>
                              <th className="px-4 py-3 text-center font-medium">
                                Stat Affinity
                              </th>
                            </tr>
                          </thead>

                          {/* Table Body */}
                          <tbody className="divide-y divide-zinc-800 bg-zinc-900/20">
                            <StatRow statType={"health"} settings={settings} stat={settings?.stats?.health} updateSettings={updateSettings} />
                            <StatRow statType={"stamina"} settings={settings} stat={settings?.stats?.stamina} updateSettings={updateSettings} />
                            <StatRow statType={"oxygen"} settings={settings} stat={settings?.stats?.oxygen} updateSettings={updateSettings} />
                            <StatRow statType={"food"} settings={settings} stat={settings?.stats?.food} updateSettings={updateSettings} />
                            <StatRow statType={"weight"} settings={settings} stat={settings?.stats?.weight} updateSettings={updateSettings} />
                            <StatRow statType={"melee"} settings={settings} stat={settings?.stats?.melee} updateSettings={updateSettings} />
                          </tbody>
                        </table>
                      </div>
                    </div>
                </div>

                <div className="flex justify-end gap-3 mt-4">
                  <button
                    type="button"
                    className="rounded-lg border border-zinc-800 bg-zinc-900 px-4 py-2 text-sm
                               text-zinc-200 hover:bg-zinc-800 transition"
                  >
                    Reset
                  </button>

                  <button
                    type="button"
                    className="rounded-lg bg-emerald-500 px-5 py-2 text-sm font-medium
                               text-zinc-950 hover:bg-emerald-400 transition"
                  >
                    Create Server
                  </button>
                </div>
            </div>
        </>
    )


}