import { useState, useEffect } from "react";


export default function NewBreedingLine({open, setOpen, CreatureList, SettingList,  SaveBreedingLineList}){
  const [dinoSelected, setDinoSelected] = useState(-1);
  const [serverSelected, setServerSelected] = useState(-1);
  const [lineNickname, setLineNickname] = useState("")
 

  const token = sessionStorage.getItem("token")
  // This is the button command that is being used when 
    const createBreedingLine = async () => {
      console.log(dinoSelected)
      console.log(serverSelected)
      if (dinoSelected === -1 || serverSelected === -1){
        alert("Please select a dinosaur and a server")
      }
      else{
        try{
          const res = await fetch(`http://localhost:8787/breeding-line/${token}/create/${Number(dinoSelected)}/${Number(serverSelected)}`,{method: "POST",
            headers: { "Content-Type": "text/plain" },
            body: lineNickname})
          if (res.status !== 201){
            throw new Error("Line Not Created")
          }

          // Saves everything together

          const newLine = await res.json()
          SaveBreedingLineList(prev => ([
            ...prev, newLine
          ]));



          // This closes the window on the screen
          closeWindow()
        }catch(error){
          console.error(error)
        }
    }
  } 
  
    const closeWindow = () => {
      setOpen(false)
      setDinoSelected(0)
      setServerSelected(-1)
    }

  if (!open) return null;
   else return(
      <>
      {/* Popup Reset */}
          <div className="fixed inset-0 z-50  flex justify-center items-center">
              {/* Popup Box */}
              <div className="min-w-100 min-h-100 max-w-100 max-h-100 border bg-white opacity-100 p-4 flex flex-col">
                  <div className="relative flex items-center">
                    <h2 className="absolute left-1/2 -translate-x-1/2 text-lg font-semibold">
                      New Breeding Line
                    </h2>

                    <button
                      type="button"
                      className="ml-auto w-10 h-10 border"
                      onClick={closeWindow}
                    >
                      X
                    </button>
                  </div>
                  {/* Each Option and Title Corresponding to it */}
                  <div className="flex gap-3 flex-col item-start">
                      <p>Creature</p>
                      <select placeholder="Choose Dinosaur" onChange={(e) => setDinoSelected(Number(e.target.value))}>
                        <option selected value={-1} disabled>Choose Dinosaur…</option>
                        {CreatureList.map((creature) => (
                            <option key={creature.creatureId} value={creature?.creatureId}>{creature?.creatureName}</option>
                          ))}

                      </select>
                      <p>Settings</p>
                      <select placeholder="Choose Server" onChange={(e) => setServerSelected(Number(e.target.value))}>
                        <option selected value={-1} disabled>Choose server…</option>
                          { SettingList.map((settings) => (
                            <option key={settings?.settingsId} value={settings?.settingsId}> {settings?.settingsName}</option>
                          ))}
                      </select>
                      <p>Line Nickname</p>
                      <input placeholder="Enter Line Nickname" onChange={(e) => setLineNickname(e.target.value)}></input>
                  </div>
                  {/* Button to Create the Breeding Line */}
                  <button className="mt-auto border" onClick={createBreedingLine}>Create New Breeding Line</button>
              </div>
          </div>
      </>
    )
}