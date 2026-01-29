import { useEffect, useState } from "react"

export default function DinoValidation() {

    const [settings, setSettings] = useState({
        Health:{"stats":0,"wildScale":1, "statAdditive":.14,"statAffinity":.44},
        Stamina:{"stats":1,"wildScale":1, "statAdditive":1,"statAffinity":1},
        Oxygen:{"stats":3,"wildScale":1, "statAdditive":1,"statAffinity":1},
        Food:{"stats":5,"wildScale":1, "statAdditive":1,"statAffinity":1},
        Weight:{"stats":6,"wildScale":1, "statAdditive":1,"statAffinity":1},
        Melee:{"stats":7,"wildScale":1, "statAdditive":.14,"statAffinity":.44}
    });


    const [dinoList, setDinoList] = useState([]);
    const [dinoStatsType, setDinoStatsType] = useState([]);
    const [tamingEffectiveness, setTamingEffectiveness] = useState(1)
    const [selectedDino, setSelectedDino] = useState("");
    const [colorRegions, setColorRegions] = useState(0);



    // handles the change with anything in the settings
    const handleChange = (key, value, statChoice) => {
      setSettings(prev => ({
        ...prev,
        [key]:{
          ...prev[key],
          [statChoice]: value === "" ? "" : Number(value)
        }
      }));
    };

    //   Grabs Creatures for the Dropdown menu
    useEffect(() => {
      if (dinoList.length == 0){
          fetch("http://localhost:8787/creatures/unvalidated_list",{
              method:"GET"
          }).then((res) => {
              if (res.status === 200){
                  return res.json();
              }
              else{
                  throw new Error("Trouble Connecting");
              }
          })
          .then((data)=>{
              setDinoList(data);
          })
          .catch((error) => {
              console.error(error);
          })
      }
    },[])



//   Does the change of the creature within the dropdown
  const setSelectedDinoID = async (creatureId) => {
  setSelectedDino(creatureId);

  dinoList.map((dino) => {
      if (dino?.creatureId == creatureId){
        setColorRegions(dino?.colorRegionTotal)
      }
  })

  try {
    const res = await fetch(`http://localhost:8787/creatures/validation/${creatureId}`);

    if (!res.ok) {
      throw new Error("Can't get creature stats");
    }

    const data = await res.json();
    setDinoStatsType(data); // <-- now guaranteed to be finished
  } catch (err) {
    console.error(err);
  }
};


// Does the change on the stat Points
const handleStatChange = (statName, newValue) => {
  setDinoStatsType((dinoStatsType) =>
    dinoStatsType.map((stat) =>
      stat.statType === statName
        ? { ...stat, totalPoints: Number(newValue) }
        : stat
    )
  );
};



const validateCreature = async () => {
    try{
        const res = await fetch(`http://localhost:8787/creatures/validate/${selectedDino}`,{method:"PUT"});
        if (!res.ok){
            throw new Error("Not able to update the validation");
        }
        const data = await res.json();
        setDinoList(data)
    }
    catch (err){
        console.error(err);
    }
}


const buildValidationJSON = () => {
   const breedingSettings = Object.values(settings);


    var returnValue = {
        "stats": dinoStatsType,
        "creatureId": selectedDino,
        "tamingEffectiveness":tamingEffectiveness,
        "breedingSettings":breedingSettings
    }
    return returnValue;
}
const calcCreature = async() => {
    try{
        const body = buildValidationJSON()
        const res = await fetch(`http://localhost:8787/creatures/validation/cal`,
          {method:"POST", 
            headers: {
              "Content-Type": "application/json",
            },
          body: JSON.stringify(body),})

        if(!res.ok){
            throw new Error("")
        }

        const data = await res.json()
        setDinoStatsType(data)
    }
    catch (err){
        console.error(err)
    }
}

return (
  <div className="w-full max-w-6xl mx-auto rounded-xl border border-slate-700 bg-slate-900/70 p-6 shadow-md">
    {/* Title + Dropdown + Taming Effectiveness */}
    <div className="flex flex-col items-center gap-4 mb-6">
      <h2 className="text-2xl font-semibold text-slate-100 text-center">
        Stat Settings
      </h2>

      {/* Row: Select + Taming Effectiveness */}
      <div className="flex flex-col sm:flex-row gap-4 w-full sm:justify-center">
        <select
          value={selectedDino}
          onChange={(e) => setSelectedDinoID(e.target.value)}
          className="
            min-w-[220px]
            rounded-lg 
            border border-slate-700 
            bg-slate-900 
            px-3 py-2 
            text-slate-100
            shadow-sm
            focus:border-teal-400 
            focus:ring-1 focus:ring-teal-400
          "
        >
          <option value="">Select Creature</option>
          {dinoList.map((dino) => (
            <option
              value={dino?.creatureId}
              key={dino?.creatureId}
            >
              {dino?.creatureName}
            </option>
          ))}
        </select>

        {/* Taming Effectiveness input */}
        <div className="flex flex-col">
          <label className="text-sm font-medium text-slate-200 mb-1">
            Taming Effectiveness
          </label>
          <input
            type="number"
            value={tamingEffectiveness}
            step={0.0001}
            max={1}
            min={0}
            onChange={(e) =>
              setTamingEffectiveness(parseFloat(e.target.value))
            }
            className="
              w-full sm:w-40
              rounded-lg 
              border border-slate-700 
              bg-slate-900 
              px-3 py-2 
              text-slate-100
              shadow-sm
              focus:border-teal-400 
              focus:ring-1 focus:ring-teal-400
            "
          />
        </div>
      </div>
    </div>

    {/* Side-by-side tables */}
    <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
      {/* Stat Settings table */}
<div className="overflow-x-auto rounded-lg border border-slate-700 bg-slate-900/70 shadow-sm">
  <table className="w-full table-fixed border-collapse text-sm text-slate-100">
    <colgroup>
      <col className="w-[120px]" />
      <col className="w-[90px]" />
      <col className="w-[90px]" />
      <col className="w-[110px]" />
    </colgroup>

    <thead className="bg-slate-800/80 text-slate-100">
      <tr>
        <th
          colSpan={4}
          className="py-3 text-center text-lg font-semibold tracking-wide border-b border-slate-700"
        >
          Settings
        </th>
      </tr>
      <tr className="text-xs uppercase tracking-wide border-b border-slate-700 bg-slate-800/60">
        <th className="px-3 py-2 text-left">Stat Type</th>
        <th className="px-3 py-2 text-center">Wild Scale</th>
        <th className="px-3 py-2 text-center">Additive</th>
        <th className="px-3 py-2 text-center">Affinity</th>
      </tr>
    </thead>

    <tbody className="divide-y divide-slate-800">
      {Object.entries(settings).map(([label, _]) => (
        <tr key={label} className="hover:bg-slate-800/40 transition">
          <td className="px-3 py-2">{label}</td>

          <td className="px-3 py-2 text-center">
            <input
              type="number"
              step="0.01"
              value={settings[label]?.wildScale ?? ""}
              onChange={(e) => handleChange(label, e.target.value, "wildScale")}
              className="w-full bg-slate-800 border border-slate-700 rounded px-2 py-1 text-xs text-slate-100 
                         focus:outline-none focus:ring-1 focus:ring-teal-400"
            />
          </td>

          <td className="px-3 py-2 text-center">
            <input
              type="number"
              step="0.01"
              value={settings[label]?.statAdditive ?? ""}
              onChange={(e) => handleChange(label, e.target.value, "statAdditive")}
              className="w-full bg-slate-800 border border-slate-700 rounded px-2 py-1 text-xs text-slate-100 
                         focus:outline-none focus:ring-1 focus:ring-teal-400"
            />
          </td>

          <td className="px-3 py-2 text-center">
            <input
              type="number"
              step="0.01"
              value={settings[label]?.statAffinity ?? ""}
              onChange={(e) => handleChange(label, e.target.value,"statAffinity")}
              className="w-full bg-slate-800 border border-slate-700 rounded px-2 py-1 text-xs text-slate-100 
                         focus:outline-none focus:ring-1 focus:ring-teal-400"
            />
          </td>
        </tr>
      ))}
    </tbody>
  </table>
</div>

      {/* Dinosaur Values table */}
<div className="overflow-x-auto rounded-lg border border-slate-700 bg-slate-900/70 shadow-sm">
  <table className="w-full border-collapse text-sm text-slate-100">
    <thead className="bg-slate-800/80 text-slate-100">
      <tr>
        <th
          colSpan={3}
          className="py-3 text-center text-lg font-semibold tracking-wide border-b border-slate-700"
        >
          Dinosaur Values
        </th>
      </tr>

      <tr className="text-xs uppercase tracking-wide border-b border-slate-700 bg-slate-800/60">
        <th className="px-3 py-2 text-left">Stat Type</th>
        <th className="px-3 py-2 text-left">Stat Points</th>
        <th className="px-3 py-2 text-left">Calculated Value</th>
      </tr>
    </thead>

    <tbody className="divide-y divide-slate-800">
      {dinoStatsType.map((stat) => {
        const statName = stat.statType
          .toLowerCase()
          .replace("_", " ")
          .replace(/\b\w/g, (c) => c.toUpperCase());

        return (
          <tr key={stat.statType} className="hover:bg-slate-800/40 transition">
            <td className="px-3 py-2 font-medium">{statName}</td>

            <td className="px-3 py-2">
              <input
                type="number"
                step={1}
                min={0}
                value={stat.totalPoints}
                onChange={(e) =>
                  handleStatChange(stat.statType, e.target.value)
                }
                className="
                  w-24 px-2 py-1 
                  bg-slate-800 border border-slate-700 rounded 
                  text-slate-100 text-sm
                  focus:outline-none focus:ring-1 focus:ring-teal-400
                "
              />
            </td>

            <td className="px-3 py-2">
              <span className="text-slate-300">{stat.calcTotal ?? "—"}</span>
            </td>
          </tr>
        );
      })}
    </tbody>
  </table>
</div>

<div className="mt-6 w-full flex justify-end">
  <div className="
    flex items-center gap-2 
    px-3 py-1.5 
    rounded-lg 
    bg-slate-800/70 
    border border-slate-600 
    shadow-sm
  ">
    <span className="text-slate-300 font-medium tracking-wide">
      Color Regions:
    </span>
    <span className="px-2 py-0.5 rounded-md bg-teal-600/40 text-teal-200 font-semibold">
      {colorRegions}
    </span>
  </div>
</div>

    {/* Buttons pinned bottom-right */}
<div className="mt-6 w-full flex justify-end gap-3">
  <button
    onClick={() => validateCreature()}
    className="
      px-4 py-2 rounded-lg 
      border border-teal-500 
      bg-slate-900 
      text-teal-200 
      font-medium
      shadow-sm
      hover:bg-teal-500/10 
      hover:border-teal-300
      focus:outline-none focus:ring-2 focus:ring-teal-500 focus:ring-offset-1 focus:ring-offset-slate-900
      transition
    "
  >
    Validate
  </button>

  <button
    onClick={() => calcCreature()}
    className="
      px-4 py-2 rounded-lg 
      border border-emerald-500 
      bg-emerald-600/90 
      text-slate-50 
      font-semibold
      shadow-sm
      hover:bg-emerald-500 
      hover:border-emerald-300
      focus:outline-none focus:ring-2 focus:ring-emerald-500 focus:ring-offset-1 focus:ring-offset-slate-900
      transition
    "
  >
    Calculate Creature
  </button>
</div>

  </div>
  </div>
);
}