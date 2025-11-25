export default function ColorRegion({index,colorRegion}){
  
  const colorRegionNumber = colorRegion?.colorRegion

    return(
        <>
        <div className="flex items-center gap-2">
          <span className="text-xs w-5 text-right text-gray-600">{index}:</span>
          <div
            className="w-10 h-7 border border-black rounded-sm"
            style={{ backgroundColor: `#${colorRegion?.hexCode}`}}
            role="img"
            aria-label={`Region ${colorRegionNumber} color ${colorRegion?.colorName}`}
          />
        </div>
        
        </>
    )



}