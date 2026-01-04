import { useEffect, useState } from "react"
import BreedingContainer from "../components/BreedingLineContainer"
import "../pages/Styling/breedingLine.css"
import NewBreedingLine from "../components/NewBreedingLinePopup"

export default function BreedingLines(){

    // Use state for dino information
    const [breedingLines, setBreedingLines] = useState([])
    const [creatureList, setCreatureList] = useState([])
    const [settingList, setSettingsList] = useState([])
    const [loading, setLoading] = useState(false)
    const [open, setOpen] = useState(false);;

    const token = sessionStorage.getItem("token");
  


    // To Do List
    // Wire Inputs to states (Dinosaur and Settings)
    // Send Request to save breeding line
    // Refresh Breeding Lines List


    // Get Command to get the breeding Line information
    useEffect(() => {
        setLoading(true)
        const stored = sessionStorage.getItem("breedingLines")
        // if ( stored !== null){
        //     setBreedingLines(JSON.parse(stored));
        //     setLoading(false)
        // }
        
            // Send the request to grab everything
            fetch(`http://localhost:8787/breeding-line/${token}/list/10`,{
                method:"GET"
                })
            .then((res)=>{
                if (res.status === 200){
                    return res.json()
                }
                else{
                    throw new Error(`Trouble connecting ${res.json()}`)
                }
            })
            .then((data) =>{
                console.log(data)
                sessionStorage.setItem("breedingLines", JSON.stringify(data))
                setBreedingLines(data?.breedingLines)
                setCreatureList(data?.creatureList)
                setSettingsList(data?.settingsList)
            })
            .catch((error) => {
                console.error(error)
            })
            .finally(() => {
                setLoading(false)
            })
        
    },[])


    const filterButton = () => {
        console.log("Hello From the filter Button")
    }

    return(

        <>
        
        <div className=" min-h-screen bg-gray-50 p-6">
            <div className="max-w-[1720px] mx-auto">

                <NewBreedingLine open={open} setOpen={setOpen} CreatureList={creatureList} SettingList={settingList} BreedingLineList={breedingLines} SaveBreedingLineList={setBreedingLines}/>
                
                {/* Title for the Page */}
                <div className="flex justify-center align-center">
                    <h1 className="text-3xl"><strong>Breeding Lines</strong></h1>
                </div>

                {/* New Breeding Line Button */}
                <div className="grid grid-cols-[1fr_2fr_1fr]">
                    <div className="col-start-2 flex justify-end mb-2">
                        <button type="button" className="flex items-center justify-center border rounded-md w-48 h-12" onClick={() => setOpen(true)}>New Breeding Line</button>
                    </div>
                </div>

                {/* Search Bar */}
                <div className="grid gap-3 grid-cols-[1fr_minmax(0fr,3fr)_1fr] ">
                    <div className="col-start-2 flex flex-wrap md:flex-nowrap lg:flex-nowrap">
                        {/* Search Bar */}

                        <div className="border-1 w-auto text-xl"><input className="" placeholder="Search"></input></div>

                        {/* Other Filters */}


                        <div className="grid grid-cols-[2fr_1fr_2fr] md:ml-4 md:gap-4 lg:ml-6 lg:gap-6">

                            {/* Filtering Option Dropdown drop down */}
                            <div className="border-1 rounded-sm">
                                <select className="text-lg size-full">
                                    <option value="creatureName">Creature Name</option>
                                    <option value="nickname">Nickname</option>
                                </select>
                            </div> 

                            {/* Ascending or Descending */}

                            <div className="border-1 rounded-sm">
                                <select className="text-lg size-full">
                                    <option value="ASC">Ascending</option>
                                    <option value="DESC">Descending</option>
                                </select>
                            </div>


                            {/* Filter Button */}

                            <button className="flex items-center justify-center w-30 border-1 hover:bg-stone-500 rounded-sm" onClick={filterButton} >
                                <h2 className="text-lg">Filter</h2>
                            </button>
                        </div>
                    </div>


                </div>


                {/* Breeding Line Cards */}
                <div className="grid gap-4 grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4">
                    {loading ? 
                    (<div>Currently Loading</div>
                    ) : (
                    breedingLines.map((bl) => (
                        <BreedingContainer breedingLine={bl}/> 
                    ))
                    )}
                </div>
            </div>
        </div>
        </>
    )
}