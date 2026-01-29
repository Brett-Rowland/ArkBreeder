export default function SettingsInput({header, settingName, defaultValue, settingsChange, settings}){


    // Function to change these properties
    const updateSettings =  (newValue,setting) => {
        settingsChange({...settings, [setting]:newValue})
    }




    return (
  <div className="flex items-center gap-5 
                  rounded-lg border border-zinc-800 
                  px-4 py-3">
    <h3 className="flex-1 text-sm font-medium text-zinc-200">
      {header}
    </h3>

    <input
      type="number"
      name={settingName}
      step={0.1}
      value={defaultValue}
      onChange={(e) => updateSettings(e.target.value, settingName)}
      className="w-24 rounded-md border border-zinc-800 
                 bg-zinc-950/40 px-2 py-1.5 text-sm
                 focus:outline-none focus:ring-2 focus:ring-emerald-500/40"
    />
  </div>
);

}