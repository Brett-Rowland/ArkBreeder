export default function DinoDisplay({breedingLines, settings}){


    return(
        <>

            {/* Holds All dinosaur  within it */}
            <div className="grid grid-cols-2 gap-6 border border-zinc-700">
                

                {/* Breeding Line Selection and Dinosaur Selection*/}
                <div className="flex flex-col gap-4">
                    
                    <div className="flex gap-4">
                        <h1>Breeding Lines</h1>
                        <select>
                            <option value="-1">Choose Breeding Line</option>
                        </select>
                    </div>
                    <div>

                        <h2>Dinosaur section</h2>
                    </div>


                </div>


                {/* Stat View */}
                <div className="flex flex-col">
                    {/* Header */}
                    <div>
                        <h2>Your Line Stats</h2>
                    </div>

                    {/* Stats */}
                    <div>
                        <p>stats</p>
                    </div>
                </div>
                
            </div>        
        </>
    )
}