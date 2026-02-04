import ColorRegion from "./ColorRegion"
import StatsDisplay from "./StatsDisplay"
import { useNavigate } from "react-router"


export default function BreedingContainer({breedingLine}){
      
  // Store everything within a
  // Base color Region item
  const baseColorRegion = {

  }
  let nav = useNavigate();
  
  // Array List of 6 elements
  var colorRegions = [0,1,2,3,4,5];
  var colorRegionNumber = 0;
  var creatureName = breedingLine?.creatureName;


  const insideLine = () => {
    nav(`/breeding-lines/${breedingLine?.breedingLineId}`)
  }

    
    return(<>
        <div
              className="rounded-2xl border border-gray-200 bg-white shadow-md p-1.5 flex flex-col" onClick={insideLine}
            >

            {/* Header */}
            <div>{breedingLine?.breedingLineNickname === '' ? creatureName : breedingLine?.breedingLineNickname}</div>


            {/* All of the content */}
            <div className="flex gap-4">
                
                {/* Left Column */}
                <div className="w-1/3 min-w-[120px] flex-shrink-0">
                    <div className="w-32 h-32 rounded-xl overflow-hidden border border-slate-200 bg-gray-50 flex items-center justify-center mb-4">
                        <img src={`/ArkCryoImages/${creatureName}.png`} alt={`${creatureName} Image`} />
                    </div>

                    {/* Color Region Box */}
                    {/* <div className="rounded-lg border border-gray-200 p-1 bg-white"> */}
                      {/* Color Region Row Generator */}
                        {/* <div className="grid grid-cols-2 gap-2">
                        
                        {colorRegions.map((index) => {
                          if (breedingLine?.colorRegions[colorRegionNumber]?.colorRegion === index){
                            
                            const region = breedingLine?.colorRegions[colorRegionNumber];
                            colorRegionNumber += 1;
                            return(
                              <ColorRegion index={region?.colorRegion} colorRegion={region}></ColorRegion>
                            )
                          }
                          else{
                            return(
                              <ColorRegion index={index} colorRegion={baseColorRegion}/>
                            )
                          }
                          
                          })
                        }
                          
                        </div> */}
                    {/* </div> */}
                </div>


                {/* Right Column */}

                <div className="flex-1">
                    <div className="rounded-lg border border-gray-200 p-1 bg-white">
                        <div className="grid grid-cols-1 gap-2">
                              {
                                breedingLine?.maxStats.map((ms, index) => 
                                  {
                                    if (index == 2 && ms?.statType != "OXYGEN"){
                                      return(
                                        <>
                                          <StatsDisplay oxygenDefault={true} />
                                          <StatsDisplay statsObject={ms} oxygenDefault={false} />
                                        </>
                                      )
                                    }
                                    return(
                                      <StatsDisplay statsObject={ms} oxygenDefault={false} />
                                    )
                                  })
                              }
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </>)
}