import { useEffect, useState } from "react"
import BreedingContainer from "../components/BreedingLineContainer"

export default function BreedingLines(){

    // Use state for dino information
    const [breedingLines, setBreedingLines] = useState([])
    const [loading, setLoading] = useState(false)


    var token = sessionStorage.getItem("token");
  


    // Get Command to get the breeding Line information
    useEffect(() => {
        setLoading(true)
        const stored = sessionStorage.getItem("breedingLines")
        if ( stored !== null){
            setBreedingLines(JSON.parse(stored));
            setLoading(false)
        }
        else{
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
                sessionStorage.setItem("breedingLines", JSON.stringify(data))
                setBreedingLines(data)
            })
            .catch((error) => {
                console.error(error)
            })
            .finally(() => {
                setLoading(false)
            })
        }
    },[])

    return(

        <>
        <div className="min-h-screen bg-gray-50 p-6">
            <div className="max-w-[1400px] mx-auto">
                <div className="grid gap-4 grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4">
                    {loading ? 
                    (<div>Currently Loading</div>
                    ) : (
                    breedingLines.map((bl,index) => (
                        <BreedingContainer breedingLine={bl}/> 
                    ))
                    )}
                </div>
            </div>
        </div>
        </>
    )
}