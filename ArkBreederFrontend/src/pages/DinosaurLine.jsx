import { useParams } from "react-router"
import FilterButtons from "../components/DinoFilterButtons"
import DinoTable from "../components/DinosaurTable"
import { useEffect, useState } from "react";


export default function DinosaurLine({}){

    const {lineId} = useParams();
    const {selectedDinosaur, SetSelectedDinosaur} = useState({})
    const {dinoList, setDinoList} = useState([])


    useEffect(() => {



        // Need to make a connection to grab the specific lines dinosaurs
        fetch("")



    },[])


    return(
    <>
        {/* This is holding everything within the Page */}
        <div className="flex flex-col">
            <div><h2>Tame Line Name</h2></div>
            {/* This is Holding the main Conent */}
            <div>

                {/* Holding The dinosaur selected or max dinosaur on start  */}
                <div></div>

                {/* Holds the dinosaur  */}
                <div>
                    {/* Filter Buttons */}
                    <div><FilterButtons/></div>

                    {/* Dino Table */}
                    <div><DinoTable/></div>

                </div>
            </div>
        </div>
    </>
    )
}