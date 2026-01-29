export default function StatRow({statType, stat, updateSettings}){
    

    const firstLetter = statType.charAt(0).toUpperCase();
    const restOfString = statType.slice(1);


    const statTypeCapitalized = firstLetter + restOfString
    
    
    const updateStat = (newValue,statPosition) => {
        updateSettings(prev => ({
            ...prev,
            stats:{
                ...prev.stats,
                [statType]: prev.stats[statType].map((v,i) => (i === statPosition ? newValue : v))
            }
        }))
    }

    return (
  <tr>
    <th className="px-4 py-3 text-left font-medium text-semibold">
      {statTypeCapitalized}
    </th>

    <td className="px-4 py-3 text-center">
      <input
        className="w-20 mx-auto px-2 text-left border rounded-lg border-zinc-700"
        type="number"
        step={0.01}
        min={0}
        value={stat[0]}
        onChange={(e) => updateStat(e.target.value, 0)}
      />
    </td>

    <td className="px-4 py-3 text-center">
      <input
        className="w-20 mx-auto px-2 text-left border rounded-lg border-zinc-700"
        type="number"
        step={0.01}
        min={0}
        value={stat[1]}
        onChange={(e) => updateStat(e.target.value, 1)}
      />
    </td>

    <td className="px-3 py-2 text-center">
      <input
        className="w-20 mx-auto px-2 text-left border rounded-lg border-zinc-700"
        type="number"
        step={0.01}
        min={0}
        value={stat[2]}
        onChange={(e) => updateStat(e.target.value, 2)}
      />
    </td>
  </tr>
);

}