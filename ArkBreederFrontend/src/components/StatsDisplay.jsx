export default function StatsDisplay({statsObject, oxygenDefault}){

    if (oxygenDefault){
        statsObject = {
            "statType":"OXYGEN"
        }
    }


    return(
        <div className="flex items-center justify-between px-1.5 py-1.5">
            <div className="text-sm text-gray-700">{statsObject?.statType}</div>
            <div className="flex items-center gap-4">

                {!oxygenDefault ? 
                (<>
                <div className="text-sm font-medium text-gray-800 tabular-nums">{statsObject?.calcTotal}</div>
                <div className="text-xs text-gray-500">{statsObject?.totalPoints}</div>
                </>
                ) : (
                 <div className="text-sm font-medium text-gray-800 tabular-nums">N/A</div>
                )
                }

                
              </div>
            </div>
    )
}