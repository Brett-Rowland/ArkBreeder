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

    return(
        <>
            <tr>
                <th>{statTypeCapitalized}</th>
                <td><input type="number" name={`${statType}Scale`} value={stat[0]} step={.01} onChange={(e) => updateStat(e.target.value,0)}/></td>
                <td><input type="number" name={`${statType}Additive`} value={stat[1]} step={.01} onChange={(e) => updateStat(e.target.value,1)}/></td>
                <td><input type="number" name={`${statType}Affinity`} value={stat[2]} step={.01} onChange={(e) => updateStat(e.target.value,2)}/></td>
            </tr>
        </>
    )
}