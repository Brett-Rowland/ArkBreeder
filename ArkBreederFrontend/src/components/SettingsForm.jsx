export default function SettingsForm(){


    return(
        <>
            {/* Main Container 3 Rows */}
            <div className="flex flex-col">
                
                {/* Div for Setting Chooser */}
                <div className="flex">

                    <select name="servers">
                        <option selected disabled value="-1">Choose Server</option>

                    </select>

                </div>

                {/* Settings Div */}
                
                <div className="flex flex-row">

                    {/* Holds Server Settings */}
                    <div className="flex flex-col">
                        <h2>Server Settings</h2>

                        <div className="flex flex-row">
                            <h3>Egg Hatch Speed</h3>
                            <input type="number" name="egg" id="egg" step={.1} value={1}/>
                        </div>
                    
                        <div className="flex flex-row">
                            <h3>Mating Interval</h3>
                            <input type="number" name="mating" id="mating" step={.1} value={1}/>
                        </div>
                    
                        <div className="flex flex-row">
                            <h3>Mature Rate</h3>
                            <input type="number" name="mature" id="mature" step={.1} value={1}/>
                        </div>

                        <div className="flex flex-row">
                            <h3>Imprint Stat Scale</h3>
                            <input type="number" name="imprint" id="imprint" step={.1} value={1}/>
                        </div>

                        <div className="flex flex-row">
                            <h3>Single Player</h3>
                            <input type="checkbox" name="single-player" id="single-player" defaultValue={false}/>
                        </div>

                    </div>

                    {/* Holds Stat Settings */}
                    <div>
                        <h1>Stat Settings</h1>
                        
                        <table>
                            <thead>
                                <tr>
                                    <th></th>
                                    <th>Wild Scale</th>
                                    <th>Stat Additive</th>
                                    <th>Stat Affinity</th>
                                </tr>
                            </thead>
                            <tbody>
                                {/* Health */}
                                <tr>
                                    <th>Health</th>
                                    <td><input type="number" name="healthScale" value={1} step={.01}/></td>
                                    <td><input type="number" name="healthAdditive" value={.14} step={.01}/></td>
                                    <td><input type="number" name="healthAffinity" value={.44} step={.01}/></td>
                                </tr>

                                {/* Stamina */}
                                <tr>
                                    <th>Stamina</th>
                                    <td><input type="number" name="staminaScale" value={1} step={.01}/></td>
                                    <td><input type="number" name="staminaAdditive" value={1} step={.01}/></td>
                                    <td><input type="number" name="staminaAffinity" value={1} step={.01}/></td>
                                </tr>

                                {/* Oxygen*/}
                                <tr>
                                    <th>Oxygen</th>
                                    <td><input type="number" name="oxygenScale" value={1} step={.01}/></td>
                                    <td><input type="number" name="oxygenAdditive" value={1} step={.01}/></td>
                                    <td><input type="number" name="oxygenAffinity" value={1} step={.01}/></td>
                                </tr>

                                {/* Food */}
                                <tr>
                                    <th>Food</th>
                                    <td><input type="number" name="FoodScale" value={1} step={.01}/></td>
                                    <td><input type="number" name="FoodAdditive" value={1} step={.01}/></td>
                                    <td><input type="number" name="FoodAffinity" value={1} step={.01}/></td>
                                </tr>

                                {/* Weight */}
                                <tr>
                                    <th>Weight</th>
                                    <td><input type="number" name="WeightScale" value={1} step={.01}/></td>
                                    <td><input type="number" name="WeightAdditive" value={1} step={.01}/></td>
                                    <td><input type="number" name="WeightAffinity" value={1} step={.01}/></td>
                                </tr>

                                {/* Melee */}
                                <tr>
                                    <th>Melee</th>
                                    <td><input type="number" name="meleeScale" value={1} step={.01}/></td>
                                    <td><input type="number" name="meleeAdditive" value={.14} step={.01}/></td>
                                    <td><input type="number" name="meleeAffinity" value={.44} step={.01}/></td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                </div>

                {/* Buttons Div */}
                <div className="flex flex-row">
                    <div><button>Create Server</button></div>
                    <div><button>Reset</button></div>
                </div>

            </div>
        </>
    )


}